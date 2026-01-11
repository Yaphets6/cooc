package cooc.common.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Creater yangyk
 * time 2026/1/7 0007:13:30
 * package cooc.common.utils
 */
public class CodeImage {
    public static String preprocessCaptcha(String imageBase64) {
        return TencentOcr.ocr(String.valueOf(imageBase64));
    }


}
