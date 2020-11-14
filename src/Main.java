import java.io.IOException;

class Main {

    public static void main (String args []) {
        try {
            User.createUsers();
            Location.createLocations();
            Activity.createActivities();
            ShoppingList.createShoppingLists();
            Product.createProducts();
            Finance.createFinance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
