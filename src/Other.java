//CREATE TABLE другое
//        (
//        id_другого            SERIAL PRIMARY KEY,
//        описание_активности   TEXT NOT NULL,
//        id_активности         INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
//        );

public class Other {
    static String[] activitiesDescription = {"уборка","готовка","маникюр","чтение","рисование","поход в ателье",
    "получение посылки с почты","поход к врачу","разговор с родителями","вышивание крестиком","просмотр фильма"};
    static int[] activitiesID;
}
