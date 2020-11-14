//CREATE TABLE финансы
//        (
//        id_финансовой_операции            SERIAL PRIMARY KEY,
//        тип                               finance_type_enum,
//        сумма                             REAL CHECK (сумма > 0) NOT NULL,
//        статья                            TEXT NOT NULL,
//        дата_совершения                   DATE NOT NULL,
//        id_пользователя                   INTEGER NOT NULL REFERENCES пользователь ON DELETE CASCADE
//        );

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//+++++++++++++++++++++++++++++++++++++++++
public class Finance {
    static String[] type = {"доход","расход"};
    // доходы
    static String scholarshipDate="2020-11-03";
    static String salaryDate="2020-11-16";
    static double[] salaries = {15000,20000,30000,35000,40000,45000,50000};
    static double[] scholarships = {1000,2100,4200,5000,9600,15000};
    // расходы
    static Map<String, double[]> spending = new HashMap<>();
    static String[] date={"2020-11-01","2020-11-02","2020-11-03","2020-11-04","2020-11-05","2020-11-06"
            ,"2020-11-07","2020-11-08","2020-11-09","2020-11-10","2020-11-11","2020-11-12","2020-11-13"
            ,"2020-11-14","2020-11-15","2020-11-16","2020-11-17","2020-11-18","2020-11-19","2020-11-20"};

    public static void createFinance() throws IOException {
        FileWriter nFile = new FileWriter("finances.sql");
        // доходы
        for (int i = 0; i < User.usersQuantity; i++) {
            // зарплата
            nFile.write(String.format("INSERT INTO \"финансы\" (\"тип\",\"сумма\",\"статья\"" +
                            ",\"дата_совершения\",\"id_пользователя\") " +
                            "VALUES (\'%s\', \'%s\', \'%s\', \'%s\', \'%s\');\n",
                    type[0], salaries[(int) (Math.random() * salaries.length)], "зарплата",
                    salaryDate, i+1));
            // стипендия
            nFile.write(String.format("INSERT INTO \"финансы\" (\"тип\",\"сумма\",\"статья\"" +
                            ",\"дата_совершения\",\"id_пользователя\") " +
                            "VALUES (\'%s\', \'%s\', \'%s\', \'%s\', \'%s\');\n",
                    type[0], scholarships[(int) (Math.random() * scholarships.length)], "стипендия",
                    scholarshipDate, i+1));
        }
        // расходы
        spending.put("Одежда", new double[]{500, 1000, 2000, 2500, 3000});
        spending.put("Продукты", new double[]{100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1110});
        spending.put("Плата за общежитие", new double[]{1000, 1600, 1800, 2000, 2700, 3000});
        spending.put("Бытовая химия", new double[]{120, 200, 360, 400, 500});
        spending.put("Кафе", new double[]{150, 250, 344, 455, 567, 888, 1000});
        spending.put("Здоровье и красота", new double[]{150, 250, 344, 455, 567, 888, 1000});
        spending.put("Все для дома", new double[]{1000, 1600, 1800, 2000, 2700, 3000});
        for (int i = 0; i < User.usersQuantity; i++) {
            for(Map.Entry<String, double[]> spending : spending.entrySet()) {
                nFile.write(String.format("INSERT INTO \"финансы\" (\"тип\",\"сумма\",\"статья\"" +
                                ",\"дата_совершения\",\"id_пользователя\") " +
                                "VALUES (\'%s\', \'%s\', \'%s\', \'%s\', \'%s\');\n",
                        type[1], spending.getValue()[(int) (Math.random() * spending.getValue().length)],
                        spending.getKey(), date[(int) (Math.random() * date.length)], i+1));
            }
        }
        nFile.close();
    }
}
