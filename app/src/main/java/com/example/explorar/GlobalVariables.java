package com.example.explorar;

import com.example.explorar.user.UserData;

public class GlobalVariables {
    private static boolean dataDeleted;
    private static boolean dataChanged;
    private static UserData userData;

    public static boolean isDataDeleted() {
        return dataDeleted;
    }

    public static void setDataDeleted(boolean dataDeleted) {
        GlobalVariables.dataDeleted = dataDeleted;
    }

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
