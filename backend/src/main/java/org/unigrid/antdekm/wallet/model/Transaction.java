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
	@JsonProperty private BigDecimal amount;
	@JsonProperty private String blockhash;
	@JsonProperty private int confirmations;
	@JsonProperty private List<Details> details;
	@JsonProperty private BigDecimal fee;
	@JsonProperty private BigDecimal time;
	@JsonProperty private String txid;

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Details implements Serializable
	{
		@JsonProperty private String address;
		@JsonProperty private BigDecimal amount;
		@JsonProperty private int vout;
		@JsonProperty private BigDecimal fee;
	}
}
