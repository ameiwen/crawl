package com.etoak.crawl.page;

import com.etoak.crawl.httpclient.RestApi;
import com.etoak.crawl.httpclient.RestExecutor;
import com.etoak.crawl.util.UrlUtil;
import org.apache.commons.httpclient.HttpStatus;


public class RequestAndResponseTool {


    public static Page sendRequstAndGetResponse(String url) {
        Page page = null;
        try {
            String port = UrlUtil.buildPort(url);
            String path = UrlUtil.buildPath(url);
            RestApi restApi = RestExecutor.getHttpExecutor();
            page = restApi.doGet(port,path);
            // 判断访问的状态码
            if (page.getStatusCode() != HttpStatus.SC_OK) {
                System.err.println("Method failed ！");
            }
            page.setUrl(url);
        } catch (Exception e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            return null;
        }
        return page;
    }
}
