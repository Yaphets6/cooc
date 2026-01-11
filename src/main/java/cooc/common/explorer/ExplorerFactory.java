package cooc.common.explorer;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Cookie;
import cooc.common.utils.FileUtils;
import cooc.conf.*;
import cooc.pageview.apis.RequestContextOptionsFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ExplorerFactory {

    public static ExplorerFactory.BrowserContextObj getExplorer(InitExplorerOption explorerOption){
        return initExplorer(explorerOption);
    }


    public static PlayWrightDefEnvOption getDefaultCreateOption(){
        return PlayWrightDefEnvOption.builder();
    }

    public static PlayWrightDefLaunchContextOption getDefContextOption(ServerBaseInfo serverBaseConf, Path userDataDir){
        return PlayWrightDefLaunchContextOption.builder(serverBaseConf,userDataDir);
    }

    public static APIRequestContext getRequestObj (Map<String,String> headerToken,BrowserContextObj browserContextObj){
        APIRequest.NewContextOptions opt;
        if(headerToken!=null && !headerToken.isEmpty()){
            opt = RequestContextOptionsFactory.create(headerToken);
        }else {
            opt = RequestContextOptionsFactory.create();
        }
        opt.setBaseURL(browserContextObj.option.launchOption.getServerBaseConf().getBaseUrl());
        return browserContextObj.playwright.request().newContext(opt);
    }



    private static ExplorerFactory.BrowserContextObj initExplorer(InitExplorerOption initExplorerOption){
        return getBrowserContextObj(initExplorerOption);
    }

    private static Playwright getPlaywright(PlayWrightDefEnvOption defEnv){
        return Playwright.create(defEnv.getOptions());
    }

    private static ExplorerFactory.BrowserContextObj getBrowserContextObj(InitExplorerOption initExplorerOption){
        ExplorerType explorerType = initExplorerOption.explorerType;
        final Playwright playwright = getPlaywright(initExplorerOption.defEvn);
        BrowserContext browserContext = null;
        final Path workTemp = initExplorerOption.launchOption.getUserDataDir();
        System.out.println("工作缓存目录——>" + workTemp);
        BrowserType.LaunchPersistentContextOptions opt = initExplorerOption.launchOption.getInitOpt();
        switch(explorerType){
            case CHROME:
                browserContext = playwright.chromium().launchPersistentContext(workTemp,opt);
                break;
            case FIREFOX:
                browserContext = playwright.firefox().launchPersistentContext(workTemp,opt);
                break;
            case WEBKIT:
                browserContext = playwright.webkit().launchPersistentContext(workTemp,opt);
                break;
        }
        return new BrowserContextObj(playwright,browserContext,initExplorerOption);
    }

    public static void close(ExplorerFactory.BrowserContextObj browserContextObj){
        browserContextObj.getPlaywright().close();
        FileUtils.clearDir(browserContextObj.getOption().launchOption.getUserDataDir().toFile());
    }



    public enum ExplorerType {
        CHROME,

        WEBKIT,
        FIREFOX;

        ExplorerType() {
        }
    }

    public static class BrowserContextObj{

        private final Playwright playwright;
        private final BrowserContext browserContext;
        private final InitExplorerOption option;

        public BrowserContextObj(Playwright playwright,BrowserContext browsercontext,InitExplorerOption option) {
            this.playwright = playwright;
            this.browserContext = browsercontext;
            this.option = option;
        }

        public BrowserContext getBrowserContext() {
            return browserContext;
        }

        public InitExplorerOption getOption() {
            return option;
        }

        public Playwright getPlaywright() {
            return playwright;
        }
    }

    public static class InitExplorerOption{
        private final ExplorerType explorerType;
        private final PlayWrightDefLaunchContextOption launchOption;
        private  PlayWrightDefEnvOption defEvn = PlayWrightDefEnvOption.builder();

        public InitExplorerOption(ExplorerType explorerType,PlayWrightDefLaunchContextOption option) {
            this.explorerType = explorerType;
            this.launchOption = option;
        }

        public ExplorerType getExplorerType() {
            return explorerType;
        }

        public PlayWrightDefLaunchContextOption getLaunchOption() {
            return launchOption;
        }

        public PlayWrightDefEnvOption getDefEvn() {
            return defEvn;
        }

        public void setDefEvn(PlayWrightDefEnvOption defEvn) {
            this.defEvn = defEvn;
        }
    }

    public static Page getActivePage(BrowserContext context){
        List<Page> all = context.pages();
        int i = 0;
        for (Page var:all
        ) {
            System.out.println("当前浏览器窗口" + i + ":" + var.title());
        }
        final Page activePage = all.get(all.size() - 1);
        System.out.println("当前浏览器激活的窗口:" + activePage.title());
        return activePage;
    }
}
