//package ru.forinnyy.tm.service;
//
//import lombok.NonNull;
//import lombok.SneakyThrows;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//import ru.forinnyy.tm.api.repository.IUserOwnedRepository;
//import ru.forinnyy.tm.exception.entity.EntityNotFoundException;
//import ru.forinnyy.tm.exception.field.IdEmptyException;
//import ru.forinnyy.tm.exception.field.IndexIncorrectException;
//import ru.forinnyy.tm.exception.field.UserIdEmptyException;
//import ru.forinnyy.tm.marker.UnitCategory;
//import ru.forinnyy.tm.model.AbstractUserOwnedModel;
//
//@Category(UnitCategory.class)
//public abstract class AbstractUserOwnedServiceTest<M extends AbstractUserOwnedModel, R extends IUserOwnedRepository<M>>
//        extends AbstractServiceTest<M, R> {
//
//    protected AbstractUserOwnedService<M, R> getUserOwnedService() {
//        return (AbstractUserOwnedService<M, R>) service;
//    }
//
//    @Test
//    @SneakyThrows
//    public void testAdd() {
//        @NonNull final M model = createModel();
//        getUserOwnedService().add(UUID1, model);
//        Assert.assertTrue(getUserOwnedService().findAll(UUID1).contains(model));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().add(null, model));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().add(EMPTY_STRING, model));
//        Assert.assertThrows(EntityNotFoundException.class, () -> getUserOwnedService().add(UUID1, null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testFindAll() {
//        @NonNull final M model = createModel();
//        getUserOwnedService().add(UUID1, model);
//        Assert.assertTrue(getUserOwnedService().findAll(UUID1).contains(model));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().findAll((String) null));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().findAll(EMPTY_STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemove() {
//        @NonNull final M model = createModel();
//        getUserOwnedService().add(UUID1, model);
//        getUserOwnedService().remove(UUID1, model);
//        Assert.assertFalse(getUserOwnedService().findAll(UUID1).contains(model));
//        getUserOwnedService().remove(UUID1, null);
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().remove(null, model));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().remove(EMPTY_STRING, model));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testFindOneByIdAndIndex() {
//        @NonNull final M model = createModel();
//        getUserOwnedService().add(UUID1, model);
//        Assert.assertEquals(model, getUserOwnedService().findOneById(UUID1, model.getId()));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().findOneById(null, model.getId()));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().findOneById(EMPTY_STRING, model.getId()));
//        Assert.assertThrows(IdEmptyException.class, () -> getUserOwnedService().findOneById(UUID1, null));
//        Assert.assertThrows(IdEmptyException.class, () -> getUserOwnedService().findOneById(UUID1, EMPTY_STRING));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().findOneByIndex(null, 0));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().findOneByIndex(EMPTY_STRING, 0));
//        Assert.assertThrows(IndexIncorrectException.class, () -> getUserOwnedService().findOneByIndex(UUID1, null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testExistsById() {
//        @NonNull final M model = createModel();
//        getUserOwnedService().add(UUID1, model);
//        Assert.assertTrue(getUserOwnedService().existsById(UUID1, model.getId()));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().existsById(null, model.getId()));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().existsById(EMPTY_STRING, model.getId()));
//        Assert.assertThrows(IdEmptyException.class, () -> getUserOwnedService().existsById(UUID1, null));
//        Assert.assertThrows(IdEmptyException.class, () -> getUserOwnedService().existsById(UUID1, EMPTY_STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testGetSize() {
//        getUserOwnedService().add(UUID1, createModel());
//        getUserOwnedService().add(UUID1, createModel());
//        Assert.assertEquals(2, getUserOwnedService().getSize(UUID1));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().getSize(null));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().getSize(EMPTY_STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveById() {
//        @NonNull final M model = createModel();
//        getUserOwnedService().add(UUID1, model);
//        getUserOwnedService().removeById(UUID1, model.getId());
//        Assert.assertFalse(getUserOwnedService().findAll(UUID1).contains(model));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().removeById(null, model.getId()));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().removeById(EMPTY_STRING, model.getId()));
//        Assert.assertThrows(IdEmptyException.class, () -> getUserOwnedService().removeById(UUID1, null));
//        Assert.assertThrows(IdEmptyException.class, () -> getUserOwnedService().removeById(UUID1, EMPTY_STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveByIndex() {
//        @NonNull final M model = createModel();
//        getUserOwnedService().add(UUID1, model);
//        getUserOwnedService().removeByIndex(UUID1, 0);
//        Assert.assertFalse(getUserOwnedService().findAll(UUID1).contains(model));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().removeByIndex(null, 0));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().removeByIndex(EMPTY_STRING, 0));
//        Assert.assertThrows(IndexIncorrectException.class, () -> getUserOwnedService().removeByIndex(UUID1, null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveAll() {
//        getUserOwnedService().add(UUID1, createModel());
//        getUserOwnedService().add(UUID1, createModel());
//        getUserOwnedService().removeAll(UUID1);
//        Assert.assertEquals(0, getUserOwnedService().findAll(UUID1).size());
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().removeAll((String) null));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().removeAll(EMPTY_STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testClear() {
//        getUserOwnedService().add(UUID1, createModel());
//        getUserOwnedService().add(UUID1, createModel());
//        getUserOwnedService().clear(UUID1);
//        Assert.assertEquals(0, getUserOwnedService().findAll(UUID1).size());
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().clear(null));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedService().clear(EMPTY_STRING));
//    }
//
//}
