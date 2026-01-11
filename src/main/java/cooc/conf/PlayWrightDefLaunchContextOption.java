package cooc.conf;

import com.microsoft.playwright.BrowserType;

import java.io.File;
import java.nio.file.Path;

public class PlayWrightDefLaunchContextOption {
    private BrowserType.LaunchPersistentContextOptions initOpt;

    public static final Path defUserDataDir = new File("temp").toPath();

    private final Path userDataDir ;

    private final ServerBaseInfo serverBaseConf;

    public static PlayWrightDefLaunchContextOption builder(ServerBaseInfo serverBaseConf, Path userDataDir){
        return new PlayWrightDefLaunchContextOption(serverBaseConf,userDataDir);
    }

    private PlayWrightDefLaunchContextOption(ServerBaseInfo serverBaseConf, Path userDataDir) {
        this.serverBaseConf = serverBaseConf;
        this.userDataDir = userDataDir;
        initDefOption();
    }


    public BrowserType.LaunchPersistentContextOptions getInitOpt() {
        return initOpt;
    }

    public Path getUserDataDir() {
        return userDataDir;
    }

    public ServerBaseInfo getServerBaseConf() {
        return serverBaseConf;
    }


    private  void initDefOption(){
        BrowserType.LaunchPersistentContextOptions var = new BrowserType.LaunchPersistentContextOptions();
        var.setAcceptDownloads(ContextDefOption.ACCEPT_DOWNLOADS);
        var.setTimeout(ContextDefOption.TIMEOUT);
        var.setIgnoreHTTPSErrors(ContextDefOption.IGNORE_HTTPS_ERRORS);
        var.setHeadless(ContextDefOption.HEADLESS);
        var.setViewportSize(ContextDefOption.WIDTH,ContextDefOption.HEIGHT);
        var.setBaseURL(serverBaseConf.getBaseUrl());
        this.initOpt = var;
    }


    public static class ContextDefOption{
        public static final double TIMEOUT = 60000;
        public static final boolean HEADLESS = false;
        public static final boolean IGNORE_HTTPS_ERRORS = true;
        public static final int WIDTH = 1920;
        public static final int HEIGHT = 1080;
        public static final boolean ACCEPT_DOWNLOADS = true;
    }

}
