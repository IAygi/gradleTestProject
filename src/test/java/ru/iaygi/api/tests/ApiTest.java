package ru.iaygi.api.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.*;
import ru.iaygi.api.data.UserData;
import ru.iaygi.dto.UpdateUserDTO;
import ru.iaygi.dto.UsersDTO;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.iaygi.api.data.UserData.userRandom;
import static ru.iaygi.helpers.Conditions.statusCode;

@Severity(NORMAL)
@Tag("apiTest")
@Tag("regression")
@Epic("Users")
@Feature("Работа с пользователями через API")
public class ApiTest extends TestBase {

    UsersDTO user;

    @BeforeEach
    void prepare() {
        user = UserData.createUser();
    }

    @AfterEach
    void cleanup() {
        user = null;
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверить корректное создание пользователя")
    void createUser() {

        step("Создать пользователя", () -> {
            restAssured.createUser(user).shouldHave(statusCode(201));
        });

        step("Проверить наличие созданного пользователя в списке пользователей", () -> {
            var res = restAssured.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UsersDTO.class);
            assertThat(res).extracting("login", "name", "surname")
                    .contains(tuple(user.login(), user.name(), user.surname()));
        });

        step("удалить пользователя по login", () -> {
            restAssured.deleteUser(user.login()).shouldHave(statusCode(204));
        });
    }

    @Test
    @Tag("smoke")
    @DisplayName("Получение списка пользователей")
    @Description("Проверить корректное получение списка пользователей")
    void getAllUsers() {

        step("Получить список пользователей", () -> {
            var res = restAssured.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UsersDTO.class);
            assertThat(res).extracting("login", "name", "surname")
                    .contains(tuple("admin", "admin", "admin"));
        });
    }

    @Test
    @Tag("smoke")
    @DisplayName("Получение пользователя по login")
    @Description("Проверить корректное получение пользователя по login")
    void getUser() {

        step("Получить пользователя admin", () -> {
            var res = restAssured.getUser("admin").shouldHave(statusCode(200)).getResponseAs(UsersDTO.class);
            assertAll(
                    () -> {
                        assertThat(res.login()).isEqualTo("admin");
                    },
                    () -> {
                        assertThat(res.name()).isEqualTo("admin");
                    },
                    () -> {
                        assertThat(res.surname()).isEqualTo("admin");
                    }
            );
        });
    }

    @Test
    @DisplayName("Обновление имени пользователя по login")
    @Description("Проверить корректное обновление имени пользователя по login")
    void updateUserNameByLogin() {

        step("Создать пользователя", () -> {
            restAssured.createUser(user);
        });

        String name = userRandom().name();

        step("Обновить имя пользователя по login", () -> {
            UpdateUserDTO updateUserDTO = new UpdateUserDTO()
                    .login(user.login())
                    .column("name")
                    .value(name);
            restAssured.updateUser(updateUserDTO).shouldHave(statusCode(200)).getResponseAs(UsersDTO.class);
        });

        step("Проверить наличие изменённого пользователя в списке пользователей", () -> {
            var res = restAssured.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UsersDTO.class);
            assertThat(res).extracting("login", "name")
                    .contains(tuple(user.login(), name));
        });

        step("удалить пользователя по login", () -> {
            restAssured.deleteUser(user.login()).shouldHave(statusCode(204));
        });
    }

    @Test
    @DisplayName("Обновление фамилии пользователя по login")
    @Description("Проверить корректное бновление фамилии пользователя по login")
    void updateUserSurnameByLogin() {

        step("Создать пользователя", () -> {
            restAssured.createUser(user);
        });

        String surname = userRandom().surname();

        step("Обновить фамилию пользователя по login", () -> {
            UpdateUserDTO updateUserDTO = new UpdateUserDTO()
                    .login(user.login())
                    .column("surname")
                    .value(surname);
            restAssured.updateUser(updateUserDTO).shouldHave(statusCode(200)).getResponseAs(UsersDTO.class);
        });

        step("Проверить наличие изменённого пользователя в списке пользователей", () -> {
            var res = restAssured.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UsersDTO.class);
            assertThat(res).extracting("login", "surname")
                    .contains(tuple(user.login(), surname));
        });

        step("удалить пользователя по login", () -> {
            restAssured.deleteUser(user.login()).shouldHave(statusCode(204));
        });
    }

    @Test
    @DisplayName("Удаление пользователя по login")
    @Description("Проверить корректное удаление пользователя по login")
    void deleteUserByLogin() {

        step("Создать пользователя", () -> {
            restAssured.createUser(user);
            var res = restAssured.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UsersDTO.class);
            assertThat(res).extracting("login", "name", "surname")
                    .contains(tuple(user.login(), user.name(), user.surname()));
        });

        step("удалить пользователя по login", () -> {
            restAssured.deleteUser(user.login()).shouldHave(statusCode(204));
        });

        step("Проверить отсутствие удалённого пользователя в списке пользователей", () -> {
            var res = restAssured.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UsersDTO.class);
            assertThat(res).extracting("login", "name")
                    .doesNotContain(tuple(user.login(), user.name()));
        });
    }
}
