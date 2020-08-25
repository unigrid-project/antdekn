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

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.http.auth.AuthenticationException;
import org.junit.Before;
import org.unigrid.antdekm.wallet.NetService;
import org.unigrid.antdekm.wallet.RpcDetailsService;
import org.unigrid.antdekm.wallet.model.Response;
import org.unigrid.antdekm.wallet.model.RpcDetails;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseTest
{
	private static final int DAEMON_RETRY = 1;

	@EJB
	protected NetService netService;

	@EJB
	protected RpcDetailsService rpcDetailsService;

	private void waitForDaemonToBeRunning(Daemon daemon) throws AuthenticationException, InterruptedException {
		Response<NetService.Service> response;

		do {
			response = netService.call(daemon.getRpcDetails());
			response.getEntity().getConnectionCount();
			TimeUnit.SECONDS.sleep(DAEMON_RETRY);
		} while (response.getStatusCode().intValue() != 200);
	}

	@Before
	public void before() throws AuthenticationException, InterruptedException {
		for (Daemon daemon : TestArchive.DAEMONS) {
			final List<RpcDetails> rpcDetails = rpcDetailsService.get(daemon.getName());

			if (rpcDetails.equals(RpcDetailsService.EMPTY)) {
				rpcDetailsService.add(daemon.getName(), daemon.getRpcDetails());
			}

			waitForDaemonToBeRunning(daemon);
		}
	}
}
