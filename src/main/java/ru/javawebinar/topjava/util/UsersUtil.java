package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static final List<User> USERS = Arrays.asList(
            new User(1, "first", "fistUser@gmail.com", "password", Role.ROLE_USER),
            new User(2, "second", "secondUser@gmail.com", "password", Role.ROLE_USER),
            new User(3, "third", "thirdUser@gmail.com", "password", Role.ROLE_ADMIN),
            new User(4, "fourth", "fourthUser@gmail.com", "password", Role.ROLE_USER),
            new User(5, "fifths", "fifthsUser@gmail.com", "password", Role.ROLE_ADMIN),
            new User(6, "six", "sixUser@gmail.com", "password", Role.ROLE_ADMIN)
    );
}
