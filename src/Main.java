import java.io.IOException;

class Main {

    public static void main (String args []) {
        try {
            User.createUsers();
            Location.createLocations();
            Activity.createActivities();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
