INSERT INTO `role` (`id`, `name`)
VALUES
    (1,'ROLE_ADMIN'),
    (2,'ROLE_USER');

INSERT INTO `user` (`id`, `address`, `created_at`, `email`, `full_name`, `password`, `username`)
VALUES
    (2,'Mohammadpur, Dhaka','2021-12-16 21:53:02.873679','m.helal.k@gmail.com','Md. Helal Khan','$2a$10$QvOWqpI5GaKDS1JpigqKMOQPV.RPZP9V9IkHBFQpSfAkkE.E2Ekzy','helal.khan'),
    (5,'Adabor, Dhaka','2021-12-16 22:13:45.814289','helal.monsterlab@gmail.com','Md. Omor Faruk','$2a$10$VHqHFNZN4WDArnNXnxvU9.129QmfBEhlPEWXq/5koUB1JdlG8geGi','omor.faruk'),
    (6,'Banani, Dhaka','2021-12-17 10:54:04.775302','firoj.ahmed@gmail.com','MD. Firoj Ahmed','$2a$10$2IHAD8jPfQ1oGS93lqzLB.r.UeujVfqnhv0DE2bNvXPOYrvRWrjPO','firoj.ahmed'),
    (7,'Dhanmondi, Dhaka','2021-12-17 12:05:06.041270','ibrahim.ahmed@gmail.com','MD. Ibrahim Ahmed','$2a$10$5lVqGVonQ5WBiBXYItJmRuL1n27Q7qcHLXSPORKUvyDgzDJYLxO96','ibrahim.ahmed'),
    (8,'Uttara, Dhaka','2021-12-17 12:06:29.946706','shahin.ahmed@gmail.com','MD. Shahin Ahmed','$2a$10$l39I4hL3G1Bmp42GAJ3OOunvETkMMlOYrUFCkNby8Vm1RKsUsKx9u','shahin.ahmed'),
    (10,'Gulshan, Dhaka','2021-12-18 00:05:40.528704','milon.khan@gmail.com','Md. Milon Khan','$2a$10$GvvLuAhr8Zipm0QgeklqnuZvNGJ6jlFeR3Ykbct9Htbr5/FDgLsWS','milon.khan');

INSERT INTO `user_role` (`user_id`, `role_id`)
VALUES
    (2,1),
    (2,2),
    (5,2),
    (6,2),
    (7,2),
    (8,2),
    (10,2);

INSERT INTO `book` (`id`, `copies`, `created_at`, `name`, `title`)
VALUES
    (1,10,'2021-12-18 06:25:44.000000','Head First Java','Head First Java, 2nd Edition'),
    (10,10,'2021-12-18 07:41:47.527371','Head First Design Patterns','Head First Design Patterns: A Brain-Friendly Guide 1st Edition'),
    (11,12,'2021-12-18 07:51:37.275580','Clean Code','Clean Code: A Handbook of Agile Software Craftsmanship'),
    (17,8,'2021-12-18 10:41:39.124895','Spring in Action','Spring in Action, Fifth Edition'),
    (18,8,'2021-12-19 08:09:45.648907','Spring in Action new','Spring in Action, Fifth Edition new');

INSERT INTO `book_meta` (`id`, `author`, `created_at`, `edition`, `isbn`, `publisher`, `book_id`)
VALUES
    (1,'Kathy Sierra, Bert Bates',NULL,'2nd Edition','978-0596009205','O\'Reilly Media',1),
	(5,'Eric Freeman, Elisabeth Robson, Bert Bates, Kathy Sierra',NULL,'1st Edition','978-0596007126','O\'Reilly Media',10),
    (6,'Robert C. Martin',NULL,'1st Edition','978-0132350884','Pearson',11),
    (10,'Craig Walls',NULL,'5th Edition','9781617294945','Manning',17),
    (11,'Craig Walls new','2021-12-19 08:09:45.648938','5th Edition new','9781617294945 new','Manning new',18);

INSERT INTO `borrow` (`id`, `created_at`, `copies`, `is_returned`, `return_date`, `book_id`, `borrowed_by`, `user_id`, `issue_date`)
VALUES
    (3,'2021-12-18 22:09:12.412898',5,b'0','2021-12-26 00:00:00.000000',1,2,5,'2021-12-19 00:00:00.000000'),
    (4,'2021-12-18 22:16:04.925301',1,b'1','2021-12-27 00:00:00.000000',1,2,5,'2021-12-20 00:00:00.000000'),
    (5,'2021-12-18 22:33:39.202267',4,b'1','2021-12-27 00:00:00.000000',1,2,6,'2021-12-20 00:00:00.000000'),
    (6,'2021-12-18 23:31:55.553400',4,b'0','2021-12-27 00:00:00.000000',1,2,6,'2021-12-20 00:00:00.000000'),
    (7,'2021-12-19 11:16:02.125266',1,b'0','2021-12-27 00:00:00.000000',10,2,10,'2021-12-20 00:00:00.000000');