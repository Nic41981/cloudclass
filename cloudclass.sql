-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: 39.107.102.246    Database: cloudclass
-- ------------------------------------------------------
-- Server version	5.7.25-0ubuntu0.16.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chapter` (
  `id` varchar(35) NOT NULL COMMENT '章节ID',
  `course` varchar(35) NOT NULL COMMENT '课程ID',
  `num` int(11) NOT NULL COMMENT '章节序号',
  `name` varchar(255) NOT NULL COMMENT '章节名',
  `video` varchar(35) DEFAULT NULL COMMENT '视频文件ID',
  `info` text NOT NULL COMMENT '章节简介',
  `chapterExam` varchar(35) DEFAULT NULL COMMENT '章节测试ID',
  `createTime` datetime NOT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chapter_exam`
--

DROP TABLE IF EXISTS `chapter_exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chapter_exam` (
  `id` varchar(35) NOT NULL COMMENT '测试ID',
  `name` varchar(255) NOT NULL COMMENT '测试名',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter_exam`
--

LOCK TABLES `chapter_exam` WRITE;
/*!40000 ALTER TABLE `chapter_exam` DISABLE KEYS */;
/*!40000 ALTER TABLE `chapter_exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` varchar(35) NOT NULL COMMENT '课程ID',
  `name` varchar(255) NOT NULL COMMENT '课程名',
  `image` varchar(35) DEFAULT NULL COMMENT '课程图片ID',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `teacher` varchar(35) NOT NULL COMMENT '教师用户ID',
  `tag` text COMMENT '标签',
  `finalExam` varchar(35) DEFAULT NULL COMMENT '期末考试ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `id` varchar(35) NOT NULL COMMENT '文件ID',
  `realName` varchar(255) NOT NULL COMMENT '文件名',
  `suffix` varchar(10) NOT NULL COMMENT '文件后缀名',
  `type` varchar(10) NOT NULL COMMENT '文件类型',
  `uploadTime` datetime NOT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `final_exam`
--

DROP TABLE IF EXISTS `final_exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `final_exam` (
  `id` varchar(35) NOT NULL COMMENT '考试ID',
  `name` varchar(255) NOT NULL COMMENT '考试名',
  `startTime` datetime NOT NULL COMMENT '开始时间',
  `stopTime` datetime NOT NULL COMMENT '结束时间',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `final_exam`
--

LOCK TABLES `final_exam` WRITE;
/*!40000 ALTER TABLE `final_exam` DISABLE KEYS */;
/*!40000 ALTER TABLE `final_exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam` varchar(35) NOT NULL COMMENT '考试ID',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `question` text NOT NULL COMMENT '问题',
  `score` int(11) NOT NULL COMMENT '分值',
  `options` text COMMENT '选项',
  `answer` text NOT NULL COMMENT '答案',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `study` varchar(35) NOT NULL COMMENT '用户ID',
  `exam` varchar(35) NOT NULL COMMENT '考试ID',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `score` int(11) NOT NULL COMMENT '得分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `study`
--

DROP TABLE IF EXISTS `study`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `study` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学习ID',
  `student` varchar(35) NOT NULL COMMENT '用户ID',
  `course` varchar(35) NOT NULL COMMENT '课程ID',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `study`
--

LOCK TABLES `study` WRITE;
/*!40000 ALTER TABLE `study` DISABLE KEYS */;
/*!40000 ALTER TABLE `study` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(35) NOT NULL COMMENT '用户ID',
  `name` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `createTime` datetime NOT NULL COMMENT '注册日期',
  `identity` int(11) NOT NULL COMMENT '身份(0-学生,1-教师)',
  `email` varchar(255) NOT NULL COMMENT '电子邮件',
  `taken` varchar(60) DEFAULT NULL COMMENT '自动登录凭证',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('0ad2785e33cc4ffabd00d9170aa0aba0','nic','$2a$10$t/6ahPESQcgylqXV/IL3BeDEXXqOlf1YZJ.ZsaxmqxFuhDM9L4TdO','2019-04-17 19:37:04',0,'783415836@qq.com','$2a$10$sP.XyzsOqf7uaqXYZgndVOyH5zYtNbe6eUkp6piiFmjI0u7FHLoiG');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-17 21:07:59
