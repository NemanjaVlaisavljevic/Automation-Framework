package poms;

import helpers.ui.BasePageObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YandexSearchPage extends BasePageObject {

    private final Logger log = LogManager.getLogger(YandexSearchPage.class);

    @FindBy(id = "text")
    WebElement searchInput;

    @FindBy(css = "li[class='serp-item serp-item_card']")
    List<WebElement> results;

    private final By TITLE_SELECTOR = By.cssSelector("div > div > a > h2 > span[class^='OrganicTitleContentSpan']");
    private final By URL_SELECTOR = By.cssSelector("div > div > div[class^='Path']");
    private final By DESCRIPTION_SELECTOR = By.cssSelector("div > div > div[class^='TextContainer']");

    private String currentSearchKeyword;

    public YandexSearchPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void searchByKeyword(String keyword) {

        log.info("Searching Yandex search engine by keyword : " + keyword);
        currentSearchKeyword = keyword;
        waitToBeClickable(searchInput, 10L);
        searchInput.sendKeys(keyword);
        searchInput.sendKeys(Keys.ENTER);
        waitForLoad(5L);
    }

    public List<Map<String, String>> extractYandexSearchResults() {

        if (results.size() == 0) {
            log.info("No search results found for keyword : " + currentSearchKeyword);
            return null;
        }

        log.info("Number of search results : " + results.size());

        if (results.size() > 10) {
            results = results.subList(0, 10);
        }

        List<Map<String, String>> formattedSearchResults = new ArrayList<>();

        for (WebElement result : results) {
            Map<String, String> formattedSearchResult = new HashMap<>();
            String currentResultTitle = result.findElement(TITLE_SELECTOR).getText();
            String currentResultUrl = result.findElement(URL_SELECTOR).getText().split("›")[0];
            String currentResultDescription = result.findElement(DESCRIPTION_SELECTOR).getText();

            formattedSearchResult.put("title", currentResultTitle);
            formattedSearchResult.put("url", currentResultUrl);
            formattedSearchResult.put("description", currentResultDescription);
            formattedSearchResult.put("engine", "yandex");

            formattedSearchResults.add(formattedSearchResult);
        }
        return formattedSearchResults;
    }
}