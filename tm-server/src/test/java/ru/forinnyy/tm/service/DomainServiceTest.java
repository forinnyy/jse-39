//package ru.forinnyy.tm.service;
//
//import lombok.SneakyThrows;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//import ru.forinnyy.tm.AbstractTest;
//import ru.forinnyy.tm.component.Bootstrap;
//import ru.forinnyy.tm.dto.Domain;
//import ru.forinnyy.tm.enumerated.Role;
//import ru.forinnyy.tm.enumerated.Status;
//import ru.forinnyy.tm.marker.UnitCategory;
//import ru.forinnyy.tm.model.Project;
//import ru.forinnyy.tm.model.Task;
//import ru.forinnyy.tm.model.User;
//import ru.forinnyy.tm.util.HashUtil;
//
//import java.io.File;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Category(UnitCategory.class)
//public class DomainServiceTest extends AbstractTest {
//
//    private DomainService domainService;
//    private List<Project> projects;
//    private List<Task> tasks;
//    private List<User> users;
//    private Bootstrap bootstrap;
//
//    @Before
//    public void init() {
//        bootstrap = new Bootstrap();
//        domainService = (DomainService) bootstrap.getDomainService();
//        domainService.setDomain(null);
//        domainService.setDomain(initDataDomain());
//    }
//
//    public Domain initDataDomain() {
//        projects = Arrays.asList(
//                new Project("Test Project 1", Status.NOT_STARTED),
//                new Project("Test Project 2", Status.COMPLETED)
//        );
//        tasks = Arrays.asList(
//                new Task("Test Task 1"),
//                new Task("Test Task 2")
//        );
//
//        User admin = new User();
//        admin.setLogin("admin");
//        admin.setPasswordHash(HashUtil.salt(bootstrap.getPropertyService(), "password"));
//        admin.setEmail("admin@mail.ru");
//        admin.setRole(Role.ADMIN);
//
//        User test = new User();
//        test.setLogin("test");
//        test.setPasswordHash(HashUtil.salt(bootstrap.getPropertyService(), "password"));
//        test.setEmail("test@mail.ru");
//        test.setRole(Role.USUAL);
//        users = Arrays.asList(admin, test);
//
//        Domain domain = new Domain();
//        domain.setProjects(projects);
//        domain.setTasks(tasks);
//        domain.setUsers(users);
//        return domain;
//    }
//
//    @Test
//    public void testSetDomain() {
//        domainService.setDomain(null);
//
//        Domain domain = initDataDomain();
//        domainService.setDomain(domain);
//
//        Assert.assertEquals(2, domain.getProjects().size());
//        Assert.assertEquals("Test Project 1", domain.getProjects().get(0).getName());
//        Assert.assertEquals("Test Project 2", domain.getProjects().get(1).getName());
//
//        Assert.assertEquals(2, domain.getTasks().size());
//        Assert.assertEquals("Test Task 1", domain.getTasks().get(0).getName());
//        Assert.assertEquals("Test Task 2", domain.getTasks().get(1).getName());
//
//        Assert.assertEquals(2, domain.getUsers().size());
//        Assert.assertEquals("admin", domain.getUsers().get(0).getLogin());
//        Assert.assertEquals("test", domain.getUsers().get(1).getLogin());
//    }
//
//    @Test
//    public void testGetDomain() {
//        Domain domain = domainService.getDomain();
//        Assert.assertEquals(2, domain.getProjects().size());
//        Assert.assertEquals("Test Project 1", domain.getProjects().get(0).getName());
//        Assert.assertEquals("Test Project 2", domain.getProjects().get(1).getName());
//
//        Assert.assertEquals(2, domain.getTasks().size());
//        Assert.assertEquals("Test Task 1", domain.getTasks().get(0).getName());
//        Assert.assertEquals("Test Task 2", domain.getTasks().get(1).getName());
//
//        Assert.assertEquals(2, domain.getUsers().size());
//        Assert.assertEquals("admin", domain.getUsers().get(0).getLogin());
//        Assert.assertEquals("test", domain.getUsers().get(1).getLogin());
//    }
//
//    @Test
//    public void testSaveAndLoadDataBackup() throws Exception {
//        domainService.saveDataBackup();
//        domainService.loadDataBackup();
//
//        Domain domain = domainService.getDomain();
//
//        Assert.assertNotNull(domain.getProjects());
//        Assert.assertNotNull(domain.getTasks());
//        Assert.assertNotNull(domain.getUsers());
//
//        Assert.assertEquals(2, domain.getProjects().size());
//        Assert.assertEquals("Test Project 1", domain.getProjects().get(0).getName());
//        Assert.assertEquals("Test Project 2", domain.getProjects().get(1).getName());
//
//        Assert.assertEquals(2, domain.getTasks().size());
//        Assert.assertEquals("Test Task 1", domain.getTasks().get(0).getName());
//        Assert.assertEquals("Test Task 2", domain.getTasks().get(1).getName());
//
//        Assert.assertEquals(2, domain.getUsers().size());
//        Assert.assertEquals("admin", domain.getUsers().get(0).getLogin());
//        Assert.assertEquals("test", domain.getUsers().get(1).getLogin());
//    }
//
//    @Test
//    public void testSaveAndLoadDataBase64() throws Exception {
//        domainService.saveDataBase64();
//        domainService.loadDataBase64();
//
//        Domain domain = domainService.getDomain();
//
//        Assert.assertNotNull(domain.getProjects());
//        Assert.assertNotNull(domain.getTasks());
//        Assert.assertNotNull(domain.getUsers());
//
//        Assert.assertEquals(2, domain.getProjects().size());
//        Assert.assertEquals("Test Project 1", domain.getProjects().get(0).getName());
//        Assert.assertEquals("Test Project 2", domain.getProjects().get(1).getName());
//
//        Assert.assertEquals(2, domain.getTasks().size());
//        Assert.assertEquals("Test Task 1", domain.getTasks().get(0).getName());
//        Assert.assertEquals("Test Task 2", domain.getTasks().get(1).getName());
//
//        Assert.assertEquals(2, domain.getUsers().size());
//        Assert.assertEquals("admin", domain.getUsers().get(0).getLogin());
//        Assert.assertEquals("test", domain.getUsers().get(1).getLogin());
//    }
//
//    @Test
//    public void testSaveAndLoadDataJson() throws Exception {
//        domainService.saveDataJsonFasterXml();
//        domainService.loadDataJsonFasterXml();
//
//        Domain domain = domainService.getDomain();
//
//        Assert.assertNotNull(domain.getProjects());
//        Assert.assertNotNull(domain.getTasks());
//        Assert.assertNotNull(domain.getUsers());
//
//        Assert.assertEquals(2, domain.getProjects().size());
//        Assert.assertEquals("Test Project 1", domain.getProjects().get(0).getName());
//        Assert.assertEquals("Test Project 2", domain.getProjects().get(1).getName());
//
//        Assert.assertEquals(2, domain.getTasks().size());
//        Assert.assertEquals("Test Task 1", domain.getTasks().get(0).getName());
//        Assert.assertEquals("Test Task 2", domain.getTasks().get(1).getName());
//
//        Assert.assertEquals(2, domain.getUsers().size());
//        Assert.assertEquals("admin", domain.getUsers().get(0).getLogin());
//        Assert.assertEquals("test", domain.getUsers().get(1).getLogin());
//    }
//
//    @Test
//    public void testFileExistenceAfterSave() throws Exception {
//        File backupFile = new File(DomainService.FILE_BACKUP);
//        if (backupFile.exists()) {
//            backupFile.delete();
//        }
//        domainService.saveDataBackup();
//        Assert.assertTrue(backupFile.exists());
//        backupFile.delete();
//    }
//
//    @Test
//    public void testFilterProjects() {
//        List<Project> completedProjects = domainService.getDomain().getProjects().stream()
//                .filter(project -> project.getStatus() == Status.COMPLETED)
//                .collect(Collectors.toList());
//
//        Assert.assertEquals(1, completedProjects.size());
//        Assert.assertEquals("Test Project 2", completedProjects.get(0).getName());
//    }
//
//    @Test
//    public void testSaveAndLoadDataBinary() throws Exception {
//        domainService.saveDataBinary();
//        domainService.loadDataBinary();
//
//        Domain domain = domainService.getDomain();
//
//        Assert.assertNotNull(domain.getProjects());
//        Assert.assertNotNull(domain.getTasks());
//        Assert.assertNotNull(domain.getUsers());
//
//        Assert.assertEquals(2, domain.getProjects().size());
//        Assert.assertEquals("Test Project 1", domain.getProjects().get(0).getName());
//        Assert.assertEquals("Test Project 2", domain.getProjects().get(1).getName());
//
//        Assert.assertEquals(2, domain.getTasks().size());
//        Assert.assertEquals("Test Task 1", domain.getTasks().get(0).getName());
//        Assert.assertEquals("Test Task 2", domain.getTasks().get(1).getName());
//
//        Assert.assertEquals(2, domain.getUsers().size());
//        Assert.assertEquals("admin", domain.getUsers().get(0).getLogin());
//        Assert.assertEquals("test", domain.getUsers().get(1).getLogin());
//    }
//
//    @Test
//    public void testSaveAndLoadDataJsonJaxB() throws Exception {
//        domainService.saveDataJsonJaxB();
//        domainService.loadDataJsonJaxB();
//
//        Domain domain = domainService.getDomain();
//
//        Assert.assertNotNull(domain.getProjects());
//        Assert.assertNotNull(domain.getTasks());
//        Assert.assertNotNull(domain.getUsers());
//
//        Assert.assertEquals(2, domain.getProjects().size());
//        Assert.assertEquals("Test Project 1", domain.getProjects().get(0).getName());
//        Assert.assertEquals("Test Project 2", domain.getProjects().get(1).getName());
//
//        Assert.assertEquals(2, domain.getTasks().size());
//        Assert.assertEquals("Test Task 1", domain.getTasks().get(0).getName());
//        Assert.assertEquals("Test Task 2", domain.getTasks().get(1).getName());
//
//        Assert.assertEquals(2, domain.getUsers().size());
//        Assert.assertEquals("admin", domain.getUsers().get(0).getLogin());
//        Assert.assertEquals("test", domain.getUsers().get(1).getLogin());
//    }
//
//    @Test
//    public void testSaveAndLoadDataXmlJaxB() throws Exception {
//        domainService.saveDataXmlJaxB();
//        domainService.loadDataXmlJaxB();
//
//        Domain domain = domainService.getDomain();
//
//        Assert.assertNotNull(domain.getProjects());
//        Assert.assertNotNull(domain.getTasks());
//        Assert.assertNotNull(domain.getUsers());
//
//        Assert.assertEquals(2, domain.getProjects().size());
//        Assert.assertEquals("Test Project 1", domain.getProjects().get(0).getName());
//        Assert.assertEquals("Test Project 2", domain.getProjects().get(1).getName());
//
//        Assert.assertEquals(2, domain.getTasks().size());
//        Assert.assertEquals("Test Task 1", domain.getTasks().get(0).getName());
//        Assert.assertEquals("Test Task 2", domain.getTasks().get(1).getName());
//
//        Assert.assertEquals(2, domain.getUsers().size());
//        Assert.assertEquals("admin", domain.getUsers().get(0).getLogin());
//        Assert.assertEquals("test", domain.getUsers().get(1).getLogin());
//    }
//
//    @Test
//    public void testSaveAndLoadDataYamlFasterXml() throws Exception {
//        domainService.saveDataYamlFasterXml();
//        domainService.loadDataYamlFasterXml();
//
//        Domain domain = domainService.getDomain();
//
//        Assert.assertNotNull(domain.getProjects());
//        Assert.assertNotNull(domain.getTasks());
//        Assert.assertNotNull(domain.getUsers());
//
//        Assert.assertEquals(2, domain.getProjects().size());
//        Assert.assertEquals("Test Project 1", domain.getProjects().get(0).getName());
//        Assert.assertEquals("Test Project 2", domain.getProjects().get(1).getName());
//
//        Assert.assertEquals(2, domain.getTasks().size());
//        Assert.assertEquals("Test Task 1", domain.getTasks().get(0).getName());
//        Assert.assertEquals("Test Task 2", domain.getTasks().get(1).getName());
//
//        Assert.assertEquals(2, domain.getUsers().size());
//        Assert.assertEquals("admin", domain.getUsers().get(0).getLogin());
//        Assert.assertEquals("test", domain.getUsers().get(1).getLogin());
//    }
//
//    @Test
//    public void testSaveDataXmlFasterXml() throws Exception {
//        domainService.saveDataXmlFasterXml();
//
//        File xmlFile = new File(DomainService.FILE_XML);
//        Assert.assertTrue(xmlFile.exists());
//    }
//
//    @Test
//    public void testLoadDataXmlFasterXml() throws Exception {
//        domainService.saveDataXmlFasterXml();
//
//        domainService.loadDataXmlFasterXml();
//
//        Domain domain = domainService.getDomain();
//
//        Assert.assertNotNull(domain.getProjects());
//        Assert.assertNotNull(domain.getTasks());
//        Assert.assertNotNull(domain.getUsers());
//
//        Assert.assertEquals(2, domain.getProjects().size());
//        Assert.assertEquals("Test Project 1", domain.getProjects().get(0).getName());
//        Assert.assertEquals("Test Project 2", domain.getProjects().get(1).getName());
//
//        Assert.assertEquals(2, domain.getTasks().size());
//        Assert.assertEquals("Test Task 1", domain.getTasks().get(0).getName());
//        Assert.assertEquals("Test Task 2", domain.getTasks().get(1).getName());
//
//        Assert.assertEquals(2, domain.getUsers().size());
//        Assert.assertEquals("admin", domain.getUsers().get(0).getLogin());
//        Assert.assertEquals("test", domain.getUsers().get(1).getLogin());
//    }
//
//    @After
//    @SneakyThrows
//    public void cleanup() {
//        deleteFileIfExists(DomainService.FILE_BACKUP);
//        deleteFileIfExists(DomainService.FILE_BASE64);
//        deleteFileIfExists(DomainService.FILE_BINARY);
//        deleteFileIfExists(DomainService.FILE_JSON);
//        deleteFileIfExists(DomainService.FILE_XML);
//        deleteFileIfExists(DomainService.FILE_YAML);
//    }
//
//    private void deleteFileIfExists(String filePath) {
//        File file = new File(filePath);
//        if (file.exists()) {
//            file.delete();
//        }
//    }
//
//}
