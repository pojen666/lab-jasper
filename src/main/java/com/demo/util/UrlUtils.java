package com.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 網址工具
 */
@Slf4j
public class UrlUtils {

    private UrlUtils() {
    }

    /**
     * URL Encode處理
     *
     * @param value 要編碼的字串
     * @return 編碼後的字串
     */
    public static String encodeURIComponent(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("\\+", "%20");
    }

    /**
     * 取得檔案下載的Header
     *
     * @param filename 檔名
     * @return Header
     */
    public static String getFileDownloadHeader(String filename) {
        var headerValue = "attachment;";
        headerValue += " filename=\"" + encodeURIComponent(filename) + "\";";
        headerValue += " filename*=utf-8''" + encodeURIComponent(filename);
        return headerValue;
    }

    /**
     * 取得檔案內嵌的Header
     *
     * @param filename 檔名
     * @return Header
     */
    public static String getFileEmbedHeader(String filename) {
        var headerValue = "inline;";
        headerValue += " filename=\"" + encodeURIComponent(filename) + "\";";
        headerValue += " filename*=utf-8''" + encodeURIComponent(filename);
        return headerValue;
    }
}
