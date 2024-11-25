-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 25, 2024 at 06:19 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `2102_ehs_2425`
--

-- --------------------------------------------------------

--
-- Table structure for table `cardpayment`
--

CREATE TABLE `cardpayment` (
  `CardID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `CardNo` varchar(16) NOT NULL,
  `CVC` varchar(4) NOT NULL,
  `CardHolder` varchar(100) NOT NULL,
  `Expiry` char(5) NOT NULL,
  `Payment` decimal(10,2) NOT NULL,
  `TransactionDate` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cardpayment`
--

INSERT INTO `cardpayment` (`CardID`, `UserID`, `CardNo`, `CVC`, `CardHolder`, `Expiry`, `Payment`, `TransactionDate`) VALUES
(1, 14, '1234567891234567', '123', 'Bimbi', '11/27', 220000.00, '2024-11-24 14:00:17'),
(2, 7, '1111222233334444', '123', 'Axle Hernando', '11/25', 1600.00, '2024-11-25 13:06:13');

-- --------------------------------------------------------

--
-- Table structure for table `cashpayment`
--

CREATE TABLE `cashpayment` (
  `CashID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `CashTendered` decimal(10,2) DEFAULT NULL,
  `TransactionDate` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cashpayment`
--

INSERT INTO `cashpayment` (`CashID`, `UserID`, `CashTendered`, `TransactionDate`) VALUES
(1, 12, 240000.00, '2024-11-24 16:06:05'),
(2, 12, 120.00, '2024-11-25 12:48:19'),
(3, 7, 300.00, '2024-11-25 13:06:26'),
(4, 9, 860.00, '2024-11-25 13:06:56');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `OrderID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `ProductID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `OrderDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `Price` decimal(10,2) NOT NULL,
  `ModeOfPayment` varchar(100) NOT NULL,
  `Category` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`OrderID`, `UserID`, `ProductID`, `Quantity`, `OrderDate`, `Price`, `ModeOfPayment`, `Category`) VALUES
(1, 12, 20, 2, '2024-11-25 04:48:18', 60.00, 'Cash On Delivery', 'Hygiene'),
(2, 7, 23, 4, '2024-11-25 05:06:14', 1600.00, 'Card Payment', 'Clothes'),
(3, 7, 26, 1, '2024-11-25 05:06:25', 150.00, 'Cash On Delivery', 'Clothes'),
(4, 9, 32, 2, '2024-11-25 05:06:55', 400.00, 'Cash On Delivery', 'Kitchen'),
(5, 9, 20, 1, '2024-11-25 05:06:55', 30.00, 'Cash On Delivery', 'Hygiene');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ProductID` int(11) NOT NULL,
  `Category` varchar(100) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Price` decimal(10,2) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `Stocks` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `Category`, `Name`, `Description`, `Price`, `UserID`, `Stocks`) VALUES
(1, 'Gadgets', 'Laptop', 'High-End Gaming Laptop for Gamers', 50000.00, 6, 10),
(2, 'Gadgets', 'iPhone', 'Newly Launched High-End Mobile Phone', 40000.00, 6, 10),
(3, 'Gadgets', 'PlayStation 5', 'Next-Gen Gaming Console', 30000.00, 6, 10),
(4, 'Gadgets', 'Nintendo Switch', 'A Versatile Gaming Console', 20000.00, 6, 10),
(6, 'Gadgets', 'AirPods', 'Wireless Bluetooth EarBuds designed by Apple', 15000.00, 6, 10),
(14, 'Hygiene', 'Tissue', 'All Around Hygienic Tissue', 20.00, 15, 200),
(18, 'Hygiene', 'Toothbrush ', 'Soft-Bristled For Gentle Cleaning', 30.00, 15, 500),
(19, 'Hygiene', 'Comb', 'Suitable For All Hair Types', 50.00, 15, 4000),
(20, 'Hygiene', 'Soap', 'Moisturizing And Nourishing', 30.00, 15, 300),
(21, 'Hygiene', 'Nailcutter', 'Maintain Clean And Healthy Nails', 45.00, 15, 300),
(22, 'Clothes', 'Coats', 'Stay Warm And Stylish', 500.00, 16, 1000),
(23, 'Clothes', 'T-Shirts', 'Soft, Breathable Fabrics', 400.00, 16, 5000),
(24, 'Clothes', 'Shorts', 'Comfort In Every Stitch', 450.00, 16, 5000),
(25, 'Clothes', 'Underwear', 'Variety Of Styles And Size', 300.00, 16, 7000),
(26, 'Clothes', 'Leggings', 'Soft And Seamless Construction', 150.00, 16, 4000),
(27, 'Appliances', 'Refrigerator', 'Rapid Cooling Technology ', 1000.00, 17, 15000),
(28, 'Appliances', 'Washing Machine', 'Stain Free, Stress Free', 1400.00, 17, 1200),
(29, 'Appliances', 'Vacuum Cleaner', 'Powerful Suction, Quiet Operation', 1500.00, 17, 3000),
(30, 'Appliances', 'Air Conditioning', 'Cooling Comfort, Effortless Convience', 30000.00, 17, 500),
(31, 'Appliances', 'Televisions', 'Experience Stunning Picture Quality ', 30000.00, 17, 2000),
(32, 'Kitchen', 'Pots', 'Cook With Confindence Everytime', 200.00, 18, 500),
(33, 'Kitchen', 'Whisk ', 'Mix, Blend, And Stir With Ease', 100.00, 18, 500),
(34, 'Kitchen', 'Graters', 'Precision Grating For Home Cooks', 400.00, 18, 300),
(35, 'Kitchen', 'Canisters', 'Store, Organize And Preserve', 300.00, 18, 20),
(36, 'Kitchen', 'Bowls', 'Versatile And Durable', 200.00, 18, 100);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` int(11) NOT NULL,
  `Username` varchar(100) DEFAULT NULL,
  `Password` varchar(100) DEFAULT NULL,
  `Role` varchar(100) DEFAULT NULL,
  `CreatedAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `FullName` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `Username`, `Password`, `Role`, `CreatedAt`, `FullName`) VALUES
(6, 'linuxadona', 'admin123', 'Admin', '2024-11-20 10:53:58', 'Linux Mandrake Adona'),
(7, 'axlehernando', 'cust123', 'Customer', '2024-11-20 10:56:36', 'Axle Hernando'),
(9, 'kayemacalalad', '12345678', 'Customer', '2024-11-23 06:13:03', 'Kaye Macalalad'),
(10, 'kristinegandaa', 'kristine03', 'Customer', '2024-11-23 07:07:32', 'kristine de torres'),
(11, 'felmaneleponga', 'felman123', 'Customer', '2024-11-24 05:50:57', 'Felman Eleponga'),
(12, 'aeron', 'aeron123', 'Customer', '2024-11-24 05:53:05', 'Aeron Salanguit'),
(13, 'millanabrenica', 'millan123', 'Admin', '2024-11-24 05:57:13', 'Millan Bimbi'),
(14, 'millanabrenics', 'millan123', 'Customer', '2024-11-24 05:57:43', 'millan bimbi'),
(15, 'xyreel', 'xy123', 'Admin', '2024-11-24 08:26:01', 'Xyreel Laguras'),
(16, 'rome', 'rome123', 'Admin', '2024-11-24 08:54:24', 'Rome Dyanne Salvid'),
(17, 'Maricris', 'mari123', 'Admin', '2024-11-24 09:07:04', 'Maricris Barcelon'),
(18, 'Djanisse', 'dj123', 'Admin', '2024-11-24 09:21:02', 'Djanisse Villafor');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cardpayment`
--
ALTER TABLE `cardpayment`
  ADD PRIMARY KEY (`CardID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `cashpayment`
--
ALTER TABLE `cashpayment`
  ADD PRIMARY KEY (`CashID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`OrderID`),
  ADD KEY `CustomerID` (`UserID`),
  ADD KEY `ProductID` (`ProductID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ProductID`),
  ADD KEY `SupplierID` (`UserID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cardpayment`
--
ALTER TABLE `cardpayment`
  MODIFY `CardID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `cashpayment`
--
ALTER TABLE `cashpayment`
  MODIFY `CashID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `OrderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `ProductID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cardpayment`
--
ALTER TABLE `cardpayment`
  ADD CONSTRAINT `cardpayment_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);

--
-- Constraints for table `cashpayment`
--
ALTER TABLE `cashpayment`
  ADD CONSTRAINT `cashpayment_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `products` (`ProductID`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
