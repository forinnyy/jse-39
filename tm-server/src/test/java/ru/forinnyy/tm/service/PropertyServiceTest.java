//package ru.forinnyy.tm.service;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//import ru.forinnyy.tm.marker.UnitCategory;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//
//@Category(UnitCategory.class)
//public final class PropertyServiceTest {
//
//    private PropertyService propertyService;
//    private File configFile;
//
//    @Before
//    public void init() throws IOException {
//        configFile = new File("application.properties");
//        if (configFile.exists()) {
//            configFile.delete();
//        }
//        configFile.createNewFile();
//
//        try (FileWriter writer = new FileWriter(configFile)) {
//            writer.write("session.timeout=10800\n");
//            writer.write("application.name=TestApplication\n");
//            writer.write("server.port=6060\n");
//        }
//
//        propertyService = new PropertyService();
//    }
//
//    @Test
//    public void testGetSessionKey() {
//        String sessionKey = propertyService.getSessionKey();
//        assertNotNull(sessionKey);
//    }
//
//    @Test
//    public void testGetSessionTimeout() {
//        Integer timeout = propertyService.getSessionTimeout();
//        Assert.assertEquals((Integer) 10800, timeout);
//
//        System.setProperty("session.timeout", "10800");
//        timeout = propertyService.getSessionTimeout();
//        Assert.assertEquals((Integer) 10800, timeout);
//    }
//
//    @Test
//    public void testGetApplicationName() {
//        String appName = propertyService.getApplicationName();
//        assertEquals("Application name should be TestApplication", "TestApplication", appName);
//    }
//
//    @Test
//    public void testGetPort() {
//        String port = propertyService.getPort();
//        assertEquals("Port should be 8080 by default", "8080", port);
//    }
//
//    @Test
//    public void testGetGitBranch() {
//        String gitBranch = propertyService.getGitBranch();
//        assertEquals("Git branch should be 'GIT_BRANCH'", "GIT_BRANCH", gitBranch);
//    }
//
//    @Test
//    public void testGetGitCommitId() {
//        String commitId = propertyService.getGitCommitId();
//        assertEquals("Git commit ID should be 'GIT_COMMIT_ID'", "GIT_COMMIT_ID", commitId);
//    }
//
//    @Test
//    public void testGetApplicationConfig() {
//        String config = propertyService.getApplicationConfig();
//        assertEquals("Application config file name should be 'application.properties'", "application.properties", config);
//    }
//
//    @Test
//    public void testFileLoading() throws IOException {
//        propertyService = new PropertyService();
//        Integer timeout = propertyService.getSessionTimeout();
//        assertEquals((Integer) 10800, timeout);
//    }
//
//    @Test
//    public void testMissingConfigFile() throws IOException {
//        configFile.delete();
//        propertyService = new PropertyService();
//        Integer timeout = propertyService.getSessionTimeout();
//        assertEquals("Default timeout should be 10800", (Integer) 10800, timeout);
//    }
//
//    @Test
//    public void testGetPasswordIteration() {
//        Integer iterations = propertyService.getPasswordIteration();
//        assertEquals((Integer) 25456, iterations);
//    }
//
//    @Test
//    public void testGetPasswordSecret() {
//        String secret = propertyService.getPasswordSecret();
//        assertEquals("345345345345", secret);
//    }
//
//    @After
//    public void tearDown() {
//        if (configFile.exists()) {
//            configFile.delete();
//        }
//    }
//
//}
