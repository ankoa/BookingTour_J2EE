-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 30, 2024 lúc 10:30 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `booking_tour`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `account_id` int(11) NOT NULL,
  `account_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  `customerID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`account_id`, `account_name`, `email`, `password`, `status`, `time`, `customerID`) VALUES
(1, 'UserOne', 'user1@example.com', 'tom001', 0, '2024-10-28 11:20:06.000000', 1),
(2, 'dsfgfhg', 'user2@example.com', 'password2', 0, '2024-10-28 21:06:13.000000', 2),
(3, 'UserThree', 'user3@example.com', 'password3', 0, '2024-10-25 09:10:00.000000', 3),
(4, 'UserFour', 'user4@example.com', 'password4', 0, '2024-10-28 11:38:45.000000', 4),
(5, 'UserFive', 'user5@example.com', 'password5', 0, '2024-10-25 09:20:00.000000', 5),
(6, 'UserSix', 'user6@example.com', 'password6', 0, '2024-10-28 11:39:01.000000', 6),
(7, 'UserSeven', 'user7@example.com', 'password7', 0, '2024-10-25 09:30:00.000000', 7),
(8, 'UserEight', 'user8@example.com', 'password8', 0, '2024-10-25 09:35:00.000000', 8),
(9, 'UserNine', 'user9@example.com', 'password9', 0, '2024-10-25 09:40:00.000000', 9),
(10, 'UserTen', 'user10@example.com', 'password10', 0, '2024-10-25 09:45:00.000000', 10),
(11, 'UserEleven', 'user11@example.com', 'password11', 0, '2024-10-25 09:50:00.000000', 11),
(12, 'UserTwelve', 'user12@example.com', 'password12', 0, '2024-10-25 09:55:00.000000', 12),
(13, 'UserThirteen', 'user13@example.com', 'password13', 0, '2024-10-25 10:00:00.000000', 13),
(14, 'UserFourteen', 'user14@example.com', 'password14', 0, '2024-10-25 10:05:00.000000', 14),
(15, 'UserFifteen', 'user15@example.com', 'password15', 0, '2024-10-25 10:10:00.000000', 15),
(16, 'UserSixteen', 'user16@example.com', 'password16', 0, '2024-10-25 10:15:00.000000', 16),
(17, 'UserSeventeen', 'user17@example.com', 'password17', 0, '2024-10-25 10:20:00.000000', 17),
(18, 'UserEighteen', 'user18@example.com', 'password18', 0, '2024-10-25 10:25:00.000000', 18),
(19, 'UserNineteen', 'user19@example.com', 'password19', 0, '2024-10-25 10:30:00.000000', 19),
(20, 'UserTwenty', 'user20@example.com', 'password20', 0, '2024-10-25 10:35:00.000000', 20),
(21, 'UserTwentyOne', 'user21@example.com', 'password21', 0, '2024-10-25 10:40:00.000000', 21),
(22, 'UserTwentyTwo', 'user22@example.com', 'password22', 0, '2024-10-25 10:45:00.000000', 22),
(23, 'UserTwentyThree', 'user23@example.com', 'password23', 0, '2024-10-25 10:50:00.000000', 23),
(24, 'UserTwentyFour', 'user24@example.com', 'password24', 0, '2024-10-25 10:55:00.000000', 24),
(25, 'UserTwentyFive', 'user25@example.com', 'password25', 0, '2024-10-25 11:00:00.000000', 25),
(26, 'UserTwentySix', 'user26@example.com', 'password26', 0, '2024-10-25 11:05:00.000000', 26),
(27, 'UserTwentySeven', 'user27@example.com', 'password27', 0, '2024-10-25 11:10:00.000000', 27),
(28, 'UserTwentyEight', 'user28@example.com', 'password28', 0, '2024-10-25 11:15:00.000000', 28),
(29, 'UserTwentyNine', 'user29@example.com', 'password29', 0, '2024-10-25 11:20:00.000000', 29),
(30, 'UserThirty', 'user30@example.com', 'password30', 0, '2024-10-25 11:25:00.000000', 30),
(31, 'UserThirtyOne', 'user31@example.com', 'password31', 0, '2024-10-25 11:30:00.000000', 31),
(32, 'UserThirtyTwo', 'user32@example.com', 'password32', 0, '2024-10-25 11:35:00.000000', 32),
(33, 'UserThirtyThree', 'user33@example.com', 'password33', 0, '2024-10-25 11:40:00.000000', 33),
(34, 'UserThirtyFour', 'user34@example.com', 'password34', 0, '2024-10-25 11:45:00.000000', 34),
(35, 'UserThirtyFive', 'user35@example.com', 'password35', 0, '2024-10-25 11:50:00.000000', 35),
(36, 'UserThirtySix', 'user36@example.com', 'password36', 0, '2024-10-25 11:55:00.000000', 36),
(37, 'UserThirtySeven', 'user37@example.com', 'password37', 0, '2024-10-25 12:00:00.000000', 37),
(38, 'UserThirtyEight', 'user38@example.com', 'password38', 0, '2024-10-25 12:05:00.000000', 38),
(39, 'UserThirtyNine', 'user39@example.com', 'password39', 0, '2024-10-25 12:10:00.000000', 39),
(40, 'UserForty', 'user40@example.com', 'password40', 0, '2024-10-25 12:15:00.000000', 40),
(41, 'UserFortyOne', 'user41@example.com', 'password41', 0, '2024-10-25 12:20:00.000000', 41),
(42, 'UserFortyTwo', 'user42@example.com', 'password42', 0, '2024-10-25 12:25:00.000000', 42),
(43, 'UserFortyThree', 'user43@example.com', 'password43', 0, '2024-10-25 12:30:00.000000', 43),
(44, 'UserFortyFour', 'user44@example.com', 'password44', 0, '2024-10-25 12:35:00.000000', 44),
(45, 'UserFortyFive', 'user45@example.com', 'password45', 0, '2024-10-25 12:40:00.000000', 45),
(46, 'UserFortySix', 'user46@example.com', 'password46', 0, '2024-10-25 12:45:00.000000', 46),
(47, 'UserFortySeven', 'user47@example.com', 'password47', 0, '2024-10-25 12:50:00.000000', 47),
(48, 'UserFortyEight', 'user48@example.com', 'password48', 0, '2024-10-25 12:55:00.000000', 48),
(49, 'UserFortyNine', 'user49@example.com', 'password49', 0, '2024-10-25 13:00:00.000000', 49),
(50, 'UserFifty', 'user50@example.com', 'password50', 0, '2024-10-25 13:05:00.000000', 50),
(51, 'user1', 'user1@example.com', 'password1', 1, '2024-10-25 11:40:00.000000', 51),
(52, 'user2', 'user2@example.com', 'password2', 0, '2024-10-25 11:41:00.000000', 52),
(53, 'user3', 'user3@example.com', 'password3', 1, '2024-10-25 11:42:00.000000', 53),
(54, 'user4', 'user4@example.com', 'password4', 1, '2024-10-25 11:43:00.000000', 54),
(55, 'user5', 'user5@example.com', 'password5', 0, '2024-10-25 11:44:00.000000', 55),
(56, 'user6', 'user6@example.com', 'password6', 0, '2024-10-25 11:45:00.000000', 56),
(57, 'user7', 'user7@example.com', 'password7', 1, '2024-10-25 11:46:00.000000', 57),
(58, 'user8', 'user8@example.com', 'password8', 0, '2024-10-25 11:47:00.000000', 58),
(59, 'user9', 'user9@example.com', 'password9', 1, '2024-10-25 11:48:00.000000', 59),
(60, 'user10', 'user10@example.com', 'password10', 0, '2024-10-25 11:49:00.000000', 60),
(61, 'user11', 'user11@example.com', 'password11', 1, '2024-10-25 11:50:00.000000', 61),
(62, 'user12', 'user12@example.com', 'password12', 1, '2024-10-25 11:51:00.000000', 62),
(63, 'user13', 'user13@example.com', 'password13', 0, '2024-10-25 11:52:00.000000', 63),
(64, 'user14', 'user14@example.com', 'password14', 1, '2024-10-25 11:53:00.000000', 64),
(65, 'user15', 'user15@example.com', 'password15', 0, '2024-10-25 11:54:00.000000', 65),
(66, 'user16', 'user16@example.com', 'password16', 1, '2024-10-25 11:55:00.000000', 66),
(67, 'user17', 'user17@example.com', 'password17', 1, '2024-10-25 11:56:00.000000', 67),
(68, 'user18', 'user18@example.com', 'password18', 0, '2024-10-25 11:57:00.000000', 68),
(69, 'user19', 'user19@example.com', 'password19', 1, '2024-10-25 11:58:00.000000', 69),
(70, 'user20', 'user20@example.com', 'password20', 0, '2024-10-25 11:59:00.000000', 70);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `booking`
--

