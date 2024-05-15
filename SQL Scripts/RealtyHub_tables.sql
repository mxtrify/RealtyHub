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

-- Property Listings Table
CREATE TABLE `property` (
  `listingID` int NOT NULL AUTO_INCREMENT,
  `sellerID` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `location` varchar(45) NOT NULL,
  `price` int NOT NULL,
  `saleStatus` tinyint(1) NOT NULL,
  PRIMARY KEY (`listingID`),
  KEY `sellerID` (`sellerID`),
  CONSTRAINT `listing_seller` FOREIGN KEY (`sellerID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Property Listing Views Table
CREATE TABLE `property_views` (
  `viewID` int NOT NULL AUTO_INCREMENT,
  `listingID` int NOT NULL,
  `viewerID` int NOT NULL,
  `view_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`viewID`),
  KEY `listingID` (`listingID`),
  KEY `viewerID` (`viewerID`),
  CONSTRAINT `view_listing` FOREIGN KEY (`listingID`) REFERENCES `property` (`listingID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `view_viewer` FOREIGN KEY (`viewerID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Property Listing Saves Table
CREATE TABLE `property_saves` (
  `saveID` int NOT NULL AUTO_INCREMENT,
  `listingID` int NOT NULL,
  `saverID` int NOT NULL,
  `save_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`saveID`),
  KEY `listingID` (`listingID`),
  KEY `saverID` (`saverID`),
  CONSTRAINT `save_listing` FOREIGN KEY (`listingID`) REFERENCES `property` (`listingID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `save_saver` FOREIGN KEY (`saverID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Real Estate Agent Ratings Table
CREATE TABLE `agent_ratings` (
  `ratingID` int NOT NULL AUTO_INCREMENT,
  `reviewerID` int NOT NULL,
  `agentID` int NOT NULL,
  `rating` tinyint NOT NULL,  -- Removed deprecated display width
  `rating_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ratingID`),
  KEY `reviewerID` (`reviewerID`),
  KEY `agentID` (`agentID`),
  CONSTRAINT `rating_reviewer` FOREIGN KEY (`reviewerID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rating_agent` FOREIGN KEY (`agentID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Real Estate Agent Reviews Table
CREATE TABLE `agent_reviews` (
  `reviewID` int NOT NULL AUTO_INCREMENT,
  `reviewerID` int NOT NULL,
  `agentID` int NOT NULL,
  `review` text NOT NULL,
  `review_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reviewID`),
  KEY `reviewerID` (`reviewerID`),
  KEY `agentID` (`agentID`),
  CONSTRAINT `review_reviewer` FOREIGN KEY (`reviewerID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `review_agent` FOREIGN KEY (`agentID`) REFERENCES `user_account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;