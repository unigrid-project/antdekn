/*
  Antdekn Explorer - Jakarta EE based blockchain explorer
  Copyright (C) 2020 The Unigrid Organization

  This program is free software: you can redistribute it and/or modify it under the
  terms of the GNU Affero General Public License as published by the Free Software
  Foundation, either version 3 of the License, or (at your option) any later
  version. This program also amends the license with additional terms as outlined
  in the addendum of the license terms.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY
  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
  PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

  The full license terms of this program are outlined in the files COPYING and
  COPYING.addendum, found in the root directory of this project repository as
  seen on <https://github.com/unigrid-project/antdekn>. Updated contact information
  can also be found on this project page. If you did not received a copy of the
  GNU Affero General Public License along with this program, please see
  <https://www.gnu.org/licenses/>.
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
	public static final List<RpcDetails> EMPTY = new ArrayList<>();
	private final Map<String, List<RpcDetails>> entries = new HashMap<>();

	public void add(String daemonName, RpcDetails rpcDetails) {
		List<RpcDetails> daemons = entries.get(daemonName);

		if (daemons == null) {
			daemons = new ArrayList<>();
		}

		daemons.add(rpcDetails);
		entries.put(daemonName, daemons);
	}

	/* Return copies to preserve thread safety */

	public List<RpcDetails> get(String daemonName) {
		if (entries.containsKey(daemonName)) {
			return new ArrayList<>(entries.get(daemonName));
		}

		return EMPTY;
	}

	public Map<String, List<RpcDetails>> get() {
		return new HashMap<>(entries);
	}
}
