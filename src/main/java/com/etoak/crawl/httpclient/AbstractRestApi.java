package com.etoak.crawl.httpclient;

import com.alibaba.fastjson.JSON;
import com.etoak.crawl.page.Page;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by baolong.wang on 2017/8/7.
 */
abstract class AbstractRestApi implements RestApi {
    protected String scheme;

    AbstractRestApi() {
    }

    public HttpUriRequest buildGetRequest(String host, int port, String path, Map<String, String> params) throws Exception {
        URIBuilder uriBuilder = this.buildUri(host, port, path);
        if(params != null && params.size() > 0) {
            Set uri = params.entrySet();
            Iterator httpGet = uri.iterator();

            while(httpGet.hasNext()) {
                Map.Entry entry = (Map.Entry)httpGet.next();
                uriBuilder.setParameter((String)entry.getKey(), (String)entry.getValue());
            }
        }

        URI uri1 = uriBuilder.build();
        HttpGet httpGet1 = new HttpGet(uri1);
        httpGet1.setConfig(Client.getRequestConfig());
        return httpGet1;
    }

    public HttpUriRequest buildPostRequest(String host, int port, String path, Map<String, String> params) throws Exception {
        URIBuilder uriBuilder = this.buildUri(host, port, path);
        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setConfig(Client.getRequestConfig());
        this.setPostAndPutFormEntity(httpPost, params);
        return httpPost;
    }


    private URIBuilder buildUri(String host, int port, String path) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(this.getScheme());
        builder.setHost(host);
        builder.setPath(path);
        if(port > 0) {
            builder.setPort(port);
        }

        return builder;
    }

    public Page doGet(String host, String path) {
        return this.doGet(host, 0, path, (Map)null);
    }

    public Page doGet(String host, String path, Map<String, String> params) {
        return this.doGet(host, 0, path, params);
    }

    public Page doGet(String host, int port, String path) {
        return this.doGet(host, port, path, (Map)null);
    }

    public Page doGet(String host, int port, String path, Map<String, String> params) {
        Page page = new Page();
        try {
            page = this.execute(page, this.buildGetRequest(host, port, path, params));
        } catch (Exception var7) {
        }

        return page;
    }

    public Page doPost(String host, String path) {
        return this.doPost(host, path, (Map)null);
    }

    public Page doPost(String host, String path, Map<String, String> params) {
        return this.doPost(host, 0, path, params);
    }

    public Page doPost(String host, int port, String path) {
        return this.doPost(host, port, path, (Map)null);
    }

    public Page doPost(String host, int port, String path, Map<String, String> params) {
        Page page = new Page();

        try {
            page = this.execute(page, this.buildPostRequest(host, port, path, params));
        } catch (Exception var7) {
        }

        return page;
    }


    public void destroy() {
        try {
            Client.getRestClient().close();
        } catch (Exception var2) {
        }

    }

    public Page execute(Page page, HttpUriRequest request) {
        CloseableHttpResponse closeableHttpResponse = null;
        BufferedInputStream bis = null;
        CloseableHttpClient client = Client.getRestClient();

        try {
            closeableHttpResponse = client.execute(request);
            HttpEntity e = closeableHttpResponse.getEntity();
            this.convertResponse(closeableHttpResponse, e, page);
            InputStream inputStream = e.getContent();
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] content = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(content, 0, 100)) > 0) {
                swapStream.write(content, 0, rc);
            }
            page.setContent(swapStream.toByteArray());
        } catch (Exception var12) {
        } finally {
            this.closeResponse(closeableHttpResponse, bis);
        }
        return page;
    }

    public Page execute(HttpUriRequest request) {
        Page page = new Page();
        CloseableHttpResponse closeableHttpResponse = null;
        BufferedInputStream bis = null;
        CloseableHttpClient client = Client.getRestClient();

        try {
            closeableHttpResponse = client.execute(request);
            HttpEntity e = closeableHttpResponse.getEntity();
            this.convertResponse(closeableHttpResponse, e, page);
            InputStream inputStream = e.getContent();
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] content = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(content, 0, 100)) > 0) {
                swapStream.write(content, 0, rc);
            }
            page.setContent(swapStream.toByteArray());
        } catch (Exception var12) {
        } finally {
            this.closeResponse(closeableHttpResponse, bis);
        }

        return page;
    }

    private void convertResponse(CloseableHttpResponse closeableHttpResponse, HttpEntity entity, Page page) {
        page.setStatusCode(closeableHttpResponse.getStatusLine().getStatusCode());
        page.setContentType(entity.getContentType().getValue());
        page.setHeaders(closeableHttpResponse.getAllHeaders());
    }

    private void setPostAndPutFormEntity(HttpEntityEnclosingRequestBase httpPostAndPut, Map<String, String> params) throws UnsupportedEncodingException {
        if(params != null && params.size() > 0) {
            Set set = params.entrySet();
            ArrayList nvps = new ArrayList();
            Iterator i$ = set.iterator();

            while(i$.hasNext()) {
                Map.Entry entry = (Map.Entry)i$.next();
                nvps.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
            }

            httpPostAndPut.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        }

    }

    private void closeResponse(CloseableHttpResponse closeableHttpResponse, BufferedInputStream bis) {
        if(bis != null) {
            try {
                bis.close();
            } catch (Exception var5) {
            }
        }

        if(closeableHttpResponse != null) {
            try {
                closeableHttpResponse.close();
            } catch (Exception var4) {
            }
        }

    }

    protected String getScheme() {
        return this.scheme;
    }

    protected void setScheme(String scheme) {
        this.scheme = scheme;
    }
}
