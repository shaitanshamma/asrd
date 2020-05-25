SET FOREIGN_KEY_CHECKS = 0;

-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `zip_code` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `place` varchar(255) NOT NULL,
  `company_id` smallint(5) unsigned NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `company_id_to_addresses_idx` (`company_id`),
  CONSTRAINT `company_id_to_addresses` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `companies` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `military_representation` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_phones`
--

DROP TABLE IF EXISTS `company_phones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company_phones` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `company_id` smallint(6) unsigned NOT NULL,
  `phone` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `copmany_id_to_phone_idx` (`company_id`),
  CONSTRAINT `copmany_id_to_phone` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_phones`
--

DROP TABLE IF EXISTS `employee_phones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_phones` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) NOT NULL,
  `employees_id` smallint(5) unsigned NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employees_id_to_mobil_idx` (`employees_id`),
  CONSTRAINT `FK_employee_phones_employees` FOREIGN KEY (`employees_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `patronymic` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `work_phone` varchar(255) NOT NULL,
  `mobil_phone` varchar(255) NOT NULL,
  `company_id` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `company_to_empl_idx` (`company_id`),
  CONSTRAINT `company_to_empl` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO companies (title, email)
VALUES ('тест 1', '1@bb'),
       ('тест 2', '2@bb');

INSERT INTO addresses (zip_code, city, street, place, company_id, description)
VALUES ('123', 'Moscow', 'Tverskay', '125',1, 'test'),
        ('222', 'Kaliningrad', 'First', '1',2, 'test');

INSERT INTO company_phones (company_id, phone, description)
VALUES (1,'22566','test'),
        (1,'22222566','test2'),
        (2,'2222566','test3');


INSERT INTO employees (name, last_name, patronymic, position, email, work_phone, mobil_phone, company_id)
VALUES ('Vasyl', 'Petrov', 'Ivanych', 'boss','123@bb', '123456','2232445',1),
('Vasyl', 'Petrov', 'Ivanych', 'boss','123@bb', '123456','2232445',1),
('Oleg', 'Ivanov', 'Evgenyevich', 'small boss','23665@aa', '54664','1233445',1),
('Veeeasyl', 'Petrowev', 'Ivaneewch', 'beeoss','1wwe23@bb', '1224243456','2232222',2);

SET FOREIGN_KEY_CHECKS = 1;