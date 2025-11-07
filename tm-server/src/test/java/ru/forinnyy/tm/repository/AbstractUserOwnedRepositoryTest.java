//package ru.forinnyy.tm.repository;
//
//import lombok.NonNull;
//import lombok.SneakyThrows;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//import ru.forinnyy.tm.exception.field.UserIdEmptyException;
//import ru.forinnyy.tm.marker.UnitCategory;
//import ru.forinnyy.tm.model.AbstractUserOwnedModel;
//
//import java.util.Collections;
//
//@Category(UnitCategory.class)
//public abstract class AbstractUserOwnedRepositoryTest<M extends AbstractUserOwnedModel> extends AbstractRepositoryTest<M> {
//
//    protected AbstractUserOwnedRepository<M> getUserOwnedRepository() {
//        return (AbstractUserOwnedRepository<M>) repository;
//    }
//
//    @Test
//    public void testClearAndFindAllWithUserId() {
//        @NonNull final M modelUserA = createModel();
//        @NonNull final M modelUserB = createModel();
//        @NonNull final M modelUserC = createModel();
//        modelUserA.setUserId(UUID1);
//        modelUserB.setUserId(UUID2);
//        modelUserC.setUserId(UUID2);
//        getUserOwnedRepository().add(UUID1, modelUserA);
//        getUserOwnedRepository().add(UUID2, modelUserB);
//        getUserOwnedRepository().add(UUID2, modelUserC);
//        getUserOwnedRepository().clear(UUID2);
//        Assert.assertEquals(0, getUserOwnedRepository().findAll(UUID2).size());
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().clear(null));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().findAll((String) null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testExistsByIdWithUserId() {
//        @NonNull final M model = createModel();
//        model.setId(UUID1);
//        model.setUserId(UUID1);
//        getUserOwnedRepository().add(UUID1, model);
//        Assert.assertTrue(getUserOwnedRepository().existsById(UUID1, UUID1));
//        Assert.assertFalse(getUserOwnedRepository().existsById(UUID2, UUID2));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().existsById(null, UUID1));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().existsById(UUID1, null));
//    }
//
//    @Test
//    public void testRemoveAllWithUserId() {
//        getUserOwnedRepository().add(UUID1, createModel());
//        getUserOwnedRepository().add(UUID2, createModel());
//        getUserOwnedRepository().removeAll(UUID1);
//        Assert.assertEquals(Collections.emptyList(), getUserOwnedRepository().findAll(UUID1));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().removeAll((String) null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveByIdWithUserId() {
//        @NonNull final M model = createModel();
//        getUserOwnedRepository().add(UUID1, model);
//        getUserOwnedRepository().removeById(UUID1, model.getId());
//        Assert.assertEquals(0, getUserOwnedRepository().getSize());
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().removeById(UUID1, null));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().removeById(null, UUID2));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().findOneById(null, UUID2));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().findOneById(UUID1, null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveByIndexWithUserId() {
//        @NonNull final M model = createModel();
//        getUserOwnedRepository().add(UUID1, model);
//        getUserOwnedRepository().removeByIndex(UUID1, 0);
//        Assert.assertEquals(0, getUserOwnedRepository().getSize());
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().removeByIndex(UUID1, null));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().removeByIndex(null, 0));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().findOneByIndex(null, 0));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().findOneByIndex(UUID1, null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveWithUserId() {
//        @NonNull final M model = createModel();
//        getUserOwnedRepository().add(UUID1, model);
//        getUserOwnedRepository().remove(UUID1, model);
//        Assert.assertEquals(0, getUserOwnedRepository().getSize());
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().remove(UUID1, null));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().remove(null, model));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testGetSizeWithUserId() {
//        getUserOwnedRepository().add(UUID1, createModel());
//        getUserOwnedRepository().add(UUID1, createModel());
//        getUserOwnedRepository().add(UUID2, createModel());
//        Assert.assertEquals(2, getUserOwnedRepository().getSize(UUID1));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getUserOwnedRepository().getSize(null));
//    }
//
//    @Test
//    public void testAddNPEWithUserId() {
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().add(UUID2, null));
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().add(null, createModel()));
//    }
//
//}