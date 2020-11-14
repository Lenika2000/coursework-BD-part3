//CREATE TABLE активность
//        (
//        id_активности                  SERIAL PRIMARY KEY,
//        допустимое_время_начала        TIMESTAMP NOT NULL,
//        допустимое_время_конца         TIMESTAMP NOT NULL,
//        продолжительность              INTERVAL NOT NULL,
//        периодичность                  INTERVAL NOT NULL,
//        формат                         format_enum,
//        влияние_на_уровень_стресса     INTEGER NOT NULL,
//        id_локации                     INTEGER NOT NULL REFERENCES локация ON DELETE SET NULL,
//        id_пользователя                INTEGER NOT NULL REFERENCES пользователь ON DELETE CASCADE
//        );

//триггер, которая проверит, что допустимое время начала не больше допустимого времени конца
//и между ними помещается заданный интервал

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Activity {
    static String [] time = {"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"
            , "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
            "22:00", "22:30", "23:00", "23:30", "00:00", "00:30"};
    static Map<String, String[]> startAndEndTime = new HashMap<>();
   // https://oracle-patches.com/db/sql/3443-%D0%BB%D0%B8%D1%82%D0%B5%D1%80%D0%B0%D0%BB%D1%8B-%D1%82%D0%B8%D0%BF%D0%B0-interval-%D0%B2-pl-sql
    static String[] periodicity = {"INTERVAL \'1\' DAY","INTERVAL \'2\' DAY","INTERVAL \'3\' DAY",
            "INTERVAL \'4\' DAY", "INTERVAL \'5\' DAY", "INTERVAL \'6\' DAY", "INTERVAL \'7\' DAY",
            "INTERVAL \'14\' DAY", "INTERVAL \'1\' MONTH", "INTERVAL \'2\' MONTH"};
    static String[] duration = { "INTERVAL \'30\' MINUTE", "INTERVAL \'1\' HOUR", "INTERVAL \'1:30\' HOUR TO MINUTE"
            , "INTERVAL \'2\' HOUR", "INTERVAL \'3\' HOUR", "INTERVAL \'4\' HOUR", "INTERVAL \'5\' HOUR"
            , "INTERVAL \'6\' HOUR", "INTERVAL \'7\' HOUR", "INTERVAL \'8\' HOUR" };
    static String[] format = {"очный","дистанционный"};
    static int[] impactOnStressLevel = {-200,-100,-50,-25,-15,0,15,25,50,100,200};
    static int [] locationsId = new int[Location.locationQuantity-1];
    static int [] usersId = new int[User.usersQuantity-1];

    public static void createActivities() throws IOException {
        for (int i = 0; i < time.length ; i++){
            startAndEndTime.put(time[i], Arrays.copyOfRange(time, i+1, time.length));
        }

        for (int i = 0; i < (Location.locationQuantity - 1) ; i++){
            locationsId[i] = i+1;
        }
        for (int i = 0; i < (User.usersQuantity-1) ; i++){
            usersId[i] = i+1;
        }
        FileWriter nFile = new FileWriter("activities.sql");
        for (int i = 0; i < 150 ; i++) {
            String startTime = time[(int) ( Math.random() * time.length)];
            nFile.write(String.format( "INSERT INTO \"активность\" (\"допустимое_время_начала\"," +
                    "\"допустимое_время_конца\", \"продолжительность\", \"периодичность\"," +
                    "\"формат\", \"влияние_на_уровень_стресса\", \"id_локации\", \"id_пользователя\")" +
                    " VALUES (\'%s\', \'%s\',\'%s\', \'%s\',\'%s\', \'%s\',\'%s\', \'%s\');\n" ,
                    startTime, startAndEndTime.get(startTime),
                    periodicity[(int) ( Math.random() * periodicity.length)],duration[(int) ( Math.random() * duration.length)],
                    format[(int) ( Math.random() * format.length)], impactOnStressLevel[(int) ( Math.random() * impactOnStressLevel.length)],
                    locationsId[(int) ( Math.random() * locationsId.length)],usersId[(int) ( Math.random() * usersId.length)]));
        }
    }
}

