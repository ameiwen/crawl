package com.etoak.crawl.httpclient;

import org.apache.http.Header;

/**
 * Created by baolong.wang on 2017/8/7.
 */
public class RestResponse {
    private String content;
    private long contentLength;
    private int statusCode;
    private boolean success = false;
    private boolean exception = false;
    private Exception exceptionObject;
    private Header contentType;
    private Header contentEncoding;
    private Header[] headers;

    public RestResponse() {
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getContentLength() {
        return this.contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isException() {
        return this.exception;
    }

    public void setException(boolean exception) {
        this.exception = exception;
    }

    public Exception getExceptionObject() {
        return this.exceptionObject;
    }

    public void setExceptionObject(Exception exceptionObject) {
        this.exceptionObject = exceptionObject;
    }

    public Header getContentType() {
        return this.contentType;
    }

    public void setContentType(Header contentType) {
        this.contentType = contentType;
    }

    public Header getContentEncoding() {
        return this.contentEncoding;
    }

    public void setContentEncoding(Header contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public Header[] getHeaders() {
        return this.headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }
}
