CREATE TABLE `general_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `patient_message` varchar(45) NOT NULL,
  `caretaker_message` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `notification_tracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` datetime NOT NULL,
  `notification_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tracker_to_notification` (`notification_id`),
  CONSTRAINT `tracker_to_notification` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`)
)

CREATE TABLE `scheduler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_scan_seconds` int(4) NOT NULL,
  `sleeping_from` time NOT NULL,
  `sleeping_to` time NOT NULL,
  `min_hours_between_meals` int(2) NOT NULL,
  `max_hours_between_meals` int(2) NOT NULL,
  `eating_minutes` int(3) NOT NULL,
  `minutes_between_drinks` int(11) NOT NULL,
  `minutes_between_eat_notifications` int(11) NOT NULL,
  `minutes_between_drink_notifications` int(11) NOT NULL,
  `stored_data_hours` int(11) NOT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `searched_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(45) NOT NULL,
  `general_action_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `searched_to_general` (`general_action_id`),
  CONSTRAINT `searched_to_general` FOREIGN KEY (`general_action_id`) REFERENCES `general_action` (`id`)
)

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
)

CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `footage` mediumtext NOT NULL,
  `timestamp` datetime NOT NULL,
  `label_id` int(11) NOT NULL,
  `score` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `video_to_searched` (`label_id`),
  CONSTRAINT `video_to_searched` FOREIGN KEY (`label_id`) REFERENCES `searched_activity` (`id`)
)






