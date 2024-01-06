CREATE TABLE `users` (
    `id` int NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(500) NOT NULL,
    `enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `authorities` (
    `id` int NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `authority` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
);

insert into users values(null, 'happy', '12345', '1');
insert into authorities values(null, 'happy', 'write');


CREATE TABLE `customer` (
    `id` int NOT NULL AUTO_INCREMENT,
    `email` varchar(45) NOT NULL,
    `pwd` varchar(200) NOT NULL,
    `role` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO customer (id, email, pwd, role) VALUES (1, 'mandu@example.com', '12345', 'admin');
