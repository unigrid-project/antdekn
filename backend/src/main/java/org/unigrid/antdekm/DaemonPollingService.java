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

package org.unigrid.antdekm;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import lombok.Getter;
import org.unigrid.antdekm.wallet.InfoService;
import org.unigrid.antdekm.wallet.model.Info;

@Startup
@Singleton
public class DaemonPollingService
{
	@Getter
	private Info info;

	@EJB
	private InfoService infoService;

	// @Inject
	// private RpcDetails rpcDetails;

	@PostConstruct
	private void init() {
		// infoService.call(rpcDetails)
	}

	@Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
	private void run() {
	}
}
