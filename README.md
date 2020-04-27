# websalescrm

A leightweight sales CRM program. Theres basic funtions like add/delete/update customer, product and order. The starting view is a list of
the customers in the CRM and when to call them.

Every customer have a customer card where you can create a order for the customer. To postpone a date to call customer,
click uppdate and change the date.

This is a very basic sales CRM, theres a lot of improvements that can be made. However, the purpose of this project was to learn Spring,
Spring Boot, Tymeleaf and Hibernate NOT to make a competitive salesCRM. But i'm sure i will come back and update the project to
learn more.

MySQL create database and tables:

DATABASE
CREATE DATABASE `sales-crm`;

CREATE TABLE `authorities` (
  `authority_id` int(11) NOT NULL,
  `authority` varchar(50) NOT NULL,
  PRIMARY KEY (`authority_id`)
);

CREATE TABLE `comments` (
  `comments_id` int(11) NOT NULL AUTO_INCREMENT,
  `comments_customer_id` int(11) DEFAULT NULL,
  `comment` varchar(5000) DEFAULT NULL,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comments_id`)
);

CREATE TABLE `company` (
  `company_user_id` int(11) NOT NULL,
  `organisation_number` varchar(45) DEFAULT NULL,
  `company_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`company_user_id`)
);

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_employee_id` int(11) DEFAULT NULL,
  `name` varchar(150) NOT NULL,
  `next_contact` date DEFAULT NULL,
  `customer_order_id` int(11) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL,
  `email` varchar(250) DEFAULT NULL,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`customer_id`)
);

CREATE TABLE `customer_shopping_cart` (
  `csc_id` int(11) NOT NULL AUTO_INCREMENT,
  `csc_product_id` int(11) NOT NULL,
  `csc_cart_id` int(11) NOT NULL,
  PRIMARY KEY (`csc_id`)
);

CREATE TABLE `employee` (
  `employee_user_id` int(11) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `employee_company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`employee_user_id`)
);

CREATE TABLE `order_id` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_customer_id` int(11) DEFAULT NULL,
  `fk_employee_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `customer_id` (`fk_customer_id`),
  CONSTRAINT `order_id_ibfk_1` FOREIGN KEY (`fk_customer_id`) REFERENCES `customer` (`customer_id`)
);

CREATE TABLE `order_product` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_product_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_id` (`order_id`),
  CONSTRAINT `order_product_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
);

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  `product_price` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
);

CREATE TABLE `shopping_cart` (
  `shopping_cart_id` int(11) NOT NULL AUTO_INCREMENT,
  `shopping_cart_customer_id` varchar(45) NOT NULL,
  PRIMARY KEY (`shopping_cart_id`)
);

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(65) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `user_authority` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  KEY `fk_user_authority_idx` (`user_authority`),
  CONSTRAINT `fk_user_authority` FOREIGN KEY (`user_authority`) REFERENCES `authorities` (`authority_id`)
);




