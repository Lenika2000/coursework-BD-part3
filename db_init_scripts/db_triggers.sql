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

-- при подтверждении покупки создается новая запись в таблице финансы
CREATE TRIGGER make_purchase
AFTER UPDATE OF подтверждение ON товар
FOR EACH ROW
EXECUTE PROCEDURE confirm_purchase();

CREATE OR REPLACE FUNCTION confirm_purchase() RETURNS trigger AS
$$
DECLARE user_id INTEGER;
	BEGIN
		user_id = (SELECT id_пользователя FROM товар JOIN поход_в_магазин USING (id_списка_покупок) JOIN активность USING (id_активности) WHERE id_товара = NEW.id_товара);

		INSERT INTO финансы (тип, сумма, статья, дата_совершения, id_пользователя) VALUES ('расход', NEW.стоимость * New.количество, NEW.наименование, CURRENT_DATE, user_id);

		RETURN NULL;
	END
$$ LANGUAGE plpgsql;

-- при подтверждении выполнения активности изменяется текущий уровень стресса
CREATE TRIGGER perform_act
AFTER UPDATE OF готовность ON активность
FOR EACH ROW
EXECUTE PROCEDURE confirm_action();

CREATE OR REPLACE FUNCTION confirm_action() RETURNS trigger AS
$$
	BEGIN
		IF NEW.готовность = 'не выполнено' THEN
		RETURN NULL;
		END IF;

		UPDATE пользователь SET текущий_ус = текущий_ус+NEW.влияние_на_уровень_стресса WHERE id_пользователя = NEW.id_пользователя;

		RETURN NULL;
	END
$$ LANGUAGE plpgsql;




