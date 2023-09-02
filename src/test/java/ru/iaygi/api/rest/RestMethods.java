package ru.iaygi.api.rest;

import ru.iaygi.dto.UpdateUserDTO;
import ru.iaygi.dto.UsersDTO;
import ru.iaygi.helpers.RestExecutor;
import ru.iaygi.ui.EndPoints;

import static io.restassured.http.ContentType.JSON;

public class RestMethods {

    public RestExecutor getAllUsers() {
        RestExecutor request = new RestExecutor(EndPoints.baseUrl)
                .contentType(JSON);
        request.get(EndPoints.getAllUsers);

        return request;
    }

    public RestExecutor getUser(String login) {
        UsersDTO UsersDTO = new UsersDTO().login(login);
        RestExecutor request = new RestExecutor(EndPoints.baseUrl)
                .contentType(JSON)
                .body(UsersDTO);
        request.post(EndPoints.getUser);

        return request;
    }

    public RestExecutor updateUser(UpdateUserDTO updateUserDTO) {
        RestExecutor request = new RestExecutor(EndPoints.baseUrl)
                .contentType(JSON)
                .body(updateUserDTO);
        request.put(EndPoints.updateUser);

        return request;
    }

    public RestExecutor createUser(UsersDTO usersDTO) {
        RestExecutor request = new RestExecutor(EndPoints.baseUrl)
                .contentType(JSON)
                .body(usersDTO);
        request.post(EndPoints.createUser);

        return request;
    }

    public RestExecutor deleteUser(String login) {
        UsersDTO UsersDTO = new UsersDTO().login(login);
        RestExecutor request = new RestExecutor(EndPoints.baseUrl)
                .contentType(JSON)
                .body(UsersDTO);
        request.delete(EndPoints.deleteUser);

        return request;
    }
}
