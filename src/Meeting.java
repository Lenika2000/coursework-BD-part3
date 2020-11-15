//CREATE TABLE встреча
//        (
//        id_встречи            SERIAL PRIMARY KEY,
//        id_активности         INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
//        );

//String.format( "INSERT INTO \"встреча\" (\"id_активности\") " +
//        "VALUES (\'%s\');\n" , activitiesID[i] )

public class Meeting extends Activity {

    public Meeting(int activityID, String startTime, String endTime, String periodicity, String duration, String format, int impactOnStressLevel, String isDone, int locationId, int userId) {
        super(activityID, startTime, endTime, periodicity, duration, format, impactOnStressLevel, isDone, locationId, userId);
    }
}
