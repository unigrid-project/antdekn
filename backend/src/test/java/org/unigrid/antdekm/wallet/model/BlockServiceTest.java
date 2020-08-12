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

import javax.ejb.EJB;
import org.apache.http.auth.AuthenticationException;
import org.jboss.arquillian.junit.Arquillian;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unigrid.antdekm.wallet.BlockService;
import org.unigrid.antdekm.wallet.TransactionService;
import org.unigrid.antdekm.wallet.model.block.Block;
import org.unigrid.antdekm.wallet.model.block.VerboseBlock;
import org.unigrid.antdekn.test.Daemon;
import org.unigrid.antdekn.test.TestArchive;

@RunWith(Arquillian.class)
public class BlockServiceTest
{
	@EJB
	private BlockService blockService;

	@EJB
	private TransactionService transactionService;

	@Test
	public void canFetchBlockByNumber() throws AuthenticationException {
		// TODO: Support other daemons than neutron
		final Daemon daemon = TestArchive.DAEMONS.get(0);
		final Block b = blockService.call(daemon.getRpcDetails()).getBlockByNumber(1333);
		final VerboseBlock vb = blockService.call(daemon.getRpcDetails()).getBlockByNumber(1333, true);

		assertEquals(b.getHash(), vb.getHash());
	}

	@Test
	public void canFetchBlockCount() throws AuthenticationException {
		// TODO: Support other daemons than neutron
		final Daemon daemon = TestArchive.DAEMONS.get(0);
		final int count = blockService.call(daemon.getRpcDetails()).getBlockCount();

		assertTrue(count > 1000);
	}
}