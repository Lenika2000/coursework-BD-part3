CREATE TABLE пользователь
(
    id_пользователя      SERIAL PRIMARY KEY,
    имя                  varchar(16) NOT NULL,
    фамилия              varchar(16) NOT NULL,
    макс_допустимый_ус   INTEGER NOT NULL CHECK (макс_допустимый_ус >= 0)
);

CREATE TABLE список_покупок
(
    id_списка_покупок    SERIAL PRIMARY KEY,
    название_магазина    varchar(32) NOT NULL
);

CREATE TABLE локация
(
    id_локации      SERIAL PRIMARY KEY,
    название        TEXT NOT NULL,
    адрес           TEXT NOT NULL
);

CREATE TABLE активность
(
    id_активности                  SERIAL PRIMARY KEY,
    допустимое_время_начала        TIMESTAMP NOT NULL,
    допустимое_время_конца         TIMESTAMP NOT NULL,
    продолжительность              INTERVAL NOT NULL,
    периодичность                  INTERVAL,
    формат                         format_enum,
    влияние_на_уровень_стресса     INTEGER NOT NULL,
    готовность			           isDone_enum,
    id_локации                     INTEGER NOT NULL REFERENCES локация ON DELETE SET NULL,
    id_пользователя                INTEGER NOT NULL REFERENCES пользователь ON DELETE CASCADE
    CONSTRAINT checkInterval
      CHECK(
	периодичность IS NULL
	AND допустимое_время_конца - продолжительность >= CURRENT_TIMESTAMP
	AND допустимое_время_конца > допустимое_время_начала 
	AND допустимое_время_конца - допустимое_время_начала >= продолжительность 
	OR (периодичность IS NOT NULL
	AND допустимое_время_конца::time > допустимое_время_начала::time
	AND допустимое_время_конца::time - допустимое_время_начала::time <  периодичность
	AND допустимое_время_конца::time - допустимое_время_начала::time >= продолжительность 
	) 
      )
);


CREATE TABLE учебное_занятие
(
    id_учебного_занятия            SERIAL PRIMARY KEY,
    аудитория                      VARCHAR(8),
    преподаватель                  VARCHAR(64) NOT NULL,
    тип                            lesson_type_enum,
    id_активности                  INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
);

CREATE TABLE поход_в_магазин
(
    id_похода_в_магазин            SERIAL PRIMARY KEY,
    id_списка_покупок              INTEGER NOT NULL REFERENCES список_покупок ON DELETE CASCADE,
    id_активности                  INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
);

CREATE TABLE рабочая_смена
(
    id_рабочей_смены            SERIAL PRIMARY KEY,
    id_активности               INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
);

CREATE TABLE спортивное_занятие
(
    id_спортивного_занятия            SERIAL PRIMARY KEY,
    вид_занятия                       TEXT NOT NULL,
    id_активности                     INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
);

CREATE TABLE встреча
(
    id_встречи            SERIAL PRIMARY KEY,
    id_активности         INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
);

CREATE TABLE встречи_люди
(
    id_встречи              INTEGER NOT NULL REFERENCES встреча ON DELETE CASCADE,
    id_пользователя         INTEGER NOT NULL REFERENCES пользователь ON DELETE CASCADE,
    PRIMARY KEY (id_встречи, id_пользователя)
);

CREATE TABLE другое
(
    id_другого            SERIAL PRIMARY KEY,
    описание_активности   TEXT NOT NULL,
    id_активности         INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
);

CREATE TABLE перемещение
(
    id_перемещения         SERIAL PRIMARY KEY,
    id_транспорта          INTEGER NOT NULL REFERENCES транспорт ON DELETE CASCADE,
    id_активности          INTEGER NOT NULL REFERENCES активность ON DELETE CASCADE
);


CREATE TABLE товар
(
    id_товара            SERIAL PRIMARY KEY,
    наименование         VARCHAR(32) NOT NULL,
    стоимость            REAL CHECK (стоимость > 0) NOT NULL,
    количество           INTEGER CHECK (количество > 0) NOT NULL,
    срочность_покупки    DATE NOT NULL,
    подтверждение        isConfirmed_enum,
    id_списка_покупок    INTEGER NOT NULL REFERENCES список_покупок ON DELETE CASCADE
);


CREATE TABLE финансы
(
    id_финансовой_операции            SERIAL PRIMARY KEY,
    тип                               finance_type_enum,
    сумма                             REAL CHECK (сумма > 0) NOT NULL,
    статья                            TEXT NOT NULL,
    дата_совершения                   DATE NOT NULL,
    id_пользователя                   INTEGER NOT NULL REFERENCES пользователь ON DELETE CASCADE
);

CREATE TABLE транспорт
(
    id_транспорта           SERIAL PRIMARY KEY,
    тип                     VARCHAR(32) NOT NULL,
    стоимость_проезда       INTEGER CHECK (стоимость_проезда > 0) NOT NULL,
    время_в_пути            INTERVAL NOT NULL,
    id_локации_а            INTEGER NOT NULL REFERENCES локация ON DELETE CASCADE,
    id_локации_б            INTEGER NOT NULL REFERENCES локация ON DELETE CASCADE
);

