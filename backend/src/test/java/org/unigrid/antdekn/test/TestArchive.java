/*
  Antdekn Explorer - Jakarta EE based blockchain explorer
  Copyright (C) 2020 The Unigrid Organization

  This program is free software: you can redistribute it and/or modify it under the
  terms of the GNU Affero General Public License as published by the Free Software
  Foundation, either version 3 of the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY
  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
  PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.

  You should have received a copy of the GNU Affero General Public License along
  with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.unigrid.antdekn.test;

import org.unigrid.antdekn.test.FakerProducer;
import org.unigrid.antdekn.test.Daemon;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.ProcessDestroyer;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.unigrid.antdekm.DaemonPollingService;
import org.unigrid.antdekm.storage.Database;
import org.unigrid.antdekm.wallet.BlockService;
import org.unigrid.antdekm.wallet.InfoService;
import org.unigrid.antdekm.wallet.model.RpcDetails;

@ArquillianSuiteDeployment
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestArchive
{
	public static final List<Daemon> DAEMONS = Arrays.asList(
		new Daemon("neutron", "neutron/neutrond-v4.1.1-linux-x86_64.AppImage", "neutron.conf", new RpcDetails(32000))
	);

	private static final String CONFIG_RPCUSER = "rpcuser=";
	private static final String CONFIG_RPCPASSWORD = "rpcpassword=";
	private static final String DATA_DIR_ARGUMENT = "-datadir=%s/datadir";

	private static void collectLoginDetails(Daemon daemon, String workingDirectory) throws IOException {
		final String configPath = String.format("%s/datadir/%s", workingDirectory, daemon.getConfigPath());

		/* Read data from file and populate user/password */
		for (String setting : FileUtils.readLines(new File(configPath), StandardCharsets.UTF_8.name())) {
			if (setting.contains(CONFIG_RPCUSER)) {
				daemon.getRpcDetails().setUserName(setting.replace(CONFIG_RPCUSER, StringUtils.EMPTY));
			} else if (setting.contains(CONFIG_RPCPASSWORD)) {
				daemon.getRpcDetails().setPassword(setting.replace(CONFIG_RPCPASSWORD, StringUtils.EMPTY));
			}
		}
	}

	private static void runCryptocurrencyDaemon(File executable) throws IOException {
		final CommandLine commandLine = new CommandLine(executable);

		final DefaultExecuteResultHandler result = new DefaultExecuteResultHandler();
		final Executor executor = new DefaultExecutor();
		final ProcessDestroyer destroyer = new ShutdownHookProcessDestroyer();

		commandLine.addArgument(String.format(DATA_DIR_ARGUMENT, executable.getParent()));
		executable.setExecutable(true);
		executor.setExitValue(0);
		executor.setProcessDestroyer(destroyer);
		executor.execute(commandLine, result);
	}

	@Deployment
	public static Archive<?> deploy() throws IOException {
		final File[] files = Maven.resolver().loadPomFromFile("pom.xml").
			importRuntimeDependencies().resolve().withTransitivity().asFile();

		for (Daemon daemon : DAEMONS) {
			final ClassLoader c = TestArchive.class.getClassLoader();
			final File exe = new File(c.getResource("daemons/" + daemon.getExecutable()).getFile());

			daemon.getRpcDetails().setIpAddress(InetAddress.getLoopbackAddress());
			collectLoginDetails(daemon, exe.getParent());
			runCryptocurrencyDaemon(exe);
		}

		return ShrinkWrap.create(WebArchive.class)
			.addClass(DaemonPollingService.class)
			.addClass(Database.class)
			.addClass(FakerProducer.class)
			.addClass(BlockService.class)
			.addClass(InfoService.class)
			.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
			.addAsLibraries(files);
	}
}
