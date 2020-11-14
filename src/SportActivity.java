//CREATE TABLE спортивное_занятие
//        (
//        id_спортивного_занятия            SERIAL PRIMARY KEY,
//        вид_занятия                       TEXT NOT NULL,
//        id_активности                     INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
//        );

//String.format( "INSERT INTO \"спортивное_занятие\" (\"вид_занятия\",\"id_активности\") " +
//        "VALUES (\'%s\', \'%s\');\n" , )


public class SportActivity {
    static String[] type = {"пилатес","функциональная тренировка","бассейн","тренажерный зал","йога","стрейчинг"};
    static int[] activityID;
}
