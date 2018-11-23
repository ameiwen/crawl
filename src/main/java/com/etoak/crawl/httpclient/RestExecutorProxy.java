package com.etoak.crawl.httpclient;


/**
 * Created by baolong.wang on 2017/8/7.
 */
public class RestExecutorProxy extends AbstractRestApi implements RestApi {
    public RestExecutorProxy(String scheme) {
        this.setScheme(scheme);
    }
}
