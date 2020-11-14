//CREATE TABLE поход_в_магазин
//        (
//        id_похода_в_магазин            SERIAL PRIMARY KEY,
//        id_списка_покупок              INTEGER NOT NULL REFERENCES список_покупок ON DELETE CASCADE,
//        id_активности                  INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
//        );

//String.format( "INSERT INTO \"поход_в_магазин\" (\"id_списка_покупок\",\"id_активности\") " +
//        "VALUES (\'%s\', \'%s\');\n" , )


public class Shopping {
    static int[] shoppingListID;
    static int[] activityID;
}
