//CREATE TABLE встреча
//        (
//        id_встречи            SERIAL PRIMARY KEY,
//        id_активности         INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
//        );

//String.format( "INSERT INTO \"встреча\" (\"id_активности\") " +
//        "VALUES (\'%s\');\n" , activitiesID[i] )

public class Meeting {
    static int[] activitiesID;
}
