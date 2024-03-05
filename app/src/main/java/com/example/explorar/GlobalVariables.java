package com.example.explorar;

import com.example.explorar.user.UserData;

public class GlobalVariables {
    private static boolean dataChanged;
    private static UserData userData;

    public static boolean isDataChanged() {
        return dataChanged;
    }

    public static void setDataChanged(boolean dataChanged) {
        GlobalVariables.dataChanged = dataChanged;
    }

    public static UserData getUserData() {
        return userData;
    }

    public static void setUserData(UserData userData) {
        GlobalVariables.userData = userData;
    }
}
