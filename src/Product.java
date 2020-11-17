//CREATE TABLE товар
//        (
//        id_товара            SERIAL PRIMARY KEY,
//        наименование         VARCHAR(32) NOT NULL,
//        стоимость            REAL CHECK (стоимость > 0) NOT NULL,
//        количество           INTEGER CHECK (количество > 0) NOT NULL,
//        срочность_покупки    DATE NOT NULL,
//        подтверждение        isConfirmed_enum,
//        id_списка_покупок    INTEGER NOT NULL REFERENCES список_покупок ON DELETE CASCADE
//        );

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//+++++++++++++++++++++++++++++++++++++++++
public class Product {
    static Map<String, String> products = new HashMap<>();
    static int[] quantity={1,2};
    static String[] urgency={"2020-11-22","2020-11-26","2020-11-27","2020-12-01","2020-12-02","2020-12-03","2020-12-08"
            ,"2020-12-09","2020-12-12","2020-12-14","2020-12-23","2020-12-26"};
    public static void createProducts() throws IOException {
        products.put("Молоко", "44.99");
        products.put("Биокефир", "27");
        products.put("Сыр", "150");
        products.put("Масло", "120");
        products.put("Творог", "79.99");
        products.put("Сметана", "55");
        products.put("Йогурт", "39");
        products.put("Свинина", "400");
        products.put("Курица", "300");
        products.put("Говядина", "450");
        products.put("Фарш", "150");
        products.put("Яйцо", "60.5");
        products.put("Колбаса", "200");
        products.put("Сосиски", "179");
        products.put("Форель", "229");
        products.put("Селедка", "100");
        products.put("Майонез", "49");
        products.put("Кетчуп", "54.3");
        products.put("Томатная паста", "40.2");
        products.put("Масло подсолнечное", "100");
        products.put("Макароны", "47.8");
        products.put("Рис", "56");
        products.put("Гречка", "66");
        products.put("Овсянка", "30");
        products.put("Пшенка", "55");
        products.put("Варенье", "100");
        products.put("Приправа", "40");
        products.put("Мука", "70");
        products.put("Сахар", "40");
        products.put("Соль", "20");
        products.put("Кофе", "300");
        products.put("Чай", "100");
        products.put("Шоколадка", "70");
        products.put("Сникерс", "30");
        products.put("Баунти", "30");
        products.put("Мороженое", "60");
        products.put("Печенье", "100");
        products.put("Кексики", "78.7");
        products.put("Вафли", "50.8");
        products.put("Мармелад", "45.9");
        products.put("Зефир", "56");
        products.put("Жевательная резинка", "25");
        products.put("Яблоки", "100");
        products.put("Виноград", "150");
        products.put("Бананы", "60");
        products.put("Грейпфруты", "89.99");
        products.put("Гранаты", "99.99");
        products.put("Апельсины", "119.99");
        products.put("Груши", "159.99");
        products.put("Морковь", "34.99");
        products.put("Огурцы", "68");
        products.put("Томаты", "88");
        products.put("Зелень", "49.99");
        products.put("Чебупели", "84.99");
        products.put("Пельмени", "300");
        products.put("Шпроты", "89");
        products.put("Маринованные огурцы", "89");
        products.put("Кабачковая икра", "50");
        products.put("Кукуруза", "47.49");
        products.put("Зеленый горошек", "55.49");
        products.put("Оливки", "76.88");
        products.put("Маринованные грибы", "119.99");
        products.put("Сок", "76.88");
        products.put("Газировка", "49.99");
        products.put("Стиральный порошок", "300");
        products.put("Средство для мытья посуды", "89.99");
        products.put("Шампунь", "200");
        products.put("Гель для душа", "112");
        products.put("Зубная паста", "145");
        products.put("Мыло", "50");
        FileWriter nFile = new FileWriter("products.sql");
        for (Map.Entry<String, String> products : products.entrySet()) {
            for (int i = 0; i < 3; i++) {
                nFile.write(String.format("INSERT INTO \"товар\" (\"наименование\",\"стоимость\",\"количество\"" +
                                ",\"срочность_покупки\",\"подтверждение\",\"id_списка_покупок\") " +
                                "VALUES (\'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\');\n",
                        products.getKey(), products.getValue(), quantity[(int) (Math.random() * quantity.length)],
                        urgency[(int) (Math.random() * urgency.length)], "не подтвержден",
                        (1+ (int)(Math.random() * ActivityTablesCreation.shoppingListsQuantity))));

            }
        }
        nFile.close();
    }
}
