package cooc.common.utils;

import java.io.File;
import java.util.Locale;

public class FileUtils {
    public static boolean clearDir(File file){
        boolean result = false;
        if(file.isFile()){
            result =  file.delete();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if(files != null && files.length > 0){
                for (File item:files
                     ) {
                    clearDir(item);
                    item.delete();
                }
            }else {
                return true;
            }
            file.delete();
        }
        return result;
    }

    public static String getPathSplit(){
        final String os = System.getProperty("os.name");
        if(os.toLowerCase(Locale.ROOT).contains("win")){
            return "\\";
        }
        return "/";
    }

}
