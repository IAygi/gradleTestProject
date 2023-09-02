package ru.iaygi.helpers;

import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import org.hamcrest.Matcher;

@AllArgsConstructor
public class BodySizeCondition implements Condition {

    private final Matcher matcher;

    @Override
    public void check(Response response) {
        response.then().assertThat().body("size()", matcher);
    }

    @Override
    public String toString() {
        return "размер: " + matcher.toString();
    }
}
