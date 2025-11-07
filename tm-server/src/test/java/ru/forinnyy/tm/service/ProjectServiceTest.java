//package ru.forinnyy.tm.service;
//
//import lombok.NonNull;
//import lombok.SneakyThrows;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//import ru.forinnyy.tm.api.repository.IProjectRepository;
//import ru.forinnyy.tm.api.service.IConnectionService;
//import ru.forinnyy.tm.enumerated.Sort;
//import ru.forinnyy.tm.enumerated.Status;
//import ru.forinnyy.tm.exception.entity.ProjectNotFoundException;
//import ru.forinnyy.tm.exception.field.*;
//import ru.forinnyy.tm.marker.UnitCategory;
//import ru.forinnyy.tm.model.Project;
//
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//
//@Category(UnitCategory.class)
//public final class ProjectServiceTest extends AbstractUserOwnedServiceTest<Project, IProjectRepository> {
//
//    @Override
//    protected AbstractUserOwnedService<Project, IProjectRepository> createService() {
//        IConnectionService connectionService = new ConnectionService(new PropertyService());
//        return new ProjectService(connectionService);
//    }
//
//    @Override
//    protected Project createModel() {
//        @NonNull final Project project = new Project();
//        project.setUserId(UUID1);
//        return project;
//    }
//
//    protected ProjectService getProjectService() {
//        return (ProjectService) service;
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCreateWithName() {
//        @NonNull final Project project = getProjectService().create(UUID1, STRING);
//        Assert.assertNotNull(getProjectService().findOneById(UUID1, project.getId()));
//
//        Assert.assertThrows(UserIdEmptyException.class, () -> getProjectService().create(null, STRING));
//        Assert.assertThrows(UserIdEmptyException.class, () -> getProjectService().create(EMPTY_STRING, STRING));
//
//        Assert.assertThrows(NameEmptyException.class, () -> getProjectService().create(UUID1, null));
//        Assert.assertThrows(NameEmptyException.class, () -> getProjectService().create(UUID1, EMPTY_STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCreateWithNameAndDescription() {
//        @NonNull final Project project = getProjectService().create(UUID1, STRING, STRING);
//        Assert.assertNotNull(getProjectService().findOneById(UUID1, project.getId()));
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().create(null, STRING, STRING));
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().create(EMPTY_STRING, STRING, STRING));
//
//        Assert.assertThrows(NameEmptyException.class,
//                () -> getProjectService().create(UUID1, null, STRING));
//        Assert.assertThrows(NameEmptyException.class,
//                () -> getProjectService().create(UUID1, EMPTY_STRING, STRING));
//
//        Assert.assertThrows(DescriptionEmptyException.class,
//                () -> getProjectService().create(UUID1, STRING, null));
//        Assert.assertThrows(DescriptionEmptyException.class,
//                () -> getProjectService().create(UUID1, STRING, EMPTY_STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testUpdateById() {
//        @NonNull final Project project = getProjectService().create(UUID1, "old", "old");
//        getProjectService().updateById(UUID1, project.getId(), STRING, STRING);
//        Assert.assertEquals(STRING, getProjectService().findOneById(UUID1, project.getId()).getName());
//        Assert.assertEquals(STRING, getProjectService().findOneById(UUID1, project.getId()).getDescription());
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().updateById(null, STRING, STRING, STRING));
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().updateById(EMPTY_STRING, STRING, STRING, STRING));
//
//        Assert.assertThrows(ProjectIdEmptyException.class,
//                () -> getProjectService().updateById(UUID1, null, STRING, STRING));
//        Assert.assertThrows(ProjectIdEmptyException.class,
//                () -> getProjectService().updateById(UUID1, EMPTY_STRING, STRING, STRING));
//
//        Assert.assertThrows(NameEmptyException.class,
//                () -> getProjectService().updateById(UUID1, STRING, null, STRING));
//        Assert.assertThrows(NameEmptyException.class,
//                () -> getProjectService().updateById(UUID1, STRING, EMPTY_STRING, STRING));
//
//        Assert.assertThrows(DescriptionEmptyException.class,
//                () -> getProjectService().updateById(UUID1, STRING, STRING, null));
//        Assert.assertThrows(DescriptionEmptyException.class,
//                () -> getProjectService().updateById(UUID1, STRING, STRING, EMPTY_STRING));
//
//        Assert.assertThrows(ProjectNotFoundException.class,
//                () -> getProjectService().updateById(UUID2, project.getId(), STRING, STRING));
//        Assert.assertThrows(ProjectNotFoundException.class,
//                () -> getProjectService().updateById(UUID1, STRING, STRING, STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testUpdateByIndex() {
//        getProjectService().create(UUID1, "old", "old");
//        getProjectService().updateByIndex(UUID1, 0, STRING, STRING);
//        Project project = getProjectService().findOneByIndex(UUID1, 0);
//        Assert.assertEquals(STRING, project.getName());
//        Assert.assertEquals(STRING, project.getDescription());
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().updateByIndex(null, 0, STRING, STRING));
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().updateByIndex(EMPTY_STRING, 0, STRING, STRING));
//
//        Assert.assertThrows(IndexIncorrectException.class,
//                () -> getProjectService().updateByIndex(UUID1, null, STRING, STRING));
//        Assert.assertThrows(IndexIncorrectException.class,
//                () -> getProjectService().updateByIndex(UUID1, -1, STRING, STRING));
//        Assert.assertThrows(IndexIncorrectException.class,
//                () -> getProjectService().updateByIndex(UUID1, 2, STRING, STRING));
//
//        Assert.assertThrows(NameEmptyException.class,
//                () -> getProjectService().updateByIndex(UUID1, 0, null, STRING));
//        Assert.assertThrows(NameEmptyException.class,
//                () -> getProjectService().updateByIndex(UUID1, 0, EMPTY_STRING, STRING));
//
//        Assert.assertThrows(DescriptionEmptyException.class,
//                () -> getProjectService().updateByIndex(UUID1, 0, STRING, null));
//        Assert.assertThrows(DescriptionEmptyException.class,
//                () -> getProjectService().updateByIndex(UUID1, 0, STRING, EMPTY_STRING));
//
//        Assert.assertThrows(ProjectNotFoundException.class,
//                () -> getProjectService().updateByIndex(UUID2, 0, STRING, STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testChangeProjectStatusById() {
//        @NonNull final Project project = getProjectService().create(UUID1, "name", "desc");
//        Assert.assertEquals(Status.NOT_STARTED, getProjectService().findOneById(UUID1, project.getId()).getStatus());
//
//        getProjectService().changeProjectStatusById(UUID1, project.getId(), Status.IN_PROGRESS);
//        Assert.assertEquals(Status.IN_PROGRESS, getProjectService().findOneById(UUID1, project.getId()).getStatus());
//
//        getProjectService().changeProjectStatusById(UUID1, project.getId(), Status.COMPLETED);
//        Assert.assertEquals(Status.COMPLETED, getProjectService().findOneById(UUID1, project.getId()).getStatus());
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().changeProjectStatusById(null, STRING, Status.IN_PROGRESS));
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().changeProjectStatusById(EMPTY_STRING, STRING, Status.IN_PROGRESS));
//
//        Assert.assertThrows(ProjectIdEmptyException.class,
//                () -> getProjectService().changeProjectStatusById(UUID1, null, Status.IN_PROGRESS));
//        Assert.assertThrows(ProjectIdEmptyException.class,
//                () -> getProjectService().changeProjectStatusById(UUID1, EMPTY_STRING, Status.IN_PROGRESS));
//
//        Assert.assertThrows(ProjectNotFoundException.class,
//                () -> getProjectService().changeProjectStatusById(UUID2, project.getId(), Status.IN_PROGRESS));
//        Assert.assertThrows(ProjectNotFoundException.class,
//                () -> getProjectService().changeProjectStatusById(UUID1, STRING, Status.IN_PROGRESS));
//
//        Assert.assertThrows(NullPointerException.class,
//                () -> getProjectService().changeProjectStatusById(UUID1, project.getId(), null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testChangeProjectStatusByIndex() {
//        getProjectService().create(UUID1, "name", "desc");
//        Assert.assertEquals(Status.NOT_STARTED, getProjectService().findOneByIndex(UUID1, 0).getStatus());
//
//        getProjectService().changeProjectStatusByIndex(UUID1, 0, Status.IN_PROGRESS);
//        Assert.assertEquals(Status.IN_PROGRESS, getProjectService().findOneByIndex(UUID1, 0).getStatus());
//
//        getProjectService().changeProjectStatusByIndex(UUID1, 0, Status.COMPLETED);
//        Assert.assertEquals(Status.COMPLETED, getProjectService().findOneByIndex(UUID1, 0).getStatus());
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().changeProjectStatusByIndex(null, 0, Status.IN_PROGRESS));
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().changeProjectStatusByIndex("", 0, Status.IN_PROGRESS));
//
//        Assert.assertThrows(IndexIncorrectException.class,
//                () -> getProjectService().changeProjectStatusByIndex(UUID1, null, Status.IN_PROGRESS));
//        Assert.assertThrows(IndexIncorrectException.class,
//                () -> getProjectService().changeProjectStatusByIndex(UUID1, -1, Status.IN_PROGRESS));
//        Assert.assertThrows(IndexIncorrectException.class,
//                () -> getProjectService().changeProjectStatusByIndex(UUID1, 2, Status.IN_PROGRESS));
//
//        Assert.assertThrows(ProjectNotFoundException.class,
//                () -> getProjectService().changeProjectStatusByIndex(UUID2, 0, Status.IN_PROGRESS));
//
//        Assert.assertThrows(NullPointerException.class,
//                () -> getProjectService().changeProjectStatusByIndex(UUID1, 0, null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testFindAllWithComparatorAndSort() {
//        @NonNull final Project projectA = getProjectService().create(UUID1, "A", "desc");
//        @NonNull final Project projectB = getProjectService().create(UUID1, "B", "desc");
//        @NonNull final List<Project> expectedList = Arrays.asList(projectA, projectB);
//        Assert.assertEquals(expectedList, getProjectService().findAll(Comparator.comparing(Project::getName)));
//        Assert.assertEquals(getProjectService().findAll(), getProjectService().findAll((Comparator<Project>) null));
//        Assert.assertEquals(expectedList, getProjectService().findAll(Sort.BY_NAME));
//        Assert.assertEquals(getProjectService().findAll(), getProjectService().findAll((Sort) null));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testFindAllWithComparatorAndSortWithUserId() {
//        @NonNull final Project projectA = getProjectService().create(UUID1, "A", "desc");
//        @NonNull final Project projectB = getProjectService().create(UUID1, "B", "desc");
//        @NonNull final List<Project> expectedList = Arrays.asList(projectA, projectB);
//        Assert.assertEquals(expectedList, getProjectService().findAll(UUID1, Comparator.comparing(Project::getName)));
//        Assert.assertEquals(getProjectService().findAll(), getProjectService().findAll(UUID1, (Comparator<Project>) null));
//        Assert.assertEquals(expectedList, getProjectService().findAll(UUID1, Sort.BY_NAME));
//        Assert.assertEquals(getProjectService().findAll(), getProjectService().findAll(UUID1, (Sort) null));
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().findAll(null, Comparator.comparing(Project::getName)));
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().findAll(EMPTY_STRING, Comparator.comparing(Project::getName)));
//
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().findAll(null, Sort.BY_NAME));
//        Assert.assertThrows(UserIdEmptyException.class,
//                () -> getProjectService().findAll(EMPTY_STRING, Sort.BY_NAME));
//    }
//
//}