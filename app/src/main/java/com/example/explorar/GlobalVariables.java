package com.example.explorar;

import com.example.explorar.user.UserData;

public class GlobalVariables {
    private static UserData userData;

    public static UserData getUserData() {
        return userData;
    }

    public static void setUserData(UserData userData) {
        GlobalVariables.userData = userData;
    }
}
