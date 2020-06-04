SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS status_user;

CREATE TABLE status_user
(
    id   TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         SMALLINT UNSIGNED    NOT NULL AUTO_INCREMENT,
    username   VARCHAR(255) NOT NULL,
    password   CHAR(80)    NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    work_phone      VARCHAR(255) DEFAULT NULL,
    mobile_phone      VARCHAR(255) DEFAULT NULL,
    status_user_id      TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_USER_SERVICE_USER FOREIGN KEY (status_user_id)
        REFERENCES status_user (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS roles;

CREATE TABLE roles
(
    id   TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS users_roles;

CREATE TABLE users_roles
(
    user_id SMALLINT UNSIGNED NOT NULL,
    role_id TINYINT UNSIGNED NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT FK_USER_ID_01 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id)
        REFERENCES roles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

-- Создание таблиц с названиями

DROP TABLE IF EXISTS topics;

CREATE TABLE topics
(
    id    INT      NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    path  VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS system_titles;

CREATE TABLE system_titles
(
    id    INT      NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    path  VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS system_component_titles;

CREATE TABLE system_component_titles
(
    id    INT      NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    path  VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS device_titles;

CREATE TABLE device_titles
(
    id    INT      NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    path  VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS device_component_titles;

CREATE TABLE device_component_titles
(
    id              INT     NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    device_title_id INT     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_DEVICE_TITLE_ID FOREIGN KEY (device_title_id)
        REFERENCES device_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS topic_system_title;

CREATE TABLE topic_system_title
(
    topic_id  INT NOT NULL,
    system_title_id INT NOT NULL,

    PRIMARY KEY (topic_id, system_title_id),

    CONSTRAINT FK_TOPIC_TITLES_ID_01 FOREIGN KEY (topic_id)
        REFERENCES topics (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_SYSTEM_TITLES_ID_01 FOREIGN KEY (system_title_id)
        REFERENCES system_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS system_titles_system_components_titles;

CREATE TABLE system_titles_system_components_titles
(
    system_titles_id            INT NOT NULL,
    system_components_titles_id INT NOT NULL,

    PRIMARY KEY (system_titles_id, system_components_titles_id),

    CONSTRAINT FK_SYSTEM_TITLES_ID_02 FOREIGN KEY (system_titles_id)
        REFERENCES system_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_SYSTEM_COMPONENT_TITLES_ID_02 FOREIGN KEY (system_components_titles_id)
        REFERENCES system_component_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- добавить таблицу ManyToMany system_titles_device_titles
DROP TABLE IF EXISTS system_titles_device_titles;

CREATE TABLE system_titles_device_titles
(
    system_titles_id INT NOT NULL,
    device_titles_id INT NOT NULL,

    PRIMARY KEY (system_titles_id, device_titles_id),

    CONSTRAINT FK_SYSTEM_TITLES_ID_03 FOREIGN KEY (system_titles_id)
        REFERENCES system_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_DEVICE_TITLES_ID_01 FOREIGN KEY (device_titles_id)
        REFERENCES device_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


-- не хватает timestamp и всего остального??
-- добавить составной ключ

DROP TABLE IF EXISTS systems;

CREATE TABLE systems
(
    id               INT     NOT NULL AUTO_INCREMENT,
    title_system_id  INT    NOT NULL,
    number           VARCHAR(255) NOT NULL,
    purpose          VARCHAR(255)          DEFAULT NULL,
    purpose_passport VARCHAR(255)          DEFAULT NULL,
    vintage          DATE                 DEFAULT NULL,
    vp_number        INT              DEFAULT NULL,
    accept_otk_date  DATE                 DEFAULT NULL,
    accept_vp_date   DATE                 DEFAULT NULL,
    location         TINYINT              DEFAULT 0,
    entity_status    TINYINT              DEFAULT 1,
    created_at       DATETIME    DEFAULT CURRENT_TIMESTAMP,
    updated_at       DATETIME    DEFAULT CURRENT_TIMESTAMP,
    user_id          SMALLINT UNSIGNED     ,
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
    id                        INT     NOT NULL AUTO_INCREMENT,
    title_system_component_id INT     NOT NULL,
    number                    VARCHAR(255) NOT NULL,
    purpose                   VARCHAR(255)          DEFAULT NULL,
    purpose_passport          VARCHAR(255)          DEFAULT NULL,
    vintage                   DATE                 DEFAULT NULL,
    vp                        INT              DEFAULT NULL,
    accept_otk                DATE                 DEFAULT NULL,
    accept_vp                 DATE                 DEFAULT NULL,
    location         TINYINT              DEFAULT 0,
    entity_status             TINYINT              DEFAULT 1,
    created_at                TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id                   SMALLINT UNSIGNED     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_TITLE_SYSTEM_COMPONENT_ID_02 FOREIGN KEY (title_system_component_id)
        REFERENCES system_component_titles (id)
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
    id               INT     NOT NULL AUTO_INCREMENT,
    device_title_id  INT     NOT NULL,
    number           VARCHAR(255) NOT NULL,
    purpose          VARCHAR(255)          DEFAULT NULL,
    purpose_passport VARCHAR(255)          DEFAULT NULL,
    system_id        INT              DeFAULT NULL,
    vintage          DATE                 DEFAULT NULL,
    vp_number        INT              DEFAULT NULL,
    accept_otk_date  DATE                 DEFAULT NULL,
    accept_vp_date   DATE                 DEFAULT NULL,
    location         TINYINT              DEFAULT 0,
    entity_status    TINYINT              DEFAULT 1,
    created_at       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id          SMALLINT UNSIGNED     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_DEVICES_DEVICE_TITLE_ID_02 FOREIGN KEY (device_title_id)
        REFERENCES device_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_DEVICES_USER_ID_01 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_DEVICES_SYSTEMS_ID_01 FOREIGN KEY (system_id)
        REFERENCES systems (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS device_components;

CREATE TABLE device_components
(
    id                        INT     NOT NULL AUTO_INCREMENT,
    device_component_title_id INT     NOT NULL,
    number                    VARCHAR(255) NOT NULL,
    device_id                 INT     DEFAULT NULL,
    purpose                   VARCHAR(255)         DEFAULT NULL,
    purpose_passport          VARCHAR(255)         DEFAULT NULL,
    vintage                   DATE                 DEFAULT NULL,
    vp_number                 INT                  DEFAULT NULL,
    accept_otk_date           DATE                 DEFAULT NULL,
    accept_vp_date            DATE                 DEFAULT NULL,
    location                  TINYINT              DEFAULT 0,
    entity_status             TINYINT              DEFAULT 1,
    created_at                TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id                   SMALLINT UNSIGNED     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_DEV_COMPONENT_TITLE_DEV_COM_ID_01 FOREIGN KEY (device_component_title_id)
        REFERENCES device_component_titles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_DEV_COM_USER_ID_01 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_DEV_COM_DEVICE_ID_01 FOREIGN KEY (device_id)
        REFERENCES devices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS system_docs;

CREATE TABLE system_docs
(
    id              INT      NOT NULL AUTO_INCREMENT,
    system_id       INT      NOT NULL,
    title           VARCHAR(255) NOT NULL,
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
    id                  INT      NOT NULL AUTO_INCREMENT,
    system_component_id INT      NOT NULL,
    title               VARCHAR(255) NOT NULL,
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
    id            	INT          NOT NULL AUTO_INCREMENT,
    device_id       INT  	     NOT NULL,
    title			VARCHAR(255) NOT NULL,
    doc_path        VARCHAR(255) NOT NULL,
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
    id            	    INT          NOT NULL AUTO_INCREMENT,
    device_component_id INT  	     NOT NULL,
    title			    VARCHAR(255) NOT NULL,
    doc_path            VARCHAR(255) NOT NULL,
    doc_description     VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_DEVICE_COMPONENT_DOC_DEVCIE_COMPONENTS_ID_01 FOREIGN KEY (device_component_id)
        REFERENCES device_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;





DROP TABLE IF EXISTS `companies`;

CREATE TABLE `companies` (
                             `id` smallint unsigned NOT NULL AUTO_INCREMENT,
                             `title` varchar(255) NOT NULL,
                             `email` varchar(255) NOT NULL,
                             `military_representation` varchar(255) DEFAULT NULL,
                             `fax` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`)
)ENGINE = InnoDB
 AUTO_INCREMENT = 1
 DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `addresses`;
CREATE TABLE `addresses` (
                             `id` smallint unsigned NOT NULL AUTO_INCREMENT,
                             `zip_code` varchar(255) NOT NULL,
                             `city` varchar(255) NOT NULL,
                             `street` varchar(255) NOT NULL,
                             `place` varchar(255) NOT NULL,
                             `company_id` smallint unsigned NOT NULL,
                             `description` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             CONSTRAINT `company_id_to_addresses` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
)ENGINE = InnoDB
 AUTO_INCREMENT = 1
 DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `company_phones`;

CREATE TABLE `company_phones` (
                                  `id` smallint NOT NULL AUTO_INCREMENT,
                                  `company_id` smallint unsigned NOT NULL,
                                  `phone` varchar(255) NOT NULL,
                                  `description` varchar(255) DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  CONSTRAINT `FK_company_phone_company` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
)ENGINE = InnoDB
 AUTO_INCREMENT = 1
 DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `employees`;

CREATE TABLE `employees` (
                             `id` smallint unsigned NOT NULL AUTO_INCREMENT,
                             `name` varchar(255) NOT NULL,
                             `last_name` varchar(255) NOT NULL,
                             `patronymic` varchar(255) DEFAULT NULL,
                             `position` varchar(255) DEFAULT NULL,
                             `email` varchar(255) DEFAULT NULL,
                             `work_phone` varchar(255) NOT NULL,
                             `mobil_phone` varchar(255) NOT NULL,
                             `company_id` smallint unsigned NOT NULL,
                             PRIMARY KEY (`id`),
                             CONSTRAINT `employee_company`
                                 FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
)ENGINE = InnoDB
 AUTO_INCREMENT = 1
 DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `employee_phones`;

CREATE TABLE `employee_phones` (
                                   `id` mediumint NOT NULL AUTO_INCREMENT,
                                   `phone` varchar(255) NOT NULL,
                                   `employees_id` smallint unsigned NOT NULL,
                                   `description` varchar(255) DEFAULT NULL,
                                   PRIMARY KEY (`id`),
                                   CONSTRAINT `FK_employee_phone_employee`
                                       FOREIGN KEY (`employees_id`) REFERENCES `employees` (`id`)
)ENGINE = InnoDB
 AUTO_INCREMENT = 1
 DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS invoices;

CREATE TABLE invoices
(
    id              INT      NOT NULL AUTO_INCREMENT,
    number          VARCHAR(255)  NOT NULL,
    invoice_date    DATE         NOT NULL,
    path            VARCHAR(255) DEFAULT NULL,
--     from_company_id INT      NOT NULL,
--     destination_id  INT      NOT NULL,
    from_company_id smallint unsigned     NOT NULL,
    destination_id  smallint unsigned      NOT NULL,
    description     VARCHAR(255) ,
    entity_status   TINYINT               DEFAULT 1,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id         SMALLINT UNSIGNED      NOT NULL,
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
    id                 INT          NOT NULL AUTO_INCREMENT,
    letter_number      VARCHAR(255) NOT NULL,
    inner_number       VARCHAR(255) DEFAULT NULL,
    letter_date        DATE         NOT NULL,
    letter_path        VARCHAR(255) NOT NULL,
    date_response      DATE         DEFAULT NULL,
    letter_description VARCHAR(255) DEFAULT NULL,
    from_company_id    INT          NOT NULL,
    destination_id     INT          NOT NULL,
    entity_status      TINYINT               DEFAULT 1,
    created_at         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id            SMALLINT UNSIGNED          NOT NULL,
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
    id              INT      NOT NULL AUTO_INCREMENT,
    letter_id       INT      NOT NULL,
    title           VARCHAR(255) NOT NULL,
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
    system_id  INT NOT NULL,
    invoice_id INT NOT NULL,

    PRIMARY KEY (system_id, invoice_id),

    CONSTRAINT FK_SYSTEMS_ID_01 FOREIGN KEY (system_id)
        REFERENCES systems (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_INVOICES_ID_01 FOREIGN KEY (invoice_id)
        REFERENCES invoices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS invoice_system_component;

CREATE TABLE invoice_system_component
(
    invoice_id INT NOT NULL,
    system_component_id INT NOT NULL,

    PRIMARY KEY (invoice_id, system_component_id),

    CONSTRAINT FK_INVOICE_ID_SYSTEM_COMPONENT_ID_1 FOREIGN KEY (invoice_id)
        REFERENCES invoices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_INVOICE_ID_SYSTEM_COMPONENT_ID_02 FOREIGN KEY (system_component_id)
        REFERENCES system_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS device_invoice;

CREATE TABLE device_invoice
(
    invoice_id INT NOT NULL,
    device_id INT NOT NULL,

    PRIMARY KEY (invoice_id, device_id),

    CONSTRAINT FK_INVOICE_ID_DEVICE_ID_1 FOREIGN KEY (invoice_id)
        REFERENCES invoices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_INVOICE_ID_DEVICE_ID_02 FOREIGN KEY (device_id)
        REFERENCES devices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS device_component_invoice;

CREATE TABLE device_component_invoice
(
    invoice_id INT NOT NULL,
    device_component_id INT NOT NULL,

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
    id            INT           NOT NULL AUTO_INCREMENT,
    number        VARCHAR(255)  NOT NULL,
    invoice_id    INT           NOT NULL,
    act_date      DATE          NOT NULL,
    path          VARCHAR(255) ,
    result        TINYINT       NOT NULL default 1,
    description   VARCHAR(255)  NOT NULL,
    entity_status TINYINT       NOT NULL       DEFAULT 1,
    created_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id       SMALLINT UNSIGNED      NOT NULL,
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
    system_id INT NOT NULL,
    act_ic_id INT NOT NULL,

    PRIMARY KEY (system_id, act_ic_id),

    CONSTRAINT FK_SYSTEMS_ID_02 FOREIGN KEY (system_id)
        REFERENCES systems (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ACTS_IC_ID_01 FOREIGN KEY (act_ic_id)
        REFERENCES acts_input_control (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS act_ic_system_component;

CREATE TABLE act_ic_system_component
(
    act_ic_id INT NOT NULL,
    system_component_id INT NOT NULL,

    PRIMARY KEY (act_ic_id, system_component_id),

    CONSTRAINT FK_ACT_ID_SYSTEM_COMPONENT_ID_1 FOREIGN KEY (act_ic_id)
        REFERENCES acts_input_control (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ACT_ID_SYSTEM_COMPONENT_ID_02 FOREIGN KEY (system_component_id)
        REFERENCES system_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS act_ic_device;

CREATE TABLE act_ic_device
(
    act_ic_id INT NOT NULL,
    device_id INT NOT NULL,

    PRIMARY KEY (act_ic_id, device_id),

    CONSTRAINT FK_ACT_ID_DEVICE_ID_1 FOREIGN KEY (act_ic_id)
        REFERENCES acts_input_control (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ACT_ID_DEVICE_ID_02 FOREIGN KEY (device_id)
        REFERENCES devices (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS act_ic_device_component;

CREATE TABLE act_ic_device_component
(
    act_ic_id INT NOT NULL,
    device_component_id INT NOT NULL,

    PRIMARY KEY (act_ic_id, device_component_id),

    CONSTRAINT FK_ACT_ID_DEVICE_COMPONENT_ID_1 FOREIGN KEY (act_ic_id)
        REFERENCES acts_input_control (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ACT_ID_DEVICE_COMPONENT_ID_02 FOREIGN KEY (device_component_id)
        REFERENCES device_components (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS letter_system;

CREATE TABLE letter_system
(
    letter_id INT NOT NULL,
    system_id INT NOT NULL,

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



DROP TABLE IF EXISTS letter_system_component;

CREATE TABLE letter_system_component
(
    letter_id INT NOT NULL,
    system_component_id INT NOT NULL,

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


DROP TABLE IF EXISTS device_letter;

CREATE TABLE device_letter
(
    letter_id INT NOT NULL,
    device_id INT NOT NULL,

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


DROP TABLE IF EXISTS device_component_letter;

CREATE TABLE device_component_letter
(
    letter_id INT NOT NULL,
    device_component_id INT NOT NULL,

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

DROP TABLE IF EXISTS file_types;

CREATE TABLE file_types (
                            `id` int(11) NOT NULL,
                            `title` varchar(255) NOT NULL,
                            `directory` varchar(255) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS files;

CREATE TABLE files (
                       `id` int(11) NOT NULL,
                       `title` varchar(255) NOT NULL,
                       `type_id` int(11) DEFAULT NULL,
                       `description` varchar(255) DEFAULT NULL,
                       PRIMARY KEY (`id`),
                       CONSTRAINT `fk_file_type_id` FOREIGN KEY (`type_id`) REFERENCES `file_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS system_files;

CREATE TABLE system_files (
                              `system_id` int(11) NOT NULL,
                              `file_id` int(11) NOT NULL,
                              PRIMARY KEY (`system_id`,`file_id`),
                              CONSTRAINT `fk_system_files_file_id` FOREIGN KEY (`file_id`) REFERENCES `files` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                              CONSTRAINT `fk_system_files_system_id` FOREIGN KEY (`system_id`) REFERENCES `systems` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS device_files;

CREATE TABLE `device_files` (
                                `device_id` int(11) NOT NULL,
                                `file_id` int(11) NOT NULL,
                                PRIMARY KEY (`device_id`,`file_id`),
                                CONSTRAINT `fk_device_files_device_id` FOREIGN KEY (`device_id`) REFERENCES `devices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `fk_device_files_file_id` FOREIGN KEY (`file_id`) REFERENCES `files` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_EMPLOYEE'),
       ('ROLE_ADMIN');

INSERT INTO status_user (name)
VALUES ('active'),
       ('inactive'),
       ('confirmed'),
       ('not confirmed');


INSERT INTO users (username, password, first_name, last_name, patronymic, email, work_phone, mobile_phone, status_user_id)
VALUES ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Admin', 'Admin', 'Adminovich',
        'admin@gmail.com', '565685', '+79881111111', 1);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (1, 3);

INSERT INTO topics (title, path)
VALUES ('Тема 1', 'тема_1'),
       ('Тема 2', 'тема_2');

INSERT INTO system_titles (title, path)
VALUES ('Система 1', 'система_1'),
       ('Система 2', 'система_2'),
       ('Система 3', 'система_3'),
       ('Система 4', 'система_3'),
       ('Система 5', 'система_3'),
       ('Система 6', 'система_3'),
       ('Система 7', 'система_3'),
       ('Система 8', 'система_3'),
       ('Система 9', 'система_3'),
       ('Система 10', 'система_3'),
       ('Система 11', 'система_3'),
       ('Система 12', 'система_3'),
       ('Система 13', 'система_3'),
       ('Система 14', 'система_3'),
       ('Система 15', 'система_3'),
       ('Система 16', 'система_3'),
       ('Система 17', 'система_3'),
       ('Система 18', 'система_3'),
       ('Система 19', 'система_3'),
       ('Система 20', 'система_3');

INSERT INTO topic_system_title
VALUES (1, 1),
       (2, 2),
       (2, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16),
       (1, 17),
       (1, 18),
       (1, 19),
       (1, 20);

INSERT INTO system_component_titles (title, path)
VALUES ('Компонент Системы', 'компонент_системы/');

INSERT INTO device_titles (title, path)
VALUES ('Прибор 1 1', 'device_1/'),
       ('Прибор 2 1', 'device_2/'),
       ('Прибор 3 1', 'device_3/'),
       ('Прибор 4 1', 'device_4/'),
       ('Прибор 5 1', 'device_5/'),
       ('Прибор 1 2', 'device_6/'),
       ('Прибор 2 2', 'device_7/'),
       ('Прибор 3 2', 'device_8/');

INSERT INTO system_titles_device_titles
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 6),
       (2, 7),
       (2, 8);

INSERT INTO device_component_titles (title, device_title_id)
VALUES ('СЧ 1 1', 1),
       ('СЧ 2 1', 1),
       ('СЧ 3 1', 1),
       ('СЧ 1 2', 2),
       ('СЧ 2 2', 2);

INSERT INTO companies (title, email)
VALUES ('тест 1', '1@bb'),
       ('тест 2', '2@bb'),
       ('тест 3', '2@bb'),
       ('тест 4', '2@bb'),
       ('тест 5', '2@bb'),
       ('тест 7', '2@bb'),
       ('тест 9', '2@bb'),
       ('тест 11', '2@bb'),
       ('тест 24', '2@bb'),
       ('тест 6', '2@bb'),
       ('тест 8', '2@bb'),
       ('тест 35', '2@bb'),
       ('тест 15', '2@bb'),
       ('тест 12', '2@bb'),
       ('тест 48', '2@bb'),
       ('тест 54', '2@bb'),
       ('тест 22', '2@bb'),
       ('тест 17', '2@bb'),
       ('тест 14', '2@bb'),
       ('тест 23', '2@bb'),
       ('тест 20', '2@bb'),
       ('тест 18', '2@bb'),
       ('тест 19', '2@bb'),
       ('тест 33', '2@bb'),
       ('тест 0', '2@bb');



INSERT INTO systems (title_system_id, number, purpose, purpose_passport, vintage, vp_number, accept_otk_date, accept_vp_date, user_id)
VALUES  ('1', '0354501', 'Testing', 'not to crash', '2001-3-1', 45, '2001-3-1', '2000-1-1', '1'),
        ('2', '0354502', 'Testing', 'not to crash', '2000-1-2', 45, '2000-1-2', '2000-1-1', '1'),
        ('3', '035453', 'Testing', 'not to crash', '2001-1-1', 45, '2001-1-1', '2000-1-1', '1'),
        ('4', '035454', 'Testing', 'not to crash', '2000-3-2', 45, '2000-3-2', '2000-1-1', '1'),
        ('5', '0354515', 'Testing', 'not to crash', '2001-1-1', 45, '2001-1-1', '2000-1-1', '1'),
        ('6', '0354516', 'Testing', 'not to crash', '2000-1-2', 45, '2000-1-2', '2000-1-1', '1'),
        ('7', '0354527', 'Testing', 'not to crash', '2001-3-1', 45, '2001-3-1', '2000-1-1', '1'),
        ('8', '0354528', 'Testing', 'not to crash', '2000-1-1', 45, '2000-1-1', '2000-1-1', '1'),
        ('9', '0354529', 'Testing', 'not to crash', '2001-1-1', 45, '2001-1-1', '2000-1-1', '1'),
        ('10', '0354530', 'Testing', 'not to crash', '2000-3-2', 45, '2000-3-2', '2000-1-1', '1'),
        ('11', '0354531', 'Testing', 'not to crash', '2001-1-1', 45, '2001-1-1', '2000-1-1', '1'),
        ('12', '0354512', 'Testing', 'not to crash', '2000-1-1', 45, '2000-1-1', '2000-1-1', '1'),
        ('13', '0354513', 'Testing', 'not to crash', '2001-3-1', 45, '2001-3-1', '2000-1-1', '1'),
        ('14', '0354594', 'Testing', 'not to crash', '2000-1-2', 45, '2000-1-2', '2000-1-1', '1'),
        ('15', '0354595', 'Testing', 'not to crash', '2001-1-2', 45, '2001-1-2', '2000-1-1', '1'),
        ('16', '0354566', 'Testing', 'not to crash', '2000-3-1', 45, '2000-3-1', '2000-1-1', '1'),
        ('17', '0354577', 'Testing', 'not to crash', '2001-1-1', 45, '2001-1-1', '2000-1-1', '1'),
        ('18', '0354578', 'Testing', 'not to crash', '2000-1-1', 45, '2000-1-1', '2000-1-1', '1'),
        ('19', '0354579', 'Testing', 'not to crash', '2001-3-2', 45, '2001-3-2', '2000-1-1', '1'),
        ('20', '0354520', 'Testing', 'not to crash', '2000-1-2', 45, '2000-1-2', '2000-1-1', '1'),
        ('2', '0354522', 'Testing', 'not to crash', '2000-1-1', 45, '2000-1-1', '2000-1-1', '1');


INSERT INTO devices (device_title_id, number, purpose, purpose_passport, system_id, vintage, vp_number, accept_otk_date, accept_vp_date, location, entity_status, created_at, updated_at, user_id)
VALUES  (1, 666148838500, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (2, 666148838501, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (3, 666148838502, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (4, 666148838503, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (5, 666148838504, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (6, 666148838505, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (7, 666148838506, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (8, 666148838507, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (1, 666148838508, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (2, 666148838509, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (3, 666148838510, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (4, 666148838511, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (5, 666148838512, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (6, 666148838513, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (7, 666148838514, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (8, 666148838515, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (1, 666148838516, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (2, 666148838517, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (3, 666148838518, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (4, 666148838519, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (5, 666148838520, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (6, 666148838521, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1),
        (7, 666148838522, 'Testing', 'not to crash', 1, '2000-1-2', 14, '2000-1-1', '2000-1-1', 0, 1, '2000-1-1', '2000-1-1', 1);


INSERT INTO invoices (number, invoice_date, path, from_company_id, destination_id, description, entity_status, user_id)
VALUES  ('000', '2019-1-12', '/home/intruder/invoice.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('001', '2019-1-1', '/home/intruder/invoice1.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('002', '2019-1-2', '/home/intruder/invoice3.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('003', '2019-1-3', '/home/intruder/invoice5.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('004', '2019-1-4', '/home/intruder/invoice7.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('005', '2019-1-5', '/home/intruder/invoice12.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('006', '2019-1-6', '/home/intruder/invoice2.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('007', '2019-1-7', '/home/intruder/invoice4.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('008', '2019-1-8', '/home/intruder/invoice5.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('009', '2019-1-9', '/home/intruder/invoice6.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('010', '2019-1-10', '/home/intruder/invoice87.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('011', '2019-1-11', '/home/intruder/invoice12.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('012', '2019-1-12', '/home/intruder/invoice35.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('013', '2019-1-13', '/home/intruder/invoice12.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('014', '2019-1-14', '/home/intruder/invoice56.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('015', '2019-1-15', '/home/intruder/invoice78.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('016', '2019-1-16', '/home/intruder/invoice12.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('017', '2019-1-17', '/home/intruder/invoice511234.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('018', '2019-1-18', '/home/intruder/invoice1234.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('019', '2019-1-19', '/home/intruder/invoice12341.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('020', '2019-1-20', '/home/intruder/invoice7565.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('021', '2019-1-21', '/home/intruder/invoice3434.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('022', '2019-1-22', '/home/intruder/invoice3452.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('023', '2019-1-23', '/home/intruder/invoice6776.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1'),
        ('024', '2019-1-24', '/home/intruder/invoice5675.pdf', '1', '2', 'Прибыл из пункта А в пункт Б', 1, '1'),
        ('025', '2019-1-25', '/home/intruder/invoice5567.pdf', '2', '1', 'Прибыл из пункта Б в пункт А', 1, '1');

INSERT INTO acts_input_control (number, invoice_id, act_date, path, result, description, user_id)
VALUES
('000', 2, '2020-2-20', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('001', 1, '2019-4-15', '/home/intruder/invoice1.pdf', 1, 'Все ок', 1),
('002', 2, '2020-3-23', '/home/intruder/invoice2.pdf', 1, 'Все ок', 1),
('003', 3, '2019-4-19', '/home/intruder/invoice3.pdf', 1, 'Все ок', 1),
('004', 4, '2020-3-23', '/home/intruder/invoice4.pdf', 1, 'Все ок', 1),
('005', 5, '2019-1-19', '/home/intruder/invoice5.pdf', 1, 'Все ок', 1),
('006', 6, '2020-2-20', '/home/intruder/invoice6.pdf', 1, 'Все ок', 1),
('008', 7, '2019-4-15', '/home/intruder/invoice8.pdf', 1, 'Все ок', 1),
('007', 8, '2020-3-23', '/home/intruder/invoice7.pdf', 1, 'Все ок', 1),
('019', 9, '2019-4-19', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('018', 10, '2020-3-23', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('017', 11, '2019-4-15', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('016', 12, '2020-2-20', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('015', 13, '2019-1-19', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('014', 14, '2020-3-21', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('013', 15, '2019-4-15', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('012', 16, '2020-3-21', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('011', 17, '2019-4-19', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('010', 19, '2020-2-20', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('024', 23, '2019-4-15', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('023', 26, '2020-3-23', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('035', 25, '2019-1-19', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('026', 20, '2020-3-23', '/home/intruder/invoice.pdf', 1, 'Все ок', 1),
('027', 21, '2019-4-15', '/home/intruder/invoice.pdf', 1, 'Все ок', 1);

INSERT INTO system_titles (title, path)
VALUES ('Система 1', 'система_1'),
       ('Система 2', 'система_2'),
       ('Система 3', 'система_3'),
       ('Система 4', 'система_4'),
       ('Система 5', 'система_5'),
       ('Система 6', 'система_6'),
       ('Система 7', 'система_7'),
       ('Система 8', 'система_8'),
       ('Система 9', 'система_9'),
       ('Система 10', 'система_10'),
       ('Система 11', 'система_11'),
       ('Система 12', 'система_12'),
       ('Система 13', 'система_13'),
       ('Система 14', 'система_14'),
       ('Система 15', 'система_15'),
       ('Система 16', 'система_16'),
       ('Система 17', 'система_17'),
       ('Система 18', 'система_18'),
       ('Система 19', 'система_19'),
       ('Система 20', 'система_20');


SET FOREIGN_KEY_CHECKS = 1;

