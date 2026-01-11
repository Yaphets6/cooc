package cooc.pageview.apis;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.microsoft.playwright.options.FilePayload;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;


import java.io.File;
import java.nio.file.Path;


public class RequestOptionsFactory {
    public static final String CONTENT_TYPE_FILE = "multipart/form-data";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
    public static final String CONTENT_TYPE_QUERY_PARAM = "";



    public static RequestOptionsBuilder getBuilder(){
        return new RequestOptionsBuilder();
    }


    public static class RequestOptionsBuilder{
         private final RequestOptions opt;

         private RequestOptionsBuilder(){
             this.opt = RequestOptions.create();
         }

         public RequestOptions setData(JSONObject data,RequestType type){
             switch (type){
                 case FILE:
                     setFileForm(data);
                     break;
                 case FORM:
                     setForm(data);
                     break;
                 case QUERY_PARAM:
                     setQueryParam(data);
                     break;
                 default:
                     setJson(data);
             }
             return opt;
         }

        private void setFileForm(JSONObject data){
            FormData formData = FormData.create();
            data.forEach((k,v)->{
                formData.set(k,new File(v.toString()).toPath());
            });
            opt.setMultipart(formData);
            opt.setHeader("Content-Type",CONTENT_TYPE_FILE);
        }

        private void setForm(JSONObject data){
            FormData formData = FormData.create();
            data.forEach((k,v)->{
                if(v instanceof Integer){
                    formData.append(k,(Integer)v);
                }
                if(v instanceof String){
                    formData.append(k,(String)v);
                }
                if(v instanceof Boolean){
                    formData.append(k,(Boolean)v);
                }
            });
            opt.setForm(formData);
            opt.setHeader("Content-Type",CONTENT_TYPE_FORM);
        }

        private void setJson(JSONObject data){
            opt.setData(JSON.toJSONString(data));
            opt.setHeader("Content-Type",CONTENT_TYPE_JSON);
        }

        private  void setQueryParam(JSONObject data){
            data.forEach((k,v)->{
                if(v instanceof Integer){
                    opt.setQueryParam(k,(Integer)v);
                }
                if(v instanceof String){
                    opt.setQueryParam(k,(String)v);
                }
                if(v instanceof Boolean){
                    opt.setQueryParam(k,(Boolean)v);
                }
            });
            opt.setHeader("Content-Type",CONTENT_TYPE_QUERY_PARAM);
        }
    }

}
