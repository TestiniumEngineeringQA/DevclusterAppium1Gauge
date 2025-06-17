package com.testinium;

import com.testinium.selector.Selector;
import com.testinium.selector.SelectorFactory;
import com.testinium.selector.SelectorType;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HookImpl {
    private Logger logger = LoggerFactory.getLogger(getClass());
    protected static AppiumDriver<MobileElement> appiumDriver;
    static EventFiringWebDriver eventDriver;
    protected static FluentWait<AppiumDriver<MobileElement>> appiumFluentWait;
    protected boolean localAndroid=false;
    public static boolean isDeviceAnd=true;
    protected static Selector selector ;

    @BeforeScenario
    public void beforeScenario() throws MalformedURLException {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!Test baslıyor!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (StringUtils.isEmpty(System.getenv("key"))) {
            if (localAndroid) {
                isDeviceAnd=true;
                logger.info("Local Browser");
                DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities
                        .setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                desiredCapabilities.setCapability("skipDeviceInitialization", true);
                desiredCapabilities.setCapability("skipServerInstallation", true);
                desiredCapabilities.setCapability("ignoreUnimportantViews", true);
                desiredCapabilities.setCapability("waitForIdleTimeout",150);
                desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
                //desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "device");
                desiredCapabilities
                        .setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                                "com.gratis.android");
                desiredCapabilities
                        .setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                                "com.app.gratis.ui.splash.SplashActivity");
                desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
                desiredCapabilities
                        .setCapability(MobileCapabilityType.NO_RESET, false);
                desiredCapabilities
                        .setCapability(MobileCapabilityType.FULL_RESET, false);
                desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 3000);
                desiredCapabilities.setCapability("unicodeKeyboard", false);
                //desiredCapabilities.setCapability("resetKeyboard", true);
                URL url = new URL("http://127.0.0.1:4723/wd/hub");
                appiumDriver = new AndroidDriver(url, desiredCapabilities);
                appiumDriver.hideKeyboard();
            } else {
                isDeviceAnd=false;
                DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities
                        .setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
                desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                desiredCapabilities.setCapability(MobileCapabilityType.UDID, "ca796f0c0ead8729c124b5bda73f8de53b4dec8a");
                desiredCapabilities
                        .setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.pharos.Gratis");
                //setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.pharos.gratis.uat");
                desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "devtestinium iPhone X");
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.3.1");
                desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);

                URL url = new URL("http://127.0.0.1:4723/wd/hub");
                appiumDriver = new IOSDriver(url, desiredCapabilities);


            }
        } else {
            String hubURL = "http://hub-devcluster.testinium.io:4444/wd/hub";
            DesiredCapabilities capabilities = new DesiredCapabilities();

            System.out.println("key:" + System.getenv("key"));
            System.out.println("platform" + System.getenv("platform"));
            System.out.println("version" + System.getenv("version"));

            if (System.getenv("platform").equals("ANDROID")) {
                isDeviceAnd=true;
                capabilities.setCapability("key", System.getenv("key"));
                capabilities
                        .setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                                "com.gratis.android");

                capabilities
                        .setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                                "com.app.gratis.ui.splash.SplashActivity");
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
                capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
                capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
                capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
                capabilities.setCapability("unicodeKeyboard", true);
                capabilities.setCapability("resetKeyboard", true);
                capabilities.setCapability("appium:settings[waitForIdleTimeout]",500);

                capabilities.setCapability("appium:[skipDeviceInitialization]",true);
                capabilities.setCapability("appium:[skipServerInstallation]",true);
                capabilities.setCapability("appium:[ignoreUnimportantViews]",true);

                appiumDriver = new AndroidDriver(new URL(hubURL), capabilities);
                localAndroid = true;
            } else {
                localAndroid=false;
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!İos Test baslıyor!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                // capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
                capabilities.setCapability("noReset", false);
                capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
                capabilities.setCapability("usePrebuiltWDA", true); //changed
                capabilities.setCapability("key", System.getenv("key"));
                capabilities.setCapability("waitForAppScript", "$.delay(1000);");
                //capabilities.setCapability("bundleId", "com.intertech.denizbankag");
                capabilities.setCapability("bundleId", "com.pharos.Gratis");
                capabilities.setCapability("usePrebuiltWDA",true);
                capabilities.setCapability("useNewWDA", false);
                capabilities.setCapability("autoAcceptAlerts",true);
                //capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 5);
                appiumDriver = new IOSDriver(new URL(hubURL), capabilities);
            }
        }
        selector = SelectorFactory
                .createElementHelper(localAndroid ? SelectorType.ANDROID : SelectorType.IOS);
        appiumDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        appiumFluentWait = new FluentWait<AppiumDriver<MobileElement>>(appiumDriver);
        appiumFluentWait.withTimeout(Duration.ofSeconds(8))
                .pollingEvery(Duration.ofMillis(350))
                .ignoring(NoSuchElementException.class);



    }

    @AfterScenario
    public void afterScenario() {
        if(appiumDriver != null)
            appiumDriver.quit();
    }

}
