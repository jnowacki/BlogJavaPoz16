-- drop database javapoz16blog;
create database javapoz16blog;
use javapoz16blog;

CREATE TABLE `users` (
                       `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                       `login` VARCHAR(50) NOT NULL UNIQUE,
                       `password` VARCHAR(250) NOT NULL
);

insert into `users` (`login`, `password`) values
('user1', '$12$hYJ5G90y1EABpyQOnS16yuocDxQLZb6u1YUM/WKhCXrP.hQpxZSTy'),
('user2', '$12$e.pVlxZjWfdhTOJbMWKEle1F.blkudK/G3rYH24u2ttYyTW2NwsP2');

select * from users;