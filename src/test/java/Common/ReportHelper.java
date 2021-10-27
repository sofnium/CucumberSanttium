package Common;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportHelper {

    private final String URI = "http://api.santtium.sofnium.com/api/";
    private String token;
    private String _idTestRun = "6161b15b-b560-4a6b-872f-08d99975ed6f";
    private String _idProject = "d3c55d52-5714-4616-2720-08d9996151a8";
    private String feature;

    private List<StepResult> _lstStepResults;
    private StepResult _stepResult;

    public ReportHelper() {
        _lstStepResults = new ArrayList<>();
        _stepResult = new StepResult();
        doLogin();
    }

    private void doLogin() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "");
        requestParams.put("password", "");
        Response response = new HttpHelper().invokePost(URI, "Auth/login", requestParams, "application/json", "");
        JsonPath jResult = response.jsonPath();
        token = jResult.get("result.token");
    }

    private void createTestRun() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("version", "5.0.4");
        requestParams.put("idTestPlan", "dcc02419-4dcc-450f-b0d6-08d999631717");
        requestParams.put("type", "0");
        requestParams.put("idDevice", "4aa25835-45bd-482b-a2da-08d99963ff0d");
        requestParams.put("idProject", "d3c55d52-5714-4616-2720-08d9996151a8");
        Response response = new HttpHelper().invokePost(URI, "TestRun/Add", requestParams, "application/json", token);
        JsonPath jResult = response.jsonPath();
        _idTestRun = jResult.get("result");
    }

    public String getIdStepByFeatureScenario(int noStep, String scenario) {
        JSONObject requestFeature = new JSONObject();
        requestFeature.put("description", feature);
        requestFeature.put("idProject", _idProject);

        JSONObject requestScenario = new JSONObject();
        requestScenario.put("description", scenario);
        requestScenario.put("feature", requestFeature);

        JSONObject requestParams = new JSONObject();
        requestParams.put("number", noStep);
        requestParams.put("scenario", requestScenario);

        Response response = new HttpHelper().invokePost(URI, "Step/GetStepByFeatureScenarioNoStep", requestParams, "application/json", token);
        JsonPath jResult = response.jsonPath();
        return jResult.get("result.id");
    }

    public void addEvidenceToTestRun() {
        _lstStepResults.add(_stepResult);
        _stepResult = new StepResult();
    }

    public void addFeature(String feature) {
        this.feature = feature;
    }

    public void addObservation(String observation) {
        _stepResult.setObservations(_stepResult.getObservations() + ". " + observation);
    }

    public void addData(String data) {
        _stepResult.setData(data);
    }

    public void addSatus(int status) {
        _stepResult.setStatus(status);
    }

    private void addIdStep(String idStep) {
        _stepResult.setIdStep(idStep);
    }

    public void addStep(int noStep, String step, String scenario) {
        String idStep = getIdStepByFeatureScenario(noStep, scenario);
        addIdStep(idStep);
    }

    public void addImg(String img) {
        _stepResult.setStringFile(img);
    }

    public void uploadResults() {
        if(_lstStepResults.size() == 0) {
            return;
        }
        Map<String, String> params = new HashMap();
        for (int i = 0; i < _lstStepResults.size(); i++) {
            params.put("stepsResult[" + i + "].IdTestRun", _idTestRun);
            params.put("stepsResult[" + i + "].IdStep", _lstStepResults.get(i).getIdStep());
            if(_lstStepResults.get(i).getObservations() != null && !_lstStepResults.get(i).getObservations().equals("")) {
                params.put("stepsResult[" + i + "].Observations", _lstStepResults.get(i).getObservations());
            }
            if(_lstStepResults.get(i).getData() != null &&!_lstStepResults.get(i).getData().equals("")) {
                params.put("stepsResult[" + i + "].Data", _lstStepResults.get(i).getData());
            }
            if(_lstStepResults.get(i).getStringFile() != null && !_lstStepResults.get(i).getStringFile().equals("")) {
                params.put("stepsResult[" + i + "].StringFile", _lstStepResults.get(i).getStringFile().split(",")[1]);
            }
            params.put("stepsResult[" + i + "].Status", String.valueOf(_lstStepResults.get(i).getStatus()));
        }
        Response response = HttpHelper.invokePostFormData(URI, "TestRun/AddStepsResultToTestResult", token, params);
        System.out.println(response.body().asPrettyString());
    }
}
