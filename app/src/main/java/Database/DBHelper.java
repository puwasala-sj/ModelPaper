package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Users.db";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UserProfile.Users.TABLE_NAME + " (" +
                        UserProfile.Users._ID + " INTEGER PRIMARY KEY," +
                        UserProfile.Users.COLUMN_NAME + " TEXT," +
                        UserProfile.Users.COLUMN_NAME_DOB + " TEXT," +
                        UserProfile.Users.COLUMN_NAME_PASSWORD + " TEXT," +
                        UserProfile.Users.COLUMN_NAME_GENDER + " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + UserProfile.Users.TABLE_NAME;

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long checkUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        String[] selectionArgs = {username, password};
        String query = "SELECT * FROM " + UserProfile.Users.TABLE_NAME + " WHERE "+ UserProfile.Users.COLUMN_NAME + " = ? AND " +
                UserProfile.Users.COLUMN_NAME_PASSWORD + " = ?";

        Cursor cursor = db.rawQuery(query, selectionArgs);

        long userId = -1;
        if (cursor.moveToFirst()) {
            userId = (cursor.getCount() >= 1) ? cursor.getLong(0) : -1;
        }

        return userId;
    }

    public boolean addInfo(String username, String dob,String password, String Gender){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_NAME, username);
        values.put(UserProfile.Users.COLUMN_NAME_DOB, dob);
        values.put(UserProfile.Users.COLUMN_NAME_PASSWORD, password);
        values.put(UserProfile.Users.COLUMN_NAME_GENDER, Gender);

        long newRowId = db.insert(UserProfile.Users.TABLE_NAME, null, values);
        if(newRowId == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean  updateInfo(long userId, String username,String dob, String password, String gender){
        SQLiteDatabase db = getWritableDatabase();
        String[] selectionArgs = {userId +" "};
        String whereClause = UserProfile.Users._ID + " = ?";

        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_NAME, username);
        values.put(UserProfile.Users.COLUMN_NAME_DOB, dob);
        values.put(UserProfile.Users.COLUMN_NAME_PASSWORD, password);
        values.put(UserProfile.Users.COLUMN_NAME_GENDER, gender);

        long newR = db.update(UserProfile.Users.TABLE_NAME, values, whereClause, selectionArgs);
        if (newR == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor readAllInfor(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_NAME,
                UserProfile.Users.COLUMN_NAME_DOB,
                UserProfile.Users.COLUMN_NAME_PASSWORD,
                UserProfile.Users.COLUMN_NAME_GENDER
        };

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public Cursor readAllInfor(long userId){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectionArgs = {userId + ""};
        String query = "SELECT * FROM " + UserProfile.Users.TABLE_NAME + " WHERE " + UserProfile.Users._ID + " = ? ";

        Cursor cursor = db.rawQuery(query,selectionArgs);
        return cursor;
    }

    public boolean deleteInfo(long userId){
        SQLiteDatabase db = getWritableDatabase();

        String[] selectionArgs = {userId+""};
        String whereClause = UserProfile.Users._ID + " = ?";

        long res = db.delete(UserProfile.Users.TABLE_NAME, whereClause, selectionArgs);
        if(res == -1){
            return false;
        }
        else{
            return true;
        }
    }
}
