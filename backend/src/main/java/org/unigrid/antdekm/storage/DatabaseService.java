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

import com.oath.halodb.HaloDB;
import com.oath.halodb.HaloDBException;
import com.oath.halodb.HaloDBOptions;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class DatabaseService
{
	private static final int MB = 1024 * 1024;
	private static final int GB = MB * 1024;

	private static final int COMPACTION_RATE = 16 * MB;
	private static final double COMPACTION_THRESHOLD = 0.7;
	private static final String DATA_DIRECTORY = "storage";
	private static final int FLUSH_SIZE = 16 * MB;
	private static final int KEY_SIZE_BYTES = 16; /* 128 bits */
	private static final int MAX_FILE_SIZE = 2 * GB;
	private static final int MAX_TOMBSTONE_SIZE = 64 * MB;
	private static final int POOL_CHUNK_SIZE = 2 * MB;

	private HaloDB db;

	@PostConstruct
	private void init() {
		final HaloDBOptions options = new HaloDBOptions();

		options.setMaxFileSize(MAX_FILE_SIZE);
		options.setMaxTombstoneFileSize(MAX_TOMBSTONE_SIZE);
		options.setFlushDataSizeBytes(FLUSH_SIZE);
		options.setCompactionJobRate(COMPACTION_RATE);
		options.setMemoryPoolChunkSize(POOL_CHUNK_SIZE);
		options.setCompactionThresholdPerFile(COMPACTION_THRESHOLD);
		options.setBuildIndexThreads(Runtime.getRuntime().availableProcessors());
		options.setFixedKeySize(KEY_SIZE_BYTES);

		// TODO: Use the wallet RPC to allocate chainsize * 2
		// options.setNumberOfRecords(...);

		options.setCleanUpTombstonesDuringOpen(true);
		options.setCleanUpInMemoryIndexOnClose(true);
		options.setUseMemoryPool(true);

		try {
			db = HaloDB.open(DATA_DIRECTORY, options);
		} catch (HaloDBException ex) {
			Logger.getLogger(
				getClass().getName()).log(Level.SEVERE,
				"Failed to open antdekn database...", ex
			);
		}
	}
}
