package cooc.conf;

import java.util.regex.Pattern;

public abstract class ServerBaseInfoImpl implements ServerBaseInfo {

    protected final String host;
    protected final String port;

    protected final String protocol;

    protected   String baseUrl;
    protected  String baseWebPathUrl;
    protected  String baseApiPathUrl;

    protected static final String DEF_PATH_SPLIT = "/";


    protected ServerBaseInfoImpl(String host, String port, String protocol, String webPref, String apiPref) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        initUrl(webPref,apiPref);
    }

    @Override
    public String getBaseWebUrl() {
        return this.baseWebPathUrl;
    }

    @Override
    public String getBaseApiUrl() {
        return this.baseApiPathUrl;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }


    protected String getServerBaseUrl(){
        final String var = protocol + "://" + host + ":" + port;
        return var.replaceAll("(:443|:80)","");
    }

    protected void initUrl(String webPref,String apiPref){
        this.baseUrl = getServerBaseUrl();
        this.baseWebPathUrl = webPref != null && !webPref.isEmpty()?baseUrl + DEF_PATH_SPLIT + webPref + DEF_PATH_SPLIT : baseUrl + DEF_PATH_SPLIT;
        this.baseApiPathUrl = apiPref != null && !apiPref.isEmpty()?baseUrl + DEF_PATH_SPLIT + apiPref + DEF_PATH_SPLIT : baseUrl + DEF_PATH_SPLIT;
    }
}
