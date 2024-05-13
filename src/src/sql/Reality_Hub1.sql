create database Reality_Hub1;
use Reality_Hub1;

CREATE TABLE `user_profile` (
  `profileID` int NOT NULL AUTO_INCREMENT,
  `profileType` varchar(45) NOT NULL,
  `profileInfo` varchar(45) NOT NULL,
  `profileStatus` tinyint(1) NOT NULL,
  PRIMARY KEY (`profileID`)
);
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
) ;
INSERT INTO `user_profile` VALUES (1,'System Admin','System Admin',1),(2,'Real Estate Agent','Real Estate Agent',1),(3,'Buyer','Buyer',1),(4,'Seller','Seller',1);

INSERT INTO user_account Values (2,'seller1','seller1','Seller', 'Two',3,1),(3,'adminone','admin123','Buyer', 'Four',2,2);

select*from user_profile;
select * from user_account;


