-- подсчитывает сумму расходов/доходов пользователя за определенный период
CREATE OR REPLACE FUNCTION calc_financial(user_id INTEGER, type finance_type_enum, from_date Date = null, to_date Date = null) RETURNS REAL AS
$$
	BEGIN

		IF (from_date IS null AND to_date IS null) THEN
			RETURN (SELECT sum(сумма) from финансы where id_пользователя = user_id AND тип = type);
		ELSIF (from_date IS null AND to_date IS NOT null) THEN
			RETURN (SELECT sum(сумма) from финансы where id_пользователя = user_id AND тип = type AND дата_совершения <= to_date);
		ELSIF (from_date IS NOT null AND to_date IS null) THEN
			RETURN (SELECT sum(сумма) from финансы where id_пользователя = user_id AND тип = type AND дата_совершения >= from_date);
		ELSE
			RETURN (SELECT sum(сумма) from финансы where id_пользователя = user_id AND тип = type AND дата_совершения >= from_date AND дата_совершения <= to_date);
		END IF;


	END
$$ LANGUAGE plpgsql;

-- рассчитывает предполагаемую стоимость похода в магазин на основе списка
-- покупок
CREATE OR REPLACE FUNCTION shopping_costs(shopping_id INTEGER) RETURNS REAL AS
$$
	BEGIN
		RETURN (SELECT sum(товар.количество * товар.стоимость) FROM товар JOIN поход_в_магазин USING (id_списка_покупок) WHERE id_похода_в_магазин = shopping_id);
	END
$$ LANGUAGE plpgsql;


-- выводит баланс пользователя
CREATE OR REPLACE FUNCTION check_balance(user_id INTEGER) RETURNS REAL AS
$$
DECLARE income REAL;
DECLARE expenses REAL;
	BEGIN
		income = (SELECT calc_financial(user_id, 'доход'));
		expenses = (SELECT calc_financial(user_id, 'расход'));
		RETURN (income - expenses);
	END
$$ LANGUAGE plpgsql;


-- выводит список дел, которые пользователь может выполнить сегодня, начиная -- с наиболее срочных.
CREATE OR REPLACE FUNCTION daily_activity_list(user_id INTEGER, day DATE) RETURNS TABLE(допустимое_время_начала timestamp, допустимое_время_конца timestamp, продолжительность interval, периодичность interval, формат format_enum, влияние_на_уровень_стресса integer, локация text) AS
$$
	BEGIN
		RETURN QUERY SELECT активность.допустимое_время_начала, активность.допустимое_время_конца, активность.продолжительность, активность.периодичность, активность.формат, активность.влияние_на_уровень_стресса, локация.название
FROM активность JOIN локация USING (id_локации)
WHERE активность.id_пользователя = user_id
AND DATE(активность.допустимое_время_начала) <= day
AND DATE(активность.допустимое_время_конца) >= day ORDER BY активность.допустимое_время_конца;
	END
$$ LANGUAGE plpgsql;

-- показывает транспорт необходимый для перемещения от активности А до
-- активности Б
CREATE OR REPLACE FUNCTION transport_for_activities(id_activity_A INTEGER, id_activity_B INTEGER) RETURNS TABLE (тип VARCHAR(32), стоимость_проезда INTEGER, время_в_пути INTERVAL, локация_А TEXT, локация_Б TEXT) AS
$$
DECLARE id_location_A INTEGER;
DECLARE id_location_B INTEGER;
	BEGIN
		id_location_A = (SELECT id_локации FROM активность where id_активности = id_activity_A);
		id_location_B = (SELECT id_локации FROM активность where id_активности = id_activity_B);
		IF id_location_A = id_location_B THEN
			RETURN;
		END IF;
		RETURN QUERY (
			SELECT транспорт.тип, транспорт.стоимость_проезда, транспорт.время_в_пути, locationA.название AS локация_А, locationB.название AS локация_Б
			FROM транспорт
			JOIN локация AS locationA ON транспорт.id_локации_а = locationA.id_локации
			JOIN локация AS locationB ON транспорт.id_локации_б = locationB.id_локации
			WHERE транспорт.id_локации_а = id_location_A AND транспорт.id_локации_б = id_location_B);
	END
$$ LANGUAGE plpgsql;
