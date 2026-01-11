package cooc.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.microsoft.playwright.Response;

import java.nio.charset.StandardCharsets;
import java.util.Objects;


public class ApiCheckTools {

    public static boolean apiResponseCheck(Response apiResponse){
        return apiBaseCheck(apiResponse);
    }

    private static boolean apiBaseCheck(Response apiResponse){
        final boolean ok = apiResponse.ok();
        JSONObject var = transformResponseBody(apiResponse);
        boolean status = var.getIntValue(ApiResponseModel.STATUS) == ApiResponseModel.SUCCESS_CODE;
        boolean msg = Objects.equals(var.getString(ApiResponseModel.MSG), ApiResponseModel.SUCCESS_MSG);
        return ok && status && msg;
    }


    public static JSONObject transformResponseBody(Response apiResponse){
        final byte[] body = apiResponse.body();
        final String var = new String(body, StandardCharsets.UTF_8);
        return JSON.parseObject(var);
    }

    public static final class ApiResponseModel{
        public static final String STATUS = "status";
        public static final String MSG = "msg";
        public static final String CODE = "code";
        public static final String RETRY = "retry";
        public static final String DATA = "data";
        public static final int SUCCESS_CODE = 200;
        public static final String SUCCESS_MSG = "success";
    }
}
