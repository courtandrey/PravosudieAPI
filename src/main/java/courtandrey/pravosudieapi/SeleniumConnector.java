package courtandrey.pravosudieapi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

class SeleniumConnector {
    private final WebDriver driver;

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
    }
    public Document connect(String url, int waitTime) {
        driver.get(url);

        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (driver.getPageSource() == null) throw new TimeoutException();

        return Jsoup.parse(driver.getPageSource());
    }
}
