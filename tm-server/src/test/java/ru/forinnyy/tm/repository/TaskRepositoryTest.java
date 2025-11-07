//package ru.forinnyy.tm.repository;
//
//import lombok.NonNull;
//import lombok.SneakyThrows;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//import ru.forinnyy.tm.enumerated.Sort;
//import ru.forinnyy.tm.marker.UnitCategory;
//import ru.forinnyy.tm.model.Project;
//import ru.forinnyy.tm.model.Task;
//import ru.forinnyy.tm.model.User;
//
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//
//@Category(UnitCategory.class)
//public final class TaskRepositoryTest extends AbstractUserOwnedRepositoryTest<Task> {
//
//    @Override
//    protected AbstractUserOwnedRepository<Task> createRepository() {
//        return new TaskRepository(connection);
//    }
//
//    @Override
//    protected Task createModel() {
//        final Task task = new Task();
//        task.setUserId(UUID1);
//        return task;
//    }
//
//    public TaskRepository getTaskRepository() {
//        return (TaskRepository) repository;
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCreateWithName() {
//        @NonNull final Task task = getTaskRepository().create(UUID1, STRING);
//        Assert.assertNotNull(task);
//        Assert.assertEquals(task, getUserOwnedRepository().findOneById(UUID1, task.getId()));
//        Assert.assertThrows(NullPointerException.class, () -> getTaskRepository().create(UUID1, null));
//        Assert.assertThrows(NullPointerException.class, () -> getTaskRepository().create(null, STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCreateWithNameAndDescription() {
//        @NonNull final Task task = getTaskRepository().create(UUID1, STRING, STRING);
//        Assert.assertNotNull(task);
//        Assert.assertEquals(task, getUserOwnedRepository().findOneById(UUID1, task.getId()));
//        Assert.assertThrows(NullPointerException.class, () -> getTaskRepository().create(UUID1, null, STRING));
//        Assert.assertThrows(NullPointerException.class, () -> getTaskRepository().create(UUID1, STRING, null));
//        Assert.assertThrows(NullPointerException.class, () -> getTaskRepository().create(null, STRING, STRING));
//    }
//
//    @Test
//    public void testFindAllWithComparing() {
//        @NonNull final Task taskC = createModel();
//        taskC.setName("C");
//        getUserOwnedRepository().add(UUID1, taskC);
//
//        @NonNull final Task taskB = createModel();
//        taskB.setName("B");
//        getUserOwnedRepository().add(UUID1, taskB);
//
//        @NonNull final Task taskA = createModel();
//        taskA.setName("A");
//        getUserOwnedRepository().add(UUID1, taskA);
//
//        List<Task> sortedExpected = Arrays.asList(taskA, taskB, taskC);
//        List<Task> sortedActual = getUserOwnedRepository().findAll(UUID1, Comparator.comparing(Task::getName));
//        Assert.assertEquals(sortedExpected, sortedActual);
//        Assert.assertThrows(NullPointerException.class, () -> getUserOwnedRepository().findAll(null, Comparator.comparing(Task::getName)));
//    }
//
//    @Test
//    public void testFindAllWithComparator() {
//        @NonNull final Task taskC = createModel();
//        taskC.setName("C");
//        getUserOwnedRepository().add(UUID1, taskC);
//
//        @NonNull final Task taskB = createModel();
//        taskB.setName("B");
//        getUserOwnedRepository().add(UUID2, taskB);
//
//        @NonNull final Task taskA = createModel();
//        taskA.setUserId(UUID2);
//        taskA.setName("A");
//        getUserOwnedRepository().add(UUID2, taskA);
//
//        @NonNull final List<Task> sortedExpected = Arrays.asList(taskA, taskB);
//        @NonNull final List<Task> sortedActual = getUserOwnedRepository().findAll(UUID2, Comparator.comparing(Task::getName));
//        Assert.assertEquals(sortedExpected, sortedActual);
//        Assert.assertThrows(NullPointerException.class, () -> getTaskRepository().findAll(null, Comparator.comparing(Task::getName)));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testFindAllWithSort() {
//        @NonNull final Task taskC = createModel();
//        taskC.setName("C");
//        getTaskRepository().add(UUID1, taskC);
//
//        @NonNull final Task taskB = createModel();
//        taskB.setUserId(UUID2);
//        taskB.setName("B");
//        getTaskRepository().add(UUID2, taskB);
//
//        @NonNull final Task taskA = createModel();
//        taskA.setUserId(UUID2);
//        taskA.setName("A");
//        getTaskRepository().add(UUID2, taskA);
//
//        @NonNull final List<Task> sortedExpected = Arrays.asList(taskA, taskB);
//        @NonNull final List<Task> sortedActual = getTaskRepository().findAll(UUID2, Sort.BY_NAME);
//        Assert.assertEquals(sortedExpected, sortedActual);
//        Assert.assertThrows(NullPointerException.class, () -> getTaskRepository().findAll(null, Sort.BY_NAME));
//        Assert.assertThrows(NullPointerException.class, () -> getTaskRepository().findAll(UUID2, (Sort) null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testFindAllByProjectId() {
//        @NonNull final User user = new User();
//        user.setId(UUID1);
//
//        @NonNull final Project project = new Project();
//        project.setUserId(UUID1);
//        project.setId(UUID2);
//
//        @NonNull final Task taskOne = createModel();
//        taskOne.setUserId(UUID1);
//        taskOne.setProjectId(UUID2);
//
//        @NonNull final Task taskTwo = createModel();
//        taskTwo.setUserId(UUID1);
//        taskTwo.setProjectId(UUID2);
//
//        getTaskRepository().add(taskOne);
//        getTaskRepository().add(taskTwo);
//
//        @NonNull final List<Task> tasks = getTaskRepository().findAllByProjectId(UUID1, UUID2);
//
//        Assert.assertNotNull(tasks);
//        Assert.assertEquals(2, tasks.size());
//        for (@NonNull final Task task : tasks) {
//            Assert.assertEquals(user.getId(), task.getUserId());
//            Assert.assertEquals(project.getId(), task.getProjectId());
//        }
//
//        Assert.assertThrows(NullPointerException.class, () -> getTaskRepository().findAllByProjectId(null, UUID1));
//        Assert.assertThrows(NullPointerException.class, () -> getTaskRepository().findAllByProjectId(UUID1, null));
//    }
//
//}