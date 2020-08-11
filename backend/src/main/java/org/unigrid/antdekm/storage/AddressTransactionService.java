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

package org.unigrid.antdekm.storage;

import com.oath.halodb.HaloDBException;
import org.apache.commons.lang3.SerializationUtils;
import org.unigrid.antdekm.storage.model.AddressRecord;
import org.unigrid.antdekm.storage.model.Transaction;

public class AddressTransactionService extends AbstractDatabaseService<AddressRecord>
{
	public void merge(String key, Transaction transaction) throws HaloDBException {
		final AddressRecord addressRecord = get(key, AddressRecord.class);

		addressRecord.addTransaction(transaction);
		database.getDb().put(addressRecord.getHash(), SerializationUtils.serialize(addressRecord));
	}
}
