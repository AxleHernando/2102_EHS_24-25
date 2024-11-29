-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 29, 2024 at 03:19 AM
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
(1, 25, '1234567890123456', '123', 'Jan Emmanuel Formentos', '12/25', 400.00, '2024-11-28 12:15:30'),
(2, 20, '9876543210987654', '456', 'John Dave Briones', '11/26', 300.00, '2024-11-24 16:00:00'),
(3, 25, '1111222233334444', '789', 'Jan Emmanuel Formentos', '01/27', 400.00, '2024-11-22 18:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `cashpayment`
--

CREATE TABLE `cashpayment` (
  `CashID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `CashTendered` decimal(10,2) DEFAULT NULL,
  `AmountChange` decimal(10,2) DEFAULT NULL,
  `TransactionDate` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cashpayment`
--

INSERT INTO `cashpayment` (`CashID`, `UserID`, `CashTendered`, `AmountChange`, `TransactionDate`) VALUES
(1, 22, 500.00, 70.00, '2024-11-29 00:50:42'),
(2, 25, 100.00, 10.00, '2024-11-27 14:45:20'),
(3, 21, 50.00, 5.00, '2024-11-25 10:10:50'),
(4, 22, 100.00, 0.00, '2024-11-23 09:20:05'),
(5, 21, 100.00, 20.00, '2024-11-21 14:15:30'),
(6, 25, 100.00, 10.00, '2024-11-27 14:45:20'),
(7, 21, 50.00, 5.00, '2024-11-25 10:10:50'),
(8, 22, 100.00, 0.00, '2024-11-23 09:20:05'),
(9, 21, 100.00, 20.00, '2024-11-21 14:15:30'),
(10, 25, 100.00, 10.00, '2024-11-27 14:45:20'),
(11, 21, 50.00, 5.00, '2024-11-25 10:10:50'),
(12, 22, 100.00, 0.00, '2024-11-23 09:20:05'),
(13, 21, 100.00, 20.00, '2024-11-21 14:15:30'),
(14, 26, 5050.00, 30.00, '2024-11-29 09:16:08');

-- --------------------------------------------------------

--
-- Table structure for table `deleted_products`
--

CREATE TABLE `deleted_products` (
  `DeletedID` int(11) NOT NULL,
  `ProductID` int(11) DEFAULT NULL,
  `Category` varchar(255) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Description` varchar(5000) DEFAULT NULL,
  `Price` decimal(10,2) DEFAULT NULL,
  `Stocks` int(11) DEFAULT NULL,
  `SupplierName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orderhistory`
--

CREATE TABLE `orderhistory` (
  `OrderHistoryID` int(11) NOT NULL,
  `OrderID` int(11) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Products` varchar(255) DEFAULT NULL,
  `Qty` int(11) DEFAULT NULL,
  `Price` decimal(10,2) DEFAULT NULL,
  `Payment` varchar(255) DEFAULT NULL,
  `Date` date DEFAULT current_timestamp(),
  `Time` time DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orderhistory`
--

INSERT INTO `orderhistory` (`OrderHistoryID`, `OrderID`, `Name`, `Products`, `Qty`, `Price`, `Payment`, `Date`, `Time`) VALUES
(1, 1, 'John Eduard De Villa', 'Toothbrush', 1, 30.00, 'Cash', '2024-11-29', '00:50:41'),
(2, 2, 'John Eduard De Villa', 'T-Shirts', 1, 400.00, 'Cash', '2024-11-29', '00:50:41'),
(3, 11, 'Jan Emmanuel Formentos', 'Mouse', 1, 400.00, 'Card', '2024-11-28', '12:15:30'),
(4, 12, 'Jan Emmanuel Formentos', 'Toothbrush', 3, 90.00, 'Cash', '2024-11-27', '14:45:20'),
(5, 13, 'John Dave Briones', 'Leggings', 2, 300.00, 'Card', '2024-11-26', '08:30:15'),
(6, 14, 'Axle Hernando', 'Nailcutter', 1, 45.00, 'Cash', '2024-11-25', '10:10:50'),
(7, 15, 'John Dave Briones', 'Nintendo Switch', 1, 20000.00, 'Card', '2024-11-24', '16:00:00'),
(8, 16, 'John Eduard De Villa', 'Whisk', 1, 100.00, 'Cash', '2024-11-23', '09:20:05'),
(9, 17, 'Jan Emmanuel Formentos', 'T-Shirts', 1, 400.00, 'Card', '2024-11-22', '18:30:00'),
(10, 18, 'Axle Hernando', 'Tissues', 4, 80.00, 'Cash', '2024-11-21', '14:15:30'),
(11, 20, 'Yno ', 'Laptop', 1, 5000.00, 'Cash', '2024-11-29', '09:16:06'),
(12, 21, 'Yno ', 'Tissues', 1, 20.00, 'Cash', '2024-11-29', '09:16:06');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `OrderID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `ProductID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Date` date NOT NULL DEFAULT current_timestamp(),
  `Time` time DEFAULT NULL,
  `Price` decimal(10,2) NOT NULL,
  `ModeOfPayment` varchar(100) NOT NULL,
  `Category` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`OrderID`, `UserID`, `ProductID`, `Quantity`, `Date`, `Time`, `Price`, `ModeOfPayment`, `Category`) VALUES
(1, 22, 18, 1, '2024-11-29', '00:50:41', 30.00, 'Cash', 'Hygiene'),
(2, 22, 23, 1, '2024-11-29', '00:50:41', 400.00, 'Cash', 'Clothes'),
(11, 25, 46, 1, '2024-11-28', '12:15:30', 400.00, 'Card', 'Gaming'),
(12, 25, 18, 3, '2024-11-27', '14:45:20', 90.00, 'Cash', 'Hygiene'),
(13, 20, 26, 2, '2024-11-26', '08:30:15', 300.00, 'Card', 'Clothes'),
(14, 21, 21, 1, '2024-11-25', '10:10:50', 45.00, 'Cash', 'Hygiene'),
(15, 20, 4, 1, '2024-11-24', '16:00:00', 20000.00, 'Card', 'Gaming'),
(16, 22, 33, 1, '2024-11-23', '09:20:05', 100.00, 'Cash', 'Kitchen'),
(17, 25, 23, 1, '2024-11-22', '18:30:00', 400.00, 'Card', 'Clothing'),
(18, 21, 14, 4, '2024-11-21', '14:15:30', 80.00, 'Cash', 'Hygiene'),
(19, 21, 3, 1, '2023-07-17', '10:38:10', 30000.00, 'Card', 'Gaming'),
(20, 26, 1, 1, '2024-11-29', '09:16:06', 5000.00, 'Cash', 'Gaming'),
(21, 26, 14, 1, '2024-11-29', '09:16:06', 20.00, 'Cash', 'Hygiene');

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
(1, 'Gaming', 'Laptop', 'High-End Gaming Laptop for Gamers', 50000.00, 9, 'Linux Adona'),
(3, 'Gaming', 'PlayStation 5', 'Next-Gen Gaming Console', 30000.00, 10, 'Linux Adona'),
(4, 'Gaming', 'Nintendo Switch', 'A Versatile Gaming Console', 20000.00, 10, 'Linux Adona'),
(14, 'Hygiene', 'Tissues', 'All Around Hygienic Tissue', 20.00, 99, 'Xyreel Laguras'),
(18, 'Hygiene', 'Toothbrush', 'Soft-Bristled For Gentle Cleaning', 30.00, 500, 'Xyreel Laguras'),
(19, 'Hygiene', 'Comb', 'Suitable For All Hair Types', 50.00, 200, 'Xyreel Laguras'),
(20, 'Hygiene', 'Soap', 'Moisturizing And Nourishing', 30.00, 200, 'Xyreel Laguras'),
(21, 'Hygiene', 'Nailcutter', 'Maintain Clean And Healthy Nails', 45.00, 300, 'Xyreel Laguras'),
(22, 'Clothes', 'Coat', 'Stay Warm And Stylish', 500.00, 100, 'Rome Dyanne Salvid'),
(23, 'Clothes', 'T-Shirts', 'Soft, Breathable Fabrics', 400.00, 500, 'Rome Dyanne Salvid'),
(24, 'Clothes', 'Shorts', 'Comfort In Every Stitch', 450.00, 500, 'Rome Dyanne Salvid'),
(26, 'Clothes', 'Leggings', 'Soft And Seamless Construction', 150.00, 500, 'Rome Dyanne Salvid'),
(27, 'Appliances', 'Refrigerator', 'Rapid Cooling Technology ', 15000.00, 50, 'Maricris Barcelon'),
(28, 'Appliances', 'Washing Machine', 'Stain Free, Stress Free', 3400.00, 42, 'Jan Emmanuel Formentos'),
(29, 'Appliances', 'Vacuum Cleaner', 'Powerful Suction, Quiet Operation', 1500.00, 30, 'Maricris Barcelon'),
(30, 'Appliances', 'Air Conditioning', 'Cooling Comfort, Effortless Convience', 30000.00, 40, 'Maricris Barcelon'),
(31, 'Appliances', 'Televisions', 'Experience Stunning Picture Quality ', 30000.00, 150, 'Maricris Barcelon'),
(32, 'Kitchen', 'Pots', 'Cook With Confindence Everytime', 200.00, 500, 'Djanisse Villaflor'),
(33, 'Kitchen', 'Whisk ', 'Mix, Blend, And Stir With Ease', 100.00, 500, 'Djanisse Villaflor'),
(34, 'Kitchen', 'Graters', 'Precision Grating For Home Cooks', 400.00, 300, 'Djanisse Villaflor'),
(35, 'Kitchen', 'Canisters', 'Store, Organize And Preserve', 300.00, 100, 'Djanisse Villaflor'),
(36, 'Kitchen', 'Bowls', 'Versatile And Durable', 200.00, 200, 'Djanisse Villaflor'),
(46, 'Gaming', 'Mouse', 'A device that can be ued to navigate around the desktop', 400.00, 100, 'Linux Adona'),
(47, 'Gaming', 'Mouse', 'A device that can be used for navigating', 300.00, 100, 'Ces Maranan');

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
(19, 'admin', 'admin123', 'Admin', '2024-11-25 11:34:12', 'Linux Adona'),
(20, 'Jaydee', 'jaydee123', 'Customer', '2024-11-25 11:41:19', 'John Dave Briones'),
(21, 'axlehernando', 'axle123', 'Customer', '2024-11-25 12:26:34', 'Axle Hernando'),
(22, 'joed', 'joed123', 'Customer', '2024-11-26 06:33:31', 'John Eduard De Villa'),
(25, 'janemmanuel', 'je123', 'Customer', '2024-11-27 13:42:45', 'Jan Emmanuel Formentos'),
(26, 'ynomadriaga', 'yno123', 'Customer', '2024-11-29 01:11:18', 'Yno ');

-- --------------------------------------------------------

--
-- Table structure for table `user_logs`
--

CREATE TABLE `user_logs` (
  `LogsID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `FullName` varchar(255) NOT NULL,
  `Role` varchar(100) NOT NULL,
  `Action` varchar(100) DEFAULT NULL,
  `Date` date DEFAULT current_timestamp(),
  `Time` time DEFAULT current_timestamp(),
  `Notes` varchar(5000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_logs`
--

INSERT INTO `user_logs` (`LogsID`, `UserID`, `FullName`, `Role`, `Action`, `Date`, `Time`, `Notes`) VALUES
(1, 19, 'Linux Adona', 'Admin', 'Logged In', '2024-11-29', '07:51:16', 'Linux Adona Logged In as Admin.'),
(2, 19, 'Linux Adona', 'Admin', 'Logged Out', '2024-11-29', '07:51:42', 'Linux Adona has Logged Out.'),
(3, 19, 'Linux Adona', 'Admin', 'Logged In', '2024-11-29', '07:57:21', 'Linux Adona Logged In as Admin.'),
(4, 19, 'Linux Adona', 'Admin', 'Logged Out', '2024-11-29', '07:58:19', 'Linux Adona has Logged Out.'),
(5, 22, 'John Eduard De Villa', 'Customer', 'Logged In', '2024-11-29', '07:58:41', 'John Eduard De Villa Logged In as Customer.'),
(6, 22, 'John Eduard De Villa', 'Customer', 'Logged Out', '2024-11-29', '07:58:50', 'John Eduard De Villa has Logged Out.'),
(7, 25, 'Jan Emmanuel Formentos', 'Customer', 'Logged In', '2024-11-29', '07:58:56', 'Jan Emmanuel Formentos Logged In as Customer.'),
(8, 25, 'Jan Emmanuel Formentos', 'Customer', 'Logged Out', '2024-11-29', '07:59:02', 'Jan Emmanuel Formentos has Logged Out.'),
(9, 21, 'Axle Hernando', 'Customer', 'Logged In', '2024-11-29', '07:59:10', 'Axle Hernando Logged In as Customer.'),
(10, 21, 'Axle Hernando', 'Customer', 'Logged Out', '2024-11-29', '07:59:15', 'Axle Hernando has Logged Out.'),
(11, 19, 'Linux Adona', 'Admin', 'Logged In', '2024-11-29', '07:59:19', 'Linux Adona Logged In as Admin.'),
(12, 19, 'Linux Adona', 'Admin', 'Logged Out', '2024-11-29', '07:59:42', 'Linux Adona has Logged Out.'),
(13, 26, 'Yno ', 'Customer', 'Signed Up', '2024-11-29', '09:11:18', 'Yno  has Signed Up to our store!'),
(14, 26, 'Yno ', 'Customer', 'Logged In', '2024-11-29', '09:12:26', 'Yno  Logged In as Customer.'),
(15, 26, 'Yno ', 'Customer', 'Ordered', '2024-11-29', '09:16:06', 'Yno  ordered 1 Laptop from our store!'),
(16, 26, 'Yno ', 'Customer', 'Ordered', '2024-11-29', '09:16:06', 'Yno  ordered 1 Tissues from our store!'),
(17, 26, 'Yno ', 'Customer', 'Logged Out', '2024-11-29', '09:17:09', 'Yno  has Logged Out.'),
(18, 19, 'Linux Adona', 'Admin', 'Logged In', '2024-11-29', '09:17:13', 'Linux Adona Logged In as Admin.'),
(19, 19, 'Linux Adona', 'Admin', 'Edit Product', '2024-11-29', '09:21:00', 'Linux Adona edited a Product (ID: 23):\n\nAdded Stocks\n\nStock added: 1'),
(20, 19, 'Linux Adona', 'Admin', 'Add Product', '2024-11-29', '09:22:50', 'Linux Adona Added a new Product:\n\nSupplier Name: Ces Maranan\nProduct: Mouse\nDesciption: A device that can be used for navigating\nPrice: ₱300.00\nStocks: 100\nCategory: Gaming'),
(21, 19, 'Linux Adona', 'Admin', 'Logged Out', '2024-11-29', '09:23:09', 'Linux Adona has Logged Out.'),
(22, 22, 'John Eduard De Villa', 'Customer', 'Logged In', '2024-11-29', '09:23:12', 'John Eduard De Villa Logged In as Customer.'),
(23, 22, 'John Eduard De Villa', 'Customer', 'Logged Out', '2024-11-29', '09:26:26', 'John Eduard De Villa has Logged Out.'),
(24, 19, 'Linux Adona', 'Admin', 'Logged In', '2024-11-29', '09:26:35', 'Linux Adona Logged In as Admin.');

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
-- Indexes for table `deleted_products`
--
ALTER TABLE `deleted_products`
  ADD PRIMARY KEY (`DeletedID`);

--
-- Indexes for table `orderhistory`
--
ALTER TABLE `orderhistory`
  ADD PRIMARY KEY (`OrderHistoryID`);

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
-- Indexes for table `user_logs`
--
ALTER TABLE `user_logs`
  ADD PRIMARY KEY (`LogsID`),
  ADD KEY `UserID` (`UserID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cardpayment`
--
ALTER TABLE `cardpayment`
  MODIFY `CardID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `cashpayment`
--
ALTER TABLE `cashpayment`
  MODIFY `CashID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `deleted_products`
--
ALTER TABLE `deleted_products`
  MODIFY `DeletedID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orderhistory`
--
ALTER TABLE `orderhistory`
  MODIFY `OrderHistoryID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `OrderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `ProductID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `user_logs`
--
ALTER TABLE `user_logs`
  MODIFY `LogsID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

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
-- Constraints for table `user_logs`
--
ALTER TABLE `user_logs`
  ADD CONSTRAINT `user_logs_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
