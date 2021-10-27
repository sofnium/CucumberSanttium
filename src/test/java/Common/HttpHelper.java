package Common;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class HttpHelper {

    public Response invokePost(String uri, String path, JSONObject requestParams, String contentType, String token) {
        RestAssured.baseURI = uri;
        RequestSpecification request = given();
        request.header("Content-Type", contentType);
        if(!token.equals("")) {
            request.header("Authorization", "Bearer "+ token);
        }
        request.body(requestParams.toJSONString());
        Response response = request.post(path);
        return response;
    }

    public static Response invokePostFormData(String uri, String path, String token, Map<String, String> params) {
        RestAssured.baseURI = uri;
        Response response = given()

                .header("Authorization", "Bearer " + token)
                //.header("Content-type", "multipart/form-data")
                .formParams(params)
                //.multiPart("asset", "image/png", fileContent).when()
                .post(path);
        return response;
    }
}
