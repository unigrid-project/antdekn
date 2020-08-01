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

package org.unigrid.antdekm.storage.model;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.CompareToBuilder;

@Data
public class Transaction implements Comparable<Transaction>, Serializable
{
	/* NOTE: This purposely breaks the equals() contract in order to allow lookups by txId and
           sorting by timestamp and txId as a secondary sort "column". */
	@EqualsAndHashCode.Exclude
	private int timestamp;

	private String txId;

	@Override
	public int compareTo(Transaction t) {
		return new CompareToBuilder()
			.append(timestamp, t.timestamp)
			.append(txId, txId).toComparison();
	}
}
