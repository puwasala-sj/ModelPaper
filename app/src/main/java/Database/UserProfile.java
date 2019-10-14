package Database;

import android.provider.BaseColumns;

public final class UserProfile {
    private UserProfile(){}

    public static class Users implements BaseColumns{
        public static final String TABLE_NAME = "UserInfo";
        public static final String COLUMN_NAME = "userName";
        public static final String COLUMN_NAME_DOB = "dateOfBirth";
        public static final String COLUMN_NAME_PASSWORD = "passWord";
        public static final String COLUMN_NAME_GENDER = "Gender";
    }
}
