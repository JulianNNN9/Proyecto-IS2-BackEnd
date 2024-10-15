# Proyecto-IS2-BackEnd

# Proyecto de Sistema de Citas para Peluquería

## Descripción

Este proyecto tiene como objetivo implementar un sistema de gestión de citas para una peluquería. La base de datos contiene información sobre estilistas, servicios y citas de clientes.

## Requisitos

- **MySQL**: Asegúrate de tener MySQL instalado en tu máquina. Puedes descargarlo desde [MySQL Downloads](https://dev.mysql.com/downloads/mysql/).

## Instrucciones de Creación de la Base de Datos

### 1. Crear la base de datos

Abre tu terminal o consola de MySQL y ejecuta el siguiente comando para crear la base de datos `peluqueria_db`:

```sql
CREATE DATABASE  IF NOT EXISTS `peluqueria_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `peluqueria_db`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: peluqueria_db
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrador` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(255) NOT NULL,
  `correo` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK571pycikd1pkkvboiav3f69gp` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cita`
--

DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cita` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comentario` varchar(255) DEFAULT NULL,
  `confirmacion` bit(1) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `fecha` datetime(6) NOT NULL,
  `propina` double DEFAULT NULL,
  `total_pago` double NOT NULL,
  `cliente_id` bigint NOT NULL,
  `estado_cita_id` bigint NOT NULL,
  `estilista_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd4bcx0srxa2870y731fa0yew2` (`cliente_id`),
  KEY `FKet5w6pue365jjek744uw72jcw` (`estado_cita_id`),
  KEY `FKed5tc8abxc1petf0hnywjy84s` (`estilista_id`),
  CONSTRAINT `FKd4bcx0srxa2870y731fa0yew2` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FKed5tc8abxc1petf0hnywjy84s` FOREIGN KEY (`estilista_id`) REFERENCES `estilista` (`id`),
  CONSTRAINT `FKet5w6pue365jjek744uw72jcw` FOREIGN KEY (`estado_cita_id`) REFERENCES `estadocita` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(255) NOT NULL,
  `correo` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKk6i2j3upwar1uora4mgiol6b` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `detalle_producto_cita`
--

DROP TABLE IF EXISTS `detalle_producto_cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_producto_cita` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `precio` double NOT NULL,
  `cita_id` bigint NOT NULL,
  `producto_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6knm31m6s3xna8ooigqld7coo` (`cita_id`),
  KEY `FK4p0795773k876jdl4w759od25` (`producto_id`),
  CONSTRAINT `FK4p0795773k876jdl4w759od25` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`),
  CONSTRAINT `FK6knm31m6s3xna8ooigqld7coo` FOREIGN KEY (`cita_id`) REFERENCES `cita` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `detalle_servicio_cita`
--

DROP TABLE IF EXISTS `detalle_servicio_cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_servicio_cita` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `precio` double NOT NULL,
  `cita_id` bigint NOT NULL,
  `servicio_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKffivqlusa5vsawc5sunw3qey1` (`cita_id`),
  KEY `FKcdooarjno0x1luc9poxc3nc4f` (`servicio_id`),
  CONSTRAINT `FKcdooarjno0x1luc9poxc3nc4f` FOREIGN KEY (`servicio_id`) REFERENCES `servicio` (`id`),
  CONSTRAINT `FKffivqlusa5vsawc5sunw3qey1` FOREIGN KEY (`cita_id`) REFERENCES `cita` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `estadocita`
--

DROP TABLE IF EXISTS `estadocita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estadocita` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKhexrvpksft57jhuahcisg4bvy` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `estadopago`
--

DROP TABLE IF EXISTS `estadopago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estadopago` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKq529aicguwmytp2h4wfd307vd` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `estilista`
--

DROP TABLE IF EXISTS `estilista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estilista` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_pago` datetime(6) NOT NULL,
  `total` double DEFAULT NULL,
  `cita_id` bigint DEFAULT NULL,
  `estado_pago_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK8ebpauwtodpjyeb1035r19sh5` (`cita_id`),
  KEY `FKqpjoick6mb66o7s4hfagt1moy` (`estado_pago_id`),
  CONSTRAINT `FKq90f1qbia6fmjfrcx472p10nq` FOREIGN KEY (`cita_id`) REFERENCES `cita` (`id`),
  CONSTRAINT `FKqpjoick6mb66o7s4hfagt1moy` FOREIGN KEY (`estado_pago_id`) REFERENCES `estadopago` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pago_detalle_pago`
--

DROP TABLE IF EXISTS `pago_detalle_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago_detalle_pago` (
  `pago_id` bigint NOT NULL,
  `detalle_pago` varchar(255) DEFAULT NULL,
  KEY `FKpbd7fqyunjvxdfiaelpx99sdc` (`pago_id`),
  CONSTRAINT `FKpbd7fqyunjvxdfiaelpx99sdc` FOREIGN KEY (`pago_id`) REFERENCES `pago` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `preferenciacliente`
--

DROP TABLE IF EXISTS `preferenciacliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preferenciacliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint NOT NULL,
  `servicio_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe05liv21s5640iqq244q2g5i3` (`cliente_id`),
  KEY `FKebxpesmdwtu9uuoib75duq0ap` (`servicio_id`),
  CONSTRAINT `FKe05liv21s5640iqq244q2g5i3` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FKebxpesmdwtu9uuoib75duq0ap` FOREIGN KEY (`servicio_id`) REFERENCES `servicio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `marca` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `precio` double NOT NULL,
  `stock` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resenia`
--

DROP TABLE IF EXISTS `resenia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resenia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `calificacion` int NOT NULL,
  `comentario` varchar(255) NOT NULL,
  `cita_id` bigint DEFAULT NULL,
  `cliente_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKggvax88vcmueksdsahv8o7ts0` (`cita_id`),
  KEY `FK9ds0dcev5xoby9vxoc2mb0fwo` (`cliente_id`),
  CONSTRAINT `FK9ds0dcev5xoby9vxoc2mb0fwo` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FKggvax88vcmueksdsahv8o7ts0` FOREIGN KEY (`cita_id`) REFERENCES `cita` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `servicio`
--

DROP TABLE IF EXISTS `servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `duracion` int NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `precio` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_especialidad_estilista`
--

DROP TABLE IF EXISTS `tipo_especialidad_estilista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_especialidad_estilista` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tipo_especialidad_id` bigint NOT NULL,
  `estilista_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgosolmn4vuuanoh6q8t0fpop2` (`tipo_especialidad_id`),
  KEY `FKfxikmrahblg0wm9gen7c9afv8` (`estilista_id`),
  CONSTRAINT `FKfxikmrahblg0wm9gen7c9afv8` FOREIGN KEY (`estilista_id`) REFERENCES `estilista` (`id`),
  CONSTRAINT `FKgosolmn4vuuanoh6q8t0fpop2` FOREIGN KEY (`tipo_especialidad_id`) REFERENCES `tipoespecialidad` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipoespecialidad`
--

DROP TABLE IF EXISTS `tipoespecialidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipoespecialidad` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjynl5fivi1b2yp69jjm1psvo4` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-07 10:43:40

```

