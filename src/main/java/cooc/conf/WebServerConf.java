package cooc.conf;

public class WebServerConf extends ServerBaseInfoImpl {
    public static final String DEF_PROTOCOL = "https";
    public static final String DEF_HTTPS_PORT = "443";

    public static final String DEF_WEB_PREF = "";
    public static final String DEF_API_PREF = "";


    public WebServerConf(String host, String port, String protocol) {
        super(host,port,protocol,DEF_WEB_PREF,DEF_API_PREF);
    }

    public WebServerConf(String host) {
        super(host,DEF_HTTPS_PORT,DEF_PROTOCOL,DEF_WEB_PREF,DEF_API_PREF);
    }



}
