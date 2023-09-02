package ru.iaygi.helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContentTypeCondition implements Condition {

    private final ContentType contentType;

    @Override
    public void check(Response response) {
        response.then().assertThat().contentType(contentType);
    }
}
