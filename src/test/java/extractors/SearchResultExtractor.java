package extractors;

import containers.TestSessionData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static containers.TestSessionData.searchResults;

public class SearchResultExtractor {

    private static final Logger log = LogManager.getLogger(SearchResultExtractor.class);

    /**
     * Method for comparing if search result contains at LEAST one keyword from the String that we use
     * TestSessionData.searchKeyword which we split on space character
     *
     * @param searchResults - Every search result is a Map containing url, title & description key with corresponding values
     */
    public static void extractSearchResultsContainingKeyword(List<Map<String, String>> searchResults) {

        List<Map<String, String>> resultsContainingKeyword = new ArrayList<>();
        List<Map<String, String>> resultsNotContainingKeyword = new ArrayList<>();

        List<String> splitKeywords = Arrays.stream(TestSessionData.searchKeyword.split(" "))
                .map(String::toLowerCase)
                .toList();

        for (Map<String, String> searchResult : searchResults) {
            if (splitKeywords.stream().anyMatch(currentKeyword ->
                    searchResult.get("title").toLowerCase().contains(currentKeyword) ||
                            searchResult.get("url").toLowerCase().contains(currentKeyword) ||
                            searchResult.getOrDefault("description", "-").toLowerCase().contains(currentKeyword))) {
                resultsContainingKeyword.add(searchResult);
            } else {
                resultsNotContainingKeyword.add(searchResult);
            }
        }

        log.info("----------------------- Results containing keyword : " + TestSessionData.searchKeyword + " -----------------------");
        resultsContainingKeyword.forEach(result -> {
            log.info("Website Title : " + result.get("title"));
            log.info("Website Url : " + result.get("url"));
            log.info("Description : " + result.getOrDefault("description", "No description provided for search result."));
            System.out.println();
        });

        log.info("----------------------- Results not containing keyword : " + TestSessionData.searchKeyword + " -----------------------");
        if (resultsNotContainingKeyword.isEmpty()) {
            log.info("Couldn't find any results that don't contain keyword : " + TestSessionData.searchKeyword);
        } else {
            resultsNotContainingKeyword.forEach(result -> {
                log.info("Website Title : " + result.get("title"));
                log.info("Website Url : " + result.get("url"));
                log.info("Description : " + result.getOrDefault("description", "No description provided for search result."));
                System.out.println();
            });
        }
    }

    /**
     * Method for extracting same search results by title in different search engines
     */
    public static void extractSearchResultsPresentInBothSearchEngines() {

        List<Map<String, String>> commonResults = new ArrayList<>();

        for (int i = 0; i < searchResults.size() - 1; i++) {
            for (int j = i + 1; j < searchResults.size(); j++) {
                if (searchResults.get(i).get(0).get("keyword").equalsIgnoreCase(searchResults.get(j).get(0).get("keyword"))) {
                    Set<String> commonUrls = new HashSet<>();
                    searchResults.get(i).forEach(map -> commonUrls.add(map.get("title").toLowerCase()));
                    searchResults.get(j).stream()
                            .filter(map -> !commonUrls.add(map.get("title").toLowerCase()))
                            .forEach(commonResults::add);
                }
            }
        }

        System.out.println();
        log.info("============================== Common results by website title ==============================");
        for (Map<String, String> commonResult : commonResults) {
            log.info("Keyword : " + commonResult.get("keyword"));
            log.info("Title : " + commonResult.get("title"));
            log.info("Url : " + commonResult.get("url"));
            log.info("Description : " + commonResult.get("description"));
            System.out.println();
        }
    }
}