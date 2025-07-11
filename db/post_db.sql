CREATE DATABASE  IF NOT EXISTS `post` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `post`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: post
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `authtbl`
--

DROP TABLE IF EXISTS `authtbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authtbl` (
  `username` varchar(10) NOT NULL,
  `fullname` varchar(20) NOT NULL,
  `joindate` date DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(10) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authtbl`
--

LOCK TABLES `authtbl` WRITE;
/*!40000 ALTER TABLE `authtbl` DISABLE KEYS */;
INSERT INTO `authtbl` VALUES ('aaa','홍길동','2025-06-18','$2a$10$PdUYkjVI1cCz9GAAeiVNY.2n7iL/glfFeslIgkykPZUXM5v6Sn9MW','ROLE_USER'),('bbb','홍길순','2025-06-18','$2a$10$AoudUxfF/ZyranUqZJPHxOHFOByIpKHAyMuVqbbiVGj4UE6BMyOtu','ROLE_USER'),('ccc','강호동','2025-06-18','$2a$10$iO4CgTn/Y1tFrjmuuh5nSuLx5RaYePlK835da86l4CgyjpvJmpyGa','ROLE_USER');
/*!40000 ALTER TABLE `authtbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posttbl`
--

DROP TABLE IF EXISTS `posttbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posttbl` (
  `postnum` int NOT NULL AUTO_INCREMENT,
  `body` tinytext,
  `title` varchar(225) NOT NULL,
  `username` varchar(10) NOT NULL,
  PRIMARY KEY (`postnum`),
  KEY `FK8id8rbdx1nrpu0f9r2jx9o7a4` (`username`),
  CONSTRAINT `FK8id8rbdx1nrpu0f9r2jx9o7a4` FOREIGN KEY (`username`) REFERENCES `authtbl` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posttbl`
--

LOCK TABLES `posttbl` WRITE;
/*!40000 ALTER TABLE `posttbl` DISABLE KEYS */;
INSERT INTO `posttbl` VALUES (1,'글내용입니다.','글입니다','bbb'),(2,'글내용 입니다.','b가 쓴 글입니다.2','bbb');
/*!40000 ALTER TABLE `posttbl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-11 16:13:08
