package ru.iaygi.api.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import ru.iaygi.api.rest.RestMethods;

import static ru.iaygi.ui.EndPoints.baseUrl;

public class TestBase {

    static RestMethods restAssured;

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = baseUrl;
        restAssured = new RestMethods();
    }
}
