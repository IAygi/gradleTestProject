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
@Tag("api_test")
@Tag("regression")
@Epic("Users")
@Feature("Работа с пользователями через API")
public class ApiTest extends TestBaseApi {

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

        step("Удалить пользователя по login", () -> {
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
    @DisplayName("Получение несуществующего пользователя по login")
    @Description("Проверить, что при получении несуществующего пользователя по login ответ 404")
    void getNotRealUser() {
        String login = userRandom().login() + "-not-real";

        step("Получить пользователя по login", () -> {
            restAssured.getUser(login).shouldHave(statusCode(404));
        });
    }

    @Test
    @DisplayName("Обновление имени пользователя по login")
    @Description("Проверить корректное обновление имени пользователя по login")
    void updateUserNameByLogin() {
        String name = userRandom().name();

        step("Создать пользователя", () -> {
            restAssured.createUser(user);
        });

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

        step("Удалить пользователя по login", () -> {
            restAssured.deleteUser(user.login()).shouldHave(statusCode(204));
        });
    }

    @Test
    @DisplayName("Обновление фамилии пользователя по login")
    @Description("Проверить корректное бновление фамилии пользователя по login")
    void updateUserSurnameByLogin() {
        String surname = userRandom().surname();

        step("Создать пользователя", () -> {
            restAssured.createUser(user);
        });

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

        step("Удалить пользователя по login", () -> {
            restAssured.deleteUser(user.login()).shouldHave(statusCode(204));
        });
    }

    @Test
    @DisplayName("Удаление пользователя по login")
    @Description("Проверить корректное удаление пользователя по login")
    void deleteNotRealUserByLogin() {
        step("Создать пользователя", () -> {
            restAssured.createUser(user);
            var res = restAssured.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UsersDTO.class);
            assertThat(res).extracting("login", "name", "surname")
                    .contains(tuple(user.login(), user.name(), user.surname()));
        });

        step("Удалить пользователя по login", () -> {
            restAssured.deleteUser(user.login()).shouldHave(statusCode(204));
        });

        step("Проверить отсутствие удалённого пользователя в списке пользователей", () -> {
            var res = restAssured.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UsersDTO.class);
            assertThat(res).extracting("login", "name")
                    .doesNotContain(tuple(user.login(), user.name()));
        });
    }

    @Test
    @DisplayName("Удаление несуществующего пользователя по login")
    @Description("Проверить, что при удалении несуществующего пользователя по login ответ 404")
    void deleteUserByLogin() {
        String login = userRandom().login() + "-not-real";

        step("Удалить пользователя по login", () -> {
            restAssured.deleteUser(login).shouldHave(statusCode(404));
        });
    }
}
