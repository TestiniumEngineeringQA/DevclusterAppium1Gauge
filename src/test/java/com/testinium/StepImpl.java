package com.testinium;

import com.testinium.helper.RandomString;
import com.testinium.helper.StoreHelper;
import com.testinium.model.SelectorInfo;
import com.thoughtworks.gauge.Step;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Random;
import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofMillis;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.Keys;

public class StepImpl extends HookImpl {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public StepImpl() {

    }


    public List<MobileElement> findElements(By by) throws Exception {
        List<MobileElement> webElementList = null;
        try {
            webElementList = appiumFluentWait.until(new ExpectedCondition<List<MobileElement>>() {
                @Nullable
                @Override
                public List<MobileElement> apply(@Nullable WebDriver driver) {
                    List<MobileElement> elements = driver.findElements(by);
                    return elements.size() > 0 ? elements : null;
                }
            });

            if (webElementList == null) {
                throw new NullPointerException(String.format("by = %s Web element list not found", by.toString()));
            }

        } catch (Exception e) {
            throw e;
        }
        return webElementList;
    }

    public List<MobileElement> findElementsWithoutAssert(By by) {

        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(by);
        } catch (Exception e) {
        }
        return mobileElements;
    }

    public List<MobileElement> findElementsWithAssert(By by) {

        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(by);
        } catch (Exception e) {
            Assertions.fail("by = %s Elements not found ", by.toString());
            e.printStackTrace();
        }
        return mobileElements;
    }


    public MobileElement findElement(By by) throws Exception {
        MobileElement mobileElement;
        try {
            mobileElement = findElements(by).get(0);
        } catch (Exception e) {
            throw e;
        }
        return mobileElement;
    }

    public MobileElement findElementWithoutAssert(By by) {
        MobileElement mobileElement = null;
        try {
            mobileElement = findElement(by);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileElement;
    }

    public MobileElement findElementWithAssertion(By by) {
        MobileElement mobileElement = null;
        try {
            mobileElement = findElement(by);
        } catch (Exception e) {
            Assertions.fail(mobileElement.getAttribute("value") + " " + "by = %s Element not found ", by.toString());
            e.printStackTrace();
        }
        return mobileElement;
    }

    public MobileElement findElementByKeyWithoutAssert(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        MobileElement mobileElement = null;
        try {
            mobileElement = selectorInfo.getIndex() > 0 ? findElements(selectorInfo.getBy())
                    .get(selectorInfo.getIndex()) : findElement(selectorInfo.getBy());
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return mobileElement;
    }

    public MobileElement findElementByKey(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

        MobileElement mobileElement = null;
        try {
            mobileElement = selectorInfo.getIndex() > 0 ? findElements(selectorInfo.getBy())
                    .get(selectorInfo.getIndex()) : findElement(selectorInfo.getBy());
        } catch (Exception e) {
            Assertions.fail("key = %s by = %s Element not found ", key, selectorInfo.getBy().toString());
            e.printStackTrace();
        }
        return mobileElement;
    }


    public List<MobileElement> findElemenstByKeyWithoutAssert(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(selectorInfo.getBy());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileElements;
    }

    public List<MobileElement> findElemenstByKey(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(selectorInfo.getBy());
        } catch (Exception e) {
            Assertions.fail("key = %s by = %s Elements not found ", key, selectorInfo.getBy().toString());
            e.printStackTrace();
        }
        return mobileElements;
    }



    @Step({"<str> elementine <str2> degerini gir", "<str> element write <str2> text"})
    public void sendK(String str, String str2) {
        findElementWithAssertion(By.id(str)).sendKeys(str2);
    }


    @Step({"Değeri <text> e eşit olan elementi bul ve tıkla",
            "Find element text equals <text> and click"})
    public void clickByText(String text) {
        findElementWithAssertion(By.xpath(".//*[contains(@text,'" + text + "')]")).click();
    }


    @Step({"İçeriği <value> e eşit olan elementli bul ve tıkla",
            "Find element value equals <value> and click"})
    public void clickByValue(String value) {
        findElementWithAssertion(MobileBy.xpath(".//*[contains(@value,'" + value + "')]")).click();
    }
    @Step("İçeriği <value> value iceren text degerinin goruntulendigi kontrol edilir")
    public void checkByValue(String value) {
        appiumDriver.findElement(MobileBy.xpath(".//*[contains(@value,'" + value + "')]"));
        System.out.println(value+ " "+ " degeri goruntulendi");
    }



    @Step({"Değeri <text> e eşit olan <index>. elementi bul ve tıkla"})
    public void clickByText(String text, int index) {
        findElementWithAssertion(By.xpath("(.//*[contains(@text,'" + text + "')])[" + index + "]")).click();
    }

    @Step({"İçeriği <value> e eşit olan <index>. elementi bul ve tıkla"})
    public void clickByValue(String value, int index) {
        findElementWithAssertion(MobileBy.xpath("(.//*[contains(@value,'" + value + "')])[" + index + "]")).click();
    }

    @Step("<key> elementinin <index> .li elementi bul ve tıkla")
    public void clickByKeyIndex(String key, int index) {
        findElementsWithoutAssert(selector.getSelectorInfo(key).getBy()).get(index).click();
    }


    @Step({"Elementine tıkla <key>", "Click element by <key>"})
    public void clickByKey(String key) {
        doesElementExistByKey(key, 5);
        findElementByKey(key).click();
        logger.info(key + " elemente tıkladı");
    }

    @Step({"Check if element <key> exists",
            "Wait for element to load with key <key>",
            "Element var mı kontrol et <key>",
            "Elementin yüklenmesini bekle <key>"})
    public MobileElement getElementWithKeyIfExists(String key) throws InterruptedException {
        MobileElement element;
        try {
            element = findElementByKey(key);
            logger.info(key + " elementi bulundu.");
        } catch (Exception ex) {
            logger.info("Element: '" + key + "' doesn't exist.");
            return null;
        }
        return element;
    }


    @Step({"<key> li elementi bul ve varsa dokun", "Click element by <key> if exist"})
    public void existTapByKey(String key) {
        if (findElementByKey(key).isDisplayed()) {
            findElementByKey(key).click();
        }
    }

    @Step({"sayfadaki <X> <Y> alana dokun"})
    public void coordinateTap(int X, int Y) {
        Dimension dimension = appiumDriver.manage().window().getSize();
        int width = dimension.width;
        int height = dimension.height;

        TouchAction action = new TouchAction(appiumDriver);
        action.tap(PointOption.point((width * X) / 100, (height * Y) / 100))
                .release().perform();

    }

    @Step({"<key> li elementi bul, temizle ve <text> değerini yaz",
            "Find element by <key> clear and send keys <text>"})
    public void sendKeysByKey(String key, String text) {
        MobileElement webElement = findElementByKey(key);
        webElement.clear();
        webElement.setValue(text);
        logger.info(key+" texti "+text+" key elementine temizlenip yazildi");
    }

    @Step({"<key> li elementi bul ve temizle",
            "Find element by <key> and clear"})
    public void clearFieldByKey(String key) {
        MobileElement webElement = findElementByKey(key);
        webElement.clear();
        logger.info(key + " elementi temizlendi");
    }

    @Step({"<key> li elementin text degeri silinir"})
    public void keyClear(String key) {
        findElementByKey(key).clear();
    }


    @Step({"<t> textini <k> elemente yaz",
            "Find element by <key> and send keys <text>"})
    public void sendKeysByKeyNotClear(String t, String k) {
        doesElementExistByKey(k, 5);
        findElementByKey(k).sendKeys(t);
        logger.info(t+" texti"+k+" key elementine yazildi");
    }

    @Step({"Saklanan <SKU> textini <key> elemente yaz"})
    public void getSendKeysByKeyNotClear(String SKU, String key) {
        String sKUs = StoreHelper.INSTANCE.getValue(SKU);
        doesElementExistByKey(key, 5);
        findElementByKey(key).sendKeys(sKUs);
        logger.info(SKU+" texti"+key+" key elementine yazildi");
    }

    public int createRandomNumber(int max) {
        Random rand = new Random();

        int randomNumber = rand.nextInt(max);

        return randomNumber;
    }


    @Step({"<key> li elementi bul ve değerini <saveKey> olarak sakla",
            "Find element by <key> and save text <saveKey>"})
    public void saveTextByKey(String key, String saveKey) {
        StoreHelper.INSTANCE.saveValue(saveKey, findElementByKey(key).getText());
        logger.info("["+StoreHelper.INSTANCE.getValue(saveKey)+"]" + " degeri ["+ saveKey + "] ismiyle hafizaya kaydedildi");
    }
    @Step({"<key> name li elementi bul ve değerini <saveKey> olarak sakla",
            "Find element by <key> and save name <saveKey>"})
    public void saveNameByKey(String key, String saveKey) {
        StoreHelper.INSTANCE.saveValue(saveKey, findElementByKey(key).getAttribute("name"));
        logger.info("["+StoreHelper.INSTANCE.getValue(saveKey)+"]" + " degeri ["+ saveKey + "] ismiyle hafizaya kaydedildi");
    }

    @Step({"<key> li promosyon elementini bul ve değerini <saveKey> olarak sakla",
            "Find element by <key> and save text <saveKey>"})
    public void saveTextByKeyPromosyon(String key, String saveKey) {
        StoreHelper.INSTANCE.saveValue(saveKey, findElementByKey(key).getText().replace("-", "").trim());
        logger.info("["+StoreHelper.INSTANCE.getValue(saveKey)+"]" + " degeri ["+ saveKey + "] ismiyle hafizaya kaydedildi");
    }

    @Step({"<key> li elementli markayı bul ve değerini <saveKey> olarak sakla",
            "Find element by <key> and save text <saveKey>"})
    public void saveTextByKeyy(String key, String saveKey) {
        StoreHelper.INSTANCE.saveValue(saveKey, findElementByKey(key).getText().substring(0,findElementByKey(key).getText().length()-5));
        logger.info("["+StoreHelper.INSTANCE.getValue(saveKey)+"]" + " degeri ["+ saveKey + "] ismiyle hafizaya kaydedildi");
    }

    @Step({"<key> li elementli küsüratı bul ve değerini <saveKey> olarak sakla",
            "Find element by <key> and save text <saveKey>"})
    public void savePriceByKeyy(String key, String saveKey) {
        String kusur = findElementByKey(key).getText().substring(1,findElementByKey(key).getText().length());
        StoreHelper.INSTANCE.saveValue(saveKey,kusur);
        logger.info("["+StoreHelper.INSTANCE.getValue(saveKey)+"]" + " degeri ["+ saveKey + "] ismiyle hafizaya kaydedildi");
    }

    @Step({"<key> li ve değeri <text> e eşit olan elementli bul ve tıkla",
            "Find element by <key> text equals <text> and click"})
    public void clickByIdWithContains(String key, String text) {
        List<MobileElement> elements = findElemenstByKey(key);
        for (MobileElement element : elements) {
            logger.info("Text !!!" + element.getText());
            if (element.getText().toLowerCase().contains(text.toLowerCase())) {
                element.click();
                break;
            }
        }
    }




    private int getScreenWidth() {
        return appiumDriver.manage().window().getSize().width;
    }

    private int getScreenHeight() {
        return appiumDriver.manage().window().getSize().height;
    }

    private int getScreenWithRateToPercent(int percent) {
        return getScreenWidth() * percent / 100;
    }

    private int getScreenHeightRateToPercent(int percent) {
        return getScreenHeight() * percent / 100;
    }


    public void swipeDownAccordingToPhoneSize(int startXLocation, int startYLocation, int endXLocation, int endYLocation) {
        startXLocation = getScreenWithRateToPercent(startXLocation);
        startYLocation = getScreenHeightRateToPercent(startYLocation);
        endXLocation = getScreenWithRateToPercent(endXLocation);
        endYLocation = getScreenHeightRateToPercent(endYLocation);

        new TouchAction(appiumDriver)
                .press(PointOption.point(startXLocation, startYLocation))
                .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                .moveTo(PointOption.point(endXLocation, endYLocation))
                .release()
                .perform();
    }




    @Step({"<key> li elementi bulana kadar swipe et",
            "Find element by <key>  swipe "})
    public void findByKeyWithSwipe(String key) {
        int maxRetryCount = 10;
        while (maxRetryCount > 0) {
            List<MobileElement> elements = findElemenstByKeyWithoutAssert(key);
            if (elements.size() > 0) {
                if (elements.get(0).isDisplayed() == false) {
                    maxRetryCount--;

                    swipeDownAccordingToPhoneSize();

                } else {
                    System.out.println(key + " element bulundu");
                    break;
                }
            } else {
                maxRetryCount--;
                swipeDownAccordingToPhoneSize();

            }

        }
    }


    @Step("<yon> yönüne swipe et")
    public void swipe(String yon) {

        Dimension d = appiumDriver.manage().window().getSize();
        int height = d.height;
        int width = d.width;

        if (yon.equals("SAĞ")) {

            int swipeStartWidth = (width * 80) / 100;
            int swipeEndWidth = (width * 30) / 100;

            int swipeStartHeight = height / 2;
            int swipeEndHeight = height / 2;

            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();
        } else if (yon.equals("SOL")) {

            int swipeStartWidth = (width * 30) / 100;
            int swipeEndWidth = (width * 80) / 100;

            int swipeStartHeight = height / 2;
            int swipeEndHeight = height / 2;

            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);

            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();

        }
    }



    public void swipeUpAccordingToPhoneSize() {
        if (appiumDriver instanceof AndroidDriver) {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;
            System.out.println(width + "  " + height);

            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = (height * 75) / 100;
            int swipeEndHeight = (height * 40) / 100;
            System.out.println("Start width: " + swipeStartWidth + " - Start height: " + swipeStartHeight + " - End height: " + swipeEndHeight);
            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction((AndroidDriver) appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeEndHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(2000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeStartHeight))
                    .release()
                    .perform();
        } else {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;

            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = (height * 75) / 100;
            int swipeEndHeight = (height * 20) / 100;
            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeEndHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(2000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeStartHeight))
                    .release()
                    .perform();
        }
    }


    public void swipeDownAccordingToPhoneSize() {
        if (appiumDriver instanceof AndroidDriver) {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;

            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = (height * 75) / 100;
            int swipeEndHeight = (height * 25) / 100;
            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();
        } else {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;

            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = (height * 75) / 100;
            int swipeEndHeight = (height * 40) / 100;
            // appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();
        }
    }

    public void quickSwipeDownAccordingToPhoneSize() {
        if (appiumDriver instanceof AndroidDriver) {
            Dimension d = appiumDriver.manage().window().getSize();
            int width = d.width;
            int height = d.height;

            int swipeStartWidth = width / 2;
            int swipeStartHeight = (height * 75) / 100;
            int swipeEndHeight = (height * 10) / 100;

            TouchAction touchAction = new TouchAction(appiumDriver);
            touchAction.press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(swipeStartWidth, swipeEndHeight))
                    .release()
                    .perform();

            touchAction.press(PointOption.point(swipeStartWidth, swipeEndHeight))
                    .release()
                    .perform();
        } else {
            Dimension d = appiumDriver.manage().window().getSize();
            int width = d.width;
            int height = d.height;

            int swipeStartWidth = width / 2;
            int swipeStartHeight = (height * 75) / 100;
            int swipeEndHeight = (height * 15) / 100;

            TouchAction touchAction = new TouchAction(appiumDriver);
            touchAction.press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(swipeStartWidth, swipeEndHeight))
                    .release()
                    .perform();

            touchAction.press(PointOption.point(swipeStartWidth, swipeEndHeight))
                    .release()
                    .perform();
        }
    }


    public boolean isElementPresent(By by) {
        return findElementWithoutAssert(by) != null;
    }





    @Step({"Klavyeyi kapat", "Hide keyboard"})
    public void hideAndroidKeyboard() {
        try {

            if (localAndroid == false) {
                appiumDriver.hideKeyboard();
            } else {
                appiumDriver.hideKeyboard();
            }
        } catch (Exception ex) {
            logger.error("Klavye kapatılamadı "+ex.getMessage());
        }
    }


    @Step({"<key> değerini sayfa üzerinde olmadıgını kontrol et"})
    public void getPageSourceFindWordKey(String key) {

        MobileElement deneme = findElementByKeyWithoutAssert(key);

        if (deneme==null)
        {
            logger.info(key + " sayfa üzerinde olmadıgı kontrol edildi");
        }
        if (deneme!=null)
        {
            Assertions.fail("Element bulundu");
        }

    }



    @Step("<key> elementi bulunana kadar en fazla <sure> saniye kadar bekle")
    public void waitUntilElementExist(String key, int sure) {
        boolean doesExist = doesElementExistByKey(key,sure);
        if (doesExist)
        {
            logger.info("element beklendi ve bulundu");
        }
        else
        {
            logger.info("element beklendi ama verilen süre içerisinde bulunamadı");
        }
    }

    @Step({"<length> uzunlugunda random bir kelime üret ve <saveKey> olarak sakla"})
    public void createRandomNumber(int length, String saveKey) {
        StoreHelper.INSTANCE.saveValue(saveKey, new RandomString(length).nextString());
    }

    @Step("Geri butonuna bas")
    public void clickBybackButton() {
        if (!localAndroid) {
            backPage();
        } else {
            ((AndroidDriver) appiumDriver).pressKeyCode(AndroidKeyCode.BACK);
        }

    }




    private void backPage() {
        appiumDriver.navigate().back();
    }


    private String getCapability(String text) {
        return appiumDriver.getCapabilities().getCapability(text).toString();

    }

    public boolean doesElementExistByKey(String key, int time) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        try {
            WebDriverWait elementExist = new WebDriverWait(appiumDriver, time);
            elementExist.until(ExpectedConditions.visibilityOfElementLocated(selectorInfo.getBy()));
            return true;
        } catch (Exception e) {
            logger.info(key + " aranan elementi bulamadı");
            return false;
        }

    }


    public void tapElementWithCoordinate(int x, int y) {
        TouchAction a2 = new TouchAction(appiumDriver);
        a2.tap(PointOption.point(x, y)).perform();
    }

    @Step("<key> li elementin  merkezine tıkla ")
    public void tapElementWithKey(String key) {

        Point point = findElementByKey(key).getCenter();
        TouchAction a2 = new TouchAction(appiumDriver);
        a2.tap(PointOption.point(point.x, point.y)).perform();
    }

    @Step("<key> li elementin x kordinatında <xInt> yüzdesi y ekseninde merkezine tıkla")
    public void tapElementCordinaateWithKey(String key, int xInt) {

        Point point = findElementByKey(key).getCenter();
        TouchAction a2 = new TouchAction(appiumDriver);
        System.out.println(point);
        a2.tap(PointOption.point(point.x*(xInt/50), point.y)).perform();
    }

    @Step("<key> li element varsa tıkla")
    public void tapElementWithKeyControl(String key) {

        logger.info("element varsa verilen tıkla ya girdi");
        MobileElement mobileElement;

        mobileElement = findElementByKeyWithoutAssert(key);

        if (mobileElement != null) {

            doesElementExistByKey(key, 3);
            findElementByKey(key).click();
            logger.info(key + "elemente tıkladı");

        }}



    @Step("<key> li element varsa tıkla yoksa devam et")
    public void tapElementWithKeyControlArea(String key) {

        logger.info("element varsa verilen tıkla ya girdi");
        MobileElement mobileElement;

        mobileElement = findElementByKeyWithoutAssert(key);

        if (mobileElement != null) {

            doesElementExistByKey(key, 3);
            findElementByKey(key).click();
            logger.info(key + "elemente tıkladı");

        }
        else {
            System.out.println(key + " element yuklenmedi");

        }}


    @Step("<key> li element varsa  <x> <y> koordinatına tıkla ")
    public void tapElementWithKeyCoordinate(String key, int x, int y) {

        logger.info("element varsa verilen koordinata tıkla ya girdi");
        MobileElement mobileElement;

        mobileElement = findElementByKeyWithoutAssert(key);

        if (mobileElement != null) {

            System.out.println("pakachu");
            Point point = mobileElement.getLocation();
            logger.info(point.x + "  " + point.y);
            Dimension dimension = mobileElement.getSize();
            logger.info(dimension.width + "  " + dimension.height);
            TouchAction a2 = new TouchAction(appiumDriver);
            a2.tap(PointOption.point(point.x + (dimension.width * x) / 100, point.y + (dimension.height * y) / 100)).perform();
        }
    }

    @Step("<key> li elementin  merkezine press ile çift tıkla ")
    public void pressElementWithKey(String key) {

        Point point = findElementByKey(key).getCenter();
        TouchAction a2 = new TouchAction(appiumDriver);
        a2.press(PointOption.point(point.x, point.y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .press(PointOption.point(point.x, point.y)).release().perform();

    }


    @Step("<key> li elementin  merkezine double tıkla ")
    public void pressElementWithKey2(String key) {
        Actions actions = new Actions(appiumDriver);
        actions.moveToElement(findElementByKey(key));
        actions.doubleClick();
        actions.perform();
        appiumDriver.getKeyboard();

    }




    @Step("<key> li elemente kadar <text> textine sahip değilse ve <timeout> saniyede bulamazsa swipe yap")
    public void swipeAndFindwithKey(String key, String text, int timeout) {
        MobileElement sktYil1 = null;
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        WebDriverWait wait = new WebDriverWait(appiumDriver, timeout);
        int count = 0;
        while (true) {
            count++;
            try {
                sktYil1 = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(selectorInfo.getBy()));
                if (text.equals("") || sktYil1.getText().trim().equals(text)) {
                    break;
                }
            } catch (Exception e) {
                logger.info("Bulamadı");

            }
            if (count == 8) {

                Assertions.fail("Element bulunamadı");
            }

            Dimension dimension = appiumDriver.manage().window().getSize();
            int startX1 = dimension.width / 2;
            int startY1 = (dimension.height * 75) / 100;
            int secondX1 = dimension.width / 2;
            int secondY1 = (dimension.height * 30) / 100;

            TouchAction action2 = new TouchAction(appiumDriver);

            action2
                    .press(PointOption.point(startX1, startY1))
                    .waitAction(WaitOptions.waitOptions(ofMillis(2000)))
                    .moveTo(PointOption.point(secondX1, secondY1))
                    .release()
                    .perform();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }





    @Step("<key> li  telefonun  <x> ve elementin <y> kordinatına göre tıkla")
    public void elementFindwithXandYcoordinate(String key, int x, int y) {

        WebElement element = findElementByKey(key);
        int height = element.getLocation().y + (element.getSize().height * y) / 100;
        int width = (appiumDriver.manage().window().getSize().width * x) / 100;
        System.out.println(height + "  " + width + "   ");
        TouchAction action = new TouchAction(appiumDriver);
        action.tap(PointOption.point(width, height)).perform();
    }

    @Step("<key> elementinin koordinatlarına x=<x> y=<y> degerlerini ekleyerek tıkla")
    public void coordinatClickWithAdds(String key, int x, int y) {
        MobileElement me = findElementByKey(key);
        tapElementWithCoordinate(me.getLocation().x + x, me.getLocation().y + y);
    }

    @Step("<x>,<y> koordinatlarına tıkla")
    public void koordinataTikla(int x, int y) {
        TouchAction a2 = new TouchAction(appiumDriver);
        a2.tap(PointOption.point(x, y)).perform();
        logger.info("tıklama yapıldı");
    }
    @Step({"Değeri <key> e eşit olan elementli bul"})
    public void clickByTexte(String key) {
        String Sec;
        Sec = findElementByKeyWithoutAssert(key).getAttribute("checked");
        String E2 = Sec;
        if (E2.equals("false")){
            doesElementExistByKey(key, 5);
            findElementByKey(key).click();
            logger.info(key + "elemente tıkladı");
        }
        else if(E2.equals("true"))
        {
            logger.info(key + " secili gelmistir");
        }

    }
    @Step("Enter tıkla")
    public void keyboardClickEnter() {

        tapElementWithCoordinate(999,1991);
        logger.info("'%s' objesi üzerinde ENTER tuşuna basıldı.");
    }




    @Step("<x> elementinde <y> yilina git")
    public void clickByKeyRepeat(String key, int y) {
        y = 2022-y;
        for (int i=1; i<(y+1);i++)
        {
            doesElementExistByKey(key, 5);
            findElementByKey(key).click();
            logger.info(key + " elementine "+i+ ". kere tıkladı");
        }
    }





    private Long getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return (timestamp.getTime());
    }

    @Step({"<key> li elementi bul, temizle ve rasgele  email değerini yaz",
            "Find element by <key> clear and send keys  random email"})
    public void RandomeMail(String key) {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd-HH-mm-s");
        Date date = new Date(System.currentTimeMillis());
        logger.info(formatter.format(date));

        MobileElement webElement = findElementByKey(key);
        webElement.clear();
        webElement.setValue("testotomasyon" + formatter.format(date) + "@beymentest.com");
    }

    @Step({"<key> li elementi bul, temizle ve <length> uzunluğunda string değer yaz",
            "Find element by <key> clear and send keys <text>"})
    public void randomStringSendKeysByKey(String key, int length) {

        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for(int i = 0; i < length; i++) {

            int index = random.nextInt(lowerAlphabet.length());
            char randomChar = lowerAlphabet.charAt(index);
            sb.append(randomChar);
        }
        String randomString = sb.toString();

        logger.info("'"+randomString+ "' random kelime olarak oluşturuldu");
        MobileElement webElement = findElementByKey(key);
        webElement.clear();
        webElement.setValue(randomString);
    }

    @Step("<key>'li elementin <attr> degerini icerdigini kontrol et")
    public void checkByAttr(String key,String attr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("***************"+dtf.format(now)+"***************");
        Dimension d = appiumDriver.manage().window().getSize();
        int height = d.height;
        String attribute = findElementByKey(key).getAttribute(attr);
        System.out.println("Height: "+height+" - Attribute: "+attribute);

        LocalDateTime now2 = LocalDateTime.now();
        System.out.println("***************"+dtf.format(now2)+"***************");

    }




    @Step({"<saveKey> olarak hafizada saklanan degeri <key> elementine yaz"})
    public void getTextAndWriteForKey(String saveKey, String key){
        String saveElementTxt= StoreHelper.INSTANCE.getValue(saveKey);
        MobileElement webElement = findElementByKey(key);
        webElement.clear();
        webElement.setValue(saveElementTxt);
    }

    @Step({"<element> li elementi yukari kaydir"})
    public void swipeBirtdayUp(String key){

        String attribute = findElementByKey(key).getAttribute("bounds");

        String allString[] = attribute.split("]");
        String firstPart = allString[0].substring(1);
        String secondPart = allString[1].substring(1);
        String start[] = firstPart.split(",");
        String stringStartWidth = start[0];
        String stringStartHeight = start[1];
        String end[] = secondPart.split(",");
        String stringEndWidth = end[0];
        String stringEndHeight = end[1];
        int startWidth = Integer.parseInt(stringStartWidth);
        int startHeight = Integer.parseInt(stringEndHeight);
        int endWidth = Integer.parseInt(stringEndWidth);
        int endHeight = Integer.parseInt(stringEndHeight);
        int width = (startWidth+endWidth)/2;

        new TouchAction(appiumDriver)
                .press(PointOption.point(width, endHeight-90))
                .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                .moveTo(PointOption.point(width, startHeight+90))
                .release()
                .perform();
    }

    @Step({"<element> li elementi asagi kaydir"})
    public void swipeBirtdayDown(String key){

        String attribute = findElementByKey(key).getAttribute("bounds");

        String allString[] = attribute.split("]");
        String firstPart = allString[0].substring(1);
        String secondPart = allString[1].substring(1);
        String start[] = firstPart.split(",");
        String stringStartWidth = start[0];
        String stringStartHeight = start[1];
        String end[] = secondPart.split(",");
        String stringEndWidth = end[0];
        String stringEndHeight = end[1];
        int startWidth = Integer.parseInt(stringStartWidth);
        int startHeight = Integer.parseInt(stringEndHeight);
        int endWidth = Integer.parseInt(stringEndWidth);
        int endHeight = Integer.parseInt(stringEndHeight);
        int width = (startWidth+endWidth)/2;

        new TouchAction(appiumDriver)
                .press(PointOption.point(width,startHeight+90 ))
                .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                .moveTo(PointOption.point(width,endHeight-90 ))
                .release()
                .perform();
    }

    @Step({"Klavyede arama tusuna bas"})
    public void enterAndroidKeyboard() {
        try {

            if (localAndroid == false) {
                Actions action = new Actions(appiumDriver);
                action.sendKeys(Keys.ENTER).perform();
            }
            else {
                Actions action = new Actions(appiumDriver);
                action.sendKeys(Keys.ENTER).perform();
            }
        } catch (Exception ex) {
            logger.error("Klavye üzerinden arama başarısız "+ex.getMessage());
        }
    }

    @Step({"Favori ürünler silinir"})
    public void deleteFavElement() {

        try {

            Boolean dongu = true;

            while (dongu) {
                List<MobileElement> elements = findElemenstByKeyWithoutAssert("FAVORI_ELEMENT_SIL");
                int elementsSize = elements.size();
                System.out.println("Element size : " + elementsSize);
                for (int i = 0; i < elementsSize; i++) {
                    clickByKey("FAVORI_ELEMENT_SIL");
                    System.out.println("i : "+i);
                }
            }
        }catch (Exception e){
            logger.info("Tüm elementler silindi");
        }

    }

    @Step({"Sepetteki ürünler silinir"})
    public void deleteBasketElement() {

        try {

            Boolean dongu = true;

            while (dongu) {
                List<MobileElement> elements = findElemenstByKeyWithoutAssert("SEPETIM_ELEMENT_SIL");
                int elementsSize = elements.size();
                System.out.println("Element size : " + elementsSize);
                for (int i = 0; i < elementsSize; i++) {
                    clickByKey("SEPETIM_ELEMENT_SIL");
                    System.out.println("i : "+i);
                }
            }
        }catch (Exception e){
            logger.info("Tüm elementler silindi");
        }
    }



    @Step({"<times> kere sağa kaydirilir"})
    public void swipeRight(int time) {



        for (int i = 0; i < time; i++) {

            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;

            int swipeStartWidth = (width * 90) / 100;
            int swipeEndWidth = (width * 15) / 100;

            int swipeStartHeight = height / 2;
            int swipeEndHeight = height / 2;

            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);

            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();
        }


    }

    @Step({"<times> kere sola kaydirilir"})
    public void swipeLeft(int time) {



        for (int i = 0; i < time; i++) {

            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;

            int swipeStartWidth = (width * 15) / 100;
            int swipeEndWidth = (width * 90) / 100;

            int swipeStartHeight = height / 2;
            int swipeEndHeight = height / 2;

            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);

            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();
        }


    }



    @Step("<key> li  elementin x ekseninde merkezine, y ekseninde yukaridan %<int> deger kadar asagisina tikla")
    public void clickCenterXandIntY(String key, int x) {

        WebElement element = findElementByKey(key);
        int height = element.getLocation().y + (element.getSize().height)/ 100;
        int height2 = element.getLocation().y + (element.getSize().height*x)/ 100;
        int width = (appiumDriver.manage().window().getSize().width)/2;
        logger.info("Elementin height degeri : "+height+", Tiklanacak height degeri"+height2);
        logger.info("Elementin width degeri : "+width+", Tiklanacak width degeri"+width);
        TouchAction action = new TouchAction(appiumDriver);
        action.tap(PointOption.point(width, height2)).perform();
    }

    @Step("<key> li  elementin x ekseninde yukaridan %<int> deger, y ekseninde yukaridan %<int> deger kadar asagisina tikla")
    public void clickIntXandIntY(String key, int x, int y) {

        WebElement element = findElementByKey(key);
        int height = element.getLocation().y + (element.getSize().height)/ 100;
        int height2 = element.getLocation().y + (element.getSize().height*y)/ 100;
        int width = element.getLocation().x + (element.getSize().width) / 100;
        int width2 = element.getLocation().x + (element.getSize().width*x) / 100;
        logger.info("Elementin height degeri : "+height+", Tiklanacak height degeri"+height2);
        logger.info("Elementin width degeri : "+width+", Tiklanacak width degeri"+width2);
        TouchAction action = new TouchAction(appiumDriver);
        action.tap(PointOption.point(width2, height2)).perform();
    }

    @Step("<String> alt kategorisinin goruntulendigi kontrol edilir")
    public void findCategory(String key) {

        String element = "//XCUIElementTypeStaticText[@name=\""+key+"\"]";
        appiumDriver.findElement(MobileBy.xpath(element));
        logger.info(key +" elementinin sayfa uzerinde goruntulendigi kontrol edilir");

    }

    @Step("<String> title degerinin goruntulendigi kontrol edilir")
    public void findTitleText(String key) {

        String element = "//XCUIElementTypeStaticText[@name=\""+key+"\"]";
        appiumDriver.findElement(MobileBy.xpath(element));
        logger.info(key +" elementinin sayfa uzerinde goruntulendigi kontrol edilir");

    }

    @Step("<String> text degerine sahip elementin goruntulendigi kontrol edilir")
    public void findTextXpath(String key) {

        String element = "//XCUIElementTypeStaticText[@name=\""+key+"\"]";
        appiumDriver.findElement(MobileBy.xpath(element));
        logger.info(key +" text degerine sahip elementin goruntulendigi kontrol edildi");
    }

    @Step("<String> text degerine sahip elemente tikla")
    public void clickWithText(String key) {

        String element = "//XCUIElementTypeStaticText[@name=\""+key+"\"]";
        appiumDriver.findElement(MobileBy.xpath(element)).click();
        logger.info(key +" text degerine sahip elemente tiklandi");
    }

    @Step("<String> text degerine sahip elemente tikla.")
    public void clickWithTextAnd(String key) {

        String element = "//android.widget.TextView[@text=\""+key+"\"]";
        appiumDriver.findElement(MobileBy.xpath(element)).click();
        logger.info(key +" text degerine sahip elemente tiklandi");
    }

    @Step("<String> text degerine sahip butonun goruntulendigi kontrol edilir")
    public void findButtonWithText(String key) {

        String element = "//XCUIElementTypeButton[@name=\""+key+"\"]";
        appiumDriver.findElement(MobileBy.xpath(element));
        logger.info(key +" text degerine sahip buton goruntulendi");
    }

    @Step("<String> text degerine sahip butona tikla")
    public void clickButtonWithText(String key) {

        String element = "//XCUIElementTypeButton[@name=\""+key+"\"]";
        appiumDriver.findElement(MobileBy.xpath(element)).click();
        logger.info(key +" text degerine sahip elemente tiklandi");
    }


    @Step("<key> ismiyle hafizada text degeri sakli elementin sayfada gorundugu kontrol edilir")
    public void clickButtonWithTextt(String key) {

        String saveElementTxt= StoreHelper.INSTANCE.getValue(key);

        String element = "//XCUIElementTypeStaticText[@name=\""+saveElementTxt+"\"]";
        appiumDriver.findElement(MobileBy.xpath(element));
        logger.info(saveElementTxt +" text degerine sahip elementin goruntulendigi kontrol edildi");

    }

    @Step("<second> saniye bekle")
    public void waitBySecond(String second) {
        try {
            int seconds = Integer.parseInt(second);
            Thread.sleep(seconds * 1000L);
            logger.info(second + " saniye beklendi.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Bekleme sırasında hata oluştu", e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Geçersiz saniye formatı: " + second, e);
        }
    }



    @Step({"Android klavye kapatılır"})
    public void closeKeyboard() {
        appiumDriver.hideKeyboard();
    }

}
























