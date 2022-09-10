# Testing Automation Framework
- POM design pattern
- Data Driven Testing approach
- Used for functional UI testing

## Prerequisites
- Maven build tool 3.8.6
- ![M2 Environment Variable](https://github.com/NemanjaVlaisavljevic/Automation-Framework/blob/master/m2_home_env_variable.png)
- ![MAVEN HOME Environment Variable](https://github.com/NemanjaVlaisavljevic/Automation-Framework/blob/master/maven_home_env_variable.png)
- Java 17 (if you are using Windows set `JAVA_HOME` environment variable to point to jdk root folder e.g. `C:\Program Files\Java\jdk-17.0.3`)
- ![Java Environment Variable](https://github.com/NemanjaVlaisavljevic/Automation-Framework/blob/master/java_env_variable.png)
- Edit System variables/Path and add the below two variables
- ![System variables/Path](https://github.com/NemanjaVlaisavljevic/Automation-Framework/blob/master/system_Path_env_variables.png)

## Project Structure
- `src/test/java/containers/Hooks` - Contains @Before and @After methods that are used for setting up object, logging results and cleaning up after the tests are finished
- `src/test/java/containers/PomContainer` - Instantiation of POM classes
- `src/test/java/containers/TestSessionData` - Contains all variables used through the test suite execution (shared variable between tests)
- `src/test/java/extractors/SearchResultExtractor` - Used for extracting search results and logging them onto the console
- `src/test/java/helpers/excel/ExcelHelper` - Contains methods used for working with Excel files
- `src/test/java/helpers/ui/BaseDriver` - Used for instantiating browser used in UI tests
- `src/test/java/helpers/ui/BasePageObject` - Contains methods used for interacting with selenium web elements (click, sendKeys, explicit waits etc.)
- `src/test/java/poms` - Containing Page Object Model classes for search engines used in test cases
- `src/test/java/runners/SearchEngineTests` - Runnable class containing all parameterized test cases and methods for getting those parameters
- `src/test/resources/log4j2` - XML file for Log4j configuration
- `src/test/resources/testData` - Excel file containing keywords which are used as parameters for UI tests
- `src/test/resources/testParameters` - Excel file containing parameters used for choosing browser and url for POMs

## Dependencies
- All dependencies used in the project are located in pom.xml file
- **Junit** - 5.9.0 - Testing framework used to execute tests
- **Selenium** - 4.4.0 - Used for browser automation
- **WebDriverManager** - 5.3.0 - Installing and setting up drivers for any browser we use with selenium
- **ApachePOI** - 5.2.2 - Used for working with Excel files
- **Log4J** - 2.18.0 - Used for logging
- **MavenSurefire** - 3.0.0-M7 - Used for running test cases using maven lifecycle method (test)

## Running Tests
- testData.xlsx file should contain at least one keyword that will be used as parameter
- testParameters.xlsx file needs to contain URLs for main search page of given search engines and which browser (`chrome` or `firefox`) you want to run the test cases on
- **Running test cases from IntelliJ** - Run SearchEngineTests class
- **Running test cases from Terminal** - Run `mvn clean test` command