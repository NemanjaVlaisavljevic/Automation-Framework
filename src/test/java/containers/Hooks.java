package containers;


import extractors.SearchResultExtractor;
import helpers.excel.ExcelHelper;
import helpers.ui.BaseDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;

public class Hooks extends BaseDriver {

    private static final Logger log = LogManager.getLogger(Hooks.class);
    protected TestSessionData testSessionData;
    protected PomContainer pages;

    @BeforeAll
    public static void beforeAllTests() {
        log.info("Filling up test parameters collection...");
        TestSessionData.testParameters = ExcelHelper
                .getDataFromExcel("src/test/resources/testParameters.xlsx", "Parameters");
        ArrayList<HashMap<String, String>> inputParamsExcel = ExcelHelper
                .getDataFromExcel("src/test/resources/testData.xlsx", "InputParams");
        for (HashMap<String, String> stringStringHashMap : inputParamsExcel) {
            String currentKeyword = stringStringHashMap.get("Keyword");
            TestSessionData.searchKeywords.add(currentKeyword);
        }
    }

    @BeforeEach
    public void setUp() {
        testSessionData = new TestSessionData();
        driver = initializeDriver(TestSessionData.testParameters.get(0).get("Browser"));
        testSessionData.setDriver(driver);
        testSessionData.setPomContainer(new PomContainer(driver));
        pages = testSessionData.getPomContainer();
        driver.manage().deleteAllCookies();
    }

    @AfterEach
    public void tearDown() {
        testSessionData.getDriver().quit();
    }

    @AfterAll
    public static void logAllSearchResults() {
        SearchResultExtractor.extractSearchResultsPresentInBothSearchEngines();
    }
}