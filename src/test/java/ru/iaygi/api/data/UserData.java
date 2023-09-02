package ru.iaygi.api.data;

import lombok.NoArgsConstructor;
import ru.iaygi.dto.UsersDTO;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserData extends FakeData {

    public static UsersDTO userRandom() {
        return new UsersDTO()
                .login(login())
                .name(firstName())
                .surname(lastName())
                .city(cityName())
                .age(String.valueOf(number()));
    }

    public static UsersDTO createUser() {
        return new UsersDTO()
                .login(userRandom().login())
                .name(userRandom().name())
                .surname(userRandom().surname())
                .city(userRandom().city())
                .age(userRandom().age());
    }
}