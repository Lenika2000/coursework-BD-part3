//CREATE TABLE список_покупок
//        (
//        id_списка_покупок    SERIAL PRIMARY KEY,
//        название_магазина    varchar(32) NOT NULL
//        );

import java.io.FileWriter;
import java.io.IOException;

//+++++++++++++++++++++++++++++++++++++++++
public class ShoppingList {
    static String[] shopName = {"Пятерочка", "Магнит", "Окей", "Дикси", "FixPrice", "Ашан"};
    static int shoppingListsQuantity = 150;

    public static void createShoppingLists() throws IOException {
        FileWriter nFile = new FileWriter("shoppingLists.sql");
        for (int i = 0; i < shoppingListsQuantity; i++) {
            nFile.write(String.format("INSERT INTO \"список_покупок\" (\"название_магазина\")" +
                    " VALUES (\'%s\');\n",shopName[(int) ( Math.random() * shopName.length)]));
        }
        nFile.close();
    }
}
