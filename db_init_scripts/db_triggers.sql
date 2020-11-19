-- триггер чтобы нельзя было запланировать встречу с самим собой
CREATE OR REPLACE FUNCTION isMeetingCorrect() RETURNS TRIGGER AS
$$
DECLARE организатор_встречи SMALLINT;
BEGIN
        организатор_встречи = (SELECT пользователь.id_пользователя from встречи_люди
        join встреча on встреча.id_встречи = NEW.id_встречи
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


-- если занятие проходит очно, то обязательно должна быть указана аудитория
CREATE OR REPLACE FUNCTION hasLessonRoom() RETURNS TRIGGER AS
$$
DECLARE формат format_enum;
BEGIN
        формат = (SELECT активность.формат from активность
        join учебное_занятие уз on активность.id_активности = NEW.id_активности
        group by активность.формат);

        IF (NEW.аудитория IS NULL and формат='очный') THEN
            RAISE EXCEPTION 'Очное занятие можно добавить лишь с указанием аудитории';
        ELSE
            RETURN NEW;
        END IF;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER hasLessonRoom
    BEFORE INSERT OR UPDATE
    ON учебное_занятие
    FOR EACH ROW
EXECUTE PROCEDURE hasLessonRoom();
