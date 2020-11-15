//CREATE TABLE активность
//        (
//        id_активности                  SERIAL PRIMARY KEY,
//        допустимое_время_начала        TIMESTAMP NOT NULL,
//        допустимое_время_конца         TIMESTAMP NOT NULL,
//        продолжительность              INTERVAL NOT NULL,
//        периодичность                  INTERVAL NOT NULL,
//        формат                         format_enum,
//        влияние_на_уровень_стресса     INTEGER NOT NULL,
//        готовность			          isDone_enum,
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


//+++++++++++++++++++++++++++++++++++++++++
public class Activity {
    int activityID;
    String startTime;
    String endTime;
    String periodicity;
    String duration;
    String format;
    int impactOnStressLevel;
    String isDone;
    int locationId;
    int userId;

    public Activity(int activityID, String startTime, String endTime, String periodicity, String duration,
                    String format, int impactOnStressLevel, String isDone, int locationId, int userId) {
        this.activityID = activityID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.periodicity = periodicity;
        this.duration = duration;
        this.format = format;
        this.impactOnStressLevel = impactOnStressLevel;
        this.locationId = locationId;
        this.userId = userId;
        this.isDone = isDone;
    }

}

