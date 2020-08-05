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

package org.unigrid.antdekn;

import java.io.File;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.ProcessDestroyer;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

@ArquillianSuiteDeployment
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestArchive
{
	private static final String[] CRYPTOCURRENCY_DAEMONS = {
		"neutron/neutrond-v4.1.1-linux-x86_64.AppImage"
	};

	private static final String DATA_DIR_ARGUMENT = "-datadir=%s/datadir";

	private static void runCryptocurrencyDaemon(String daemon) throws IOException {
		final ClassLoader c = TestArchive.class.getClassLoader();
		final File file = new File(c.getResource("daemons/" + daemon).getFile());
		final CommandLine commandLine = new CommandLine(file);

		final DefaultExecuteResultHandler result = new DefaultExecuteResultHandler();
		final Executor executor = new DefaultExecutor();
		final ProcessDestroyer destroyer = new ShutdownHookProcessDestroyer();

		commandLine.addArgument(String.format(DATA_DIR_ARGUMENT, file.getParent()));
		file.setExecutable(true);
		executor.setExitValue(0);
		executor.setProcessDestroyer(destroyer);
		executor.execute(commandLine, result);
	}

	@Deployment
	public static Archive<?> deploy() throws IOException {
		final File[] files = Maven.resolver().loadPomFromFile("pom.xml").
			importRuntimeDependencies().resolve().withTransitivity().asFile();

		for (String daemon : CRYPTOCURRENCY_DAEMONS) {
			runCryptocurrencyDaemon(daemon);
		}

		return ShrinkWrap.create(WebArchive.class).
			addClass(FakerProducer.class).
			addAsLibraries(files);
	}
}
