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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction implements Serializable
{
	@JsonProperty("txid") private String txId;
	@JsonProperty("vin") private List<In> vIn;
	@JsonProperty("vout") private List<Out> vOut;

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class In implements Serializable
	{
		private int n;

		@JsonProperty("txid") private String txId;
		@JsonProperty("vout") private int vOut;
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Out implements Serializable
	{
		private int n;
		private ScriptPubKey scriptPubKey;
		private BigDecimal value;

		@Data
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class ScriptPubKey implements Serializable
		{
			private List<String> addresses;
			private String type;
		}
	}
}
