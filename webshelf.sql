-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: webshelf
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` varchar(255) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `loaned` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `library_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaojxagnfmppd09k35kye5eph5` (`library_id`),
  CONSTRAINT `FKaojxagnfmppd09k35kye5eph5` FOREIGN KEY (`library_id`) REFERENCES `library` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES ('191621','Scott Fitzgerald','Drama',_binary '','The Great Gatsby','241031191409'),('191658','James Joyce','Historic',_binary '\0','Ulysses','241031191409'),('191836','Marcel Proust','Drama',_binary '',' In Search of Lost Time','241031191409'),('191857','Gabriel García','Drama',_binary '\0','One Hundred Years of Solitude','241031191409'),('192021','J. D. Salinger','Non Ficcion',_binary '\0','The Catcher in the Rye','241031191409'),('192054','Tolstoy','Non Ficcion',_binary '\0','Anna Karenina','241031191409'),('192115','Emily Brontë','Romance',_binary '\0','Wuthering Heights','241031191409'),('192133','Jane Austen','Romance',_binary '\0','Pride and Prejudice','241031191409'),('192205','Fyodor Dostoevsky','Drama',_binary '\0','Crime and Punishment','241031191409');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id` varchar(255) NOT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `registry_date` date NOT NULL,
  `library_id` varchar(255) NOT NULL,
  `has_active_loan` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_plnstg0h55xcbwkkf8iehxo71` (`cpf`),
  UNIQUE KEY `UK_bfgjs3fem0hmjhvih80158x29` (`email`),
  KEY `FK5sd1dukhd2g8bvcm2i2ps4hql` (`library_id`),
  CONSTRAINT `FK5sd1dukhd2g8bvcm2i2ps4hql` FOREIGN KEY (`library_id`) REFERENCES `library` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES ('241031192259','47725525882','lucas.silvaambrosio@hotmail.com','Lucas Henrique','2024-10-31','241031191409',_binary '\0'),('241031192352','44459109026','Pedro.lima@gmail.com','Pedro Lima','2024-10-31','241031191409',_binary '\0'),('241031192430','64342070006','Jose.silva12345@hotmail.com','José da Silva','2024-10-31','241031191409',_binary ''),('241031192507','51044718021','aline.rocha321@yahoo.com','Aline rocha','2024-10-31','241031191409',_binary ''),('241031192818','48066937075','Aristides.Cunha456@hotmail.com','Aristides Cunha','2024-10-31','241031191409',_binary '\0'),('241031192928','269.955.840-07','Paulo@gmail.com','Paulo ','2024-10-31','241031191409',_binary '\0');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library`
--

DROP TABLE IF EXISTS `library`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `library` (
  `id` varchar(255) NOT NULL,
  `library_name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k1q3l3qsqywala4j8ncfufpj7` (`username`),
  UNIQUE KEY `UK_rxgiipxnarh7ihhhjtq931a78` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library`
--

LOCK TABLES `library` WRITE;
/*!40000 ALTER TABLE `library` DISABLE KEYS */;
INSERT INTO `library` VALUES ('241031191409','Céos Library','CeosTitan','CeosLib','CeosLibrary@outlook.com');
/*!40000 ALTER TABLE `library` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan` (
  `id` varchar(255) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `loan_date` date NOT NULL,
  `return_date` date NOT NULL,
  `book_id` varchar(255) NOT NULL,
  `client_id` varchar(255) NOT NULL,
  `library_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK88c0ydlo57pcgp137tntrgqx1` (`book_id`),
  KEY `FK62s5k229ouak16t2k5pvq4n16` (`client_id`),
  KEY `FKgjticxpyvhf1w48gm96t0uev3` (`library_id`),
  CONSTRAINT `FK62s5k229ouak16t2k5pvq4n16` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  CONSTRAINT `FK88c0ydlo57pcgp137tntrgqx1` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FKgjticxpyvhf1w48gm96t0uev3` FOREIGN KEY (`library_id`) REFERENCES `library` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` VALUES ('241031230230',_binary '','2024-10-31','2024-11-08','191621','241031192507','241031191409'),('241101012414',_binary '','2024-11-01','2024-11-05','191836','241031192430','241031191409');
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-01  1:27:56
