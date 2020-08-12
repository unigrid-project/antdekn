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
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.codec.digest.MurmurHash3;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;

@Stateless
public class AbstractDatabaseService<T> implements Serializable
{
	@EJB
	protected Database database;

	public T get(String key, Class<T> clazz) throws HaloDBException {
		final long[] hash = MurmurHash3.hash128(key.getBytes(StandardCharsets.UTF_8));
		final ByteBuffer hashKey = ByteBuffer.allocate(hash.length * Long.BYTES);

		hashKey.putLong(hash[0]);
		hashKey.putLong(hash[1]);

		final byte[] value = database.getDb().get(hashKey.array());
		T addressRecord = null;

		if (value.length > 0) {
			addressRecord = SerializationUtils.deserialize(value);
		} else {
			try {
				addressRecord = ConstructorUtils.invokeConstructor(clazz, hashKey.array());
			} catch (IllegalAccessException | InstantiationException
				| InvocationTargetException | NoSuchMethodException ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			}
		}

		return addressRecord;
	}
}
