package ru.iaygi.helpers;

import io.restassured.http.ContentType;
import org.hamcrest.Matcher;

public class Conditions {

    public static StatusCodeCondition statusCode(int statusCode) {
        return new StatusCodeCondition(statusCode);
    }
}
