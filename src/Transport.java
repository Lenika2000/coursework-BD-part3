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
    public String type;
    public int price;
    public String time;
    public int location1ID;
    public int location2ID;

    public Transport(String type, int price, String time, int location1ID, int location2ID) {
        this.type = type;
        this.price = price;
        this.time = time;
        this.location1ID = location1ID;
        this.location2ID = location2ID;
    }

}
