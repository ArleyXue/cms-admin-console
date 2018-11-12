package com.arley.cms.console.util.httputil;

/**
 * @author XueXianlei
 * @Description:
 * @date Created in 2018/2/26 11:40
 */
public class HttpClientFactory {
  

    private static HttpSyncClient httpSyncClient = new HttpSyncClient();


    private HttpClientFactory() {
    }

    private static HttpClientFactory httpClientFactory = new HttpClientFactory();

    public static HttpClientFactory getInstance() {

        return httpClientFactory;

    }

  

    public HttpSyncClient getHttpSyncClientPool() {
        return httpSyncClient;
    }

}
