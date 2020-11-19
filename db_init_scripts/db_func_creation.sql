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
