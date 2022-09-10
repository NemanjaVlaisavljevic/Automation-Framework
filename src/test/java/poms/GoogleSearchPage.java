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

public class GoogleSearchPage extends BasePageObject {

    @FindBy(name = "q")
    WebElement searchInput;

    @FindBy(css = ".xpdopen .g > div > div > div > a")
    WebElement partialFirstWebsiteUrlAndTitle;

    @FindBy(css = "div[data-header-feature=\"0\"] > div > a")
    List<WebElement> urlAndTitle;

    @FindBy(css = "div[data-content-feature=\"1\"] > div")
    List<WebElement> description;

    private final By TITLE_SELECTOR = By.cssSelector("h3");
    private final By URL_SELECTOR = By.cssSelector("cite");

    private String currentSearchKeyword;

    private final long ELEMENT_WAIT_TIME = 5;

    private final Logger log = LogManager.getLogger(GoogleSearchPage.class);

    public GoogleSearchPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void searchByKeyword(String keyword) {

        log.info("Searching Google search engine by keyword : " + keyword);
        currentSearchKeyword = keyword;
        waitToBeClickable(searchInput, ELEMENT_WAIT_TIME);
        searchInput.sendKeys(keyword);
        searchInput.sendKeys(Keys.ENTER);
    }

    public List<Map<String, String>> extractGoogleSearchResults() {

        if (urlAndTitle.size() == 0) {
            log.info("No results found for given keyword : " + currentSearchKeyword);
            return null;
        }

        List<WebElement> searchResultElements = new ArrayList<>();

        boolean doesPartialFirstElementExist = false;
        if (waitToBeClickable(partialFirstWebsiteUrlAndTitle, ELEMENT_WAIT_TIME)) {
            searchResultElements.add(partialFirstWebsiteUrlAndTitle);
            doesPartialFirstElementExist = true;
        }
        searchResultElements.addAll(urlAndTitle);

        log.info("Number of search results : " + searchResultElements.size());

        if (searchResultElements.size() > 10) {
            searchResultElements = searchResultElements.subList(0, 10);
        }

        return extractSearchResultsComponents(searchResultElements, doesPartialFirstElementExist);
    }

    /**
     * Method for extracting url, title and description from given search results
     *
     * @param doesPartialFirstElementExist - First element on search page can be without description,
     *                                     so we need to check if he exists
     */
    private List<Map<String, String>> extractSearchResultsComponents(List<WebElement> searchResultWebElements,
                                                                     boolean doesPartialFirstElementExist) {

        List<Map<String, String>> formattedSearchResults = new ArrayList<>();

        for (int i = 0; i < searchResultWebElements.size(); i++) {

            Map<String, String> parsedSearchResult = new HashMap<>();
            String currentResultTitle = searchResultWebElements.get(i).findElement(TITLE_SELECTOR).getText();
            String currentResultUrl = searchResultWebElements.get(i).findElement(URL_SELECTOR).getText().split("›")[0];

            parsedSearchResult.put("title", currentResultTitle);
            parsedSearchResult.put("url", currentResultUrl);

            if (doesPartialFirstElementExist) {
                if (i > 0) {
                    parsedSearchResult.put("description", description.get(i - 1).getText());
                }
            } else {
                parsedSearchResult.put("description", description.get(i).getText());
            }
            parsedSearchResult.put("engine", "google");
            formattedSearchResults.add(parsedSearchResult);
        }
        return formattedSearchResults;
    }
}