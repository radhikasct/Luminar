package utils;

import org.openqa.selenium.By;



import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;

public class CucumberListener extends BaseUtil {

	@When("Feature Setup")
	public void feature_setup() {

	}

	@Before
	public void scenario_setup(Scenario scenario) {
		BaseUtil.sScenario = scenario.getName().trim();
		System.out.println("----------------------------------------");
		System.out.println("Start of Scenario - " + scenario.getName());
	}
	
}
