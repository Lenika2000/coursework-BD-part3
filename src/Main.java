import java.io.IOException;

class Main {

    public static void main (String args []) {
        try {
            User.createUsers();
            ActivityTablesCreation.createTables();
            Product.createProducts();
            Finance.createFinance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
