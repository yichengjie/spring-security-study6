package com.yicj.hello.util;

import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class GetRequestJsonUtils {

    public static String getRequestJsonContent(HttpServletRequest request) throws IOException {
        String method = request.getMethod();
        if ("GET".equalsIgnoreCase(method)){
            Charset iso88591 = StandardCharsets.ISO_8859_1;
            Charset utf8 = StandardCharsets.UTF_8;
            String content = new String(request.getQueryString().getBytes(iso88591), utf8);
            return content.replaceAll("%22", "\"") ;
        }
        return getRequestPostContent(request) ;
    }


    private static byte [] getRequestPostBytes(HttpServletRequest request) throws IOException{
        int contentLength = request.getContentLength();
        if (contentLength < 0){
            return null ;
        }
        byte[] buffer = new byte[contentLength] ;
        for (int i = 0 ; i< contentLength ;){
            ServletInputStream in = request.getInputStream();
            int readLength = in.read(buffer,i , contentLength -1) ;
            if (readLength == -1){
                break;
            }
            i += readLength ;
        }
        return buffer ;
    }

    private static String getRequestPostContent(HttpServletRequest request)throws IOException{
        byte [] buffer = getRequestPostBytes(request) ;
        String characterEncoding = request.getCharacterEncoding();
        if (StringUtils.isEmpty(characterEncoding)){
            characterEncoding = "UTF-8" ;
        }
        return new String(buffer,characterEncoding) ;
    }
}
