package cooc.test;


import cooc.conf.Args;

import org.testng.TestNG;


public class TestCaseRun {

    private static TestNG testNG;
    public static void main(String[] args){
        initArgs(args);
        initTestng();
        testNG.run();
    }

    private static void initArgs(String[] args){
        Args.initArgs(args);
    }

    private static void initTestng(){
        Class[] classes = new Class[]{DemoTest.class};
        testNG = new TestNG();
        testNG.setThreadCount(1);
        testNG.setTestClasses(classes);
    }
}
