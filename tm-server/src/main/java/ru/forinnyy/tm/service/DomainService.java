package ru.forinnyy.tm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import ru.forinnyy.tm.api.repository.IProjectRepository;
import ru.forinnyy.tm.api.repository.ITaskRepository;
import ru.forinnyy.tm.api.repository.IUserRepository;
import ru.forinnyy.tm.api.service.IConnectionService;
import ru.forinnyy.tm.api.service.IDomainService;
import ru.forinnyy.tm.api.service.IServiceLocator;
import ru.forinnyy.tm.dto.Domain;
import ru.forinnyy.tm.model.Project;
import ru.forinnyy.tm.model.Task;
import ru.forinnyy.tm.model.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class DomainService implements IDomainService {

    @NonNull
    public static final String FILE_BASE64 = "./data.base64";

    @NonNull
    public static final String FILE_BINARY = "./data.bin";

    @NonNull
    public static final String FILE_BACKUP = "./backup.base64";

    @NonNull
    public static final String FILE_XML = "./data.xml";

    @NonNull
    public static final String FILE_JSON = "./data.json";

    @NonNull
    public static final String FILE_YAML = "./data.yaml";

    @NonNull
    public final String CONTEXT_FACTORY = "javax.xml.bind.context.factory";

    @NonNull
    public final String CONTEXT_FACTORY_JAXB = "org.eclipse.persistence.jaxb.JAXBContextFactory";

    @NonNull
    public final String MEDIA_TYPE = "eclipselink.media-type";

    @NonNull
    public final String APPLICATION_TYPE_JSON = "application/json";

    @NonNull
    private final IServiceLocator serviceLocator;

    @NonNull
    private final IConnectionService connectionService;

    public DomainService(@NonNull final IServiceLocator serviceLocator,
                         @NonNull final IConnectionService connectionService) {
        this.serviceLocator = serviceLocator;
        this.connectionService = connectionService;
    }

    @NonNull
    @SneakyThrows
    public Domain getDomain() {
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository userRepository = session.getMapper(IUserRepository.class);
            @NonNull final IProjectRepository projectRepository = session.getMapper(IProjectRepository.class);
            @NonNull final ITaskRepository taskRepository = session.getMapper(ITaskRepository.class);

            @NonNull final List<User> users = userRepository.findAll();

            @NonNull final List<Project> projects = new ArrayList<>();
            @NonNull final List<Task> tasks = new ArrayList<>();

            for (@NonNull final User user : users) {
                projects.addAll(projectRepository.findAll(user.getId()));
                tasks.addAll(taskRepository.findAll(user.getId()));
            }

            @NonNull final Domain domain = new Domain();
            domain.setUsers(users);
            domain.setProjects(projects);
            domain.setTasks(tasks);
            return domain;
        }
    }

    @SneakyThrows
    public void setDomain(final Domain domain) {
        if (domain == null) return;

        @NonNull final List<User> users = domain.getUsers();
        @NonNull final List<Project> projects = domain.getProjects();
        @NonNull final List<Task> tasks = domain.getTasks();

        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository userRepository = session.getMapper(IUserRepository.class);
            @NonNull final IProjectRepository projectRepository = session.getMapper(IProjectRepository.class);
            @NonNull final ITaskRepository taskRepository = session.getMapper(ITaskRepository.class);

            @NonNull final List<User> usersFromRepository = userRepository.findAll();
            for (@NonNull final User user : usersFromRepository) {
                userRepository.removeById(user.getId());
            }

            for (@NonNull final User user : users) {
                userRepository.add(user);
            }
            for (@NonNull final Project project : projects) {
                projectRepository.add(project);
            }
            for (@NonNull final Task task : tasks) {
                taskRepository.add(task);
            }

            session.commit();
        }
    }

    public void deleteFilesAfterTests() {
        List<String> listFilesToDelete = Arrays.asList(
                "backup.base64",
                "data.base64",
                "data.bin",
                "data.json",
                "data.xml",
                "data.yaml"
        );

        for (String fileName : listFilesToDelete) {
            File file = new File(fileName);

            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }

    @Override
    @SneakyThrows
    public void loadDataBackup() {
        @NonNull final byte[] base64Byte = Files.readAllBytes(Paths.get(FILE_BACKUP));
        final String base64Date = new String(base64Byte);
        final byte[] bytes = Base64.getDecoder().decode(base64Date);
        @NonNull final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        @NonNull final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        @NonNull final Domain domain = (Domain) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        setDomain(domain);
    }

    @Override
    @SneakyThrows
    public void saveDataBackup() {
        @NonNull final Domain domain = getDomain();
        @NonNull final File file = new File(FILE_BACKUP);
        @NonNull final Path path = file.toPath();
        Files.deleteIfExists(path);
        Files.createFile(path);

        @Cleanup @NonNull final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        @Cleanup @NonNull final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(domain);

        @NonNull final byte[] bytes = byteArrayOutputStream.toByteArray();
        @NonNull final String base64 = Base64.getEncoder().encodeToString(bytes);

        @Cleanup @NonNull final FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(base64.getBytes());
        fileOutputStream.flush();
    }

    @Override
    @SneakyThrows
    public void loadDataBase64() {
        @NonNull final byte[] base64Byte = Files.readAllBytes(Paths.get(FILE_BASE64));
        final String base64Date = new String(base64Byte);
        final byte[] bytes = Base64.getDecoder().decode(base64Date);
        @NonNull final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        @NonNull final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        @NonNull final Domain domain = (Domain) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        setDomain(domain);
    }

    @Override
    @SneakyThrows
    public void saveDataBase64() {
        @NonNull final Domain domain = getDomain();
        @NonNull final File file = new File(FILE_BASE64);
        @NonNull final Path path = file.toPath();
        Files.deleteIfExists(path);
        Files.createFile(path);

        @Cleanup @NonNull final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        @Cleanup @NonNull final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(domain);

        @NonNull final byte[] bytes = byteArrayOutputStream.toByteArray();
        @NonNull final String base64 = Base64.getEncoder().encodeToString(bytes);

        @Cleanup @NonNull final FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(base64.getBytes());
        fileOutputStream.flush();
    }

    @Override
    @SneakyThrows
    public void loadDataBinary() {
        @Cleanup @NonNull final FileInputStream fileInputStream = new FileInputStream(FILE_BINARY);
        @Cleanup @NonNull final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        @NonNull final Domain domain = (Domain) objectInputStream.readObject();
        setDomain(domain);
    }

    @Override
    @SneakyThrows
    public void saveDataBinary() {
        @NonNull final Domain domain = getDomain();
        @NonNull final File file = new File(FILE_BINARY);
        @NonNull final Path path = file.toPath();

        Files.deleteIfExists(path);
        Files.createFile(path);

        @Cleanup @NonNull final FileOutputStream fileOutputStream = new FileOutputStream(file);
        @Cleanup @NonNull final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(domain);
    }

    @Override
    @SneakyThrows
    public void loadDataJsonFasterXml() {
        @NonNull final byte[] bytes = Files.readAllBytes(Paths.get(FILE_JSON));
        @NonNull final String json = new String(bytes);
        @NonNull final ObjectMapper objectMapper = new JsonMapper();
        @NonNull final Domain domain = objectMapper.readValue(json, Domain.class);
        setDomain(domain);
    }

    @Override
    @SneakyThrows
    public void saveDataJsonFasterXml() {
        @NonNull final Domain domain = getDomain();
        @NonNull final File file = new File(FILE_JSON);
        Files.deleteIfExists(file.toPath());
        Files.createFile(file.toPath());
        @Cleanup @NonNull final FileOutputStream fileOutputStream = new FileOutputStream(file);
        @NonNull final ObjectMapper objectMapper = new JsonMapper();
        @NonNull final String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(domain);
        fileOutputStream.write(json.getBytes());
        fileOutputStream.flush();
    }

    @Override
    @SneakyThrows
    public void loadDataJsonJaxB() {
        System.setProperty(CONTEXT_FACTORY, CONTEXT_FACTORY_JAXB);
        @NonNull JAXBContext jaxbContext = JAXBContext.newInstance(Domain.class);
        @NonNull final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setProperty(MEDIA_TYPE, APPLICATION_TYPE_JSON);
        @NonNull final File file = new File(FILE_JSON);
        @NonNull final Domain domain = (Domain) unmarshaller.unmarshal(file);
        setDomain(domain);
    }

    @Override
    @SneakyThrows
    public void saveDataJsonJaxB() {
        System.setProperty(CONTEXT_FACTORY, CONTEXT_FACTORY_JAXB);
        @NonNull final Domain domain = getDomain();
        @NonNull final File file = new File(FILE_JSON);
        Files.deleteIfExists(file.toPath());
        Files.createFile(file.toPath());
        @NonNull final JAXBContext jaxbContext = JAXBContext.newInstance(Domain.class);
        @NonNull final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(MEDIA_TYPE, APPLICATION_TYPE_JSON);
        @Cleanup @NonNull final FileOutputStream fileOutputStream = new FileOutputStream(file);
        jaxbMarshaller.marshal(domain, fileOutputStream);
        fileOutputStream.flush();
    }

    @Override
    @SneakyThrows
    public void loadDataXmlFasterXml() {
        @NonNull final byte[] bytes = Files.readAllBytes(Paths.get(FILE_XML));
        @NonNull final String xml = new String(bytes);
        @NonNull final ObjectMapper objectMapper = new XmlMapper();
        @NonNull final Domain domain = objectMapper.readValue(xml, Domain.class);
        setDomain(domain);
    }

    @Override
    @SneakyThrows
    public void saveDataXmlFasterXml() {
        @NonNull final Domain domain = getDomain();
        @NonNull final File file = new File(FILE_XML);
        Files.deleteIfExists(file.toPath());
        Files.createFile(file.toPath());
        @Cleanup @NonNull final FileOutputStream fileOutputStream = new FileOutputStream(file);
        @NonNull final ObjectMapper objectMapper = new XmlMapper();
        @NonNull final String xml = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(domain);
        fileOutputStream.write(xml.getBytes());
        fileOutputStream.flush();
    }

    @Override
    @SneakyThrows
    public void loadDataXmlJaxB() {
        @NonNull JAXBContext jaxbContext = JAXBContext.newInstance(Domain.class);
        @NonNull final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        @NonNull final File file = new File(FILE_XML);
        @NonNull final Domain domain = (Domain) unmarshaller.unmarshal(file);
        setDomain(domain);
    }

    @Override
    @SneakyThrows
    public void saveDataXmlJaxB() {
        @NonNull final Domain domain = getDomain();
        @NonNull final File file = new File(FILE_XML);
        Files.deleteIfExists(file.toPath());
        Files.createFile(file.toPath());
        @NonNull JAXBContext jaxbContext = JAXBContext.newInstance(Domain.class);
        @NonNull final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        @Cleanup @NonNull final FileOutputStream fileOutputStream = new FileOutputStream(file);
        jaxbMarshaller.marshal(domain, fileOutputStream);
        fileOutputStream.flush();
    }

    @Override
    @SneakyThrows
    public void loadDataYamlFasterXml() {
        @NonNull final byte[] bytes = Files.readAllBytes(Paths.get(FILE_YAML));
        @NonNull final String yaml = new String(bytes);
        @NonNull final ObjectMapper objectMapper = new YAMLMapper();
        @NonNull final Domain domain = objectMapper.readValue(yaml, Domain.class);
        setDomain(domain);
    }

    @Override
    @SneakyThrows
    public void saveDataYamlFasterXml() {
        @NonNull final Domain domain = getDomain();
        @NonNull final File file = new File(FILE_YAML);
        Files.deleteIfExists(file.toPath());
        Files.createFile(file.toPath());
        @Cleanup @NonNull final FileOutputStream fileOutputStream = new FileOutputStream(file);
        @NonNull final ObjectMapper objectMapper = new YAMLMapper();
        @NonNull final String yaml = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(domain);
        fileOutputStream.write(yaml.getBytes());
        fileOutputStream.flush();
    }

}
