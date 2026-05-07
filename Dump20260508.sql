CREATE DATABASE  IF NOT EXISTS `bicivalen` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `bicivalen`;
-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 79.116.29.181    Database: bicivalen
-- ------------------------------------------------------
-- Server version	5.5.5-10.3.39-MariaDB-0ubuntu0.20.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bicicleta`
--

DROP TABLE IF EXISTS `bicicleta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bicicleta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(50) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `estacion` varchar(50) DEFAULT NULL,
  `estadoUso` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `estacion_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `estacion` (`estacion`),
  KEY `FK7sp3erfgqx6t6lykikeeo7wl5` (`estacion_id`),
  CONSTRAINT `FK7sp3erfgqx6t6lykikeeo7wl5` FOREIGN KEY (`estacion_id`) REFERENCES `estaciones` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `estacion` FOREIGN KEY (`estacion`) REFERENCES `estaciones` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=404 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bicicleta`
--

LOCK TABLES `bicicleta` WRITE;
/*!40000 ALTER TABLE `bicicleta` DISABLE KEYS */;
INSERT INTO `bicicleta` VALUES (352,'Electrica','Genial',NULL,'Disponible',NULL,NULL,152),(353,'Electrica','Genial',NULL,'Disponible',NULL,NULL,152),(354,'Electrica','Genial',NULL,'Disponible',NULL,NULL,152),(402,'Electrica','Genial',NULL,'Disponible',NULL,NULL,103),(403,'Electrica','Genial',NULL,'Disponible',NULL,NULL,103);
/*!40000 ALTER TABLE `bicicleta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bicicleta_SEQ`
--

DROP TABLE IF EXISTS `bicicleta_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bicicleta_SEQ` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bicicleta_SEQ`
--

LOCK TABLES `bicicleta_SEQ` WRITE;
/*!40000 ALTER TABLE `bicicleta_SEQ` DISABLE KEYS */;
INSERT INTO `bicicleta_SEQ` VALUES (501);
/*!40000 ALTER TABLE `bicicleta_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bono`
--

DROP TABLE IF EXISTS `bono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bono` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bono`
--

LOCK TABLES `bono` WRITE;
/*!40000 ALTER TABLE `bono` DISABLE KEYS */;
INSERT INTO `bono` VALUES (1,'Corta Duración','1 Dia','Un bono especial para turistas');
/*!40000 ALTER TABLE `bono` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bono_SEQ`
--

DROP TABLE IF EXISTS `bono_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bono_SEQ` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bono_SEQ`
--

LOCK TABLES `bono_SEQ` WRITE;
/*!40000 ALTER TABLE `bono_SEQ` DISABLE KEYS */;
INSERT INTO `bono_SEQ` VALUES (101);
/*!40000 ALTER TABLE `bono_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estaciones`
--

DROP TABLE IF EXISTS `estaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estaciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `ubicacion` varchar(50) DEFAULT NULL,
  `capacidad` int(11) DEFAULT NULL,
  `puestos` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  UNIQUE KEY `ubicacion_UNIQUE` (`ubicacion`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estaciones`
--

LOCK TABLES `estaciones` WRITE;
/*!40000 ALTER TABLE `estaciones` DISABLE KEYS */;
INSERT INTO `estaciones` VALUES (102,'La graciosa','Calle La graciosa',10,11),(103,'Cartero','Calle Los Carteros',2,4),(152,'El jefe','Calle Los Jefes',2,1),(155,'El','Calle',2,2),(202,'El Boss','Av Boss',2,2),(252,'El Primo','Calle Los Primos',2,2);
/*!40000 ALTER TABLE `estaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estaciones_SEQ`
--

DROP TABLE IF EXISTS `estaciones_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estaciones_SEQ` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estaciones_SEQ`
--

LOCK TABLES `estaciones_SEQ` WRITE;
/*!40000 ALTER TABLE `estaciones_SEQ` DISABLE KEYS */;
INSERT INTO `estaciones_SEQ` VALUES (351);
/*!40000 ALTER TABLE `estaciones_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ruta`
--

DROP TABLE IF EXISTS `ruta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ruta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario_id` int(11) DEFAULT NULL,
  `estacionI_id` int(11) DEFAULT NULL,
  `estacionD_id` int(11) DEFAULT NULL,
  `duracion` int(11) DEFAULT NULL,
  `valoracion` int(11) DEFAULT NULL,
  `bicicleta_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh84e8ncmmvnrpebdba18maa4m` (`bicicleta_id`),
  KEY `ruta_ibfk_1` (`usuario_id`),
  KEY `ruta_ibfk_2` (`estacionI_id`),
  KEY `ruta_ibfk_3` (`estacionD_id`),
  CONSTRAINT `FKh84e8ncmmvnrpebdba18maa4m` FOREIGN KEY (`bicicleta_id`) REFERENCES `bicicleta` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ruta_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ruta_ibfk_2` FOREIGN KEY (`estacionI_id`) REFERENCES `estaciones` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ruta_ibfk_3` FOREIGN KEY (`estacionD_id`) REFERENCES `estaciones` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=503 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ruta`
--

LOCK TABLES `ruta` WRITE;
/*!40000 ALTER TABLE `ruta` DISABLE KEYS */;
INSERT INTO `ruta` VALUES (303,1,102,152,1,5,352),(352,1,102,152,0,5,354),(402,1,102,152,0,5,353),(452,1,103,102,0,5,403),(453,1,102,103,0,5,403),(454,1,103,103,0,5,403),(455,1,103,103,0,5,403),(502,1,103,103,0,5,403);
/*!40000 ALTER TABLE `ruta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ruta_SEQ`
--

DROP TABLE IF EXISTS `ruta_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ruta_SEQ` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ruta_SEQ`
--

LOCK TABLES `ruta_SEQ` WRITE;
/*!40000 ALTER TABLE `ruta_SEQ` DISABLE KEYS */;
INSERT INTO `ruta_SEQ` VALUES (601);
/*!40000 ALTER TABLE `ruta_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `contraseña` varchar(50) DEFAULT NULL,
  `bono_expiracion` date DEFAULT NULL,
  `bono_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkxbakft1oadmisyst07fdysg2` (`bono_id`),
  CONSTRAINT `FKkxbakft1oadmisyst07fdysg2` FOREIGN KEY (`bono_id`) REFERENCES `bono` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Erik','Erik@gmail.com','erik','2026-05-07',1),(2,'Erik','Erik@gmail.com','Erik',NULL,NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_SEQ`
--

DROP TABLE IF EXISTS `usuarios_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios_SEQ` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_SEQ`
--

LOCK TABLES `usuarios_SEQ` WRITE;
/*!40000 ALTER TABLE `usuarios_SEQ` DISABLE KEYS */;
INSERT INTO `usuarios_SEQ` VALUES (151);
/*!40000 ALTER TABLE `usuarios_SEQ` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-08  0:13:49
