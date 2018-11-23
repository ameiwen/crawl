package com.etoak.crawl.httpclient;

/**
 * Created by baolong.wang on 2017/8/7.
 */
public class RestExecutor {
    private static RestApi httpProxy = new RestExecutorProxy("http");
    private static RestApi httpsProxy = new RestExecutorProxy("https");

    public RestExecutor() {
    }

    public static RestApi getHttpExecutor() {
        return httpProxy;
    }

    public static RestApi getHttpsExecutor() {
        return httpsProxy;
    }
}
