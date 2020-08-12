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

package org.unigrid.antdekn.test;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.Before;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseTest
{
	protected Faker faker;

	@Before
	public void before() {
		faker = new FakerProducer().getFaker();
	}
}