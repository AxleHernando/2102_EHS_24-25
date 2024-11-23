-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 23, 2024 at 08:13 AM
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
(6, 7, 80000.00, '2024-11-23 14:09:07'),
(7, 9, 100000.00, '2024-11-23 14:13:18'),
(8, 7, 30000.00, '2024-11-23 14:45:16'),
(9, 9, 30000.00, '2024-11-23 14:45:29'),
(10, 10, 240000.00, '2024-11-23 15:08:21');

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
  `ModeOfPayment` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`OrderID`, `UserID`, `ProductID`, `Quantity`, `OrderDate`, `Price`, `ModeOfPayment`) VALUES
(33, 7, 2, 1, '2024-11-23 06:08:25', 40000.00, 'Cash On Delivery'),
(34, 7, 2, 1, '2024-11-23 06:09:06', 40000.00, 'Cash On Delivery'),
(35, 9, 4, 1, '2024-11-23 06:13:17', 20000.00, 'Cash On Delivery'),
(36, 9, 6, 2, '2024-11-23 06:13:17', 30000.00, 'Cash On Delivery'),
(37, 7, 6, 1, '2024-11-23 06:45:15', 15000.00, 'Cash On Delivery'),
(38, 9, 6, 1, '2024-11-23 06:45:28', 15000.00, 'Cash On Delivery'),
(39, 10, 6, 2, '2024-11-23 07:08:20', 30000.00, 'Cash On Delivery'),
(40, 10, 3, 3, '2024-11-23 07:08:20', 90000.00, 'Cash On Delivery');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ProductID` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Price` decimal(10,2) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `Stocks` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `Name`, `Description`, `Price`, `UserID`, `Stocks`) VALUES
(1, 'Laptop', 'High-End Gaming Laptop for Gamers', 50000.00, 6, 10),
(2, 'iPhone', 'Newly Launched High-End Mobile Phone', 40000.00, 6, 10),
(3, 'PlayStation 5', 'Next-Gen Gaming Console', 30000.00, 6, 10),
(4, 'Nintendo Switch', 'A Versatile Gaming Console', 20000.00, 6, 10),
(6, 'AirPods', 'Wireless Bluetooth EarBuds designed by Apple', 15000.00, 6, 10);

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `SaleID` int(11) NOT NULL,
  `ProductID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `TotalPrice` decimal(10,2) NOT NULL,
  `SaleDate` datetime NOT NULL,
  `UserID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`SaleID`, `ProductID`, `Quantity`, `TotalPrice`, `SaleDate`, `UserID`) VALUES
(1, 2, 1, 40000.00, '2024-11-23 14:09:06', 7),
(2, 4, 1, 20000.00, '2024-11-23 14:13:17', 9),
(3, 6, 2, 30000.00, '2024-11-23 14:13:17', 9),
(4, 6, 1, 15000.00, '2024-11-23 14:45:15', 7),
(5, 6, 1, 15000.00, '2024-11-23 14:45:28', 9),
(6, 6, 2, 30000.00, '2024-11-23 15:08:20', 10),
(7, 3, 3, 90000.00, '2024-11-23 15:08:20', 10);

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
(10, 'kristinegandaa', 'kristine03', 'Customer', '2024-11-23 07:07:32', 'kristine de torres');

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
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`SaleID`),
  ADD KEY `ProductID` (`ProductID`),
  ADD KEY `UserID` (`UserID`);

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
  MODIFY `CardID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `cashpayment`
--
ALTER TABLE `cashpayment`
  MODIFY `CashID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `OrderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `ProductID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `SaleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

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

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`ProductID`) REFERENCES `products` (`ProductID`),
  ADD CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
