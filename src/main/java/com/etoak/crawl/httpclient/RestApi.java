package com.etoak.crawl.httpclient;

import com.etoak.crawl.page.Page;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.Map;

/**
 * Created by baolong.wang on 2017/8/7.
 */
public interface RestApi {

    Page doGet(String var1, String var2);

    Page doGet(String var1, String var2, Map<String, String> var3);

    Page doGet(String var1, int var2, String var3);

    Page doGet(String var1, int var2, String var3, Map<String, String> var4);

    Page doPost(String var1, String var2);

    Page doPost(String var1, String var2, Map<String, String> var3);

    Page doPost(String var1, int var2, String var3);

    Page doPost(String var1, int var2, String var3, Map<String, String> var4);

    void destroy();

    Page execute(HttpUriRequest var1);

}
