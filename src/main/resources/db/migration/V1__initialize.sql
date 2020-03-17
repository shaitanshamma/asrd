SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         INT(11)     NOT NULL AUTO_INCREMENT,
    username   VARCHAR(50) NOT NULL,
    password   CHAR(80)    NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50) NOT NULL,
    email      VARCHAR(50) NOT NULL,
    phone      VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS roles;

CREATE TABLE roles
(
    id   INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS users_roles;

CREATE TABLE users_roles
(
    user_id INT(11) NOT NULL,
    role_id INT(11) NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT FK_USER_ID_01 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id)
        REFERENCES roles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Создание таблиц с названиями

DROP TABLE IF EXISTS topic_titles;

CREATE TABLE topic_titles
(
    id    INT(11)      NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) DEFAULT NULL,
    path  VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS system_titles;

CREATE TABLE system_titles
(
    id    INT(11)      NOT NULL AUTO_INCREMENT,
    title VARCHAR(50)  NOT NULL,
    path  VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS system_components_titles;

CREATE TABLE system_components_titles
(
    id    INT(11)      NOT NULL AUTO_INCREMENT,
    title VARCHAR(50)  NOT NULL,
    path  VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS device_titles;

CREATE TABLE device_titles
(
    id    INT(11)      NOT NULL AUTO_INCREMENT,
    title VARCHAR(50)  NOT NULL,
    path  VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS device_components_titles;

CREATE TABLE device_components_titles
(
    id              INT(11)     NOT NULL AUTO_INCREMENT,
    title           VARCHAR(50) NOT NULL,
    title_device_id INT(11)     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_TITLE_DEVICE_ID FOREIGN KEY (title_device_id)
        REFERENCES device_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


-- добавить таблицу ManyToMany topic_titles_system_titles
DROP TABLE IF EXISTS topic_titles_system_titles;

CREATE TABLE topic_titles_system_titles
(
    topic_titles_id  INT(11) NOT NULL,
    system_titles_id INT(11) NOT NULL,

    PRIMARY KEY (topic_titles_id, system_titles_id),

    CONSTRAINT FK_TOPIC_TITLES_ID_01 FOREIGN KEY (topic_titles_id)
        REFERENCES topic_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_SYSTEM_TITLES_ID_01 FOREIGN KEY (system_titles_id)
        REFERENCES system_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- добавить таблицу ManyToMany system_titles_system_components_titles
DROP TABLE IF EXISTS system_titles_system_components_titles;

CREATE TABLE system_titles_system_components_titles
(
    system_titles_id            INT(11) NOT NULL,
    system_components_titles_id INT(11) NOT NULL,

    PRIMARY KEY (system_titles_id, system_components_titles_id),

    CONSTRAINT FK_SYSTEM_TITLES_ID_02 FOREIGN KEY (system_titles_id)
        REFERENCES system_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_SYSTEM_COMPONENT_TITLES_ID_02 FOREIGN KEY (system_components_titles_id)
        REFERENCES system_components_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- добавить таблицу ManyToMany system_titles_device_titles
DROP TABLE IF EXISTS system_titles_device_titles;

CREATE TABLE system_titles_device_titles
(
    system_titles_id INT(11) NOT NULL,
    device_titles_id INT(11) NOT NULL,

    PRIMARY KEY (system_titles_id, device_titles_id),

    CONSTRAINT FK_SYSTEM_TITLES_ID_03 FOREIGN KEY (system_titles_id)
        REFERENCES system_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_DEVICE_TITLES_ID_01 FOREIGN KEY (device_titles_id)
        REFERENCES device_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- не хватает timestamp и всего остального??
-- добавить составной ключ

DROP TABLE IF EXISTS systems;

CREATE TABLE systems
(
    id               INT(11)     NOT NULL AUTO_INCREMENT,
    title_system_id  INT(11)     NOT NULL,
    number           VARCHAR(32) NOT NULL,
    purpose          VARCHAR(64)          DEFAULT NULL,
    purpose_passport VARCHAR(64)          DEFAULT NULL,
    vintage          DATE                 DEFAULT NULL,
    vp_number        INT(11)              DEFAULT NULL,
    accept_otk_date  DATE                 DEFAULT NULL,
    accept_vp_date   DATE                 DEFAULT NULL,
    entity_status    VARCHAR(16)          DEFAULT 'active',
    created_at       DATETIME    DEFAULT CURRENT_TIMESTAMP,
    updated_at       DATETIME    DEFAULT CURRENT_TIMESTAMP,
    user_id          INT(11)     ,
    PRIMARY KEY (id),
    CONSTRAINT FK_TITLE_SYSTEM_ID_02 FOREIGN KEY (title_system_id)
        REFERENCES system_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_USER_ID_02 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS system_components;

CREATE TABLE system_components
(
    id                        INT(11)     NOT NULL AUTO_INCREMENT,
    title_system_component_id INT(11)     NOT NULL,
    number                    VARCHAR(32) NOT NULL,
    purpose                   VARCHAR(64)          DEFAULT NULL,
    purpose_passport          VARCHAR(64)          DEFAULT NULL,
    vintage                   DATE                 DEFAULT NULL,
    vp                        INT(11)              DEFAULT NULL,
    accept_otk                DATE                 DEFAULT NULL,
    accept_vp                 DATE                 DEFAULT NULL,
    entity_status             VARCHAR(16)          DEFAULT 'active',
    created_at                TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id                   INT(11)     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_TITLE_SYSTEM_COMPONENT_ID_02 FOREIGN KEY (title_system_component_id)
        REFERENCES system_components_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_SYSTEM_COMPONENT_USER_ID_02 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS devices;

CREATE TABLE devices
(
    id               INT(11)     NOT NULL AUTO_INCREMENT,
    device_title_id  INT(11)     NOT NULL,
    number           VARCHAR(32) NOT NULL,
    purpose          VARCHAR(64)          DEFAULT NULL,
    purpose_passport VARCHAR(64)          DEFAULT NULL,
    vintage          DATE                 DEFAULT NULL,
    vp               INT(11)              DEFAULT NULL,
    accept_otk       DATE                 DEFAULT NULL,
    accept_vp        DATE                 DEFAULT NULL,
    entity_status    VARCHAR(16)          DEFAULT 'active',
    created_at       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id          INT(11)     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_DEVICES_DEVICE_TITLE_ID_02 FOREIGN KEY (device_title_id)
        REFERENCES device_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_DEVICES_USER_ID_01 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS device_components;

CREATE TABLE device_components
(
    id                        INT(11)     NOT NULL AUTO_INCREMENT,
    device_component_title_id INT(11)     NOT NULL,
    number                    VARCHAR(32) NOT NULL,
    purpose                   VARCHAR(64)          DEFAULT NULL,
    purpose_passport          VARCHAR(64)          DEFAULT NULL,
    vintage                   DATE                 DEFAULT NULL,
    vp                        INT(11)              DEFAULT NULL,
    accept_otk                DATE                 DEFAULT NULL,
    accept_vp                 DATE                 DEFAULT NULL,
    entity_status             VARCHAR(16)          DEFAULT 'active',
    created_at                TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id                   INT(11)     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_DEV_COMPONENT_TITLE_DEV_COM_ID_01 FOREIGN KEY (device_component_title_id)
        REFERENCES device_components_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_DEV_COM_USER_ID_01 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS system_docs;

CREATE TABLE system_docs
(
    id              INT(11)      NOT NULL AUTO_INCREMENT,
    system_id       INT(11)      NOT NULL,
    title           VARCHAR(127) NOT NULL,
    doc_path        VARCHAR(255) NOT NULL,
    doc_description VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_SYSTEM_DOC_SYSTEM_ID_01 FOREIGN KEY (system_id)
        REFERENCES systems (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS system_components_docs;

CREATE TABLE system_components_docs
(
    id                  INT(11)      NOT NULL AUTO_INCREMENT,
    system_component_id INT(11)      NOT NULL,
    title               VARCHAR(127) NOT NULL,
    doc_path            VARCHAR(255) NOT NULL,
    doc_description     VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_SYSTEM_COMPONENT_DOC_SYSTEM_COMPONENT_ID_01 FOREIGN KEY (system_component_id)
        REFERENCES system_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS device_docs;

CREATE TABLE device_docs
(
    id            	INT(11)      NOT NULL AUTO_INCREMENT,
    device_id       INT(11)  	 NOT NULL,
    title			VARCHAR(127) NOT NULL,
    doc_path     VARCHAR(255) NOT NULL,
    doc_description VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_DEVICE_DOC_DEVCIES_ID_01 FOREIGN KEY (device_id)
        REFERENCES devices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS device_components_docs;

CREATE TABLE device_components_docs
(
    id            	INT(11)      NOT NULL AUTO_INCREMENT,
    device_component_id       INT(11)  	 NOT NULL,
    title			VARCHAR(127) NOT NULL,
    doc_path     VARCHAR(255) NOT NULL,
    doc_description VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_DEVICE_COMPONENT_DOC_DEVCIE_COMPONENTS_ID_01 FOREIGN KEY (device_component_id)
        REFERENCES device_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS companies;

CREATE TABLE companies
(
    id    INT(11)     NOT NULL AUTO_INCREMENT,
    title VARCHAR(64) NOT NULL UNIQUE,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS invoices;

CREATE TABLE invoices
(
    id              INT(11)      NOT NULL AUTO_INCREMENT,
    number          VARCHAR(32)  NOT NULL,
    invoice_date    DATE         NOT NULL,
    path            VARCHAR(255) NOT NULL,
    from_company_id INT(11)      NOT NULL,
    destination_id  INT(11)      NOT NULL,
    description     VARCHAR(255) NOT NULL,
    entity_status   VARCHAR(16)           DEFAULT 'active',
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id         INT(11)      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_INVOICE_COMPANY_ID_01 FOREIGN KEY (from_company_id)
        REFERENCES companies (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_INVOICE_COMPANY_ID_02 FOREIGN KEY (destination_id)
        REFERENCES companies (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_USER_ID_03 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS letters;

CREATE TABLE letters
(
    id                 INT(11)      NOT NULL AUTO_INCREMENT,
    letter_number      VARCHAR(32)  NOT NULL,
    inner_number       VARCHAR(32)           DEFAULT NULL,
    letter_date        DATE         NOT NULL,
    letter_path        VARCHAR(255) NOT NULL,
    date_response      DATE                  DEFAULT NULL,
    letter_description VARCHAR(255)          DEFAULT NULL,
    from_company_id    INT(11)      NOT NULL,
    destination_id     INT(11)      NOT NULL,
    entity_status      VARCHAR(16)           DEFAULT 'active',
    created_at         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id            INT(11)      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_USER_ID_05 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS letter_documents;

CREATE TABLE letter_documents
(
    id              INT(11)      NOT NULL AUTO_INCREMENT,
    letter_id       INT(11)      NOT NULL,
    title           VARCHAR(127) NOT NULL,
    letter_path     VARCHAR(255) NOT NULL,
    doc_description VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_DOCS_LETTER_ID_01 FOREIGN KEY (letter_id)
        REFERENCES letters (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS systems_invoices;

CREATE TABLE systems_invoices
(
    system_id  INT(11) NOT NULL,
    invoice_id INT(11) NOT NULL,

    PRIMARY KEY (system_id, invoice_id),

    CONSTRAINT FK_SYSTEMS_ID_01 FOREIGN KEY (system_id)
        REFERENCES systems (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_INVOICES_ID_01 FOREIGN KEY (invoice_id)
        REFERENCES invoices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS invoice_id_system_component_id;

CREATE TABLE invoice_id_system_component_id
(
    invoice_id INT(11) NOT NULL,
    system_component_id INT(11) NOT NULL,

    PRIMARY KEY (invoice_id, system_component_id),

    CONSTRAINT FK_INVOICE_ID_SYSTEM_COMPONENT_ID_1 FOREIGN KEY (invoice_id)
        REFERENCES invoices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_INVOICE_ID_SYSTEM_COMPONENT_ID_02 FOREIGN KEY (system_component_id)
        REFERENCES system_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS invoice_id_device_id;

CREATE TABLE invoice_id_device_id
(
    invoice_id INT(11) NOT NULL,
    device_id INT(11) NOT NULL,

    PRIMARY KEY (invoice_id, device_id),

    CONSTRAINT FK_INVOICE_ID_DEVICE_ID_1 FOREIGN KEY (invoice_id)
        REFERENCES invoices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_INVOICE_ID_DEVICE_ID_02 FOREIGN KEY (device_id)
        REFERENCES devices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS invoice_id_device_component_id;

CREATE TABLE invoice_id_device_component_id
(
    invoice_id INT(11) NOT NULL,
    device_component_id INT(11) NOT NULL,

    PRIMARY KEY (invoice_id, device_component_id),

    CONSTRAINT FK_INVOICE_ID_DEVICE_COMPONENT_ID_1 FOREIGN KEY (invoice_id)
        REFERENCES invoices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_INVOICE_ID_DEVICE_COMPONENT_ID_02 FOREIGN KEY (device_component_id)
        REFERENCES device_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS acts_input_control;

CREATE TABLE acts_input_control
(
    id            INT(11)      NOT NULL AUTO_INCREMENT,
    number        VARCHAR(32)  NOT NULL,
    invoice_id    INT(11)      NOT NULL,
    act_date      DATE         NOT NULL,
    path          VARCHAR(255) NOT NULL,
    result        VARCHAR(16)           DEFAULT 'positive',
    description   VARCHAR(255) NOT NULL,
    entity_status VARCHAR(16)           DEFAULT 'active',
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id       INT(11)      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_ACTS_INVOICE_ID_01 FOREIGN KEY (invoice_id)
        REFERENCES invoices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_USER_ID_04 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS acts_ic_systems;

CREATE TABLE acts_ic_systems
(
    system_id INT(11) NOT NULL,
    act_ic_id INT(11) NOT NULL,

    PRIMARY KEY (system_id, act_ic_id),

    CONSTRAINT FK_SYSTEMS_ID_02 FOREIGN KEY (system_id)
        REFERENCES systems (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ACTS_IC_ID_01 FOREIGN KEY (act_ic_id)
        REFERENCES acts_input_control (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS act_ic_id_system_component_id;

CREATE TABLE act_ic_id_system_component_id
(
    act_ic_id INT(11) NOT NULL,
    system_component_id INT(11) NOT NULL,

    PRIMARY KEY (act_ic_id, system_component_id),

    CONSTRAINT FK_ACT_ID_SYSTEM_COMPONENT_ID_1 FOREIGN KEY (act_ic_id)
        REFERENCES acts_input_control (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ACT_ID_SYSTEM_COMPONENT_ID_02 FOREIGN KEY (system_component_id)
        REFERENCES system_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS act_ic_id_device_id;

CREATE TABLE act_ic_id_device_id
(
    act_ic_id INT(11) NOT NULL,
    device_id INT(11) NOT NULL,

    PRIMARY KEY (act_ic_id, device_id),

    CONSTRAINT FK_ACT_ID_DEVICE_ID_1 FOREIGN KEY (act_ic_id)
        REFERENCES acts_input_control (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ACT_ID_DEVICE_ID_02 FOREIGN KEY (device_id)
        REFERENCES devices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS act_ic_id_device_component_id;

CREATE TABLE act_ic_id_device_component_id
(
    act_ic_id INT(11) NOT NULL,
    device_component_id INT(11) NOT NULL,

    PRIMARY KEY (act_ic_id, device_component_id),

    CONSTRAINT FK_ACT_ID_DEVICE_COMPONENT_ID_1 FOREIGN KEY (act_ic_id)
        REFERENCES acts_input_control (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ACT_ID_DEVICE_COMPONENT_ID_02 FOREIGN KEY (device_component_id)
        REFERENCES device_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS letter_id_system_id;

CREATE TABLE letter_id_system_id
(
    letter_id INT(11) NOT NULL,
    system_id INT(11) NOT NULL,

    PRIMARY KEY (letter_id, system_id),

    CONSTRAINT FK_LETTER_ID_SYSTEM_ID_01 FOREIGN KEY (letter_id)
        REFERENCES letters (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_LETTER_ID_SYSTEM_ID_02 FOREIGN KEY (system_id)
        REFERENCES systems (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;



DROP TABLE IF EXISTS letter_id_system_component_id;

CREATE TABLE letter_id_system_component_id
(
    letter_id INT(11) NOT NULL,
    system_component_id INT(11) NOT NULL,

    PRIMARY KEY (letter_id, system_component_id),

    CONSTRAINT FK_LETTER_ID_SYSTEM_COMPONENT_ID_01 FOREIGN KEY (letter_id)
        REFERENCES letters (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_LETTER_ID_SYSTEM_COMPONENT_ID_02 FOREIGN KEY (system_component_id)
        REFERENCES system_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS letter_id_device_id;

CREATE TABLE letter_id_device_id
(
    letter_id INT(11) NOT NULL,
    device_id INT(11) NOT NULL,

    PRIMARY KEY (letter_id, device_id),

    CONSTRAINT FK_LETTER_ID_DEVICE_ID_01 FOREIGN KEY (letter_id)
        REFERENCES letters (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_LETTER_ID_DEVICE_ID_02 FOREIGN KEY (device_id)
        REFERENCES devices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS letter_id_device_component_id;

CREATE TABLE letter_id_device_component_id
(
    letter_id INT(11) NOT NULL,
    device_component_id INT(11) NOT NULL,

    PRIMARY KEY (letter_id, device_component_id),

    CONSTRAINT FK_LETTER_ID_DEVICE_COMPONENT_ID_01 FOREIGN KEY (letter_id)
        REFERENCES letters (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_LETTER_ID_DEVICE_COMPONENT_ID_02 FOREIGN KEY (device_component_id)
        REFERENCES device_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;



INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_MANAGER'),
       ('ROLE_ADMIN');

INSERT INTO users (username, password, first_name, last_name, patronymic, email, phone)
VALUES ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Admin', 'Admin', 'Adminovich',
        'admin@gmail.com', '+79881111111');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (1, 3);


INSERT INTO invoices (number, invoice_date, path, from_company_id, destination_id, description, entity_status, user_id)
VALUES ('000', '2019-1-12', '/home/intruder/invoice.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 'active', '1');

INSERT INTO topic_titles (title, path)
VALUES ('К-001Р(СНАУ-Р)', 'k-001r/'),
       ('БСУ-506', 'bsu-506/');

INSERT INTO system_titles (title, path)
VALUES ('К-001Р', 'k-001r/'),
       ('БСУ-506', 'bsu-506/');

INSERT INTO topic_titles_system_titles
VALUES (1, 1),
       (2, 2);




INSERT INTO system_components_titles (title, path)
VALUES ('МБП-001', 'mbp-001/');

INSERT INTO device_titles (title, path)
VALUES ('Ц-009Р', 'c-009r/'),
       ('МН-Р', 'mn-r/'),
       ('СН-Р', 'sn-r/'),
       ('ИНС-Р', 'ins-r/'),
       ('А-080-05', 'a-080-05/'),
       ('ТПС-715', 'tps-715/'),
       ('ОЭС-715', 'oes-715/'),
       ('СИБ-506', 'sib-506/');

INSERT INTO system_titles_device_titles
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 6),
       (2, 7),
       (2, 8);

INSERT INTO device_components_titles (title, title_device_id)
VALUES ('СИВД-Р', 1),
       ('П-103', 1),
       ('ППД-6БР', 1),
       ('ГБ МН-Р', 2),
       ('БМЧЭ', 2);

INSERT INTO companies (title)
VALUES ('тест 1'),
       ('тест 2');



INSERT INTO systems (title_system_id, number, purpose, purpose_passport, vintage, vp_number, accept_otk_date, accept_vp_date, user_id)
VALUES ('1', '0354552', 'Испытания', 'не падать', '2000-1-1', 45, '2000-1-1', '2000-1-1', '1');

SET FOREIGN_KEY_CHECKS = 1;