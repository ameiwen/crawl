package com.etoak.crawl.httpclient;


import com.etoak.crawl.util.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;


public class Client {
    private static CloseableHttpClient httpClient = null;
    private static int maxTotal = 400;
    private static int defaultMaxPerRoute = 200;
    private static int connectTimeout = 3000;
    private static int socketTimeout = 3000;
    private static int keepAlive = 5000;
    private static RequestConfig requestConfig = null;
    private static SocketConfig socketConfig = null;

    public Client() {
    }

    public static CloseableHttpClient getRestClient() {
        return httpClient;
    }

    public static RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public static SocketConfig getSocketConfig() {
        return socketConfig;
    }

    private static void resolvePropertyConfig() {
        String configFileName = "restClientConfig.properties";
        String conMaxTotal = PropertyReader.get("connection.maxTotal", configFileName);
        String conDefaultMaxPerRoute = PropertyReader.get("connection.defaultMaxPerRoute", configFileName);
        String conTimeout = PropertyReader.get("connection.timeout", configFileName);
        String soTimeout = PropertyReader.get("connection.socket.timeout", configFileName);
        String conKeepAlive = PropertyReader.get("connection.keepAlive", configFileName);
        if(StringUtils.isNotBlank(conMaxTotal) && !conMaxTotal.contains("$")) {
            try {
                maxTotal = Integer.valueOf(conMaxTotal).intValue();
            } catch (Exception var11) {
                var11.printStackTrace();
            }
        }

        if(StringUtils.isNotBlank(conDefaultMaxPerRoute) && !conDefaultMaxPerRoute.contains("$")) {
            try {
                defaultMaxPerRoute = Integer.valueOf(conDefaultMaxPerRoute).intValue();
            } catch (Exception var10) {
            }
        }

        if(StringUtils.isNotBlank(conTimeout) && !conTimeout.contains("$")) {
            try {
                connectTimeout = Integer.valueOf(conTimeout).intValue();
            } catch (Exception var9) {
                var9.printStackTrace();
            }
        }

        if(StringUtils.isNotBlank(soTimeout) && !soTimeout.contains("$")) {
            try {
                socketTimeout = Integer.valueOf(soTimeout).intValue();
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }

        if(StringUtils.isNotBlank(conKeepAlive) && !conKeepAlive.contains("$")) {
            try {
                keepAlive = Integer.valueOf(conKeepAlive).intValue();
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }

    }

    static {
        resolvePropertyConfig();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
        requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
        socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).setTcpNoDelay(true).build();
        httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).setDefaultSocketConfig(socketConfig).setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long ka = super.getKeepAliveDuration(response, context);
                if(ka == -1L) {
                    ka = (long)Client.keepAlive;
                }

                return ka;
            }
        }).build();
    }
}
