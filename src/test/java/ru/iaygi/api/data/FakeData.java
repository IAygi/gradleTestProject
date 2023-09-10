package ru.iaygi.api.data;

import com.github.javafaker.Faker;

public class FakeData {

    private static final Faker faker = new Faker();

    public static String login() {
        return fakerResult(faker.name().username());
    }

    public static String firstName() {
        return fakerResult(faker.name().firstName());
    }

    public static String lastName() {
        return fakerResult(faker.name().lastName());
    }

    public static String cityName() {
        return fakerResult(faker.address().city());
    }

    public static int number() {
        return faker.number().numberBetween(18, 60);
    }

    public static String fakerResult(String result) {
        return result.replaceAll("[.^=\"':,]", "-");
    }
}
