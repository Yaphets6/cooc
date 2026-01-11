package cooc.common.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.cvm.v20170312.CvmClient;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.GeneralAccurateOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse;
import cooc.conf.Args;
import okhttp3.Response;

import java.awt.image.BufferedImage;
import java.util.Base64;

/**
 * Creater yangyk
 * time 2026/1/8 0008:20:44
 * package cooc.common.utils
 */
public class TencentOcr {
    public static String ocr(String codeImageBase64){
        return getOcrResult(codeImageBase64);
    }


    private static String getOcrResult(String codeImageBase64){
        GeneralBasicOCRRequest request = new GeneralBasicOCRRequest();
        OcrClient ocrClient = getOcrClient();
        request.setImageBase64(codeImageBase64);
        request.setLanguageType("auto"); // auto, zh, en等
        request.setIsPdf(false);
        request.setPdfPageNumber(1L);
        GeneralBasicOCRResponse response;
        try {
            response = ocrClient.GeneralBasicOCR(request);
        } catch(Exception e){
           throw new RuntimeException("验证码识别异常" + e);
        }
        return response.getTextDetections().toString();
    }

    private static OcrClient getOcrClient(){
        ClientProfile profile = TencentCloud.getClientProfile(Args.OCR.getValue());
        Credential credential = TencentCloud.getCredential();
        return new OcrClient(credential, Args.REGION.getValue(), profile);
    }

    private static String getBase64(byte[] image){
        return Base64.getEncoder().encodeToString(image);
    }
}
