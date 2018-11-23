package com.etoak.crawl.util;

public class UrlUtil {

    public static String buildPort(String url){

        int index = url.indexOf("/");
        if(index>1){
            return url.substring(0,index);
        }
        return url;
    }

    public static String buildPath(String url){
        int index = url.indexOf("/");
        if(index > 1){
            return url.substring(index,url.length());
        }
        return null;
    }

}
