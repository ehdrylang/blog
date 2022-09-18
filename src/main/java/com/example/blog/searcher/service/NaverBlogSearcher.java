package com.example.blog.searcher.service;

import com.example.blog.common.exception.BusinessException;
import com.example.blog.common.exception.ErrorCode;
import com.example.blog.searcher.domain.Sort;
import com.example.blog.searcher.model.BlogResponse;
import com.example.blog.searcher.model.BlogSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(value = 2)
@Slf4j
public class NaverBlogSearcher implements BlogSearcher{

    @Value("${search.blog.naver.url}")
    private String apiUrl;
    @Value("${search.blog.naver.clientId}")
    private String clientId;
    @Value("${search.blog.naver.clientSecret}")
    private String clientSecret;

    @Override
    @Async
    public BlogResponse search(BlogSearchRequest model) {
        String apiURL = createApiUrl(model);

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);

        System.out.println(responseBody);
        return null;
    }

    private String createApiUrl(BlogSearchRequest model) {
        String query;
        try {
            query = URLEncoder.encode(model.getKeyword(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_ENCODING_EXCEPTION);
        }
        String display = model.getSize().toString();
        String start = model.getPage().toString();
        String sort = parse(model.getSort());
        String apiURL = apiUrl +
                "?query=" + query
                + "&display=" + display
                + "&start=" + start
                + "&sort=" + sort;
        return apiURL;
    }

    private String parse(Sort sort) {
        if (Sort.RECENCY.equals(sort)) {
            return "date";
        }
        if (Sort.ACCURACY.equals(sort)) {
            return "sim";
        }
        return "date";
    }

    private String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }
    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }
    private String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);
        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}
