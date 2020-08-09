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

package org.unigrid.antdekm.wallet.model.block;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;
import org.unigrid.antdekm.wallet.model.json.UnixTimestampDeserializer;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseBlock implements Serializable
{
	private long confirmations;
	private BigDecimal difficulty;
	private String flags;
	private String hash;
	private long height;
	private BigDecimal mint;

	@JsonProperty("nextblockhash") private String nextBlockHash;
	@JsonProperty("previousblockhash") private String previousBlockHash;
	@JsonDeserialize(using = UnixTimestampDeserializer.class) private Instant time;
}
