package ru.iaygi.api.data;

import com.github.javafaker.Faker;

public class FakeData {

    private static final Faker faker = new Faker();

    public static String login() {
        return faker.name().username();
    }

    public static String firstName() {
        return faker.name().firstName();
    }

    public static String lastName() {
        return faker.name().lastName();
    }

    public static String cityName() {
        return faker.address().city();
    }

    public static int number() {
        return faker.number().numberBetween(18, 60);
    }
}
