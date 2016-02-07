package com.mytway.database;

public class UserTable {
    // Labels table name
    public static final String TABLE = "User";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "user_name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String TYPE_WORK = "type_work";
    public static final String LENGTH_TIME_WORK = "length_time_work";
    public static final String START_STANDARD_TIME = "start_standard_time";
    public static final String WORK_PLACE_LATITUDE = "work_place_latitude";
    public static final String WORK_PLACE_LONGITUDE = "work_place_longitude";
    public static final String HOME_PLACE_LATITUDE = "home_place_latitude";
    public static final String HOME_PLACE_LONGITUDE = "home_place_longitude";
    public static final String WORK_WEEK = "work_week";

    // property help us to keep data
    public int userId;
    public String userName;
    public String email;
    public String password;
    public int typeWork;
    public String lengthTimeWork;
    public String startStandardTimeWork;
    public double workPlaceLatitude;
    public double workPlaceLongitude;
    public double homePlaceLatitude;
    public double homePlaceLongitude;
    public String workWeek;

}
