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


//+++++++++++++++++++++++++++++++++++++++++
public class Location {
    String address;
    String name;

    public Location(String address, String name) {
        this.address = address;
        this.name = name;
    }
}
