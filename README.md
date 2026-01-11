# cooc
工程为学习playwright自动化框架，基于若依原型做的Demo基础框架

1、框架：kuaplaywright+TestNg+Extentreports

2、适用场景：UI自动化、接口自动化

## 使用说明
### 环境要求
java-11、playwright-1.49.0、testng-7.8.0、extentreports-5.1.2
### 项目结构
PlaywrightBase

|
|____AutoTestDemoTimestamp  //可视化报告。

|____browser  //playwright浏览器。

|____src/main/java/cooc


| ___________________|____common        //浏览器初始以及通用工具类


| ___________________|____conf         //配置类，涉及web、浏览器、参数配置


|____________________|____pageview    //页面模型，涉及Unit（公共组件）、PageObject（页面模型）Api（接口工具）。


|____________________|____report      //报告以及测试初始化，涉及报告封装以及测试钩子函数等。


|____________________|____test       //测试启动类，以及TestCase脚本


|____temp  //playwright运行时工作目录

### 备注
当前示例基于若依原型跑的，涉及到iframe的处理与元素定位，接口测试是cookie。示例中也有涉及Vue等主流前端框架的原始定位，以及接口测试token处理。可根据实际项目情况做选择。

跑demo，需要手动输入验证码，目前腾讯云OCR识别无法调试。
### 效果
[Demo报告](https://github.com/Yaphets6/cooc.git/AutoTestDemo20260111122212/reportIndex.html)
![image](https://github.com/Yaphets6/cooc.git/AutoTestDemo20260111122212/TestScreenshot/1.png)
![image](https://github.com/Yaphets6/cooc.git/AutoTestDemo20260111122212/TestScreenshot/2.png)
