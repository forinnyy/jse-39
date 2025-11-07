//package ru.forinnyy.tm.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.NonNull;
//import lombok.SneakyThrows;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//import ru.forinnyy.tm.AbstractTest;
//import ru.forinnyy.tm.api.service.IConnectionService;
//import ru.forinnyy.tm.api.service.IPropertyService;
//import ru.forinnyy.tm.api.service.ISessionService;
//import ru.forinnyy.tm.api.service.IUserService;
//import ru.forinnyy.tm.enumerated.Role;
//import ru.forinnyy.tm.exception.field.LoginEmptyException;
//import ru.forinnyy.tm.exception.field.PasswordEmptyException;
//import ru.forinnyy.tm.exception.user.AccessDeniedException;
//import ru.forinnyy.tm.exception.user.PermissionException;
//import ru.forinnyy.tm.marker.UnitCategory;
//import ru.forinnyy.tm.model.Session;
//import ru.forinnyy.tm.model.User;
//import ru.forinnyy.tm.util.CryptUtil;
//
//import javax.security.sasl.AuthenticationException;
//import java.util.Date;
//
//@Category(UnitCategory.class)
//public final class AuthServiceTest extends AbstractTest {
//
//    private final IPropertyService propertyService = new PropertyService();
//    private final IConnectionService connectionService = new ConnectionService(propertyService);
//
//    private final ISessionService sessionService = new SessionService(connectionService);
//    private final IUserService userService = new UserService(propertyService, connectionService);
//    private final AuthService authService = new AuthService(propertyService, userService, sessionService);
//
//    @Test
//    @SneakyThrows
//    public void testLoginAndToken() {
//        userService.create("testUser", "testPassword");
//        @NonNull final User lockedUser = userService.create("lockedUser", "lockedPassword");
//        lockedUser.setLocked(true);
//
//        String token = authService.login("testUser", "testPassword");
//        Assert.assertNotNull(token);
//
//        Assert.assertThrows(LoginEmptyException.class,
//                () -> authService.login(null, "testPassword"));
//        Assert.assertThrows(LoginEmptyException.class,
//                () -> authService.login(EMPTY_STRING, "testPassword"));
//
//        Assert.assertThrows(PasswordEmptyException.class,
//                () -> authService.login("testUser", null));
//        Assert.assertThrows(PasswordEmptyException.class,
//                () -> authService.login("testUser", EMPTY_STRING));
//
//        Assert.assertThrows(AuthenticationException.class,
//                () -> authService.login("testUser", "wrongPassword"));
//        Assert.assertThrows(AccessDeniedException.class,
//                () -> authService.login("lockedUser", "lockedPassword"));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testValidateToken() {
//        userService.create("testUser", "testPassword", "testUser@example.com");
//
//        String token = authService.login("testUser", "testPassword");
//        Session session = authService.validateToken(token);
//        Assert.assertNotNull(session);
//
//        Assert.assertThrows(AccessDeniedException.class, () -> authService.validateToken(null));
//        Assert.assertThrows(AccessDeniedException.class, () -> authService.validateToken("invalidToken"));
//
//        authService.invalidate(session);
//        authService.invalidate(null);
//
//        Assert.assertThrows(AccessDeniedException.class, () -> authService.validateToken(token));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRegistry() {
//        User user = authService.registry("newUser", "newPassword", "newUser@example.com");
//        Assert.assertEquals("newUser", user.getLogin());
//
//        Assert.assertThrows(NullPointerException.class,
//                () -> authService.registry(null, null, null));
//        Assert.assertThrows(NullPointerException.class,
//                () -> authService.registry(null, null, "email@example.com"));
//        Assert.assertThrows(NullPointerException.class,
//                () -> authService.registry(null, "password", "email@example.com"));
//        Assert.assertThrows(NullPointerException.class,
//                () -> authService.registry(null, "password", null));
//        Assert.assertThrows(NullPointerException.class,
//                () -> authService.registry("newUser", null, "email@example.com"));
//        Assert.assertThrows(NullPointerException.class,
//                () -> authService.registry("newUser", null, null));
//        Assert.assertThrows(NullPointerException.class,
//                () -> authService.registry("newUser", "newPassword", null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCheck() {
//        userService.create("testUser", "testPassword", "testUser@example.com");
//        @NonNull final User lockedUser = userService.create("lockedUser", "lockedPassword");
//        lockedUser.setLocked(true);
//
//        User checkedUser = authService.check("testUser", "testPassword");
//        Assert.assertNotNull(checkedUser);
//        Assert.assertEquals("testUser", checkedUser.getLogin());
//
//        Assert.assertThrows(LoginEmptyException.class,
//                () -> authService.check(null , "testPassword"));
//        Assert.assertThrows(LoginEmptyException.class,
//                () -> authService.check(EMPTY_STRING, "testPassword"));
//
//        Assert.assertThrows(PasswordEmptyException.class,
//                () -> authService.check("testUser" , null));
//        Assert.assertThrows(PasswordEmptyException.class,
//                () -> authService.check("testUser", EMPTY_STRING));
//
//        Assert.assertThrows(PermissionException.class,
//                () -> authService.check("lockedUser", "lockedPassword"));
//        Assert.assertThrows(PermissionException.class,
//                () -> authService.check("testUser", "wrongPassword"));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testTimeout() {
//        User user = userService.create("testUser", "testPassword");
//        Assert.assertThrows(AccessDeniedException.class,
//                () -> authService.validateToken(getTokenWithTimeout(user)));
//    }
//
//    @NonNull
//    @SneakyThrows
//    private String getTokenWithTimeout(@NonNull final User user) {
//        @NonNull final Session session = new Session();
//        session.setUserId(user.getId());
//        @NonNull final Role role = user.getRole();
//        session.setRole(role);
//        session.setDate(new Date(System.currentTimeMillis() - (3 * 60 * 60 * 1000 + 1000)));
//        sessionService.add(session);
//
//        @NonNull final ObjectMapper objectMapper = new ObjectMapper();
//        @NonNull final String token = objectMapper.writeValueAsString(session);
//        @NonNull final String sessionKey = propertyService.getSessionKey();
//
//        return CryptUtil.encrypt(sessionKey, token);
//    }
//
//}