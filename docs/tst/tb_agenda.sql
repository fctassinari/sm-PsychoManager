-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: psychomanager
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_agenda`
--

DROP TABLE IF EXISTS `tb_agenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_agenda` (
  `DT_CONSULTA` datetime NOT NULL,
  `ID_PACIENTE` int(11) NOT NULL,
  `DS_OBS` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DT_CONSULTA_ATE` datetime DEFAULT NULL,
  `ID_PRODUTO` int(11) DEFAULT NULL,
  `ST_PAGAMENTO` int(11) DEFAULT NULL,
  `ST_PRESENCA` bit(1) DEFAULT NULL,
  `VL_PRECO` double DEFAULT NULL,
  PRIMARY KEY (`DT_CONSULTA`,`ID_PACIENTE`),
  KEY `FK_4jmqxih0vx16yjr3dtmfyn41y` (`ID_PACIENTE`),
  KEY `FK_dfkji5o284heto1fa14990rxw` (`ID_PRODUTO`),
  CONSTRAINT `FK_4jmqxih0vx16yjr3dtmfyn41y` FOREIGN KEY (`ID_PACIENTE`) REFERENCES `tb_paciente` (`ID_PACIENTE`),
  CONSTRAINT `FK_dfkji5o284heto1fa14990rxw` FOREIGN KEY (`ID_PRODUTO`) REFERENCES `tb_produto` (`ID_PRODUTO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-05  9:58:01
