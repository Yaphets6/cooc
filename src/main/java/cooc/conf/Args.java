package cooc.conf;

import java.io.*;
import java.util.*;

public enum Args {
    SERVER_HOST("host","127.0.0.1"),
    SERVER_PORT("port","80"),
    SERVER_PROTOCOL("protocol","http"),
    WORK_TEMP("workTemp",".\\temp"),
    WORK_BROWSER_PATH("browserPath",".\\ms-playwright\\chromium-1148\\chrome-win\\chrome.exe"),
    OCR("ocr","ocr.tencentcloudapi.com"),
    REGION("region","ap-shanghai"),
    SECRET_ID("secretId","cooc"),
    SECRET_KEY("secretKey","cooc"),
    ;
    private final String key;
    private String value;

    Args(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String[] getKeys(){
        return Arrays.stream(Args.values()).map(Args::getKey).toArray(String[]::new);
    }





    public static void initArgs(String[] args){
        if(args!= null && args.length > 0){
            setArgs(args);
            setProperties(args);
        }else {
            setProperties(null);
        }
    }

    private static Args getByKey(String key){
        Args result = null;
        for (Args item:values()
             ) {
            if(item.key.equals(key)){
                result = item;
            }
        }
        return result;
    }

    private static void setArgs(String[] args){
        for (String arg:args
        ) {
            String[] vars = arg.split("=");
            System.out.println("key:" +vars[0] + "value:" +vars[1] );
            setArg(vars[0],vars[1]);
        }
    }

    private static String[] getArgsKey(String[] args){
        String[] keys = new String[args.length];
        for(String arg:args){
            String[] vars = arg.split("=");
            keys[Arrays.asList(args).indexOf(arg)] = vars[0];
        }
        return keys;
    }

    private static void setProperties(String[] args){
        try {
            InputStream inputStream = Args.class.getClassLoader().getResourceAsStream("conf.properties");
            Properties conf = new Properties();
            conf.load(inputStream);
            setProperties(conf, args);
        }catch (IOException e){
            System.out.println("配置文件加载失败，使用默认配置" + e);
        }
    }

    private static void setArg(String key,String value){
        Args var = Args.getByKey(key);
        if(var!=null){
            var.setValue(value);
        }
        else {
            System.out.println("未找到自动化测试需要的参数项:" + key);
        }
    }

    private static void setProperties(Properties properties,String[] args){
        String[] all = getKeys();
        Set<String> commandLineKeys = args != null && args.length > 0
                ? new HashSet<>(Arrays.asList(getArgsKey(args)))
                : Collections.emptySet();
        Arrays.stream(all)
                .filter(key -> !commandLineKeys.contains(key))
                .forEach(key -> setArg(key, properties.getProperty(key)));
    }
}
