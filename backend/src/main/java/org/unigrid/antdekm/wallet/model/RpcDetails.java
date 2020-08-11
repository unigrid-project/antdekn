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

package org.unigrid.antdekm.wallet.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import lombok.Data;

@Singleton
public class RpcDetails
{
	private final Map<String, List<Entry>> entries = new HashMap<>();

	@Data
	public static class Entry implements Serializable
	{
		private String userName;
		private String password;
		private InetAddress ipAddress;
		private final int port;
	}

	public void add(String daemon, Entry entry) {
		List<Entry> daemons = entries.get(daemon);

		if (daemons == null) {
			daemons = new ArrayList<>();
		}

		daemons.add(entry);
	}

	public List<Entry> get(String daemon) {
		return new ArrayList<>(entries.get(daemon));
	}
}
