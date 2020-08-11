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

package org.unigrid.antdekm.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import org.unigrid.antdekm.wallet.model.RpcDetails;

@Singleton
public class RpcDetailsService
{
	private final Map<String, List<RpcDetails>> entries = new HashMap<>();

	public void add(String daemon, RpcDetails rpcDetails) {
		List<RpcDetails> daemons = entries.get(daemon);

		if (daemons == null) {
			daemons = new ArrayList<>();
		}

		daemons.add(rpcDetails);
	}

	public List<RpcDetails> get(String daemon) {
		return new ArrayList<>(entries.get(daemon));
	}
}
