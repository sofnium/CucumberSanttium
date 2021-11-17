package stepsDefinitions;

import io.cucumber.java.*;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import santtium.helpers.ReportHelper;
import santtium.implement.SanttiumProvider;
import santtium.interfaces.Santtium;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class Context {

    public static Santtium santtium;
    private static int _noStep;
    private List<PickleStepTestStep> testSteps;
    public static ReportHelper reportHelper;
    //Data for API SANTTIUM

    private static String username = "USERNAME";
    private static String password = "PASSWORD";
    private static String idProyect = "ID PROJECT";
    private static String idTestPlan= "ID TEST PLAN";
    private static String idDevice= "ID DEVICE";
    private static String version= "1.0.0";
    private static String apiKey= "API KEY";
    //End Data

    @BeforeAll
    public static void init() throws Exception {
        reportHelper = new ReportHelper(idProyect,username,password,idTestPlan,idDevice,version);
        //You can call Overloaded constructor for use an existing TestRun
        //reportHelper = new ReportHelper(idProyect,username,password,"8abd1b96-173a-4d6e-473e-08d9a97dacce");
    }

    @Before
    public void before(Scenario scenario) throws Exception {
        System.out.println("primero");
        _noStep = 0;
        getListSteps(scenario);
        santtium = SanttiumProvider.GetProvider(System.getProperty("key_device"), apiKey, 10);
        santtium.start();
    }

    @AfterStep
    public void afterStep(Scenario scenario) throws Exception {
        if(_noStep == 0) {
            _noStep++;
            return;
        }
        String errors = getErrorsInScenario(scenario);
        String step = testSteps.get(_noStep).getStep().getText();
        reportHelper.addStep(_noStep, step, scenario.getName());
        if(!errors.equals("")) {
            reportHelper.addObservation(errors);
            reportHelper.addSatus(1);
        } else {
            reportHelper.addSatus(0);
            _noStep++;
        }
        String img = step.contains("player page") ? santtium.takeScreenShot(50) : santtium.takeScreenShotRaw(50);
        reportHelper.addImg(img);
        reportHelper.addEvidenceToTestRun();
    }

    @After
    public void afterScenario(Scenario scenario) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        // String errors = getErrorsInScenario(scenario);
        // String step = testSteps.get(_noStep).getStep().getText();
        // if(!errors.equals("")) {
        //     reportHelper.addStep(_noStep, step, scenario.getName());
        //     reportHelper.addObservation(errors);
        //     reportHelper.addSatus(1);
        //     // reportHelper.addImg(santtium.takeScreenShot(10));
        // }
        // reportHelper.addEvidenceToTestRun();
        // _noStep++;
        System.out.println("Fin del scenario");
    }

    @AfterAll
    public static void finish() throws Exception {
        santtium.stop();
        reportHelper.uploadResults();
    }

    private void getListSteps(Scenario scenario) throws NoSuchFieldException, IllegalAccessException {

        Field f = scenario.getClass().getDeclaredField("delegate");
        f.setAccessible(true);
        io.cucumber.core.backend.TestCaseState sc = (io.cucumber.core.backend.TestCaseState) f.get(scenario);
        Field f1 = sc.getClass().getDeclaredField("testCase");
        f1.setAccessible(true);
        io.cucumber.plugin.event.TestCase testCase = (io.cucumber.plugin.event.TestCase) f1.get(sc);
        testSteps = testCase.getTestSteps().stream().filter(x -> x instanceof PickleStepTestStep).map(x -> (PickleStepTestStep) x).collect(Collectors.toList());
    }

    private String getErrorsInScenario(Scenario scenario) throws NoSuchFieldException, IllegalAccessException {
        Field f = scenario.getClass().getDeclaredField("delegate");
        f.setAccessible(true);
        io.cucumber.core.backend.TestCaseState sc = (io.cucumber.core.backend.TestCaseState) f.get(scenario);
        Field f1 = sc.getClass().getDeclaredField("stepResults");
        f1.setAccessible(true);
        List<io.cucumber.plugin.event.Result> resultList = (List<Result>) f1.get(sc);
        String errors = "";
        for (io.cucumber.plugin.event.Result err: resultList) {
            if(err.getStatus() == Status.FAILED) {
                errors += " " + err.getError() + ".";
            }
        }
        return errors;
    }
}
