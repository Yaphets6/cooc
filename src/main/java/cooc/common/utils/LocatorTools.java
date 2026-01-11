package cooc.common.utils;

import com.microsoft.playwright.Locator;

public class LocatorTools {


    public static Locator getLocatorAndView(Locator locator){
        locator.scrollIntoViewIfNeeded();
        return locator;
    }
}
