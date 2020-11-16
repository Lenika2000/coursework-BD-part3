import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ActivityTablesCreation {
    static FileWriter activitiesFile;
    static Location[] locations = new Location[15];
    // разграничение айдишников локаций
    static int itmoStart;
    static int itmoEnd;
    static int distant;
    static int sport;
    static int shopsStart;
    static int shopsEnd;

    static int shoppingListsQuantity = 70;
    static String[] shoppingLists = new String[shoppingListsQuantity];
    // интервалы различных типов активностей
    static int lessonsEnd = 51;
    static int workEnd = lessonsEnd+User.usersQuantity;
    static int sportEnd = workEnd+50;
    static int otherEnd = sportEnd+50;
    static int shoppingEnd = otherEnd+70;
    static int meetingEnd = shoppingEnd+50;

    // активность
    static String [] time = {"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"
            , "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
            "22:00", "22:30", "23:00", "23:30", "00:00", "00:30"};
    static Map<String, String[]> startAndEndTime = new HashMap<>();
    // https://oracle-patches.com/db/sql/3443-%D0%BB%D0%B8%D1%82%D0%B5%D1%80%D0%B0%D0%BB%D1%8B-%D1%82%D0%B8%D0%BF%D0%B0-interval-%D0%B2-pl-sql
    static String[] periodicity = {"","INTERVAL \'1\' DAY","INTERVAL \'2\' DAY","INTERVAL \'3\' DAY",
            "INTERVAL \'4\' DAY", "INTERVAL \'5\' DAY", "INTERVAL \'6\' DAY", "INTERVAL \'7\' DAY",
            "INTERVAL \'14\' DAY", "INTERVAL \'1\' MONTH", "INTERVAL \'2\' MONTH"};
    //    static String[] duration = { "INTERVAL \'30\' MINUTE", "INTERVAL \'1\' HOUR", "INTERVAL \'1:30\' HOUR TO MINUTE"
//            , "INTERVAL \'2\' HOUR", "INTERVAL \'3\' HOUR", "INTERVAL \'4\' HOUR", "INTERVAL \'5\' HOUR"
//            , "INTERVAL \'6\' HOUR", "INTERVAL \'7\' HOUR", "INTERVAL \'8\' HOUR" };
    static String[] duration = { "INTERVAL \'30\' MINUTE", "INTERVAL \'1\' HOUR", "INTERVAL \'1:30\' HOUR TO MINUTE"
            , "INTERVAL \'2\' HOUR"};
    static String[] format = {"очный","дистанционный"};
    static int[] impactOnStressLevel = {-200,-100,-50,-25,-15,0,15,25,50,100,200};
    static int [] usersId = new int[User.usersQuantity];


    public static void createTables() throws IOException {
        activitiesFile = new FileWriter("activities.sql");
        for (int i = 0; i < User.usersQuantity; i++) {
            usersId[i] = i + 1;
        }
        // разнообразные интервалы
        for (int i = 0; i < time.length - 4; i++) {
            startAndEndTime.put(time[i], Arrays.copyOfRange(time, i + 4, time.length));
        }
        createLocations();
        createLessons();
        createWork();
        createSport();
        createOther();
        createShoppingLists();
        createShopping();
        createMeeting();
        System.out.println(meetingEnd);
        activitiesFile.close();
    }


    public static void createLocations() throws IOException {
        itmoStart = 1;
        itmoEnd = 5;
        locations[1] = new Location("г.Санкт-Петербург Ломоносова,д.9", "Университет ИТМО");
        locations[2] = new Location("г.Санкт-Петербург Кронверкский проспект,д.49", "Университет ИТМО");
        locations[3] = new Location("г.Санкт-Петербург Биржевая Линия,д.19", "Университет ИТМО");
        locations[4] = new Location("г.Санкт-Петербург Переулок Гривцова,д.14", "Университет ИТМО");
        locations[5] = new Location("г.Санкт-Петербург Чайковского,д.11/2", "Университет ИТМО");

        // дистант+другое
        distant = 6;
        locations[6] = new Location("г.Санкт-Петербург Новоизмайловский проспект,д.16к.3", "Общежитие МСГ");

        // спорт
        sport = 7;
        locations[7] = new Location("г.Санкт-Петербург Новоизмайловский проспект,д.16к.6", "Спортивный центр МСГ");

        shopsStart = 8;
        shopsEnd = 14;
        locations[8] = new Location("г.Санкт-Петербург Кузнецовская,д.11", "Пятерочка");
        locations[9] = new Location("г.Санкт-Петербург Варшавская,д.23", "Пятерочка");
        locations[10] = new Location("г.Санкт-Петербург Варшавская,д.29", "Магнит");
        locations[11] = new Location("г.Санкт-Петербург Проспект Космонавтов,д.14", "ТЦ Радуга");
        locations[12] = new Location("г.Санкт-Петербург Московский проспект,д.137", "Окей");
        locations[13] = new Location("г.Санкт-Петербург Бассейная,д.41", "Дикси");
        locations[14] = new Location("г.Санкт-Петербург Новоизмайловский проспект,д.28", "FixPrice");

        FileWriter locationsFile = new FileWriter("locations.sql");
        for (int i = 1; i < locations.length; i++) {
            locationsFile.write(String.format( "INSERT INTO \"локация\" (\"название\",\"адрес\") " +
                    "VALUES (\'%s\', \'%s\');\n" , locations[i].name, locations[i].address));
        }
        locationsFile.close();
    }

    public static void writeInActivitiesFile(String startTime, String endTime, String periodicity,
                                             String interval, String format, int impactOnStressLevel, int location,
                                             int usersId) throws IOException {
        periodicity = (periodicity=="") ? "" : ","+periodicity;
        String periodicityTableColumn = (periodicity=="") ? "" : ", периодичность";
        activitiesFile.write(String.format("INSERT INTO \"активность\" (\"допустимое_время_начала\"," +
                        "\"допустимое_время_конца\", \"продолжительность\" %s," +
                        "\"формат\", \"влияние_на_уровень_стресса\", \"готовность\", \"id_локации\", \"id_пользователя\")" +
                        " VALUES (\'%s\', \'%s\' %s, %s,\'%s\', \'%s\',\'%s\',\'%s\', \'%s\');\n",
                periodicityTableColumn,
                "2000-11-28 " + startTime,
                "2000-11-28 " + endTime,
                periodicity,
                interval,
                format ,
                impactOnStressLevel,
                "не выполнено",
                location,
                usersId));
    }

    // 70 списков покупок
    public static void createShoppingLists() throws IOException {
        FileWriter shoppingListFile = new FileWriter("shoppingList.sql");
        String[] shopName = {"Пятерочка", "Магнит", "Окей", "Дикси", "FixPrice"};
        for (int i = 0; i < shoppingListsQuantity; i++) {
                String printName = shopName[(int) (Math.random() * shopName.length)];
                shoppingListFile.write(String.format("INSERT INTO \"список_покупок\" (\"название_магазина\")" +
                        " VALUES (\'%s\');\n", printName));
                shoppingLists[i] = printName;
        }
        shoppingListFile.close();
    }


    // первые 50 строк активностей - пары
    public static void createLessons() throws IOException {
        FileWriter lessonsFile = new FileWriter("lessons.sql");
        int activityID = 1;
        String[] rooms = {"422", "466", "375", "324", "1310"};
        String[] teachers = {"Тропченко А.Ю", "Клименков С.В", "Ососкова М.И", "Балканский А.А", "Перл И.А",
                "Осипов С.В", "Болдырева Е.А", "Николаев В.В", "Харитонова А.Е", "Бессмертный И.А",
                "Малышева Т.А", "Балакшин П.В", "Поляков В.И", "Тертычный В.Ю", "Письмак А.Е"};
        String[] type = {"лекция", "практика"};
        for (int i = activityID; i < lessonsEnd; i++) {
            int indexStartTime = (int) (Math.random() * (time.length - 4));
            String startTime = time[indexStartTime];
            String endTime = time[indexStartTime+3];
            String printFormat = format[(int) (Math.random() * format.length)];
            int locationPrint = (printFormat == "дистанционный") ? distant :  (itmoStart + (int) (Math.random() * itmoEnd));
            String printRoom = (printFormat == "дистанционный") ? "" :  rooms[(int) (Math.random() * rooms.length)];
            writeInActivitiesFile(
                    startTime,
                    endTime,
                    periodicity[(int) (Math.random() * periodicity.length)],
                    "INTERVAL \'1:30\' HOUR TO MINUTE",
                    printFormat ,
                    impactOnStressLevel[(int) (Math.random() * impactOnStressLevel.length)],
                    locationPrint,
                    usersId[(int) (Math.random() * usersId.length)]);

            lessonsFile.write(String.format( "INSERT INTO \"учебное_занятие\" (\"аудитория\",\"преподаватель\", \"тип\", \"id_активности\") " +
                    "VALUES (\'%s\', \'%s\', \'%s\', \'%s\');\n" ,
                    printRoom,
                    teachers[(int) (Math.random() * teachers.length)],
                    type[(int) (Math.random() * type.length)],
                    i ));
        }
        lessonsFile.close();
    }

    // работа для всех пользователей дистант
    public static void createWork() throws IOException {
        FileWriter workFile = new FileWriter("work.sql");
        int activityID = lessonsEnd;
        for (int i = activityID; i < workEnd; i++) {
            writeInActivitiesFile(
                    "10:00",
                    "20:00",
                    "INTERVAL \'2\' DAY",
                    "INTERVAL \'5\' HOUR",
                    "дистанционный" ,
                    impactOnStressLevel[(int) (Math.random() * impactOnStressLevel.length)],
                    distant,
                    usersId[i-lessonsEnd]);

            workFile.write(String.format( "INSERT INTO \"рабочая_смена\" (\"id_активности\")" +
                            " VALUES (\'%s\');\n" , i ));
        }
        workFile.close();
    }

    // спорт
    public static void createSport() throws IOException {
        FileWriter sportFile = new FileWriter("sport.sql");
        String[] type = {"пилатес","функциональная тренировка","бассейн","тренажерный зал","йога","стрейчинг"};
        int activityID = workEnd;
        for (int i = activityID; i < sportEnd; i++) {
            String printType = type[(int) (Math.random() * type.length)];
            String printFormat = (printType == "бассейн" || printType == "тренажерный зал") ?
                    "очный" : format[(int) (Math.random() * format.length)];
            int printLocation = (printFormat == "очный") ? sport : distant;
            writeInActivitiesFile(
                    "08:00",
                    "22:00",
                    "INTERVAL \'3\' DAY",
                    "INTERVAL \'1\' HOUR",
                    printFormat,
                    impactOnStressLevel[(int) (Math.random() * impactOnStressLevel.length)],
                    printLocation,
                    usersId[(int) (Math.random() * usersId.length)]);

            sportFile.write(String.format( "INSERT INTO \"спортивное_занятие\" (\"вид_занятия\",\"id_активности\") " +
                    " VALUES (\'%s\', \'%s\');\n" , printType, i));
        }
        sportFile.close();
    }

    // другое
    public static void createOther() throws IOException {
        FileWriter othersFile = new FileWriter("other.sql");
        String[] activitiesDescription = {"уборка", "готовка", "маникюр", "чтение", "рисование",  "разговор с родителями", "вышивание крестиком", "просмотр фильма"};
        int activityID = sportEnd;

        for (int i = activityID; i < otherEnd; i++) {
            String startTime = time[(int) (Math.random() * (time.length - 4))];
            writeInActivitiesFile(
                    startTime,
                    startAndEndTime.get(startTime)[(int) (Math.random() * startAndEndTime.get(startTime).length)],
                    periodicity[(int) (Math.random() * periodicity.length)],
                    duration[(int) (Math.random() * duration.length)],
                    "дистанционный",
                    impactOnStressLevel[(int) (Math.random() * impactOnStressLevel.length)],
                    distant,
                    usersId[(int) (Math.random() * usersId.length)]);

            othersFile.write(String.format( "INSERT INTO \"другое\" (\"описание_активности\",\"id_активности\") " +
                    " VALUES (\'%s\', \'%s\');\n" ,
                    activitiesDescription[(int) (Math.random() *  activitiesDescription.length)]
                    , i));
        }
        othersFile.close();
    }

    static int choseShoppingLocation(String shop){
        switch (shop) {
            case "Пятерочка":
                return 8;
            case "Магнит":
                return 10;
            case "Окей":
                return  12;
            case "Дикси":
                return  13;
            case "FixPrice":
                return  14;
            default:
                return 9;
        }
    }


    // 70 походов в магазин
    public static void createShopping() throws IOException {
        FileWriter shoppingFile = new FileWriter("shopping.sql");
        int activityID = otherEnd;
        for (int i = activityID; i < shoppingEnd; i++) {
            String startTime = time[(int) (Math.random() * (time.length - 4))];
            int shoppingListID = (int) (Math.random() * shoppingLists.length);
            writeInActivitiesFile(
                    startTime,
                    startAndEndTime.get(startTime)[(int) (Math.random() * startAndEndTime.get(startTime).length)],
                    periodicity[(int) (Math.random() * periodicity.length)],
                    "INTERVAL \'1\' HOUR",
                    "очный",
                    impactOnStressLevel[(int) (Math.random() * impactOnStressLevel.length)],
                    choseShoppingLocation(shoppingLists[shoppingListID]),
                    usersId[(int) (Math.random() * usersId.length)]);

            shoppingFile.write(String.format( "INSERT INTO \"поход_в_магазин\" (\"id_списка_покупок\",\"id_активности\") " +
                            " VALUES (\'%s\', \'%s\');\n" ,
                    shoppingListID+1 , i));

        }
        shoppingFile.close();
    }

    public static void createMeeting() throws IOException {
        FileWriter meetingFile = new FileWriter("meeting.sql");
        FileWriter meetingsUsers = new FileWriter("meetings-users.sql");

        int activityID = shoppingEnd;
        for (int i = activityID; i < meetingEnd; i++) {
            String startTime = time[(int) (Math.random() * (time.length - 4))];
            int firstUser = usersId[(int) (Math.random() * usersId.length)];
            writeInActivitiesFile(
                    startTime,
                    startAndEndTime.get(startTime)[(int) (Math.random() * startAndEndTime.get(startTime).length)],
                    periodicity[(int) (Math.random() * periodicity.length)],
                    duration[(int) (Math.random() * duration.length)],
                    "очный",
                    impactOnStressLevel[(int) (Math.random() * impactOnStressLevel.length)],
                    1+ (int)Math.random() * locations.length,
                    firstUser);

            // встречи
            meetingFile.write(String.format( "INSERT INTO \"встреча\" (\"id_активности\") " +
                            " VALUES (\'%s\');\n" , i));

            // встречи-люди
            for (int j = 0; j < 2; j++) {
                int secondUser = usersId[(int) (Math.random() * usersId.length)];
                while (secondUser == firstUser) {
                    secondUser = usersId[(int) (Math.random() * usersId.length)];
                }
                meetingsUsers.write(String.format("INSERT INTO \"встречи_люди\" (\"id_встречи\",\"id_пользователя\") " +
                                " VALUES (\'%s\', \'%s\');\n",
                        i - activityID + 1, secondUser));
            }

        }
        meetingFile.close();
        meetingsUsers.close();
    }
}
