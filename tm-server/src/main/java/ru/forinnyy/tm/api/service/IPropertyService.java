package ru.forinnyy.tm.api.service;

import lombok.NonNull;
import ru.forinnyy.tm.api.component.ISaltProvider;

public interface IPropertyService extends ISaltProvider {

    @NonNull
    String getApplicationVersion();

    @NonNull
    String getApplicationConfig();

    @NonNull
    String getApplicationName();

    @NonNull
    String getAuthorEmail();

    @NonNull
    String getAuthorName();

    @NonNull
    String getGitBranch();

    @NonNull
    String getGitCommitId();

    @NonNull
    String getGitCommitterName();

    @NonNull
    String getGitCommitterEmail();

    @NonNull
    String getGitCommitMessage();

    @NonNull
    String getGitCommitTime();

    @NonNull
    String getHost();

    @NonNull
    String getPort();

    @NonNull
    String getDBDriver();

    @NonNull
    String getSessionKey();

    @NonNull
    Integer getSessionTimeout();

    @NonNull
    String getDBUser();

    @NonNull
    String getDBPassword();

    @NonNull
    String getDBUrl();

}
