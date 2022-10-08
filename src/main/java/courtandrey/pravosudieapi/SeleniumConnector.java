package courtandrey.pravosudieapi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

class SeleniumConnector {
    private final WebDriver driver;
    private boolean isInited = false;

    SeleniumConnector() {
        String os = System.getProperty("os.name");
        String nul = "nul";
        if (os.toLowerCase().contains("linux")) {
            nul = "/dev/null";
            System.setProperty("webdriver.gecko.driver", "./src/main/resources/linux/geckodriver");
        }
        else if (os.toLowerCase().contains("windows")) {
            System.setProperty("webdriver.gecko.driver", "./src/main/resources/windows/geckodriver.exe");
        }
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, nul);
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().scriptTimeout(Duration.of(10, ChronoUnit.MINUTES));
        driver.manage().timeouts().pageLoadTimeout(Duration.of(1, ChronoUnit.MINUTES));
    }
    public Document connect(String url, long waitTime) {
        driver.get(url);
        try {
            if (!isInited) {
                Thread.sleep(45 * 1000);
            } else {
                Thread.sleep(waitTime * 1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (driver.getPageSource() == null) throw new TimeoutException();

        isInited = true;

        return Jsoup.parse(driver.getPageSource());
    }


    public String getText(String href, long waitTime) {
        List<WebElement> elements = driver.findElements(By.className("bgs-result"));
        String text = null;
        for (WebElement el:elements) {
            WebElement element;
            try {
                element = el.findElement(By.tagName("a"));
            } catch (NoSuchElementException e) {
                continue;
            }
            Document document = Jsoup.parse(el.getAttribute("innerHTML"));
            Element clazz = document.getElementsByClass("resultHeader openCardLink").get(0);
            String link = clazz.attr("href");
            if (link.equals(href)) {
                element.click();
                try {
                    Thread.sleep(waitTime * 1000);
                    WebElement button = driver.findElements(By.className("tab")).get(2);
                    try {
                        button.click();
                    } catch (ElementNotInteractableException ignored) {}
                    Thread.sleep(waitTime * 1000);
                    driver.switchTo().frame(driver.findElement(By.cssSelector("iframe.width100")));
                    StringBuilder sb = new StringBuilder();
                    for (WebElement p:driver.findElements(By.tagName("p"))) {
                        sb.append(p.getText()).append("\n");
                    }
                    text = sb.toString();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    return null;
                }
                break;
            }
        }
        return text;
    }


}
