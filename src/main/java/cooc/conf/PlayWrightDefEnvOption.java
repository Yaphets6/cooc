package cooc.conf;

import com.microsoft.playwright.Playwright;

import java.util.HashMap;
import java.util.Map;


public class PlayWrightDefEnvOption {
    private Playwright.CreateOptions options;

    public static PlayWrightDefEnvOption builder(){
        return new PlayWrightDefEnvOption();
    }

    private PlayWrightDefEnvOption() {
        initEvn();
    }

    public void setEvn(Map<String,String> evn){
        this.options.env.putAll(evn);
    }

    private void initEvn(){
        Playwright.CreateOptions var = new Playwright.CreateOptions();
        Map<String,String> def = new HashMap<>();
        def.put(CreateOptionKey.PLAYWRIGHT_BROWSERS_PATH,CreateOptionValue.BROWSERS_PATH_ROOT);
        def.put(CreateOptionKey.PLAYWRIGHT_DISABLE_HEADLESS,CreateOptionValue.SKIP_HEADLESS);
        def.put(CreateOptionKey.PLAYWRIGHT_DEBUG,CreateOptionValue.LOG_DEBUG);
        def.put(CreateOptionKey.PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD,CreateOptionValue.SKIP_DOWNLOAD);
        var.setEnv(def);
        this.options = var;
    }

    public Playwright.CreateOptions getOptions() {
        return options;
    }

    public static class CreateOptionKey{
        public static final String PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD = "PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD";
        public static final String PLAYWRIGHT_BROWSERS_PATH = "PLAYWRIGHT_BROWSERS_PATH";

        public static final String PLAYWRIGHT_DISABLE_HEADLESS = "PLAYWRIGHT_DISABLE_HEADLESS";

        public static final String PLAYWRIGHT_DEBUG = "PLAYWRIGHT_DEBUG";
    }

    public static class CreateOptionValue{
        public static final String DOWNLOAD = "false";//0
        public static final String SKIP_DOWNLOAD = "true";//1
        public static final String BROWSERS_PATH_ROOT = "browser/ms-playwright";//浏览器根目录
        public static final String HEADLESS = "false"; //无头模式
        public static final String SKIP_HEADLESS = "true"; //非无头模式
        public static final String LOG_DEBUG = "pw:api"; //内部调试
    }

}
