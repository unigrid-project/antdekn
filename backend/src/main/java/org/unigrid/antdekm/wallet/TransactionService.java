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

package org.unigrid.antdekm.wallet;

import org.unigrid.antdekm.wallet.model.Transaction;
import com.github.arteam.simplejsonrpc.client.JsonRpcParams;
import com.github.arteam.simplejsonrpc.client.ParamsType;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;
import javax.ejb.Stateless;

@Stateless
public class TransactionService extends AbstractWalletService<TransactionService.Service>
{
	public TransactionService() {
		super(TransactionService.Service.class);
	}

	@JsonRpcService
	@JsonRpcParams(ParamsType.ARRAY)
	public interface Service {
		@JsonRpcMethod("gettransaction")
		Transaction getTransaction(@JsonRpcParam("txid") String txid);
	}
}
