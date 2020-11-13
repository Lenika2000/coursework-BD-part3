//CREATE TABLE пользователь
//        (
//        id_пользователя      SERIAL PRIMARY KEY,
//        имя                  varchar(16) NOT NULL,
//        фамилия              varchar(16) NOT NULL,
//        макс_допустимый_ус   INTEGER NOT NULL CHECK (макс_допустимый_ус >= 0)
//        );

//String.format( "INSERT INTO "пользователь" ("имя","фамилия","макс_допустимый_ус")
//        VALUES ('%s', '%s', '%s')" , firstName, lastName, maxStressLevel);

import java.io.FileWriter;
import java.io.IOException;

public class User {
    static String[] firstNames = {"Екатерина","Евгения","Елена","Алена","Виктория","Анна","Анастасия",
    "Зарина"}; // имя
    static String[] lastNames= {"Маньшина","Корнишова","Гумирова","Андреева","Подсекина","Белова",
    "Петрова", "Иванова"};   // фамилия
    static int[] maxStressLevel = {100,200,300,400,500,600,700};
    static int usersQuantity = firstNames.length * lastNames.length;

    public static void createUsers() throws IOException {
        FileWriter nFile = new FileWriter("users.sql");
        for (String firstName : firstNames) {
            for (String lastName : lastNames) {
                int maxStressLevelIndex = (int) ( Math.random() * 7);
                nFile.write(String.format( "INSERT INTO \"пользователь\" (\"имя\",\"фамилия\",\"макс_допустимый_ус\") " +
                        "VALUES (\'%s\', \'%s\', \'%s\');\n" , firstName, lastName, maxStressLevel[maxStressLevelIndex]));
            }
        }
        nFile.close();
    }
}
