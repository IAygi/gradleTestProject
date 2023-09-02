package ru.iaygi.helpers;

import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import org.hamcrest.Matcher;

@AllArgsConstructor
public class BodyFieldCondition implements Condition {

    private final String path;
    private final Matcher matcher;

    @Override
    public void check(Response response) {
        response.then().assertThat().body(path, matcher);
    }

    @Override
    public String toString() {
        return "значение \"" + path + "\" : " + matcher.toString();
    }
}
