package ru.iaygi.helpers;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.NoAuthScheme;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RestExecutor {

    private String basePath = "";
    private String baseURI = "";
    private Object body = null;
    private Response response = null;
    private boolean uriDefined = false;
    private boolean pathDefined = false;
    private boolean bodyDefined = false;
    private boolean resetRequest = true;
    private RestAssuredConfig restAssuredConfig = RestAssured.config();
    private AuthenticationScheme authentication = new NoAuthScheme();
    private RequestSpecBuilder requestBuilder = new RequestSpecBuilder();

    public RestExecutor(String baseURI) {
        baseURI(baseURI);
        appendDefaultCharset(false);
        setRelaxedHTTPSValidation();
    }

    @Step("Ответ сервера содержит {condition}")
    public RestExecutor shouldHave(Condition condition) {
        condition.check(response);
        return this;
    }


    public <T> T getResponseAs(Class<T> tClass) {
        return response.as(tClass);
    }


    public <T> List<T> getResponseAsList(Class<T> tClass) {
        return getResponseAsJson().getList("$", tClass);
    }

    public JsonPath getResponseAsJson() {
        return response.jsonPath();
    }


    public static void resetRequestSpecification() {
        RestAssured.requestSpecification = null;
    }

    public RestExecutor resetAuth() {
        authentication = new NoAuthScheme();

        return this;
    }

    public RestExecutor resetRequest() {
        resetRequestSpecification();
        requestBuilder = new RequestSpecBuilder();

        return this;
    }

    public RestExecutor param(String paramKey, Object paramValue) {
        requestBuilder.addParam(paramKey, paramValue);

        return this;
    }

    public RestExecutor formParam(String paramKey, String paramValue) {
        requestBuilder.addFormParam(paramKey, paramValue);
        return this;
    }

    public RestExecutor pathParam(String paramKey, Object paramValue) {
        requestBuilder.addPathParam(paramKey, paramValue);

        return this;
    }

    public RestExecutor queryParam(String paramKey, Object paramValue) {
        requestBuilder.addQueryParam(paramKey, paramValue);

        return this;
    }

    public RestExecutor appendDefaultCharset(boolean value) {
        restAssuredConfig = restAssuredConfig.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(value));

        return this;
    }

    public RestExecutor setRelaxedHTTPSValidation() {
        RestAssured.useRelaxedHTTPSValidation();

        return this;
    }

    public RestExecutor baseURI(String baseURI) {
        this.baseURI = baseURI;
        uriDefined = true;

        return this;
    }

    public RestExecutor body(Object body) {
        if (body == null) {
            return this;
        }
        this.body = body;
        bodyDefined = true;

        return this;
    }

    public RestExecutor contentType(ContentType contentType) {
        requestBuilder.setContentType(contentType);

        return this;
    }

    public RestExecutor setSendFile(String name, String fileName, String fileType, String text) {
        MultiPartSpecBuilder multipart = new MultiPartSpecBuilder(text)
                .header("Content-Disposition", "form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"")
                .header("Content-Type", fileType);
        requestBuilder.addMultiPart(multipart.build());

        return this;
    }

    private static void setStaticRequestSpec(RequestSpecification specification) {
        RestAssured.requestSpecification = specification;
    }

    public Response get(String uri) {
        log.debug("Send GET request to URI: {uri}");
        return sendRequest(Method.GET, uri);
    }

    public Response post(String uri) {
        log.debug("Send POST request to URI: {uri}");
        return sendRequest(Method.POST, uri);
    }

    public Response put(String uri) {
        log.debug("Send PUT request to URI: {uri}");
        return sendRequest(Method.PUT, uri);
    }

    public Response delete(String uri) {
        log.debug("Send DELETE request to URI: {uri}");
        return sendRequest(Method.DELETE, uri);
    }

    private Response sendRequest(Method requestType, String uri) {
        if (uriDefined) {
            requestBuilder.setBaseUri(baseURI);
        }

        if (pathDefined) {
            requestBuilder.setBasePath(basePath);
        }

        if (bodyDefined) {
            requestBuilder.setBody(body);
        }

        requestBuilder.setConfig(restAssuredConfig)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter());

        requestBuilder.setAuth(authentication);
        setStaticRequestSpec(requestBuilder.build());

        switch (requestType) {
            case GET -> response = RestAssured.get(uri);
            case POST -> response = RestAssured.post(uri);
            case PUT -> response = RestAssured.put(uri);
            case DELETE -> response = RestAssured.delete(uri);
            case PATCH -> response = RestAssured.patch(uri);
        }

        if (resetRequest) {
            resetRequest();
        }

        return response;
    }
}
