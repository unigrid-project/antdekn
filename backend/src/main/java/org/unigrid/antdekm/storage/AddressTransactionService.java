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

package org.unigrid.antdekm.storage;

import com.oath.halodb.HaloDBException;
import org.apache.commons.lang3.SerializationUtils;
import org.unigrid.antdekm.storage.model.Address;
import org.unigrid.antdekm.storage.model.Transaction;

/* @Stateless */
public class AddressTransactionService extends AbstractDatabaseService<Address>
{
	@Override
	protected String getClassName() {
		return AddressTransactionService.class.getSimpleName();
	}

	public void merge(String key, Transaction transaction) throws HaloDBException {
		final Address address = get(key, Address.class);

		address.addTransaction(transaction);
		database.getDb().put(getHash(address.getChainAddress()), SerializationUtils.serialize(address));
	}
}
