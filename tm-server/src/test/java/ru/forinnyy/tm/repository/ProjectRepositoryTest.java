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
//
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//
//@Category(UnitCategory.class)
//public final class ProjectRepositoryTest extends AbstractUserOwnedRepositoryTest<Project> {
//
//    @Override
//    protected AbstractUserOwnedRepository<Project> createRepository() {
//        return new ProjectRepository(connection);
//    }
//
//    @Override
//    protected Project createModel() {
//        @NonNull final Project project = new Project();
//        project.setUserId(UUID1);
//        return project;
//    }
//
//    public ProjectRepository getProjectRepository() {
//        return (ProjectRepository) repository;
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCreateWithName() {
//        @NonNull final Project project = getProjectRepository().create(UUID1, STRING);
//        Assert.assertNotNull(project);
//        Assert.assertEquals(project, getUserOwnedRepository().findOneById(UUID1, project.getId()));
//        Assert.assertThrows(NullPointerException.class, () -> getProjectRepository().create(UUID1, null));
//        Assert.assertThrows(NullPointerException.class, () -> getProjectRepository().create(null, STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testCreateWithNameAndDescription() {
//        @NonNull final Project project = getProjectRepository().create(UUID1, STRING, STRING);
//        Assert.assertNotNull(project);
//        Assert.assertEquals(project, getUserOwnedRepository().findOneById(UUID1, project.getId()));
//        Assert.assertThrows(NullPointerException.class, () -> getProjectRepository().create(UUID1, null, STRING));
//        Assert.assertThrows(NullPointerException.class, () -> getProjectRepository().create(UUID1, STRING, null));
//        Assert.assertThrows(NullPointerException.class, () -> getProjectRepository().create(null, STRING, STRING));
//    }
//
//    @Test
//    @SneakyThrows
//    public void testFindAllWithComparatorAndSort() {
//        @NonNull final Project projectB = createModel();
//        projectB.setName("B");
//        getUserOwnedRepository().add(UUID2, projectB);
//
//        @NonNull final Project projectA = createModel();
//        projectA.setName("A");
//        getUserOwnedRepository().add(UUID2, projectA);
//
//        @NonNull final List<Project> sortedExpected = Arrays.asList(projectA, projectB);
//        Assert.assertEquals(sortedExpected, getUserOwnedRepository().findAll(UUID2, Comparator.comparing(Project::getName)));
//        Assert.assertThrows(NullPointerException.class, () -> getProjectRepository().findAll(null, Comparator.comparing(Project::getName)));
//        Assert.assertEquals(sortedExpected, getUserOwnedRepository().findAll(UUID2, Sort.BY_NAME));
//        Assert.assertThrows(NullPointerException.class, () -> getProjectRepository().findAll(null, Sort.BY_NAME));
//        Assert.assertThrows(NullPointerException.class, () -> getProjectRepository().findAll(UUID2, (Sort) null));
//    }
//
//}