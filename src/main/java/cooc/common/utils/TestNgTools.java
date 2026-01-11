package cooc.common.utils;

import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class TestNgTools {
    public static String getTestDesc(Method test){
        return test.getAnnotation(Test.class).description();
    }

}
