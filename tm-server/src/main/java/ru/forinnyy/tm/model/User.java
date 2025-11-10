package ru.forinnyy.tm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import ru.forinnyy.tm.enumerated.Role;


@Getter
@Setter
@NoArgsConstructor
public final class User extends AbstractModel {

    private static final long serialVersionUID = 1;

    private String login;

    private String passwordHash;

    private String email;

    private String firstName;

    private String lastName;

    private String middleName;

    @NonNull
    private Role role = Role.USUAL;

    private boolean locked = false;

}
