-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 27, 2024 at 04:42 PM
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
(1, 22, 0.00, '2024-11-27 23:00:23');

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
(1, 22, 20, 1, '2024-11-27', '23:00:22', 30.00, 'Cash', 'Hygiene');

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
(1, 'Gaming', 'Laptop', 'High-End Gaming Laptop for Gamers', 50000.00, 20, 'Linux Adona'),
(2, 'Gadgets', 'iPhone', 'Newly Launched High-End Mobile Phone', 40000.00, 10, 'Linux Adona'),
(3, 'Gaming', 'PlayStation 5', 'Next-Gen Gaming Console', 30000.00, 10, 'Linux Adona'),
(4, 'Gaming', 'Nintendo Switch', 'A Versatile Gaming Console', 20000.00, 10, 'Linux Adona'),
(14, 'Hygiene', 'Tissues', 'All Around Hygienic Tissue', 20.00, 100, 'Xyreel Laguras'),
(18, 'Hygiene', 'Toothbrush ', 'Soft-Bristled For Gentle Cleaning', 30.00, 500, 'Xyreel Laguras'),
(19, 'Hygiene', 'Comb', 'Suitable For All Hair Types', 50.00, 200, 'Xyreel Laguras'),
(20, 'Hygiene', 'Soap', 'Moisturizing And Nourishing', 30.00, 199, 'Xyreel Laguras'),
(21, 'Hygiene', 'Nailcutter', 'Maintain Clean And Healthy Nails', 45.00, 300, 'Xyreel Laguras'),
(22, 'Clothes', 'Coats', 'Stay Warm And Stylish', 500.00, 100, 'Rome Dyanne Salvid'),
(23, 'Clothes', 'T-Shirts', 'Soft, Breathable Fabrics', 400.00, 500, 'Rome Dyanne Salvid'),
(24, 'Clothes', 'Shorts', 'Comfort In Every Stitch', 450.00, 500, 'Rome Dyanne Salvid'),
(26, 'Clothes', 'Leggings', 'Soft And Seamless Construction', 150.00, 400, 'Rome Dyanne Salvid'),
(27, 'Appliances', 'Refrigerator', 'Rapid Cooling Technology ', 15000.00, 50, 'Maricris Barcelon'),
(28, 'Appliances', 'Washing Machine', 'Stain Free, Stress Free', 3400.00, 50, 'Jan Emmanuel Formentos'),
(29, 'Appliances', 'Vacuum Cleaner', 'Powerful Suction, Quiet Operation', 1500.00, 30, 'Maricris Barcelon'),
(30, 'Appliances', 'Air Conditioning', 'Cooling Comfort, Effortless Convience', 30000.00, 40, 'Maricris Barcelon'),
(31, 'Appliances', 'Televisions', 'Experience Stunning Picture Quality ', 30000.00, 150, 'Maricris Barcelon'),
(32, 'Kitchen', 'Pots', 'Cook With Confindence Everytime', 200.00, 500, 'Djanisse Villaflor'),
(33, 'Kitchen', 'Whisk ', 'Mix, Blend, And Stir With Ease', 100.00, 500, 'Djanisse Villaflor'),
(34, 'Kitchen', 'Graters', 'Precision Grating For Home Cooks', 400.00, 300, 'Djanisse Villaflor'),
(35, 'Kitchen', 'Canisters', 'Store, Organize And Preserve', 300.00, 100, 'Djanisse Villaflor'),
(36, 'Kitchen', 'Bowls', 'Versatile And Durable', 200.00, 200, 'Djanisse Villaflor'),
(37, 'Gaming', 'Keyboard', 'Fast Typing for Gamers', 500.00, 50, 'Hanns Donor');

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
(25, 'janemmanuel', 'je123', 'Customer', '2024-11-27 13:42:45', 'Jan Emmanuel Formentos');

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
(1, 19, 'Linux Adona', 'Admin', 'Logged In', '2024-11-27', '23:30:21', 'Linux Adona Logged In as Admin.'),
(2, 19, 'Linux Adona', 'Admin', 'Add Product', '2024-11-27', '23:31:06', 'Linux Adona Added a new Product:\n\nSupplier Name: Hanns Donor\nProduct: Mouse\nDesciption: Wired Mouse that can be used for desktops\nPrice: ₱500.00\nStocks: 200\nCategory: Gaming'),
(3, 19, 'Linux Adona', 'Admin', 'Remove Product', '2024-11-27', '23:31:10', 'Linux Adona Removed a Product:\n\nSupplier Name: Hanns Donor\nProduct: Mouse\nDesciption: Wired Mouse that can be used for desktops\nPrice: ₱500.00\nStocks: 200\nCategory: Gaming'),
(4, 19, 'Linux Adona', 'Admin', 'Logged Out', '2024-11-27', '23:31:35', 'Linux Adona has Logged Out.'),
(5, 19, 'Linux Adona', 'Admin', 'Logged In', '2024-11-27', '23:33:09', 'Linux Adona Logged In as Admin.'),
(6, 19, 'Linux Adona', 'Admin', 'Logged Out', '2024-11-27', '23:33:47', 'Linux Adona has Logged Out.');

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
  MODIFY `CardID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cashpayment`
--
ALTER TABLE `cashpayment`
  MODIFY `CashID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `OrderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `ProductID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `user_logs`
--
ALTER TABLE `user_logs`
  MODIFY `LogsID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

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
