CREATE DATABASE `buddies`;

USE `buddies`;

CREATE TABLE `profile` (
    `profile_id` INT NOT NULL AUTO_INCREMENT ,
    `profile_name` VARCHAR(64) NOT NULL ,
    `profile_desc` VARCHAR(256) NOT NULL ,
    PRIMARY KEY (`profile_id`)
);

CREATE TABLE `role` (
    `role_id` INT NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(64) NOT NULL,
    `role_desc` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE `user_account` (
    `username` VARCHAR(64) NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    `f_name` VARCHAR(64) NOT NULL,
    `l_name` VARCHAR(64) NOT NULL,
    `email` VARCHAR(64) NOT NULL,
    `max_slot` INT NULL,
    `profile_id` INT NOT NULL,
    `role_id` INT NULL,
    `status` BOOLEAN NOT NULL,
    PRIMARY KEY (`username`),
    FOREIGN KEY (`profile_id`) REFERENCES profile(`profile_id`),
    FOREIGN KEY (`role_id`) REFERENCES role(`role_id`)
);

CREATE TABLE `work_slot` (
    `work_slot_id` INT NOT NULL AUTO_INCREMENT,
    `date` DATE NOT NULL,
    PRIMARY KEY (`work_slot_id`)
);

CREATE TABLE `role_amount` (
    `work_slot_id` INT NOT NULL AUTO_INCREMENT,
    `role_id` INT NOT NULL,
    `amount` INT NOT NULL,
    PRIMARY KEY (`work_slot_id`, `role_id`),
    FOREIGN KEY (`work_slot_id`) REFERENCES work_slot(`work_slot_id`),
    FOREIGN KEY (`role_id`) REFERENCES role(`role_id`)
);

CREATE TABLE `bid` (
    `bid_id` INT NOT NULL AUTO_INCREMENT,
    `bid_status` VARCHAR(64) NOT NULL,
    `username` VARCHAR(64) NOT NULL,
    `work_slot_id` INT NOT NULL,
    PRIMARY KEY (`bid_id`),
    FOREIGN KEY (`username`) REFERENCES user_account(`username`),
    FOREIGN KEY (`work_slot_id`) REFERENCES work_slot(`work_slot_id`)
);

INSERT INTO `role` (role_name, role_desc) VALUES
    ('Waiter', 'Serving customer'),
    ('Cashier', 'Do transaction'),
    ('Chef', 'Cook food');

INSERT INTO `profile`(profile_name, profile_desc) VALUES
    ('System Admin', 'Make user account'),
    ('Cafe Owner', 'Make work slot'),
    ('Cafe Manager', 'Manage bids'),
    ('Cafe Staff', 'Make bids');

INSERT INTO user_account (username, password, f_name, l_name, email, max_slot, profile_id, role_id, status) VALUES
    ('admin', 'admin', 'Fadmin', 'Ladmin', 'admin@gmail.com', NULL, 1, NULL, TRUE),
    ('owner', 'owner', 'Fowner', 'Lowner', 'owner@gmail.com', NULL, 2, NULL, TRUE),
    ('manager', 'manager', 'Fanager', 'Lanager', 'manager@gmail.com', NULL, 3, NULL, TRUE),
    ('chef', 'chef', 'Gordon', 'Ramsay', 'chef@gmail.com', 5, 4, 3, TRUE),
    ('cashier', 'cashier', 'Jerry', 'Ma', 'cashier@gmail.com', 5, 4, 2, TRUE),
    ('waiter', 'waiter', 'Tom', 'Brooklyn', 'waiter@gmail.com', 5, 4, 1, TRUE)
    ;

