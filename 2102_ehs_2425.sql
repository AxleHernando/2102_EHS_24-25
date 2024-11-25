-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 25, 2024 at 01:29 PM
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
(1, 20, '3726459183721947', '123', 'Jaydee', '09/11', 50400.00, '2024-11-25 19:46:54');

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
(1, 12, 50500.00, '2024-11-25 19:28:49'),
(2, 12, 0.00, '2024-11-25 19:30:02');

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
(1, 12, 1, 1, '2024-11-25 11:30:02', 50000.00, 'Cash On Delivery', 'Gadgets'),
(2, 12, 37, 1, '2024-11-25 11:30:02', 500.00, 'Cash On Delivery', 'Gaming'),
(3, 20, 14, 5, '2024-11-25 11:46:55', 100.00, 'Card Payment', 'Hygiene'),
(4, 20, 1, 1, '2024-11-25 11:46:55', 50000.00, 'Card Payment', 'Gadgets'),
(5, 20, 25, 1, '2024-11-25 11:46:56', 300.00, 'Card Payment', 'Clothes');

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
  `Stocks` int(11) DEFAULT NULL,
  `SupplierName` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `Category`, `Name`, `Description`, `Price`, `Stocks`, `SupplierName`) VALUES
(1, 'Gadgets', 'Laptop', 'High-End Gaming Laptop for Gamers', 50000.00, 8, 'Linux Adona'),
(2, 'Gadgets', 'iPhone', 'Newly Launched High-End Mobile Phone', 40000.00, 10, 'Linux Adona'),
(3, 'Gadgets', 'PlayStation 5', 'Next-Gen Gaming Console', 30000.00, 10, 'Linux Adona'),
(4, 'Gadgets', 'Nintendo Switch', 'A Versatile Gaming Console', 20000.00, 10, 'Linux Adona'),
(14, 'Hygiene', 'Tissue', 'All Around Hygienic Tissue', 20.00, 195, 'Xyreel Laguras'),
(18, 'Hygiene', 'Toothbrush ', 'Soft-Bristled For Gentle Cleaning', 30.00, 500, 'Xyreel Laguras'),
(19, 'Hygiene', 'Comb', 'Suitable For All Hair Types', 50.00, 4000, 'Xyreel Laguras'),
(20, 'Hygiene', 'Soap', 'Moisturizing And Nourishing', 30.00, 300, 'Xyreel Laguras'),
(21, 'Hygiene', 'Nailcutter', 'Maintain Clean And Healthy Nails', 45.00, 300, 'Xyreel Laguras'),
(22, 'Clothes', 'Coats', 'Stay Warm And Stylish', 500.00, 1000, 'Rome Dyanne Salvid'),
(23, 'Clothes', 'T-Shirts', 'Soft, Breathable Fabrics', 400.00, 5000, 'Rome Dyanne Salvid'),
(24, 'Clothes', 'Shorts', 'Comfort In Every Stitch', 450.00, 5000, 'Rome Dyanne Salvid'),
(25, 'Clothes', 'Underwear', 'Variety Of Styles And Size', 300.00, 6999, 'Rome Dyanne Salvid'),
(26, 'Clothes', 'Leggings', 'Soft And Seamless Construction', 150.00, 4000, 'Rome Dyanne Salvid'),
(27, 'Appliances', 'Refrigerator', 'Rapid Cooling Technology ', 1000.00, 15000, 'Maricris Barcelon'),
(28, 'Appliances', 'Washing Machine', 'Stain Free, Stress Free', 1400.00, 1200, 'Jan Emmanuel Formentos'),
(29, 'Appliances', 'Vacuum Cleaner', 'Powerful Suction, Quiet Operation', 1500.00, 3000, 'Maricris Barcelon'),
(30, 'Appliances', 'Air Conditioning', 'Cooling Comfort, Effortless Convience', 30000.00, 500, 'Maricris Barcelon'),
(31, 'Appliances', 'Televisions', 'Experience Stunning Picture Quality ', 30000.00, 2000, 'Maricris Barcelon'),
(32, 'Kitchen', 'Pots', 'Cook With Confindence Everytime', 200.00, 500, 'Djanisse Villaflor'),
(33, 'Kitchen', 'Whisk ', 'Mix, Blend, And Stir With Ease', 100.00, 500, 'Djanisse Villaflor'),
(34, 'Kitchen', 'Graters', 'Precision Grating For Home Cooks', 400.00, 300, 'Djanisse Villaflor'),
(35, 'Kitchen', 'Canisters', 'Store, Organize And Preserve', 300.00, 20, 'Djanisse Villaflor'),
(36, 'Kitchen', 'Bowls', 'Versatile And Durable', 200.00, 100, 'Djanisse Villaflor'),
(37, 'Gaming', 'Keyboard', 'Fast Typing for Gamers', 500.00, 29, 'Hanns Donor');

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
(19, 'linuxadona', 'admin123', 'Admin', '2024-11-25 11:34:12', 'Linux Adona'),
(20, 'Jaydee', 'jaydee123', 'Customer', '2024-11-25 11:41:19', 'John Dave Briones'),
(21, 'axlehernando', 'axle123', 'Customer', '2024-11-25 12:26:34', 'Axle Hernando');

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
  ADD PRIMARY KEY (`ProductID`);

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
  MODIFY `CardID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `cashpayment`
--
ALTER TABLE `cashpayment`
  MODIFY `CashID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `OrderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `ProductID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
