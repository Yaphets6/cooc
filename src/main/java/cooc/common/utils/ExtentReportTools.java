package cooc.common.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;

public class ExtentReportTools {

    public static Media getScreenshot(String path){
        return MediaEntityBuilder.createScreenCaptureFromPath(path).build();
    }
}
