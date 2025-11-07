//package ru.forinnyy.tm.repository;
//
//import lombok.NonNull;
//import lombok.SneakyThrows;
//import org.junit.*;
//import org.junit.experimental.categories.Category;
//import ru.forinnyy.tm.AbstractTest;
//import ru.forinnyy.tm.marker.UnitCategory;
//import ru.forinnyy.tm.model.AbstractModel;
//import ru.forinnyy.tm.service.ConnectionService;
//import ru.forinnyy.tm.service.PropertyService;
//
//import java.sql.Connection;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//
//@Category(UnitCategory.class)
//public abstract class AbstractRepositoryTest<M extends AbstractModel> extends AbstractTest {
//
//    protected static Connection connection;
//
//    protected AbstractRepository<M> repository;
//
//    protected abstract AbstractRepository<M> createRepository();
//    protected abstract M createModel();
//
//    @BeforeClass
//    public static void setUpDb() throws Exception {
//        System.setProperty("database.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
//        System.setProperty("database.username", "sa");
//        System.setProperty("database.password", "");
//
//        connection = new ConnectionService(new PropertyService()).getConnection();
//
//        new UserRepository(connection).initTable();
//        new ProjectRepository(connection).initTable();
//        new TaskRepository(connection).initTable();
//    }
//
//    @AfterClass
//    public static void tearDownDb() throws Exception {
//        if (connection != null && !connection.isClosed()) connection.close();
//    }
//
//    @Before
//    public void init() {
//        repository = createRepository();
//        repository.clear();
//    }
//
//    @Test
//    public void testRemoveAll() {
//        @NonNull final List<M> models = new ArrayList<>();
//        models.add(createModel());
//        models.add(createModel());
//        repository.add(models);
//        repository.removeAll(models);
//        Assert.assertThrows(NullPointerException.class, () -> repository.removeAll(null));
//        Assert.assertEquals(Collections.emptyList(), repository.findAll());
//    }
//
//    @Test
//    public void testExistsById() {
//        final M model = createModel();
//        model.setId(UUID1);
//        repository.add(model);
//        Assert.assertTrue(repository.existsById(UUID1));
//        Assert.assertFalse(repository.existsById(UUID2));
//    }
//
//    @Test
//    public void testSet() {
//        @NonNull final List<M> models = new ArrayList<>();
//        models.add(createModel());
//        models.add(createModel());
//        repository.add(models);
//        @NonNull final List<M> expectedModels = new ArrayList<>();
//        expectedModels.add(createModel());
//        expectedModels.add(createModel());
//        repository.set(expectedModels);
//        Assert.assertFalse(repository.findAll().containsAll(models));
//        Assert.assertTrue(repository.findAll().containsAll(expectedModels));
//        Assert.assertThrows(NullPointerException.class, () -> repository.set(null));
//    }
//
//    @Test
//    public void testFindAllAndAddCollection() {
//        @NonNull final List<M> models = new LinkedList<>();
//        models.add(createModel());
//        models.add(createModel());
//        repository.add(models);
//        Assert.assertEquals(repository.getSize(), repository.findAll().size());
//        Assert.assertTrue(repository.findAll().containsAll(models));
//        Assert.assertThrows(NullPointerException.class, () -> repository.add((List<M>) null));
//    }
//
//    @Test
//    public void testAddAndFindOneById() {
//        @NonNull final M expectedModel = createModel();
//        repository.add(expectedModel);
//        @NonNull final M model = repository.findOneById(expectedModel.getId());
//        Assert.assertEquals(expectedModel, model);
//        Assert.assertThrows(NullPointerException.class, () -> repository.add((M) null));
//        Assert.assertThrows(NullPointerException.class, () -> repository.findOneById(null));
//        Assert.assertNull(repository.findOneByIndex(null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveById() {
//        @NonNull final M model = createModel();
//        repository.add(model);
//        repository.removeById(model.getId());
//        Assert.assertEquals(0, repository.getSize());
//        Assert.assertThrows(NullPointerException.class, () -> repository.remove(null));
//        Assert.assertThrows(NullPointerException.class, () -> repository.removeById(null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveByIndex() {
//        @NonNull final M model = createModel();
//        repository.add(model);
//        repository.removeByIndex(0);
//        Assert.assertEquals(0, repository.getSize());
//        Assert.assertThrows(NullPointerException.class, () -> repository.removeByIndex(null));
//    }
//
//}