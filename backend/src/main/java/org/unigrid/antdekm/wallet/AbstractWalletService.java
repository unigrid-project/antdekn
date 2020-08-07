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

import com.github.arteam.simplejsonrpc.client.JsonRpcClient;
import com.github.arteam.simplejsonrpc.client.Transport;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.NotNull;
import org.apache.http.Header;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.unigrid.antdekm.wallet.model.RpcDetails;

public class AbstractWalletService<T>
{
	private final Class<T> serviceType;
	private int cachedRpcDetailsHashCode;
	private CloseableHttpClient httpClient;
	private JsonRpcClient client;

	public AbstractWalletService(Class<T> serviceType) {
		this.serviceType = serviceType;
	}

	public T call(final RpcDetails rpcDetails) throws AuthenticationException {
		if (cachedRpcDetailsHashCode != rpcDetails.hashCode()) {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage());
			}

			client = new JsonRpcClient(new Transport()
			{
				private final CredentialsProvider provider = new BasicCredentialsProvider();

				private final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
					rpcDetails.getUserName(), rpcDetails.getPassword()
				);

				@Override
				public String pass(@NotNull String request) throws IOException {
					try {
						httpClient = HttpClientBuilder.create().build();

						final String url = String.format("http://%s:%d",
							rpcDetails.getIpAddress().getHostAddress(), rpcDetails.getPort()
						);

						final HttpPost post = new HttpPost(url);
						post.setEntity(new StringEntity(request, StandardCharsets.UTF_8));

						final Header header = new BasicScheme(StandardCharsets.UTF_8).authenticate(
							new UsernamePasswordCredentials(rpcDetails.getUserName(),
								rpcDetails.getPassword()
							), post, null
						);

						post.addHeader(header);

						try (CloseableHttpResponse httpResponse = httpClient.execute(post)) {
							String statusCode = httpResponse.getStatusLine().getReasonPhrase();

							return EntityUtils.toString(httpResponse.getEntity(),
								StandardCharsets.UTF_8
							);
						}
					} catch (AuthenticationException ex) {
						Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
					}

					return null;
				}
			});
		}

		return client.onDemand(serviceType);
	}
}
