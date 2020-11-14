//CREATE TABLE встречи_люди
//        (
//        id_встречи              INTEGER NOT NULL REFERENCES встреча ON DELETE CASCADE,
//        id_пользователя         INTEGER NOT NULL REFERENCES пользователь ON DELETE CASCADE,
//        PRIMARY KEY (id_встречи, id_пользователя)
//        );

public class UsersMeetings {
    static int[] meetingsID;
    static int[] usersID;
}
