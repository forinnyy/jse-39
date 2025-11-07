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
//import ru.forinnyy.tm.enumerated.Status;
//import ru.forinnyy.tm.exception.entity.TaskNotFoundException;
//import ru.forinnyy.tm.exception.field.*;
//import ru.forinnyy.tm.marker.UnitCategory;
//import ru.forinnyy.tm.model.Project;
//import ru.forinnyy.tm.model.Task;
//import ru.forinnyy.tm.model.User;
//
//import java.util.Collections;
//import java.util.List;
//
//@Category(UnitCategory.class)
//public final class TaskServiceTest extends AbstractTest {
//
//    private IConnectionService connectionService;
//    private TaskService taskService;
//
//    @Before
//    public void init() {
//        connectionService = new ConnectionService(new PropertyService());
//        taskService = new TaskService(connectionService);
//        taskService.initTable();
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
//        @NonNull final Task taskOne = taskService.create(UUID1, "Task1", "Desc1");
//        taskOne.setProjectId(UUID2);
//        taskService.updateById(UUID1, taskOne.getId(), taskOne.getName(), taskOne.getDescription());
//
//        @NonNull final Task taskTwo = taskService.create(UUID1, "Task2", "Desc2");
//        taskTwo.setProjectId(UUID2);
//        taskService.updateById(UUID1, taskTwo.getId(), taskTwo.getName(), taskTwo.getDescription());
//
//        @NonNull final List<Task> tasks = taskService.findAllByProjectId(UUID1, UUID2);
//
//        Assert.assertEquals(2, tasks.size());
//        for (@NonNull final Task task : tasks) {
//            Assert.assertEquals(user.getId(), task.getUserId());
//            Assert.assertEquals(project.getId(), task.getProjectId());
//        }
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> taskService.findAllByProjectId(null, UUID2));
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> taskService.findAllByProjectId("", UUID2));
//
//        Assert.assertEquals(Collections.emptyList(), taskService.findAllByProjectId(UUID1, null));
//        Assert.assertEquals(Collections.emptyList(), taskService.findAllByProjectId(UUID1, ""));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCreateWithName() {
//        @NonNull final Task task = taskService.create(UUID1, STRING);
//        Assert.assertEquals(task, taskService.findOneById(UUID1, task.getId()));
//
//        Assert.assertThrows(UserIdEmptyException.class, () -> taskService.create(null, STRING));
//        Assert.assertThrows(UserIdEmptyException.class, () -> taskService.create(EMPTY_STRING, STRING));
//
//        Assert.assertThrows(NameEmptyException.class, () -> taskService.create(UUID1, null));
//        Assert.assertThrows(NameEmptyException.class, () -> taskService.create(UUID1, EMPTY_STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCreateWithNameAndDescription() {
//        @NonNull final Task task = taskService.create(UUID1, STRING, STRING);
//        Assert.assertEquals(task, taskService.findOneById(UUID1, task.getId()));
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> taskService.create(null, STRING, STRING));
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> taskService.create(EMPTY_STRING, STRING, STRING));
//
//        Assert.assertThrows(NameEmptyException.class,
//                () -> taskService.create(UUID1, null, STRING));
//        Assert.assertThrows(NameEmptyException.class,
//                () -> taskService.create(UUID1, EMPTY_STRING, STRING));
//
//        Assert.assertThrows(DescriptionEmptyException.class,
//                () -> taskService.create(UUID1, STRING, null));
//        Assert.assertThrows(DescriptionEmptyException.class,
//                () -> taskService.create(UUID1, STRING, EMPTY_STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testUpdateById() {
//        @NonNull final Task task = taskService.create(UUID1, "Old", "OldDesc");
//        taskService.updateById(UUID1, task.getId(), STRING, STRING);
//
//        Assert.assertEquals(STRING, taskService.findOneById(UUID1, task.getId()).getName());
//        Assert.assertEquals(STRING, taskService.findOneById(UUID1, task.getId()).getDescription());
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> taskService.updateById(null, STRING, STRING, STRING));
//        Assert.assertThrows(TaskIdEmptyException.class,
//                () -> taskService.updateById(UUID1, null, STRING, STRING));
//        Assert.assertThrows(NameEmptyException.class,
//                () -> taskService.updateById(UUID1, task.getId(), null, STRING));
//        Assert.assertThrows(DescriptionEmptyException.class,
//                () -> taskService.updateById(UUID1, task.getId(), STRING, null));
//        Assert.assertThrows(TaskNotFoundException.class,
//                () -> taskService.updateById(UUID2, task.getId(), STRING, STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testUpdateByIndex() {
//        taskService.create(UUID1, "Old", "OldDesc");
//        taskService.updateByIndex(UUID1, 0, STRING, STRING);
//
//        Assert.assertEquals(STRING, taskService.findOneByIndex(UUID1, 0).getName());
//        Assert.assertEquals(STRING, taskService.findOneByIndex(UUID1, 0).getDescription());
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> taskService.updateByIndex(null, 0, STRING, STRING));
//        Assert.assertThrows(IndexIncorrectException.class,
//                () -> taskService.updateByIndex(UUID1, -1, STRING, STRING));
//        Assert.assertThrows(NameEmptyException.class,
//                () -> taskService.updateByIndex(UUID1, 0, null, STRING));
//        Assert.assertThrows(DescriptionEmptyException.class,
//                () -> taskService.updateByIndex(UUID1, 0, STRING, null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testChangeTaskStatusById() {
//        @NonNull final Task task = taskService.create(UUID1, "Name", "Desc");
//        Assert.assertEquals(Status.NOT_STARTED, task.getStatus());
//
//        taskService.changeTaskStatusById(UUID1, task.getId(), Status.IN_PROGRESS);
//        Assert.assertEquals(Status.IN_PROGRESS, taskService.findOneById(UUID1, task.getId()).getStatus());
//
//        taskService.changeTaskStatusById(UUID1, task.getId(), Status.COMPLETED);
//        Assert.assertEquals(Status.COMPLETED, taskService.findOneById(UUID1, task.getId()).getStatus());
//
//        Assert.assertThrows(TaskIdEmptyException.class,
//                () -> taskService.changeTaskStatusById(UUID1, null, Status.IN_PROGRESS));
//        Assert.assertThrows(TaskNotFoundException.class,
//                () -> taskService.changeTaskStatusById(UUID2, task.getId(), Status.IN_PROGRESS));
//        Assert.assertThrows(NullPointerException.class,
//                () -> taskService.changeTaskStatusById(UUID1, task.getId(), null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testChangeTaskStatusByIndex() {
//        taskService.create(UUID1, "Name", "Desc");
//
//        taskService.changeTaskStatusByIndex(UUID1, 0, Status.IN_PROGRESS);
//        Assert.assertEquals(Status.IN_PROGRESS, taskService.findOneByIndex(UUID1, 0).getStatus());
//
//        taskService.changeTaskStatusByIndex(UUID1, 0, Status.COMPLETED);
//        Assert.assertEquals(Status.COMPLETED, taskService.findOneByIndex(UUID1, 0).getStatus());
//
//        Assert.assertThrows(IndexIncorrectException.class,
//                () -> taskService.changeTaskStatusByIndex(UUID1, -1, Status.IN_PROGRESS));
//        Assert.assertThrows(NullPointerException.class,
//                () -> taskService.changeTaskStatusByIndex(UUID1, 0, null));
//    }
//
//}