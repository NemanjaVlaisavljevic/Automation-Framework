package runners;

import containers.Hooks;
import containers.TestSessionData;
import extractors.SearchResultExtractor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchEngineTests extends Hooks {

    @ParameterizedTest
    @MethodSource("provideKeywordForSearch")
    public void searchGoogleByKeyword(String keyword) {

        String googleSearchUrl = TestSessionData.testParameters.get(0)
                .get("Google Search Engine URL");

        pages.googleSearchPage.openWebPage(googleSearchUrl);
        pages.googleSearchPage.searchByKeyword(keyword);

        TestSessionData.searchKeyword = keyword;

        List<Map<String, String>> searchResults = pages.googleSearchPage.extractGoogleSearchResults();
        assertNotNull(searchResults, "No results found for keyword : " + keyword);
        searchResults.forEach(result -> result.put("keyword", keyword));

        TestSessionData.searchResults.add(searchResults);

        SearchResultExtractor.extractSearchResultsContainingKeyword(searchResults);
    }

    @ParameterizedTest
    @MethodSource("provideKeywordForSearch")
    public void searchYandexByKeyword(String keyword) {

        String yandexSearchUrl = TestSessionData.testParameters
                .get(0).get("Yandex Search Engine URL");

        pages.yandexSearchPage.openWebPage(yandexSearchUrl);
        pages.yandexSearchPage.searchByKeyword(keyword);

        TestSessionData.searchKeyword = keyword;

        List<Map<String, String>> searchResults = pages.yandexSearchPage.extractYandexSearchResults();
        assertNotNull(searchResults, "No results found for keyword : " + keyword);
        searchResults.forEach(result -> result.put("keyword", keyword));

        TestSessionData.searchResults.add(searchResults);

        SearchResultExtractor.extractSearchResultsContainingKeyword(searchResults);
    }

    public static Object[] provideKeywordForSearch() {

        Object[] parameters = new Object[TestSessionData.searchKeywords.size()];

        for (int i = 0; i < TestSessionData.searchKeywords.size(); i++) {
            parameters[i] = TestSessionData.searchKeywords.get(i);
        }
        return parameters;
    }
}