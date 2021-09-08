    package testRunner;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import utils.BaseUtil;


@CucumberOptions(

		features= {  ".//src/test/resources/feature/Luminar.feature",
				
					},
		glue = {"stepDef",
				}, 
		dryRun = false,  
		tags = {"not @not_ready"},
		monochrome = true, 
		strict = true,	
		
		plugin = { "pretty","html:test-output", "json:target/cucumber.json"})
		
public class TestNGRunner {
	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		
	}

	@Test(groups = "Cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
	public void scenario(PickleEventWrapper pickleEvent, CucumberFeatureWrapper cucumberFeature) throws Throwable {
		testNGCucumberRunner.runScenario(pickleEvent.getPickleEvent());
				
	}


	@DataProvider
	public Object[][] scenarios() throws InterruptedException {		
		return testNGCucumberRunner.provideScenarios();
	}

   // @AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		testNGCucumberRunner.finish();
//		BaseUtil.logger.info("Teardown");
//		BaseUtil.driver.quit();
	}
}
