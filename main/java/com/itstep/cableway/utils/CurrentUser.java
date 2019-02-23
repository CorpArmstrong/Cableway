package com.itstep.cableway.utils;

import com.itstep.cableway.db.entities.User;

public class CurrentUser {

    private static User currentUser;

    public static String name;
    public static String password;
    public static boolean isAdministrator;
    public static boolean isRoot;

    public static void registerUser(User user) {

        currentUser = user;

        name = currentUser.getName();
        password = currentUser.getPassword();
        isAdministrator = currentUser.isAdministrator();
        isRoot = currentUser.isRoot();
    }
}
