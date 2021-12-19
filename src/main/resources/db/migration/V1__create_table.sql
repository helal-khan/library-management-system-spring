CREATE TABLE IF NOT EXISTS `role` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `user` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `full_name` varchar(255) NOT NULL,
    `username` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `email` varchar(255) NOT NULL,
    `address` text NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`),
    UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `user_role` (
    `user_id` bigint NOT NULL,
    `role_id` int NOT NULL,
    PRIMARY KEY (`user_id`,`role_id`),
    KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
    CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `book` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `title` varchar(255) NOT NULL,
    `copies` int NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKwugryet8mf6oi28n00x2eoc4` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `book_meta` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `author` varchar(255) NOT NULL,
    `publisher` varchar(255) DEFAULT NULL,
    `edition` varchar(255) DEFAULT NULL,
    `isbn` varchar(255) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `book_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_8iu7g49bo9by5b3bar1a7ecgj` (`book_id`),
    CONSTRAINT `FK30v13rrost15o8941sd48cfi8` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `borrow` (
    `id` int NOT NULL AUTO_INCREMENT,
    `user_id` bigint DEFAULT NULL,
    `book_id` bigint DEFAULT NULL,
    `copies` int NOT NULL,
    `issue_date` datetime(6) NOT NULL,
    `return_date` datetime(6) NOT NULL,
    `is_returned` bit(1) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `borrowed_by` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKgqh01ty3c1u7ja2rjdua05c36` (`book_id`),
    KEY `FK81lf68jcvpplu195timt08nt2` (`borrowed_by`),
    KEY `FKtlx8cbafjlyp2hgfog0bdmni3` (`user_id`),
    CONSTRAINT `FK81lf68jcvpplu195timt08nt2` FOREIGN KEY (`borrowed_by`) REFERENCES `user` (`id`),
    CONSTRAINT `FKgqh01ty3c1u7ja2rjdua05c36` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
    CONSTRAINT `FKtlx8cbafjlyp2hgfog0bdmni3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;