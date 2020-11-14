//CREATE TABLE транспорт
//        (
//        id_транспорта            SERIAL PRIMARY KEY,
//        тип                     VARCHAR(32) NOT NULL,
//        стоимость_проезда       INTEGER CHECK (стоимость_проезда > 0) NOT NULL,
//        время_в_пути            INTERVAL NOT NULL,
//        id_локации_а            INTEGER NOT NULL REFERENCES локация ON DELETE CASCADE,
//        id_локации_б            INTEGER NOT NULL REFERENCES локация ON DELETE CASCADE
//        );


public class Transport {
    static String[] type = {"метро","автобус","трамвай","электричка"};
    static int[] price = {50,55,60};
    static String[] time={};
    static int[] location1ID={};
    static int[] location2ID={};
}
