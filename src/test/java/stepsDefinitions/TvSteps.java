package stepsDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.TvPage;

import java.io.IOException;

public class TvSteps {
    private TvPage tvPage;

    public TvSteps() {
        tvPage = new TvPage();
    }

    @Then("I navigate between the movies")
    public void iNavigateBetweenTheMovies() throws InterruptedException, IOException {
        tvPage.navegateByPage();
    }

    @Given("I validate label {string}")
    public void iValidateLabel(String label) throws InterruptedException {
        tvPage.validateLabelHome(label);
    }

    @When("I pause the player")
    public void iPauseThePlayer() throws InterruptedException {
        tvPage.pausePlayer();
    }
}
