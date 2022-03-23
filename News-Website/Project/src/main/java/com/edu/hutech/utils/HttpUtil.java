package com.edu.hutech.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpUtil {
    private String value;
    public HttpUtil(String value) {
        this.value = value;
    }
    public <T> T toModel (Class<T> tClass) {
        try {
            return new ObjectMapper().readValue(value, tClass); //value là chuỗi String JSon, tClass là Class Model
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static HttpUtil of(BufferedReader reader) {
        StringBuilder sb =new StringBuilder();
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HttpUtil(sb.toString()); //trả về lại object HttpUtil có sẵn value là chuỗi JSon
    }
}
