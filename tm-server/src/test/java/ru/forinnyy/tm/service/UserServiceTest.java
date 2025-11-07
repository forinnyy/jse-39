//package ru.forinnyy.tm.service;
//
//import lombok.NonNull;
//import lombok.SneakyThrows;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//import ru.forinnyy.tm.AbstractTest;
//import ru.forinnyy.tm.api.service.IConnectionService;
//import ru.forinnyy.tm.enumerated.Role;
//import ru.forinnyy.tm.exception.entity.UserNotFoundException;
//import ru.forinnyy.tm.exception.field.*;
//import ru.forinnyy.tm.exception.user.ExistsEmailException;
//import ru.forinnyy.tm.exception.user.ExistsLoginException;
//import ru.forinnyy.tm.marker.UnitCategory;
//import ru.forinnyy.tm.model.User;
//import ru.forinnyy.tm.util.HashUtil;
//
//@Category(UnitCategory.class)
//public final class UserServiceTest extends AbstractTest {
//
//    private IConnectionService connectionService;
//    private PropertyService propertyService;
//    private UserService userService;
//
//    @Before
//    public void setUp() {
//        propertyService = new PropertyService();
//        connectionService = new ConnectionService(propertyService);
//        userService = new UserService(propertyService, connectionService);
//
//        userService.initTable();
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCreateUserWithValidLoginAndPassword() {
//        User user = userService.create("login", STRING);
//        Assert.assertEquals("login", user.getLogin());
//
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.create(null, STRING));
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.create(EMPTY_STRING, STRING));
//
//        Assert.assertThrows(PasswordEmptyException.class, () -> userService.create(STRING, null));
//        Assert.assertThrows(PasswordEmptyException.class, () -> userService.create(STRING, EMPTY_STRING));
//
//        Assert.assertThrows(ExistsLoginException.class, () -> userService.create("login", STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCreateUserWithEmail() {
//        User user = userService.create("login", STRING, "email");
//        Assert.assertEquals("login", user.getLogin());
//
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.create(null, STRING, STRING));
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.create(EMPTY_STRING, STRING, STRING));
//
//        Assert.assertThrows(PasswordEmptyException.class, () -> userService.create(STRING, null, STRING));
//        Assert.assertThrows(PasswordEmptyException.class, () -> userService.create(STRING, EMPTY_STRING, STRING));
//
//        Assert.assertThrows(ExistsLoginException.class, () -> userService.create("login", STRING, STRING));
//        Assert.assertThrows(ExistsEmailException.class, () -> userService.create(STRING, STRING, "email"));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCreateUserWithRole() {
//        User user = userService.create("login", STRING, Role.ADMIN);
//        Assert.assertEquals("login", user.getLogin());
//
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.create(null, STRING, Role.ADMIN));
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.create(EMPTY_STRING, STRING, Role.ADMIN));
//
//        Assert.assertThrows(PasswordEmptyException.class, () -> userService.create(STRING, null, Role.ADMIN));
//        Assert.assertThrows(PasswordEmptyException.class, () -> userService.create(STRING, EMPTY_STRING, Role.ADMIN));
//
//        Assert.assertThrows(ExistsLoginException.class, () -> userService.create("login", STRING, Role.ADMIN));
//        Assert.assertThrows(RoleEmptyException.class, () -> userService.create(STRING, STRING, (Role) null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testFindBy() {
//        User user = userService.create(STRING, STRING, STRING);
//        User foundUser = userService.findByLogin(STRING);
//        User foundUserByEmail = userService.findByEmail(STRING);
//
//        Assert.assertEquals(foundUser, user);
//        Assert.assertEquals(foundUserByEmail, user);
//
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.findByLogin(null));
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.findByLogin(EMPTY_STRING));
//        Assert.assertThrows(UserNotFoundException.class, () -> userService.findByLogin("wrongLogin"));
//
//        Assert.assertThrows(EmailEmptyException.class, () -> userService.findByEmail(null));
//        Assert.assertThrows(EmailEmptyException.class, () -> userService.findByEmail(EMPTY_STRING));
//        Assert.assertThrows(UserNotFoundException.class, () -> userService.findByEmail("wrongEmail"));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveByLogin() {
//        User user = userService.create(STRING, STRING);
//        User removedUser = userService.removeByLogin(STRING);
//        Assert.assertEquals(0, userService.findAll().size());
//        Assert.assertEquals(user, removedUser);
//
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.removeByLogin(null));
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.removeByLogin(EMPTY_STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveByEmail() {
//        User user = userService.create(STRING, STRING, STRING);
//        User removedUser = userService.removeByEmail(STRING);
//        Assert.assertEquals(0, userService.findAll().size());
//        Assert.assertEquals(user, removedUser);
//
//        Assert.assertThrows(EmailEmptyException.class, () -> userService.removeByEmail(null));
//        Assert.assertThrows(EmailEmptyException.class, () -> userService.removeByEmail(EMPTY_STRING));
//    }
//
//    @Test
//    public void testRemove() {
//        Assert.assertThrows(NullPointerException.class, () -> userService.remove(null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testLockUserByLogin() {
//        User user = userService.create(STRING, STRING);
//        userService.lockUserByLogin(STRING);
//        Assert.assertTrue(user.isLocked());
//
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.lockUserByLogin(null));
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.lockUserByLogin(EMPTY_STRING));
//        Assert.assertThrows(UserNotFoundException.class, () -> userService.lockUserByLogin("wrongLogin"));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testUnlockUserByLogin() {
//        User user = userService.create(STRING, STRING);
//        user.setLocked(true);
//        userService.unlockUserByLogin(STRING);
//        Assert.assertFalse(user.isLocked());
//
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.unlockUserByLogin(null));
//        Assert.assertThrows(LoginEmptyException.class, () -> userService.unlockUserByLogin(EMPTY_STRING));
//        Assert.assertThrows(UserNotFoundException.class, () -> userService.unlockUserByLogin("wrongLogin"));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testSetPassword() {
//        User user = userService.create(STRING, "oldPassword");
//        user.setId(UUID1);
//        userService.add(user);
//
//        User updatedUser = userService.setPassword(UUID1, "newPassword");
//        Assert.assertNotNull(updatedUser);
//        Assert.assertEquals(HashUtil.salt(propertyService, "newPassword"), updatedUser.getPasswordHash());
//    }
//
//    @Test
//    @SneakyThrows
//    public void testSetPasswordWhenPasswordEmpty() {
//        User user = userService.create(STRING, STRING);
//        user.setId(UUID1);
//
//        Assert.assertThrows(PasswordEmptyException.class, () -> userService.setPassword(UUID1, EMPTY_STRING));
//
//        Assert.assertThrows(NullPointerException.class, () -> userService.setPassword(null, STRING));
//        Assert.assertThrows(NullPointerException.class, () -> userService.setPassword(UUID1, null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testIsLoginAndEmailExist() {
//        userService.create(STRING, STRING, STRING);
//
//        Assert.assertTrue(userService.isLoginExist(STRING));
//        Assert.assertTrue(userService.isEmailExist(STRING));
//
//        Assert.assertFalse(userService.isLoginExist(null));
//        Assert.assertFalse(userService.isLoginExist(EMPTY_STRING));
//
//        Assert.assertFalse(userService.isEmailExist(null));
//        Assert.assertFalse(userService.isEmailExist(EMPTY_STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testUpdateUser() {
//        @NonNull final User user = userService.create(STRING, STRING);
//        user.setId(UUID1);
//        userService.add(user);
//        userService.updateUser(UUID1, "name", "lastname", "middlename");
//        Assert.assertEquals("name", user.getFirstName());
//        Assert.assertEquals("lastname", user.getLastName());
//        Assert.assertEquals("middlename", user.getMiddleName());
//
//        Assert.assertThrows(IdEmptyException.class, () -> userService.updateUser(EMPTY_STRING, STRING, STRING, STRING));
//
//        Assert.assertThrows(NullPointerException.class, () -> userService.updateUser(null, STRING, STRING, STRING));
//        Assert.assertThrows(NullPointerException.class, () -> userService.updateUser(STRING, null, STRING, STRING));
//        Assert.assertThrows(NullPointerException.class, () -> userService.updateUser(STRING, STRING, null, STRING));
//        Assert.assertThrows(NullPointerException.class, () -> userService.updateUser(STRING, STRING, STRING, null));
//    }
//
//}