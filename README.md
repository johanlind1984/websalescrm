# websalescrm

A leightweight sales CRM program. Theres basic funtions like add/delete/update customer, product and order. The starting view is a list of
the customers in the CRM and when to call them.

Every customer have a customer card where you can create a order for the customer. To postpone a date to call customer,
click uppdate and change the date.

This is a very basic sales CRM, theres a lot of improvements that can be made. However, the purpose of this project was to learn Spring,
Spring Boot, Tymeleaf and Hibernate NOT to make the best salesCRM in the world. But i'm sure i will come back and update the project to
to learn more.

Requirements:
Maven
MySQL

MySQL create database and tables:

DATABASE
CREATE DATABASE `sales-crm`;



EMPLOYEE TABLE

TABLE
CREATE TABLE IF NOT EXISTS employee (
	id INTEGER AUTO_INCREMENT NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	sales INTEGER,
	orders INTEGER,
   PRIMARY KEY(id)
);

ORDER_ID
CREATE TABLE `order_id` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_customer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `customer_id` (`fk_customer_id`),
  CONSTRAINT `order_id_ibfk_1` FOREIGN KEY (`fk_customer_id`) REFERENCES `customer` (`customer_id`)
);

PRODUCT
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`product_id`)
);



ORDER_PRODUCT
CREATE TABLE `order_product` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_product` FOREIGN KEY (`order_id`) REFERENCES `order_id` (`order_id`),
  CONSTRAINT `order_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
);


CUSTOMER
CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `next_contact` date DEFAULT NULL,
  `comments` varchar(2000) DEFAULT NULL,
  `customer_order_id` int(11) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
)



