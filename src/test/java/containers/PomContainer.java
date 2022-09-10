package containers;

import org.openqa.selenium.WebDriver;
import poms.YandexSearchPage;
import poms.GoogleSearchPage;


public class PomContainer {

    public GoogleSearchPage googleSearchPage;
    public YandexSearchPage yandexSearchPage;

    public PomContainer(WebDriver driver) {
        this.googleSearchPage = new GoogleSearchPage(driver);
        this.yandexSearchPage = new YandexSearchPage(driver);
    }
}