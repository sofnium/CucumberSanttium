package stepsDefinitions;

import io.cucumber.java.en.Given;

public class CommonSteps {
    @Given("Initialize test {string}")
    public void initializeTest(String testName) {
        Context.reportHelper.addFeature(testName);
    }
}
