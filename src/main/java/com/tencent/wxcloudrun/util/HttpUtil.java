//package com.tencent.wxcloudrun.util;
//
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.*;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.ConnectionKeepAliveStrategy;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.message.BasicHeaderElementIterator;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.protocol.HttpContext;
//import org.apache.http.util.EntityUtils;
//import org.springframework.util.CollectionUtils;
//
//import java.io.IOException;
//import java.net.URI;
//import java.nio.charset.Charset;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @description: 有http连接池管理的http工具类，连接池核心参数：最大连接数（200），相同路由下最大链接（40），连接超时时间（2000ms），请求超时时间（2000ms）
// * ，响应超时时间（2000ms），参考：https://www.baeldung.com/httpclient-connection-management
// * @author: Muyun
// * @create: 2018-09-28 18:19
// **/
//@Slf4j
//public class HttpUtil {
//
//    /**
//     * 全局连接池对象
//     */
//    private static final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
//
//    /**
//     * 超时时间
//     */
//    private static final Integer TIME_OUT = 2000;
//
//    /**
//     * 静态代码块配置连接池信息
//     */
//    static {
//
//        // 设置最大连接数
//        connManager.setMaxTotal(200);
//        // 设置每个连接的路由数
//        connManager.setDefaultMaxPerRoute(40);
//
//    }
//
//    /**
//     * 获取Http客户端连接对象
//     *
//     * @param timeOut 超时时间
//     * @return Http客户端连接对象
//     */
//    public static CloseableHttpClient getHttpClient(Integer timeOut) {
//        // 创建Http请求配置参数
//        RequestConfig requestConfig = RequestConfig.custom()
//                // 获取连接超时时间
//                .setConnectionRequestTimeout(timeOut)
//                // 请求超时时间
//                .setConnectTimeout(timeOut)
//                // 响应超时时间
//                .setSocketTimeout(timeOut)
//                .build();
//
//        // keepAlive策略，复用连接，默认采用header声明的策略，如果没有，默认设置为5s
//        ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {
//            @Override
//            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
//                HeaderElementIterator it = new BasicHeaderElementIterator
//                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
//                while (it.hasNext()) {
//                    HeaderElement he = it.nextElement();
//                    String param = he.getName();
//                    String value = he.getValue();
//                    if (value != null && param.equalsIgnoreCase
//                            ("timeout")) {
//                        return Long.parseLong(value) * 1000;
//                    }
//                }
//                return 5 * 1000;
//            }
//        };
//
//        // 创建httpClient
//        return HttpClients.custom()
//                // 把请求相关的超时信息设置到连接客户端
//                .setDefaultRequestConfig(requestConfig)
//                // 设置保活策略
//                .setKeepAliveStrategy(keepAliveStrategy)
//                // 配置连接池管理对象
//                .setConnectionManager(connManager)
//                .build();
//    }
//
//    public static String httpGet(String uri, int timeout) throws Exception {
//        return httpGet(new HttpGet(uri), timeout);
//    }
//
//    public static String httpGet(URI uri, int timeout) throws Exception {
//        return httpGet(new HttpGet(uri), timeout);
//    }
//
//    public static String httpGet(String uri) throws Exception {
//        return httpGet(new HttpGet(uri));
//    }
//
//    public static String httpGet(URI uri) throws Exception {
//        return httpGet(new HttpGet(uri));
//    }
//
//    public static String httpGet(URI uri, Map<String, String> cookies, boolean printLog) throws Exception {
//        HttpGet httpGet = new HttpGet(uri);
//        if (!CollectionUtils.isEmpty(cookies)) {
//            for (String key : cookies.keySet()) {
//                httpGet.addHeader(new BasicHeader("Cookie", "cookie2=" + cookies.get(key)));
//            }
//        }
//        if (printLog) {
//            log.warn("HttpRequestUtils_buildUrl,httpGet={}", JSON.toJSONString(httpGet));
//        }
//        return httpGet(httpGet);
//    }
//
//    /**
//     * Get请求
//     *
//     * @param httpGet
//     * @return http请求结果内容字符串
//     * @throws Exception 系统异常
//     */
//    public static String httpGet(HttpGet httpGet) throws Exception {
//
//        // 获取客户端连接对象
//        CloseableHttpClient httpClient = getHttpClient(TIME_OUT);
//
//        return httpGet(httpGet, httpClient);
//    }
//
//    /**
//     * Get请求
//     *
//     * @param httpGet
//     * @param timeout
//     * @return http请求结果内容字符串
//     * @throws Exception 系统异常
//     */
//    public static String httpGet(HttpGet httpGet, int timeout) throws Exception {
//
//        // 获取客户端连接对象
//        CloseableHttpClient httpClient = getHttpClient(timeout);
//
//        return httpGet(httpGet, httpClient);
//    }
//
//    /**
//     * Get请求
//     *
//     * @param httpGet
//     * @param httpClient
//     * @return http请求结果内容字符串
//     * @throws Exception 系统异常
//     */
//    public static String httpGet(HttpGet httpGet, CloseableHttpClient httpClient) throws Exception {
//
//        CloseableHttpResponse response = null;
//
//        try {
//            // 执行请求
//            response = httpClient.execute(httpGet);
//
//            // 获取响应实体
//            HttpEntity entity = response.getEntity();
//
//            // 获取响应信息
//            String entityContent = EntityUtils.toString(entity, "UTF-8");
//
//            EntityUtils.consume(response.getEntity());
//
//            return entityContent;
//        } catch (ClientProtocolException e) {
//            LogMonitor.createSysExMonitor().fluentPut("uri", httpGet.getURI().toString()).fluentPut("errorMsg", "协议错误").log(e);
//            throw e;
//        } catch (ParseException e) {
//            LogMonitor.createSysExMonitor().fluentPut("uri", httpGet.getURI().toString()).fluentPut("errorMsg", "解析错误").log(e);
//            throw e;
//        } catch (IOException e) {
//            LogMonitor.createSysExMonitor().fluentPut("uri", httpGet.getURI().toString()).fluentPut("errorMsg", "IO错误").log(e);
//            throw e;
//        } finally {
//            if (null != response) {
//                response.close();
//            }
//
//        }
//    }
//
//    public static HttpPost httpPost(URI uri, String content) {
//        HttpPost httpPost = new HttpPost(uri);
//
//        StringEntity entity = new StringEntity(content, Charset.forName("UTF-8"));
//        httpPost.setEntity(entity);
//
//        return httpPost;
//    }
//
//    public static HttpPost httpPost(String uri, String content) throws Exception {
//        HttpPost httpPost = new HttpPost(uri);
//
//        StringEntity entity = new StringEntity(content, Charset.forName("UTF-8"));
//        httpPost.setEntity(entity);
//
//        return httpPost;
//    }
//
//    /**
//     * Post请求
//     *
//     * @param httpPost
//     * @return http请求结果内容字符串
//     * @throws Exception 系统异常
//     */
//    public static String httpPost(HttpPost httpPost) throws Exception {
//
//        // 创建httpClient
//        CloseableHttpClient httpClient = getHttpClient(TIME_OUT);
//
//        // 发起请求
//        CloseableHttpResponse response = null;
//        try {
//            response = httpClient.execute(httpPost);
//            // 检查http请求状态
//            StatusLine statusLine = response.getStatusLine();
//            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
//                // 记录日志
//                LogMonitor.createSysExMonitor().fluentPut("url", httpPost.getURI().toString()).fluentPut("content",
//                                httpPost.getEntity().getContent()).fluentPut("statusCode", statusLine.getStatusCode())
//                        .fluentPut("reason", statusLine.getReasonPhrase()).log();
//                return null;
//            }
//
//            // 提取http返回数据
//            HttpEntity httpEntity = response.getEntity();
//            String entityContent = EntityUtils.toString(httpEntity);
//
//            EntityUtils.consume(response.getEntity());
//
//            return entityContent;
//        } catch (Exception e) {
//            LogMonitor.createSysExMonitor().fluentPut("url", httpPost.getURI().toString()).log(e);
//            throw e;
//        } finally {
//            if (null != response) {
//                response.close();
//            }
//        }
//    }
//
//}
