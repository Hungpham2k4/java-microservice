-- Drop và tạo database
DROP DATABASE IF EXISTS `microservice`;
CREATE DATABASE `microservice`;
USE `microservice`;

-- Tạo bảng user trước do các bảng khác phụ thuộc
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `created_at` datetime(6) NOT NULL,
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `updated_at` datetime(6) NOT NULL,
                        `email` varchar(50) NOT NULL,
                        `name` varchar(50) NOT NULL,
                        `username` varchar(50) NOT NULL,
                        `password` varchar(100) NOT NULL,
                        `access_token` varchar(150) DEFAULT NULL,
                        `refresh_token` varchar(150) DEFAULT NULL,
                        `role` enum('ADMIN','MANAGER','USER') DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY (`email`),
                        UNIQUE KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng refreshtoken phụ thuộc user
DROP TABLE IF EXISTS `refreshtoken`;
CREATE TABLE `refreshtoken` (
                                `id` bigint NOT NULL,
                                `expiry_date` datetime(6) NOT NULL,
                                `token` varchar(255) NOT NULL,
                                `user_id` bigint DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY (`token`),
                                UNIQUE KEY (`user_id`),
                                CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng cart phụ thuộc user
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
                        `created_at` datetime(6) NOT NULL,
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `updated_at` datetime(6) NOT NULL,
                        `user_id` bigint NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY (`user_id`),
                        CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng category
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
                            `created_at` datetime(6) NOT NULL,
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `parent_id` bigint DEFAULT '0',
                            `updated_at` datetime(6) NOT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            `status` enum('CANCELLED','PENDING','PUBLIC') NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng products
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
                            `price` double DEFAULT NULL,
                            `created_at` datetime(6) NOT NULL,
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `quantity` bigint DEFAULT NULL,
                            `updated_at` datetime(6) NOT NULL,
                            `description` varchar(255) DEFAULT NULL,
                            `detail` varchar(255) DEFAULT NULL,
                            `images` varchar(255) DEFAULT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            `slug` varchar(255) DEFAULT NULL,
                            `thumbnail` varchar(255) DEFAULT NULL,
                            `status` enum('CANCELLED','PENDING','PUBLIC') NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng product_category
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
                                    `category_id` bigint NOT NULL,
                                    `created_at` datetime(6) NOT NULL,
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `product_id` bigint NOT NULL,
                                    `updated_at` datetime(6) NOT NULL,
                                    PRIMARY KEY (`id`),
                                    CONSTRAINT FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
                                    CONSTRAINT FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng cart_item
DROP TABLE IF EXISTS `cart_item`;
CREATE TABLE `cart_item` (
                             `quantity` int NOT NULL,
                             `cart_id` bigint NOT NULL,
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `product_id` bigint NOT NULL,
                             PRIMARY KEY (`id`),
                             CONSTRAINT FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE,
                             CONSTRAINT FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng orders
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
                          `total_price` double NOT NULL,
                          `created_at` datetime(6) NOT NULL,
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `updated_at` datetime(6) NOT NULL,
                          `user_id` bigint NOT NULL,
                          `address` varchar(255) NOT NULL,
                          `phone_number` varchar(255) NOT NULL,
                          `recipient_name` varchar(255) NOT NULL,
                          `status` enum('CANCELED','DELIVERED','PENDING','SHIPPED') NOT NULL,
                          PRIMARY KEY (`id`),
                          CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng order_item
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
                              `price` double NOT NULL,
                              `quantity` int NOT NULL,
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `order_id` bigint NOT NULL,
                              `product_id` bigint NOT NULL,
                              PRIMARY KEY (`id`),
                              CONSTRAINT FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
                              CONSTRAINT FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng post
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
                        `created_at` datetime(6) NOT NULL,
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `updated_at` datetime(6) NOT NULL,
                        `title` varchar(50) NOT NULL,
                        `description` varchar(100) NOT NULL,
                        `content` varchar(150) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng comment
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
                           `created_at` datetime(6) NOT NULL,
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `post_id` bigint NOT NULL,
                           `updated_at` datetime(6) NOT NULL,
                           `name` varchar(50) NOT NULL,
                           `email` varchar(75) NOT NULL,
                           `body` varchar(100) NOT NULL,
                           PRIMARY KEY (`id`),
                           CONSTRAINT FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================================
-- INSERT DATA
-- =============================================

-- User data
INSERT INTO `user` VALUES
                       ('2025-03-17 11:05:32.000000',1,'2025-03-17 11:05:32.000000','john@example.com','John Doe','johndoe','password123',NULL,NULL,'ADMIN'),
                       ('2025-03-17 11:05:32.000000',2,'2025-03-17 11:05:32.000000','jane@example.com','Jane Smith','janesmith','password456',NULL,NULL,'ADMIN'),
                       ('2025-03-17 11:05:32.000000',3,'2025-03-17 11:05:32.000000','alice@example.com','Alice Johnson','alicej','password789',NULL,NULL,'ADMIN'),
                       ('2025-03-17 11:05:32.000000',4,'2025-03-17 11:05:32.000000','22.phamviethung.12a12@gmail.com','Phạm Viết Hùng','hungpham','$2a$12$690I4qJGW1amnOaOemGlYOLPOeLUr62FzjsUn4WmuC0rcpzXLb9YW',NULL,NULL,'ADMIN');

-- Category data
INSERT INTO `category` VALUES
                           ('2025-03-17 11:05:32.000000',1,NULL,'2025-03-17 11:05:32.000000','Programming','PUBLIC'),
                           ('2025-03-17 11:05:32.000000',2,1,'2025-03-17 11:05:32.000000','Web Development','PUBLIC'),
                           ('2025-03-17 11:05:32.000000',3,1,'2025-03-17 11:05:32.000000','Mobile Development','PUBLIC');

-- Products data
INSERT INTO `products` VALUES
                           (29.99,'2025-03-17 11:05:32.000000',1,100,'2025-03-17 11:05:32.000000','A comprehensive guide to Spring Framework','This book covers all aspects of Spring Framework.','assets/images/ps4_console_white_1.png,assets/images/ps4_console_white_2.png,assets/images/ps4_console_white_3.png,assets/images/ps4_console_white_4.png','Wireless Controller for PS4™','spring-framework-book','assets/images/ps4_console_white_1.png','PUBLIC'),
                           (19.99,'2025-03-17 11:05:32.000000',2,50,'2025-03-17 11:05:32.000000','Learn the essentials of Spring Boot','This book is perfect for beginners.','assets/images/Image Popular Product 2.png','Nike Sport White - Man Pant','spring-boot-essentials','assets/images/Image Popular Product 2.png','PUBLIC'),
                           (39.99,'2025-03-17 11:05:32.000000',3,75,'2025-03-17 11:05:32.000000','A practical guide to microservices','This book provides practical examples.','assets/images/glap.png','Gloves XC Omega - Polygon','microservices-in-action','assets/images/glap.png','PUBLIC'),
                           (39.99,'2025-03-17 11:05:32.000000',4,75,'2025-03-17 11:05:32.000000','A practical guide to microservices','This book provides practical examples.','assets/images/wireless headset.png','Logitech Head','microservices-in-action','assets/images/wireless headset.png','PUBLIC'),
                           (600000,'2025-03-17 11:06:41.191871',5,32,'2025-03-17 11:06:41.191871','asdasd','asasd','https://gateway.pinata.cloud/ipfs/QmTqQtuesWv7Vyz9K13tw6arF3us1YVne3heFTWvag7gR5,https://gateway.pinata.cloud/ipfs/QmXae2SiTj6HqNtepxExe22ksvtaZe2MxSynPRRcK66J2a,https://gateway.pinata.cloud/ipfs/QmcB6jy8MVHbi62nreaPHUzurAFBruCdLn4z9G7kiaZtvY','PHẠM VIẾT HÙNG','microservices-in-action','https://gateway.pinata.cloud/ipfs/Qmbq4LocCjPeqx8viiJwedkt5ijUCPVmhwTPnstbR7Aspg','PUBLIC');

-- Product_category data
INSERT INTO `product_category` VALUES
                                   (2,'2025-03-17 11:05:32.000000',1,1,'2025-03-17 11:05:32.000000'),
                                   (2,'2025-03-17 11:05:32.000000',2,2,'2025-03-17 11:05:32.000000'),
                                   (1,'2025-03-17 11:05:32.000000',3,3,'2025-03-17 11:05:32.000000'),
                                   (1,'2025-03-17 11:06:41.217238',4,5,'2025-03-17 11:06:41.217238');

-- Cart data
INSERT INTO `cart` VALUES
                       ('2025-03-17 11:05:32.000000',1,'2025-03-17 11:05:32.000000',1),
                       ('2025-03-17 11:05:32.000000',2,'2025-03-17 11:05:32.000000',2),
                       ('2025-03-17 11:05:32.000000',3,'2025-03-17 11:05:32.000000',3),
                       ('2025-03-17 11:32:21.990639',4,'2025-03-17 11:32:21.990639',4);

-- Cart_item data
INSERT INTO `cart_item` VALUES
                            (2,1,1,1),
                            (1,1,2,2),
                            (3,2,3,3),
                            (1,4,4,5);

-- Orders data
INSERT INTO `orders` VALUES
                         (79.97,'2025-03-17 11:05:32.000000',1,'2025-03-17 11:05:32.000000',1,'123 Đường ABC, TP.HCM','0123456789','Nguyen Van A','PENDING'),
                         (119.97,'2025-03-17 11:05:32.000000',2,'2025-03-17 11:05:32.000000',2,'456 Đường XYZ, Hà Nội','0987654321','Tran Van B','SHIPPED'),
                         (49.98,'2025-03-17 11:05:32.000000',3,'2025-03-17 11:05:32.000000',3,'789 Đường LMN, Đà Nẵng','0912345678','Le Thi C','DELIVERED');

-- Order_item data
INSERT INTO `order_item` VALUES
                             (29.99,2,1,1,1),
                             (19.99,1,2,1,2),
                             (39.99,3,3,2,3),
                             (29.99,1,4,3,1),
                             (19.99,2,5,3,2);

-- Post data
INSERT INTO `post` VALUES
                       ('2025-03-17 11:05:32.000000',1,'2025-03-17 11:05:32.000000','Spring Framework','Learn Spring Framework','Learn with Mentor Khoa'),
                       ('2025-03-17 11:05:32.000000',2,'2025-03-17 11:05:32.000000','Spring Boot','Learn Spring Boot','Build applications with Spring Boot'),
                       ('2025-03-17 11:05:32.000000',3,'2025-03-17 11:05:32.000000','Microservices','Understanding Microservices','Learn how to build microservices');

-- Comment data
INSERT INTO `comment` VALUES
                          ('2025-03-17 11:05:32.000000',1,1,'2025-03-17 11:05:32.000000','User1','user1@example.com','Great post!'),
                          ('2025-03-17 11:05:32.000000',2,2,'2025-03-17 11:05:32.000000','User2','user2@example.com','Very informative.'),
                          ('2025-03-17 11:05:32.000000',3,3,'2025-03-17 11:05:32.000000','User3','user3@example.com','Thanks for sharing!');