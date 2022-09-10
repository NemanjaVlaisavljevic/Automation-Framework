package containers;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Containing fields that we use throughout test suite execution
 */
public class TestSessionData {

    private WebDriver driver;
    private PomContainer pomContainer;
    public static List<HashMap<String, String>> testParameters = new ArrayList<>();
    public static String searchKeyword;
    public static List<String> searchKeywords = new ArrayList<>();
    public static List<List<Map<String, String>>> searchResults = new ArrayList<>();

    public TestSessionData() { }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public PomContainer getPomContainer() {
        return pomContainer;
    }

    public void setPomContainer(PomContainer pomContainer) {
        this.pomContainer = pomContainer;
    }

}
