//CREATE TABLE локация
//        (
//        id_локации      SERIAL PRIMARY KEY,
//        название        TEXT NOT NULL,
//        адрес           TEXT NOT NULL
//        );

//String.format( "INSERT INTO \"локация\" (\"название\",\"адрес\") " +
//        "VALUES (\'%s\', \'%s\');\n" , location.getValue(), location.getKey())

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Location {
    static Map<String, String> locations = new HashMap<>();
    static int locationQuantity;

    public static void createLocations() throws IOException {
        locations.put("г.Санкт-Петербург Ломоносова,д.9", "Университет ИТМО");
        locations.put("г.Санкт-Петербург Кронверкский проспект,д.49", "Университет ИТМО");
        locations.put("г.Санкт-Петербург Биржевая Линия,д.19", "Университет ИТМО");
        locations.put("г.Санкт-Петербург Переулок Гривцова,д.14", "Университет ИТМО");
        locations.put("г.Санкт-Петербург Чайковского,д.11/2", "Университет ИТМО");
        locations.put("г.Санкт-Петербург Новоизмайловский проспект,д.16к.3", "Общежитие МСГ");
        locations.put("г.Санкт-Петербург Вяземский переулок,д.5/7", "Студенческий городок университета ИТМО");
        locations.put("г.Альпийский переулок,д.15", "Общежитие ИТМО №3");
        locations.put("г.Санкт-Петербург Новоизмайловский проспект,д.16к.6", "Спортивный центр МСГ");
        locations.put("г.Санкт-Петербург Кузнецовская,д.11", "Пятерочка");
        locations.put("г.Санкт-Петербург Варшавская,д.23", "Пятерочка");
        locations.put("г.Санкт-Петербург Проспект Космонавтов,д.14", "ТЦ Радуга");
        locationQuantity = locations.size();

        FileWriter nFile = new FileWriter("locations.sql");
        for(Map.Entry<String, String> location : locations.entrySet()) {
            nFile.write(String.format( "INSERT INTO \"локация\" (\"название\",\"адрес\") " +
                    "VALUES (\'%s\', \'%s\');\n" , location.getValue(), location.getKey()));
        }
        nFile.close();
    }
}
