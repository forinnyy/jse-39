//package ru.forinnyy.tm.service;
//
//import lombok.NonNull;
//import lombok.SneakyThrows;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//import ru.forinnyy.tm.AbstractTest;
//import ru.forinnyy.tm.api.repository.IRepository;
//import ru.forinnyy.tm.api.service.IConnectionService;
//import ru.forinnyy.tm.exception.entity.EntityNotFoundException;
//import ru.forinnyy.tm.exception.field.IdEmptyException;
//import ru.forinnyy.tm.exception.field.IndexIncorrectException;
//import ru.forinnyy.tm.marker.UnitCategory;
//import ru.forinnyy.tm.model.AbstractModel;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//
//@Category(UnitCategory.class)
//public abstract class AbstractServiceTest<M extends AbstractModel, R extends IRepository<M>> extends AbstractTest {
//
//    protected AbstractService<M, R> service;
//    protected IConnectionService connectionService;
//
//    protected abstract AbstractService<M, R> createService();
//
//    protected abstract M createModel();
//
//    @Before
//    public void init() {
//        connectionService = new ConnectionService(new PropertyService());
//        service = createService();
//        service.clear();
//    }
//
//    @Test
//    @SneakyThrows
//    public void testAddMethod() {
//        @NonNull final M model_one = createModel();
//        service.add(model_one);
//        Assert.assertTrue(service.findAll().contains(model_one));
//        Assert.assertThrows(EntityNotFoundException.class, () -> service.remove(null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testAddCollectionMethod() {
//        @NonNull final M model_two = createModel();
//        @NonNull final M model_three = createModel();
//        @NonNull final Collection<M> collection = Arrays.asList(model_two, model_three);
//        service.add(collection);
//        Assert.assertTrue(service.findAll().containsAll(collection));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testSetMethod() {
//        @NonNull final M model = createModel();
//        service.set(Arrays.asList(model));
//        Assert.assertTrue(service.findAll().contains(model));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testFindAllMethod() {
//        service.add(Arrays.asList(createModel()));
//        @NonNull final List<M> models = service.findAll();
//        Assert.assertFalse(models.isEmpty());
//    }
//
//    @Test
//    @SneakyThrows
//    public void testClearMethod() {
//        service.add(createModel());
//        service.clear();
//        Assert.assertTrue(service.findAll().isEmpty());
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveMethod() {
//        @NonNull final M model = createModel();
//        service.add(Arrays.asList(model));
//        service.remove(model);
//        @NonNull final List<M> models = service.findAll();
//        Assert.assertFalse(models.contains(model));
//    }
//
//    @Test
//    public void testRemoveAllMethod() {
//        @NonNull final List<M> models = Arrays.asList(createModel(), createModel());
//        service.set(models);
//        Assert.assertEquals(2, service.getSize());
//        service.removeAll(models);
//        Assert.assertEquals(0, service.getSize());
//    }
//
//    @Test
//    @SneakyThrows
//    public void testFindOneByIdMethod() {
//        @NonNull final M model = createModel();
//        service.add(model);
//        Assert.assertEquals(model, service.findOneById(model.getId()));
//
//        Assert.assertThrows(IdEmptyException.class, () -> service.findOneById(null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testFindByIndexMethod() {
//        @NonNull final M model = createModel();
//        service.add(model);
//        Assert.assertEquals(model, service.findOneByIndex(0));
//        Assert.assertThrows(IndexIncorrectException.class, () -> service.findOneByIndex(null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveByIdMethod() {
//        @NonNull final M model = createModel();
//        service.add(model);
//        Assert.assertTrue(service.findAll().contains(model));
//        service.removeById(model.getId());
//        Assert.assertFalse(service.findAll().contains(model));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveByIndexMethod() {
//        @NonNull final M model = createModel();
//        service.add(model);
//        Assert.assertTrue(service.findAll().contains(model));
//        service.removeByIndex(0);
//        Assert.assertFalse(service.findAll().contains(model));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testExistsByIdMethod() {
//        @NonNull final M model = createModel();
//        service.add(model);
//        Assert.assertTrue(service.existsById(model.getId()));
//        Assert.assertFalse(service.existsById(null));
//    }
//
//}
