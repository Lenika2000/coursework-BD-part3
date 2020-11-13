import java.io.IOException;

class Main {

    public static void main (String args []) {
        try {
            User.createUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
