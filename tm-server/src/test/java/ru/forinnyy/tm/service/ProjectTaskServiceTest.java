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
//import ru.forinnyy.tm.exception.entity.ProjectNotFoundException;
//import ru.forinnyy.tm.exception.entity.TaskNotFoundException;
//import ru.forinnyy.tm.exception.field.ProjectIdEmptyException;
//import ru.forinnyy.tm.exception.field.TaskIdEmptyException;
//import ru.forinnyy.tm.exception.field.UserIdEmptyException;
//import ru.forinnyy.tm.marker.UnitCategory;
//import ru.forinnyy.tm.model.Project;
//import ru.forinnyy.tm.model.Task;
//
//@Category(UnitCategory.class)
//public final class ProjectTaskServiceTest extends AbstractTest {
//
//    private IConnectionService connectionService;
//
//    private ProjectService projectService;
//
//    private TaskService taskService;
//
//    private ProjectTaskService projectTaskService;
//
//    @Before
//    public void init() {
//        connectionService = new ConnectionService(new PropertyService());
//        projectService = new ProjectService(connectionService);
//        taskService = new TaskService(connectionService);
//        projectTaskService = new ProjectTaskService(connectionService);
//
//        projectService.initTable();
//        taskService.initTable();
//    }
//
//    @Test
//    @SneakyThrows
//    public void testBindTaskToProject() {
//        @NonNull final Project project = projectService.create(UUID1, "Project1", "Desc");
//        @NonNull final Task task = taskService.create(UUID1, "Task1", "Desc");
//
//        @NonNull final Task bindedTask = projectTaskService.bindTaskToProject(UUID1, project.getId(), task.getId());
//        Assert.assertNotNull(bindedTask);
//        Assert.assertEquals(project.getId(), bindedTask.getProjectId());
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> projectTaskService.bindTaskToProject(null, project.getId(), task.getId()));
//        Assert.assertThrows(ProjectIdEmptyException.class,
//                () -> projectTaskService.bindTaskToProject(UUID1, null, task.getId()));
//        Assert.assertThrows(TaskIdEmptyException.class,
//                () -> projectTaskService.bindTaskToProject(UUID1, project.getId(), null));
//        Assert.assertThrows(ProjectNotFoundException.class,
//                () -> projectTaskService.bindTaskToProject(UUID1, UUID5, task.getId()));
//        Assert.assertThrows(TaskNotFoundException.class,
//                () -> projectTaskService.bindTaskToProject(UUID1, project.getId(), UUID5));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testUnbindTaskFromProject() {
//        @NonNull final Project project = projectService.create(UUID1, "Project1", "Desc");
//        @NonNull final Task task = taskService.create(UUID1, "Task1", "Desc");
//        projectTaskService.bindTaskToProject(UUID1, project.getId(), task.getId());
//
//        @NonNull final Task unbindedTask = projectTaskService.unbindTaskFromProject(UUID1, project.getId(), task.getId());
//        Assert.assertNotNull(unbindedTask);
//        Assert.assertNull(unbindedTask.getProjectId());
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> projectTaskService.unbindTaskFromProject(null, project.getId(), task.getId()));
//        Assert.assertThrows(ProjectIdEmptyException.class,
//                () -> projectTaskService.unbindTaskFromProject(UUID1, null, task.getId()));
//        Assert.assertThrows(TaskIdEmptyException.class,
//                () -> projectTaskService.unbindTaskFromProject(UUID1, project.getId(), null));
//        Assert.assertThrows(ProjectNotFoundException.class,
//                () -> projectTaskService.unbindTaskFromProject(UUID1, UUID5, task.getId()));
//        Assert.assertThrows(TaskNotFoundException.class,
//                () -> projectTaskService.unbindTaskFromProject(UUID1, project.getId(), UUID5));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testRemoveProjectById() {
//        @NonNull final Project project = projectService.create(UUID1, "Project1", "Desc");
//        @NonNull final Task task = taskService.create(UUID1, "Task1", "Desc");
//        projectTaskService.bindTaskToProject(UUID1, project.getId(), task.getId());
//
//        projectTaskService.removeProjectById(UUID1, project.getId());
//
//        Assert.assertFalse(projectService.existsById(UUID1, project.getId()));
//        Assert.assertTrue(taskService.findAllByProjectId(UUID1, project.getId()).isEmpty());
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> projectTaskService.removeProjectById(null, project.getId()));
//        Assert.assertThrows(ProjectIdEmptyException.class,
//                () -> projectTaskService.removeProjectById(UUID1, null));
//        Assert.assertThrows(ProjectNotFoundException.class,
//                () -> projectTaskService.removeProjectById(UUID1, UUID5));
//    }
//
//}