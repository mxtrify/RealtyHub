-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: realtyhub
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `agent_ratings`
--

DROP TABLE IF EXISTS `agent_ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent_ratings` (
  `ratingID` int NOT NULL AUTO_INCREMENT,
  `reviewerID` int NOT NULL,
  `reviewerType` int NOT NULL,
  `agentID` int NOT NULL,
  `rating` tinyint NOT NULL,
  PRIMARY KEY (`ratingID`),
  KEY `reviewerID` (`reviewerID`),
  KEY `agentID` (`agentID`),
  KEY `reviewer_Type_idx` (`reviewerType`),
  CONSTRAINT `rating_agent` FOREIGN KEY (`agentID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rating_reviewer` FOREIGN KEY (`reviewerID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reviewer_Type` FOREIGN KEY (`reviewerType`) REFERENCES `user_profile` (`profileID`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `agent_reviews`
--

DROP TABLE IF EXISTS `agent_reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent_reviews` (
  `reviewID` int NOT NULL AUTO_INCREMENT,
  `reviewerID` int NOT NULL,
  `reviewerType` int NOT NULL,
  `agentID` int NOT NULL,
  `review` text NOT NULL,
  PRIMARY KEY (`reviewID`),
  KEY `reviewerID` (`reviewerID`),
  KEY `agentID` (`agentID`),
  KEY `reviewer_type_idx` (`reviewerType`),
  CONSTRAINT `review_agent` FOREIGN KEY (`agentID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `review_reviewer` FOREIGN KEY (`reviewerID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `review_type` FOREIGN KEY (`reviewerType`) REFERENCES `user_profile` (`profileID`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property` (
  `listingID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `location` varchar(45) NOT NULL,
  `info` varchar(100) NOT NULL,
  `price` int NOT NULL,
  `sellerID` int NOT NULL,
  `saleStatus` tinyint(1) NOT NULL,
  PRIMARY KEY (`listingID`),
  KEY `sellerID` (`sellerID`),
  CONSTRAINT `listing_seller` FOREIGN KEY (`sellerID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property_saves`
--

DROP TABLE IF EXISTS `property_saves`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property_saves` (
  `saveID` int NOT NULL AUTO_INCREMENT,
  `listingID` int NOT NULL,
  `saverID` int NOT NULL,
  PRIMARY KEY (`saveID`),
  KEY `listingID` (`listingID`),
  KEY `saverID` (`saverID`),
  CONSTRAINT `save_listing` FOREIGN KEY (`listingID`) REFERENCES `property` (`listingID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `save_saver` FOREIGN KEY (`saverID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property_views`
--

DROP TABLE IF EXISTS `property_views`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property_views` (
  `listingID` int NOT NULL,
  `viewCount` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`listingID`),
  CONSTRAINT `view_listing` FOREIGN KEY (`listingID`) REFERENCES `property` (`listingID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_account` (
  `accountID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `fName` varchar(45) NOT NULL,
  `lName` varchar(45) NOT NULL,
  `profileID` int NOT NULL,
  `accountStatus` tinyint(1) NOT NULL,
  PRIMARY KEY (`accountID`),
  KEY `profileID` (`profileID`),
  CONSTRAINT `profile_id` FOREIGN KEY (`profileID`) REFERENCES `user_profile` (`profileID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile` (
  `profileID` int NOT NULL AUTO_INCREMENT,
  `profileType` varchar(45) NOT NULL,
  `profileInfo` varchar(45) NOT NULL,
  `profileStatus` tinyint(1) NOT NULL,
  PRIMARY KEY (`profileID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-19 20:58:41