CREATE TABLE `booking` (
  `booking_id` int(11) NOT NULL,
  `adult_count` int(11) NOT NULL,
  `child_count` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `time` datetime(6) NOT NULL,
  `total_price` int(11) NOT NULL,
  `tour_time_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `booking_detail`
--

CREATE TABLE `booking_detail` (
  `booking_detail_id` int(11) NOT NULL,
  `detail` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `booking_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  `category_detail` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`category_id`, `category_name`, `category_detail`, `status`) VALUES
(1, 'Du lịch trong nước', '', 1),
(2, 'Du lịch ngoài nước', '', 1),
(3, 'Du lịch Châu Á', '', 1),
(4, 'Du lịch sale', '', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthday` datetime(6) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  `customer_rel_id` int(11) DEFAULT NULL,
  `phone_number` int(11) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `customer_type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `customer`
--

INSERT INTO `customer` (`customer_id`, `address`, `birthday`, `customer_name`, `sex`, `status`, `time`, `customer_rel_id`, `phone_number`, `account_id`, `customer_type`) VALUES
(1, 'Bình Tân', '2003-11-10 00:00:00.000000', 'Quang Linh', 1, 1, '2024-10-17 10:52:45.000000', NULL, 123456789, NULL, NULL),
(2, 'Tân Bình', '2003-11-11 00:00:00.000000', 'Quang Hải', 1, 1, '2024-10-17 10:52:45.000000', NULL, 123456789, NULL, NULL),
(3, 'Tân Phú', '2002-11-11 00:00:00.000000', 'Hải', 1, 1, '2024-10-17 10:52:45.000000', 1, 123456789, NULL, NULL),
(4, '123 Main St', '1990-01-01 00:00:00.000000', 'Customer One', 1, 1, '2024-10-25 09:00:00.000000', NULL, 123456789, NULL, NULL),
(5, '456 Elm St', '1991-02-02 00:00:00.000000', 'Customer Two', 0, 1, '2024-10-25 09:05:00.000000', NULL, 987654321, NULL, NULL),
(6, '789 Oak St', '1992-03-03 00:00:00.000000', 'Customer Three', 1, 1, '2024-10-25 09:10:00.000000', NULL, 123456789, NULL, NULL),
(7, '101 Maple St', '1989-04-04 00:00:00.000000', 'Customer Four', 0, 1, '2024-10-25 09:15:00.000000', NULL, 987123456, NULL, NULL),
(8, '202 Cedar St', '1993-05-05 00:00:00.000000', 'Customer Five', 1, 1, '2024-10-25 09:20:00.000000', NULL, 123789456, NULL, NULL),
(9, '303 Pine St', '1994-06-06 00:00:00.000000', 'Customer Six', 0, 1, '2024-10-25 09:25:00.000000', NULL, 987654320, NULL, NULL),
(10, '404 Birch St', '1995-07-07 00:00:00.000000', 'Customer Seven', 1, 1, '2024-10-25 09:30:00.000000', NULL, 123456701, NULL, NULL),
(11, '505 Walnut St', '1996-08-08 00:00:00.000000', 'Customer Eight', 0, 1, '2024-10-25 09:35:00.000000', NULL, 987345612, NULL, NULL),
(12, '606 Chestnut St', '1997-09-09 00:00:00.000000', 'Customer Nine', 1, 1, '2024-10-25 09:40:00.000000', NULL, 123654789, NULL, NULL),
(13, '707 Aspen St', '1998-10-10 00:00:00.000000', 'Customer Ten', 0, 1, '2024-10-25 09:45:00.000000', NULL, 987543210, NULL, NULL),
(14, '808 Cypress St', '1990-01-11 00:00:00.000000', 'Customer Eleven', 1, 1, '2024-10-25 09:50:00.000000', NULL, 123456782, NULL, NULL),
(15, '909 Spruce St', '1991-02-12 00:00:00.000000', 'Customer Twelve', 0, 1, '2024-10-25 09:55:00.000000', NULL, 987456312, NULL, NULL),
(16, '1001 Beech St', '1992-03-13 00:00:00.000000', 'Customer Thirteen', 1, 1, '2024-10-25 10:00:00.000000', NULL, 123456783, NULL, NULL),
(17, '1102 Fir St', '1993-04-14 00:00:00.000000', 'Customer Fourteen', 0, 1, '2024-10-25 10:05:00.000000', NULL, 987564321, NULL, NULL),
(18, '1203 Willow St', '1994-05-15 00:00:00.000000', 'Customer Fifteen', 1, 1, '2024-10-25 10:10:00.000000', NULL, 123456784, NULL, NULL),
(19, '1304 Poplar St', '1995-06-16 00:00:00.000000', 'Customer Sixteen', 0, 1, '2024-10-25 10:15:00.000000', NULL, 987234567, NULL, NULL),
(20, '1405 Redwood St', '1996-07-17 00:00:00.000000', 'Customer Seventeen', 1, 1, '2024-10-25 10:20:00.000000', NULL, 123345678, NULL, NULL),
(21, '1506 Mahogany St', '1997-08-18 00:00:00.000000', 'Customer Eighteen', 0, 1, '2024-10-25 10:25:00.000000', NULL, 987652345, NULL, NULL),
(22, '1607 Ash St', '1998-09-19 00:00:00.000000', 'Customer Nineteen', 1, 1, '2024-10-25 10:30:00.000000', NULL, 123478569, NULL, NULL),
(23, '1708 Elm St', '1999-10-20 00:00:00.000000', 'Customer Twenty', 0, 1, '2024-10-25 10:35:00.000000', NULL, 987451230, NULL, NULL),
(24, '1809 Maple St', '2000-11-21 00:00:00.000000', 'Customer Twenty-One', 1, 1, '2024-10-25 10:40:00.000000', NULL, 123456710, NULL, NULL),
(25, '1910 Pine St', '2001-12-22 00:00:00.000000', 'Customer Twenty-Two', 0, 1, '2024-10-25 10:45:00.000000', NULL, 987456321, NULL, NULL),
(26, '2011 Cedar St', '2002-01-23 00:00:00.000000', 'Customer Twenty-Three', 1, 1, '2024-10-25 10:50:00.000000', NULL, 123789457, NULL, NULL),
(27, '2112 Birch St', '2003-02-24 00:00:00.000000', 'Customer Twenty-Four', 0, 1, '2024-10-25 10:55:00.000000', NULL, 987345613, NULL, NULL),
(28, '2213 Walnut St', '2004-03-25 00:00:00.000000', 'Customer Twenty-Five', 1, 1, '2024-10-25 11:00:00.000000', NULL, 123654780, NULL, NULL),
(29, '2314 Chestnut St', '2005-04-26 00:00:00.000000', 'Customer Twenty-Six', 0, 1, '2024-10-25 11:05:00.000000', NULL, 987453219, NULL, NULL),
(30, '2415 Aspen St', '2006-05-27 00:00:00.000000', 'Customer Twenty-Seven', 1, 1, '2024-10-25 11:10:00.000000', NULL, 123456709, NULL, NULL),
(31, '2516 Cypress St', '2007-06-28 00:00:00.000000', 'Customer Twenty-Eight', 0, 1, '2024-10-25 11:15:00.000000', NULL, 987564322, NULL, NULL),
(32, '2617 Spruce St', '2008-07-29 00:00:00.000000', 'Customer Twenty-Nine', 1, 1, '2024-10-25 11:20:00.000000', NULL, 123456711, NULL, NULL),
(33, '2718 Beech St', '2009-08-30 00:00:00.000000', 'Customer Thirty', 0, 1, '2024-10-25 11:25:00.000000', NULL, 987652346, NULL, NULL),
(34, '2819 Fir St', '2010-09-01 00:00:00.000000', 'Customer Thirty-One', 1, 1, '2024-10-25 11:30:00.000000', NULL, 123478570, NULL, NULL),
(35, '2920 Willow St', '2011-10-02 00:00:00.000000', 'Customer Thirty-Two', 0, 1, '2024-10-25 11:35:00.000000', NULL, 987543211, NULL, NULL),
(36, '3021 Poplar St', '2012-11-03 00:00:00.000000', 'Customer Thirty-Three', 1, 1, '2024-10-25 11:40:00.000000', NULL, 123789468, NULL, NULL),
(37, '3122 Redwood St', '2013-12-04 00:00:00.000000', 'Customer Thirty-Four', 0, 1, '2024-10-25 11:45:00.000000', NULL, 987234568, NULL, NULL),
(38, '3223 Mahogany St', '2014-01-05 00:00:00.000000', 'Customer Thirty-Five', 1, 1, '2024-10-25 11:50:00.000000', NULL, 123456785, NULL, NULL),
(39, '3324 Ash St', '2015-02-06 00:00:00.000000', 'Customer Thirty-Six', 0, 1, '2024-10-25 11:55:00.000000', NULL, 987453212, NULL, NULL),
(40, '3425 Elm St', '2016-03-07 00:00:00.000000', 'Customer Thirty-Seven', 1, 1, '2024-10-25 12:00:00.000000', NULL, 123789455, NULL, NULL),
(41, '3526 Maple St', '2017-04-08 00:00:00.000000', 'Customer Thirty-Eight', 0, 1, '2024-10-25 12:05:00.000000', NULL, 987345670, NULL, NULL),
(42, '3627 Pine St', '2018-05-09 00:00:00.000000', 'Customer Thirty-Nine', 1, 1, '2024-10-25 12:10:00.000000', NULL, 123456786, NULL, NULL),
(43, '3728 Cedar St', '2019-06-10 00:00:00.000000', 'Customer Forty', 0, 1, '2024-10-25 12:15:00.000000', NULL, 987654323, NULL, NULL),
(44, '3829 Birch St', '2020-07-11 00:00:00.000000', 'Customer Forty-One', 1, 1, '2024-10-25 12:20:00.000000', NULL, 123478532, NULL, NULL),
(45, '3930 Walnut St', '2021-08-12 00:00:00.000000', 'Customer Forty-Two', 0, 1, '2024-10-25 12:25:00.000000', NULL, 987543208, NULL, NULL),
(46, '4031 Chestnut St', '2022-09-13 00:00:00.000000', 'Customer Forty-Three', 1, 1, '2024-10-25 12:30:00.000000', NULL, 123654790, NULL, NULL),
(47, '4132 Aspen St', '2023-10-14 00:00:00.000000', 'Customer Forty-Four', 0, 1, '2024-10-25 12:35:00.000000', NULL, 987654321, NULL, NULL),
(48, '4233 Maple St', '2024-11-15 00:00:00.000000', 'Customer Forty-Five', 1, 1, '2024-10-25 12:40:00.000000', NULL, 123789469, NULL, NULL),
(49, '4334 Pine St', '2025-01-16 00:00:00.000000', 'Customer Forty-Six', 0, 1, '2024-10-25 12:45:00.000000', NULL, 987654310, NULL, NULL),
(50, '4435 Cedar St', '2024-03-17 00:00:00.000000', 'Customer Forty-Seven', 1, 1, '2024-10-25 12:50:00.000000', NULL, 123654787, NULL, NULL),
(51, '4536 Birch St', '2023-05-18 00:00:00.000000', 'Customer Forty-Eight', 0, 1, '2024-10-25 12:55:00.000000', NULL, 987543291, NULL, NULL),
(52, '4637 Walnut St', '2022-07-19 00:00:00.000000', 'Customer Forty-Nine', 1, 1, '2024-10-25 13:00:00.000000', NULL, 123789456, NULL, NULL),
(53, '4738 Chestnut St', '2021-09-20 00:00:00.000000', 'Customer Fifty', 0, 1, '2024-10-25 13:05:00.000000', NULL, 987654324, NULL, NULL),
(54, '4839 Aspen St', '2020-11-21 00:00:00.000000', 'Customer Fifty-One', 1, 1, '2024-10-25 13:10:00.000000', NULL, 123456702, NULL, NULL),
(55, '4940 Cypress St', '2019-01-22 00:00:00.000000', 'Customer Fifty-Two', 0, 1, '2024-10-25 13:15:00.000000', NULL, 987453213, NULL, NULL),
(56, '5041 Spruce St', '2018-03-23 00:00:00.000000', 'Customer Fifty-Three', 1, 1, '2024-10-25 13:20:00.000000', NULL, 123654791, NULL, NULL),
(57, '5142 Beech St', '2017-05-24 00:00:00.000000', 'Customer Fifty-Four', 0, 1, '2024-10-25 13:25:00.000000', NULL, 987654312, NULL, NULL),
(58, '5243 Fir St', '2016-07-25 00:00:00.000000', 'Customer Fifty-Five', 1, 1, '2024-10-25 13:30:00.000000', NULL, 123789460, NULL, NULL),
(59, '5344 Willow St', '2015-09-26 00:00:00.000000', 'Customer Fifty-Six', 0, 1, '2024-10-25 13:35:00.000000', NULL, 987543214, NULL, NULL),
(60, '5445 Poplar St', '2014-11-27 00:00:00.000000', 'Customer Fifty-Seven', 1, 1, '2024-10-25 13:40:00.000000', NULL, 123456788, NULL, NULL),
(61, '5546 Redwood St', '2013-01-28 00:00:00.000000', 'Customer Fifty-Eight', 0, 1, '2024-10-25 13:45:00.000000', NULL, 987654325, NULL, NULL),
(62, '5647 Mahogany St', '2012-03-29 00:00:00.000000', 'Customer Fifty-Nine', 1, 1, '2024-10-25 13:50:00.000000', NULL, 123789461, NULL, NULL),
(63, '5748 Ash St', '2011-05-30 00:00:00.000000', 'Customer Sixty', 0, 1, '2024-10-25 13:55:00.000000', NULL, 987453215, NULL, NULL),
(64, '5849 Elm St', '2010-07-31 00:00:00.000000', 'Customer Sixty-One', 1, 1, '2024-10-25 14:00:00.000000', NULL, 123654792, NULL, NULL),
(65, '5950 Maple St', '2009-09-01 00:00:00.000000', 'Customer Sixty-Two', 0, 1, '2024-10-25 14:05:00.000000', NULL, 987654313, NULL, NULL),
(66, '6051 Pine St', '2008-11-02 00:00:00.000000', 'Customer Sixty-Three', 1, 1, '2024-10-25 14:10:00.000000', NULL, 123789462, NULL, NULL),
(67, '6152 Cedar St', '2007-01-03 00:00:00.000000', 'Customer Sixty-Four', 0, 1, '2024-10-25 14:15:00.000000', NULL, 987543216, NULL, NULL),
(68, '6253 Birch St', '2006-03-04 00:00:00.000000', 'Customer Sixty-Five', 1, 1, '2024-10-25 14:20:00.000000', NULL, 123456789, NULL, NULL),
(69, '6354 Walnut St', '2005-05-05 00:00:00.000000', 'Customer Sixty-Six', 0, 1, '2024-10-25 14:25:00.000000', NULL, 987654326, NULL, NULL),
(70, '6455 Chestnut St', '2004-07-06 00:00:00.000000', 'Customer Sixty-Seven', 1, 1, '2024-10-25 14:30:00.000000', NULL, 123789463, NULL, NULL),
(71, '6556 Aspen St', '2003-09-07 00:00:00.000000', 'Customer Sixty-Eight', 0, 1, '2024-10-25 14:35:00.000000', NULL, 987453217, NULL, NULL),
(72, '6657 Cypress St', '2002-11-08 00:00:00.000000', 'Customer Sixty-Nine', 1, 1, '2024-10-25 14:40:00.000000', NULL, 123654793, NULL, NULL),
(73, '6758 Spruce St', '2001-01-09 00:00:00.000000', 'Customer Seventy', 0, 1, '2024-10-25 14:45:00.000000', NULL, 987654327, NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `discount`
--

CREATE TABLE `discount` (
  `discount_id` int(11) NOT NULL,
  `discount_code` varchar(255) NOT NULL,
  `discount_value` int(11) NOT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `discount`
--

INSERT INTO `discount` (`discount_id`, `discount_code`, `discount_value`, `end_date`, `note`, `start_date`, `status`) VALUES
(1, 'QWE23D', 2000000, NULL, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tour`
--

CREATE TABLE `tour` (
  `tour_id` int(11) NOT NULL,
  `tour_stay` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `tour_code` varchar(255) DEFAULT NULL,
  `tour_detail` varchar(255) DEFAULT NULL,
  `tour_name` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tour`
--

INSERT INTO `tour` (`tour_id`, `tour_stay`, `status`, `tour_code`, `tour_detail`, `tour_name`, `category_id`) VALUES
(1, '3', 1, 'S999G01', 'fsghjhkkl', 'Bà nà', 1),
(3, '3', 0, 'S999G01', '', 'Bà nà', 1),
(4, '4', 0, 'S999G02', '', 'Thái Lan', 2),
(5, '5', 0, 'S999G03', '', 'Du lịch Tượng Nữ Thần Tự Do', 2),
(6, '6', 1, 'S999G04', '', 'Du lịch Mỹ', 2),
(7, '7', 0, 'S999G05', '', 'Du lịch Thành Phố Hồ Chí Minh', 1),
(8, '8', 0, 'S999G07', '', 'Du lịch Hà Nội', 1),
(9, '9', 1, 'S999G06', '', 'Du lịch Quản Trị', 1),
(10, '2', 1, 'S999G08', '', 'Du lịch Đông Hà', 1),
(11, '4', 1, 'S999G10', 'dưa', 'Dương', 1),
(12, '3', 1, 'S999G11', '', 'Tour du lịch Đà Nẵng', 1),
(13, '6', 1, 'S999G12', '', 'Du lịch Nam Định', 1),
(14, '7', 0, 'S999G13', '', 'Du lịch Tây Bắc', 1),
(15, '1', 1, '1', '1', '1', 3),
(16, '1', 1, '1', '1', '1', 3),
(17, '1', 1, 'sdfgh', '1fdgghj', '1', 3),
(18, '1', 1, '11111', '1', '1', 3),
(19, '1', 1, '11', '1', '1', 2),
(20, '11', 1, 'qqqqqq', 'q', 'q', 2),
(21, '3', 0, 'hhhhhh', 'hhhhhhhhhh', 'hhhhhh', 2),
(22, '3', 1, 'szdfc', 'dứagdhfjg', '1', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tour_discount`
--

CREATE TABLE `tour_discount` (
  `tour_time_id` int(11) NOT NULL,
  `discount_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tour_discount`
--

INSERT INTO `tour_discount` (`tour_time_id`, `discount_id`) VALUES
(1, 1),
(3, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tour_image`
--

CREATE TABLE `tour_image` (
  `image_id` int(11) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `tour_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tour_time`
--

CREATE TABLE `tour_time` (
  `tour_time_id` int(11) NOT NULL,
  `departure_time` datetime(6) NOT NULL,
  `note` varchar(255) NOT NULL,
  `price_adult` int(11) NOT NULL,
  `price_child` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `return_time` datetime(6) NOT NULL,
  `status` int(11) NOT NULL,
  `time_name` varchar(255) NOT NULL,
  `tour_time_code` varchar(255) NOT NULL,
  `tour_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tour_time`
--

INSERT INTO `tour_time` (`tour_time_id`, `departure_time`, `note`, `price_adult`, `price_child`, `quantity`, `return_time`, `status`, `time_name`, `tour_time_code`, `tour_id`) VALUES
(1, '2024-01-02 21:49:32.000000', 'sadaaaaaaaaaaaaaaaaaaaaaaaaaaa', 20000000, 15000000, 14, '2024-10-21 21:49:32.000000', 1, 'Tour Đi Thành Cổ tháng 1', 'S999G09_1', 1),
(2, '2024-01-02 21:49:32.000000', '23423412sdddddddddddddddddddddddddddddddddddddddddddddddddddd', 20000000, 15000000, 14, '2024-10-09 03:09:14.000000', 1, 'Tour Đi Thành Cổ tháng 2', 'S999G09_2', 1),
(3, '2024-10-27 21:49:32.000000', 'Tour Cần nhiều khách ủng hộ', 21000000, 16000000, 14, '2024-10-07 03:09:18.000000', 1, 'Tour Đi Thành Cổ tháng 3', 'S999G09_3', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `transport`
--

CREATE TABLE `transport` (
  `transport_id` int(11) NOT NULL,
  `departure_location` varchar(255) NOT NULL,
  `destination_location` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `transport_code` varchar(255) NOT NULL,
  `transport_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `transport`
--

INSERT INTO `transport` (`transport_id`, `departure_location`, `destination_location`, `status`, `transport_code`, `transport_name`) VALUES
(1, 'SB Tân Sơn Nhất', 'SB Phú Bài', 1, 'VJ305', 'Vietjet'),
(2, 'SB Phú Bài', 'SB Tân Sơn Nhất', 1, 'VJ306', 'Vietjet'),
(3, 'SB Phú Quốc', 'SB Tân Sơn Nhất', 1, 'VN204', 'Vietnam airline'),
(4, 'SB Nội Bài', 'SB Tân Sơn Nhất', 1, 'VN407', 'Vietnam airline'),
(5, 'BX Miền Tây', 'BX Biên Hòa', 1, 'XTB', 'Xe Trần Biện'),
(6, 'BX Đà Nẵng', 'BX Mỹ Đình', 1, 'XQD', 'Xe Quang Dũng'),
(7, 'BX Đà Nẵng', 'BX Bãi Cháy', 1, 'XQD', 'Xe Quang Dũng'),
(8, 'SB Phú Bài', 'SB Nội Bài', 1, 'VJ505', 'Vietjet'),
(9, 'BX Mỹ Đình', 'BX Bãi Cháy', 1, 'XQT', 'Xe Quỳnh Thi'),
(10, 'SB Tân Sơn Nhất', 'SB Nội Bài', 1, 'VN704', 'Vietnam airline'),
(11, 'SB Nội Bài', 'SB Phú Quốc', 1, 'VJ456', 'Vietjet'),
(12, 'BX Biên Hòa', 'BX Bãi Cháy', 1, 'XQT', 'Xe Quỳnh Thi'),
(13, 'BX Mỹ Đình', 'BX Đà Nẵng', 1, 'XQT', 'Xe Quỳnh Thi'),
(14, 'BX Lộc Nga', 'BX Miền Tây', 1, 'XPT', 'Xe Quỳnh Thi');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `transport_detail`
--

CREATE TABLE `transport_detail` (
  `transport_detail_id` int(11) NOT NULL,
  `arrival_time` datetime(6) DEFAULT NULL,
  `departure_time` datetime(6) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `tour_time_id` int(11) NOT NULL,
  `transport_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `transport_detail`
--

INSERT INTO `transport_detail` (`transport_detail_id`, `arrival_time`, `departure_time`, `status`, `tour_time_id`, `transport_id`) VALUES
(1, '2024-10-17 17:47:58.000000', '2024-10-16 17:47:58.000000', 1, 1, 9),
(2, '2024-10-19 17:48:54.000000', '2024-10-17 17:48:54.000000', 1, 2, 7),
(3, '2024-10-19 17:49:15.000000', '2024-10-18 17:49:15.000000', 1, 1, 4),
(4, '2024-10-18 00:14:18.000000', '2024-10-15 00:14:18.000000', 2, 1, 2),
(5, '2024-10-19 00:14:38.000000', '2024-10-17 00:14:38.000000', 2, 1, 5);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_id`),
  ADD KEY `customerID` (`customerID`);

--
-- Chỉ mục cho bảng `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `FK63w6e91icx2v6u7oko1gtn0q3` (`tour_time_id`),
  ADD KEY `FKlnnelfsha11xmo2ndjq66fvro` (`customer_id`);

--
-- Chỉ mục cho bảng `booking_detail`
--
ALTER TABLE `booking_detail`
  ADD PRIMARY KEY (`booking_detail_id`),
  ADD KEY `FK59941ondg9nwaifm2umnrduev` (`booking_id`),
  ADD KEY `FKo10xk8g7n012j2n9y6w0laeel` (`customer_id`);

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Chỉ mục cho bảng `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `UKod6gs5c5g422iwnfkh4iiifkw` (`customer_rel_id`),
  ADD UNIQUE KEY `UKjwt2qo9oj3wd7ribjkymryp8s` (`account_id`);

--
-- Chỉ mục cho bảng `discount`
--
ALTER TABLE `discount`
  ADD PRIMARY KEY (`discount_id`);

--
-- Chỉ mục cho bảng `tour`
--
ALTER TABLE `tour`
  ADD PRIMARY KEY (`tour_id`),
  ADD KEY `FKfi3if9cr2rk7llxvky6boui8h` (`category_id`);

--
-- Chỉ mục cho bảng `tour_discount`
--
ALTER TABLE `tour_discount`
  ADD PRIMARY KEY (`tour_time_id`,`discount_id`),
  ADD KEY `FK3skwy53j8hn9t61p0t9w27x4t` (`discount_id`);

--
-- Chỉ mục cho bảng `tour_image`
--
ALTER TABLE `tour_image`
  ADD PRIMARY KEY (`image_id`),
  ADD KEY `FKe829px6ivuaq4s9chmdn2wree` (`tour_id`);

--
-- Chỉ mục cho bảng `tour_time`
--
ALTER TABLE `tour_time`
  ADD PRIMARY KEY (`tour_time_id`),
  ADD KEY `FKm9q84iyk48xyc9x947ojbcdfd` (`tour_id`);

--
-- Chỉ mục cho bảng `transport`
--
ALTER TABLE `transport`
  ADD PRIMARY KEY (`transport_id`);

--
-- Chỉ mục cho bảng `transport_detail`
--
ALTER TABLE `transport_detail`
  ADD PRIMARY KEY (`transport_detail_id`),
  ADD KEY `FKp60iq59r9npt32jfg4qwfo32o` (`tour_time_id`),
  ADD KEY `FKtcqafwh2f5qda0th3dh3yqgml` (`transport_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `account`
--
ALTER TABLE `account`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=80;

--
-- AUTO_INCREMENT cho bảng `booking`
--
ALTER TABLE `booking`
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT cho bảng `discount`
--
ALTER TABLE `discount`
  MODIFY `discount_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `tour`
--
ALTER TABLE `tour`
  MODIFY `tour_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT cho bảng `tour_image`
--
ALTER TABLE `tour_image`
  MODIFY `image_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tour_time`
--
ALTER TABLE `tour_time`
  MODIFY `tour_time_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `transport`
--
ALTER TABLE `transport`
  MODIFY `transport_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `transport_detail`
--
ALTER TABLE `transport_detail`
  MODIFY `transport_detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customer_id`);

--
-- Các ràng buộc cho bảng `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FK63w6e91icx2v6u7oko1gtn0q3` FOREIGN KEY (`tour_time_id`) REFERENCES `tour_time` (`tour_time_id`),
  ADD CONSTRAINT `FKlnnelfsha11xmo2ndjq66fvro` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_rel_id`);

--
-- Các ràng buộc cho bảng `booking_detail`
--
ALTER TABLE `booking_detail`
  ADD CONSTRAINT `FK59941ondg9nwaifm2umnrduev` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`),
  ADD CONSTRAINT `FKo10xk8g7n012j2n9y6w0laeel` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`);

--
-- Các ràng buộc cho bảng `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `FKkdgk9jkvgwwit7uxkew7amrnh` FOREIGN KEY (`customer_rel_id`) REFERENCES `customer` (`customer_id`),
  ADD CONSTRAINT `FKn9x2k8svpxj3r328iy1rpur83` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`);

--
-- Các ràng buộc cho bảng `tour`
--
ALTER TABLE `tour`
  ADD CONSTRAINT `FKfi3if9cr2rk7llxvky6boui8h` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`);

--
-- Các ràng buộc cho bảng `tour_discount`
--
ALTER TABLE `tour_discount`
  ADD CONSTRAINT `FK3skwy53j8hn9t61p0t9w27x4t` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`discount_id`),
  ADD CONSTRAINT `FKcobwl7ebq1qefcjxl75wu16mw` FOREIGN KEY (`tour_time_id`) REFERENCES `tour_time` (`tour_time_id`);

--
-- Các ràng buộc cho bảng `tour_image`
--
ALTER TABLE `tour_image`
  ADD CONSTRAINT `FKe829px6ivuaq4s9chmdn2wree` FOREIGN KEY (`tour_id`) REFERENCES `tour` (`tour_id`);

--
-- Các ràng buộc cho bảng `tour_time`
--
ALTER TABLE `tour_time`
  ADD CONSTRAINT `FKm9q84iyk48xyc9x947ojbcdfd` FOREIGN KEY (`tour_id`) REFERENCES `tour` (`tour_id`);

--
-- Các ràng buộc cho bảng `transport_detail`
--
ALTER TABLE `transport_detail`
  ADD CONSTRAINT `FKp60iq59r9npt32jfg4qwfo32o` FOREIGN KEY (`tour_time_id`) REFERENCES `tour_time` (`tour_time_id`),
  ADD CONSTRAINT `FKtcqafwh2f5qda0th3dh3yqgml` FOREIGN KEY (`transport_id`) REFERENCES `transport` (`transport_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
