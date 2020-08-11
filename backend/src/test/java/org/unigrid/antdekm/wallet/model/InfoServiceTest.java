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
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unigrid.antdekm.wallet.InfoService;
import org.unigrid.antdekn.test.Daemon;
import org.unigrid.antdekn.test.TestArchive;

@RunWith(Arquillian.class)
public class InfoServiceTest
{
	@EJB
	private InfoService infoService;

	@Test
	public void canFetch() throws AuthenticationException, InterruptedException {
		// TODO: Support other daemons than neutron
		final Daemon daemon = TestArchive.DAEMONS.get(0);
		assertNotNull(infoService.call(daemon.getRpcDetails()).getInfo());
	}
}
