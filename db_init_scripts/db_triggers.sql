-- триггер чтобы нельзя было запланировать встречу с самим собой
CREATE OR REPLACE FUNCTION isMeetingCorrect() RETURNS TRIGGER AS
$$
DECLARE организатор_встречи SMALLINT;
BEGIN
        организатор_встречи = (SELECT пользователь.id_пользователя from встречи_люди
        join встреча on встреча.id_встречи = 1
        join активность  on активность.id_активности = встреча.id_активности
        join пользователь on пользователь.id_пользователя = активность.id_пользователя
        GROUP BY пользователь.id_пользователя);

        IF (NEW.id_пользователя = организатор_встречи) THEN
            RAISE EXCEPTION 'Невозможно запланировать встречу с самим собой';
        ELSE
            RETURN NEW;
        END IF;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER isMeetingCorrect
    BEFORE INSERT OR UPDATE
    ON встречи_люди
    FOR EACH ROW
EXECUTE PROCEDURE isMeetingCorrect();

DROP TRIGGER isMeetingCorrect ON встречи_люди;
