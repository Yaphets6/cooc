package cooc.pageview.apis;

import com.alibaba.fastjson2.JSON;
import com.microsoft.playwright.APIRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestContextOptionsFactory {

    public static final String ACCEPT = "application/json, text/plain, */*";
    public static final String ACCEPT_ENCODING = "gzip, deflate, br";
    public static final String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.9";
    public static final String AUTH_TOKEN = "authToken";
    public static final String REFRESH_TOKEN = "refreshToken";


    public static APIRequest.NewContextOptions create(Map<String,String> headerToken){
        return getDef(headerToken);
    }


    public static APIRequest.NewContextOptions create(){
        return getDef(null);
    }

    private static APIRequest.NewContextOptions getDef(Map<String,String> headerToken){
        APIRequest.NewContextOptions options = new APIRequest.NewContextOptions();
        options.setIgnoreHTTPSErrors(true);
        options.setExtraHTTPHeaders(getDefHeader(headerToken));
        options.setTimeout(15000);
        System.out.println("contextOpt:" + JSON.toJSONString(options));
        return options;
    }

    public static void setCer(){

    }

    private static Map<String,String> getDefHeader(Map<String,String> headerToken){
        Map<String,String> opt = new HashMap<>();
        opt.put("Accept",ACCEPT);
        opt.put("Accept-Encoding",ACCEPT_ENCODING);
        opt.put("Accept-Language",ACCEPT_LANGUAGE);
        if(headerToken!=null){
            opt.putAll(headerToken);
        }
        return opt;
    }
}
