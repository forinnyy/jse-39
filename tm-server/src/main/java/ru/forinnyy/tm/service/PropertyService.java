package ru.forinnyy.tm.service;

import com.jcabi.manifests.Manifests;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.forinnyy.tm.api.service.IPropertyService;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public final class PropertyService implements IPropertyService {

    @NonNull
    public static final String GIT_BRANCH = "gitBranch";

    @NonNull
    public static final String GIT_COMMIT_ID = "gitCommitId";

    @NonNull
    public static final String GIT_COMMITTER_NAME = "gitCommitterName";

    @NonNull
    public static final String GIT_COMMITTER_EMAIL = "gitCommitterEmail";

    @NonNull
    public static final String GIT_COMMIT_MESSAGE = "gitCommitMessage";

    @NonNull
    public static final String GIT_COMMIT_TIME = "gitCommitTime";

    @NonNull
    public static final String APPLICATION_NAME_KEY = "application.name";

    @NonNull
    public static final String APPLICATION_NAME_DEFAULT = "tm";

    @NonNull
    public static final String APPLICATION_FILE_NAME_KEY = "config";

    @NonNull
    public static final String APPLICATION_FILE_NAME_DEFAULT = "application.properties";

    @NonNull
    public static final String APPLICATION_VERSION_KEY = "buildNumber";

    @NonNull
    public static final String AUTHOR_EMAIL_KEY = "email";

    @NonNull
    public static final String AUTHOR_NAME_KEY = "developer";

    @NonNull
    public static final String PASSWORD_ITERATION_DEFAULT = "25456";

    @NonNull
    public static final String PASSWORD_ITERATION_KEY = "password.iteration";

    @NonNull
    public static final String PASSWORD_SECRET_DEFAULT = "345345345345";

    @NonNull
    public static final String PASSWORD_SECRET_KEY = "password.secret";

    @NonNull
    public static final String PORT = "server.port";

    @NonNull
    public static final String PORT_DEFAULT = "8080";

    @NonNull
    public static final String HOST = "server.host";

    @NonNull
    public static final String EMPTY_VALUE = "---";

    @NonNull
    public static final String SESSION_KEY = "session.key";

    @NonNull
    public static final String SESSION_TIMEOUT = "session.timeout";

    @NonNull
    public static final String SESSION_TIMEOUT_DEFAULT = "10800";

    @NonNull
    public static final String DATABASE_URL = "database.url";

    @NonNull
    public static final String DATABASE_USERNAME = "database.username";

    @NonNull
    public static final String DATABASE_PASSWORD = "database.password";

    @NonNull
    public static final String DATABASE_DRIVER = "database.driver";

    @NonNull
    private final Properties properties = new Properties();

    @SneakyThrows
    public PropertyService() {
        final boolean existsConfig = isExistsExternalConfig();
        if (existsConfig) loadExternalConfig(properties);
        else loadInternalConfig(properties);
    }

    @SneakyThrows
    private void loadInternalConfig(@NonNull final Properties properties) {
        @NonNull final String name = APPLICATION_FILE_NAME_DEFAULT;
        @Cleanup final InputStream inputStream = getSystemResourceAsStream(name);
        if (inputStream == null) return;
        properties.load(inputStream);
    }

    @SneakyThrows
    private void loadExternalConfig(@NonNull final Properties properties) {
        @NonNull final String name = getApplicationConfig();
        @NonNull final File file = new File(name);
        @Cleanup @NonNull final InputStream inputStream = new FileInputStream(file);
        properties.load(inputStream);
    }

    private boolean isExistsExternalConfig() {
        @NonNull final String name = getApplicationConfig();
        @NonNull final File file = new File(name);
        return file.exists();
    }

    @Override
    public @NonNull String getDBUser() {
        return getStringValue(DATABASE_USERNAME);
    }

    @Override
    public @NonNull String getDBPassword() {
        return getStringValue(DATABASE_PASSWORD);
    }

    @Override
    public @NonNull String getDBUrl() {
        return getStringValue(DATABASE_URL);
    }

    @Override
    @NonNull
    public String getDBDriver() {
        return getStringValue(DATABASE_DRIVER);
    }

    @Override
    public @NonNull String getSessionKey() {
        return getStringValue(SESSION_KEY);
    }

    @Override
    public @NonNull Integer getSessionTimeout() {
        return getIntegerValue(SESSION_TIMEOUT, SESSION_TIMEOUT_DEFAULT);
    }

    @NonNull
    @Override
    public String getApplicationName() {
        return getStringValue(APPLICATION_NAME_KEY, APPLICATION_NAME_DEFAULT);
    }

    @NonNull
    @Override
    public String getApplicationConfig() {
        return getStringValue(APPLICATION_FILE_NAME_KEY, APPLICATION_FILE_NAME_DEFAULT);
    }

    @NonNull
    @Override
    public String getApplicationVersion() {
//        return read(APPLICATION_VERSION_KEY);
        return "1.1.1";
    }

    @NonNull
    @Override
    public String getAuthorEmail() {
//        return read(AUTHOR_EMAIL_KEY);
        return "AUTHOR_EMAIL_KEY";
    }

    @NonNull
    @Override
    public String getAuthorName() {
//        return read(AUTHOR_NAME_KEY);
        return "AUTHOR_NAME_KEY";
    }

    @NonNull
    private String read(final String key) {
        if (key == null || key.isEmpty()) return EMPTY_VALUE;
        if (!Manifests.exists(key)) return EMPTY_VALUE;
        return Manifests.read(key);
    }

    @Override
    public @NonNull String getGitBranch() {
//        return read(GIT_BRANCH);
        return "GIT_BRANCH";
    }

    @Override
    public @NonNull String getGitCommitId() {
//        return read(GIT_COMMIT_ID);
        return "GIT_COMMIT_ID";
    }

    @Override
    public @NonNull String getGitCommitterName() {
//        return read(GIT_COMMITTER_NAME);
        return "GIT_COMMITTER_NAME";
    }

    @Override
    public @NonNull String getGitCommitterEmail() {
//        return read(GIT_COMMITTER_EMAIL);
        return "GIT_COMMITTER_EMAIL";
    }

    @Override
    public @NonNull String getGitCommitMessage() {
//        return read(GIT_COMMIT_MESSAGE);
        return "GIT_COMMIT_MESSAGE";
    }

    @Override
    public @NonNull String getGitCommitTime() {
//        return read(GIT_COMMIT_TIME);
        return "GIT_COMMIT_TIME";
    }

    @NonNull
    @Override
    public String getPort() {
        return "8080";
//        return getStringValue(PORT, PORT_DEFAULT);
    }

    @NonNull
    @Override
    public String getHost() {
        return "0.0.0.0";
//        return getStringValue(HOST);
    }

    @NonNull
    private String getEnvKey(@NonNull final String key) {
        return key.replace(".", "_").toUpperCase();
    }

    @NonNull
    @Override
    public Integer getPasswordIteration() {
        return getIntegerValue(PASSWORD_ITERATION_KEY, PASSWORD_ITERATION_DEFAULT);
    }

    @NonNull
    @Override
    public String getPasswordSecret() {
        return getStringValue(PASSWORD_SECRET_KEY, PASSWORD_SECRET_DEFAULT);
    }

    @NonNull
    private Integer getIntegerValue(@NonNull final String key, @NonNull final String defaultValue) {
        return Integer.parseInt(getStringValue(key, defaultValue));
    }

    @NonNull
    private String getStringValue(@NonNull final String key, @NonNull final String defaultValue) {
        if (System.getProperties().containsKey(key)) return System.getProperties().getProperty(key);
        @NonNull final String envKey = getEnvKey(key);
        if (System.getenv().containsKey(envKey)) return System.getenv(envKey);
        return properties.getProperty(key, defaultValue);
    }

    @NonNull
    private String getStringValue(@NonNull final String key) {
        return getStringValue(key, EMPTY_VALUE);
    }

}
