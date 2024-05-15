-- User Profiles Table
CREATE TABLE `user_profile` (
  `profileID` int NOT NULL AUTO_INCREMENT,
  `profileType` varchar(45) NOT NULL,
  `profileInfo` varchar(45) NOT NULL,
  `profileStatus` tinyint(1) NOT NULL,
  PRIMARY KEY (`profileID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- User Accounts Table
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;