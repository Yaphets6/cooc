package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

import java.io.File;

public class UploadInput extends AbstractUnit{

    private static final String UPLOAD_DIV = "div.el-upload:has( input.el-upload__input)";
    private static final String UPLOAD_INPUT = "input.el-upload__input";

    public UploadInput(Locator unitRange,  PageView pageView) {
        super(unitRange, UPLOAD_DIV, pageView);
    }

    public void uploadFile(String btnName,String filePath){
        final Locator input = this.getUnitByText(btnName).locator(UPLOAD_INPUT);
        input.setInputFiles(new File(filePath).toPath());
    }
}
