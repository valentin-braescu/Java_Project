-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           5.7.19-log - MySQL Community Server (GPL)
-- SE du serveur:                Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Export de la structure de la base pour bddjava
CREATE DATABASE IF NOT EXISTS `bddjava` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bddjava`;

-- Export de la structure de la table bddjava. food
CREATE TABLE IF NOT EXISTS `food` (
  `code` int(11) NOT NULL AUTO_INCREMENT,
  `type_de_produit` varchar(50) DEFAULT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `marque` varchar(50) DEFAULT NULL,
  `categorie` varchar(50) DEFAULT NULL,
  `score` char(50) DEFAULT NULL,
  `valeur_energetique` int(11) DEFAULT NULL,
  `acides_gras_satures` float DEFAULT NULL,
  `sucres` float DEFAULT NULL,
  `proteines` float DEFAULT NULL,
  `fibres` float DEFAULT NULL,
  `sel_ou_sodium` float DEFAULT NULL,
  `teneur_fruits_legumes` int(11) DEFAULT NULL,
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- Export de données de la table bddjava.food : ~47 rows (environ)
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` (`code`, `type_de_produit`, `nom`, `marque`, `categorie`, `score`, `valeur_energetique`, `acides_gras_satures`, `sucres`, `proteines`, `fibres`, `sel_ou_sodium`, `teneur_fruits_legumes`) VALUES
	(1, 'autres', 'chips creme et oignons', 'pringles', 'aperitif', 'd', 2155, 5, 3.2, 4, 2.6, 2.11, 0),
	(2, 'autres', 'apericube tonic', 'null', 'aperitif', 'e', 1110, 15.5, 4, 11.5, 0, 2.2, 0),
	(3, 'autres', 'chips de betteraves. panais et carotte au sel de mer', 'tyrell', 'aperitif', 'e', 2047, 4.1, 22.6, 4.7, 14.2, 1.52, 0),
	(4, 'autres', 'chips paysannes nature', 'lay\'s', 'aperitif', 'd', 2490, 4, 0.4, 6, 4, 1.5, 0),
	(5, 'autres', 'belvita petit dejeuner chocolat', 'belvita', 'biscuits', 'd', 1845, 3.3, 28, 7.5, 6.5, 0.63, 0),
	(6, 'autres', 'prince', 'lu', 'biscuits', 'd', 1955, 5.7, 32, 6.3, 4.7, 0.5, 0),
	(7, 'autres', 'fourres chocolat noir bio', 'bjorg', 'biscuits', 'd', 2010, 7, 27, 7.1, 4.8, 0.5, 0),
	(8, 'autres', 'petit beurre', 'lu', 'biscuits', 'e', 1850, 7.6, 23, 8, 3, 1.4, 0),
	(9, 'autres', 'galette riz complet sesame', 'bjorg', 'biscuits', 'a', 1650, 0.8, 0.8, 8.7, 4.5, 0.25, 0),
	(10, 'autres', 'muesli sans sucre ajoute bio', 'bjorg', 'cereales', 'a', 1500, 1, 17.7, 11.7, 9.9, 0.1, 0),
	(11, 'autres', 'muesli sans sucre ajoute bio', 'jordans', 'cereales', 'b', 1533, 2.5, 25, 8, 7.8, 0.03, 0),
	(12, 'autres', 'coco pops', 'kellogg\'s', 'cereales', 'd', 1619, 1, 35, 5, 2, 0.75, 0),
	(13, 'autres', 'tresor', 'kellogg\'s', 'cereales', 'd', 1892, 4, 29, 6.9, 3.3, 1.13, 0),
	(14, 'boissons', 'coca-cola', 'coca-cola', 'boissons', 'd', 180, 0, 10.6, 0, 0, 0, 0),
	(15, 'boissons', 'ice tea saveur peche', 'lipton', 'boissons', 'c', 120, 0, 6.9, 0, 0, 0.01, 0),
	(16, 'boissons', 'jus d\'orange pur jus', 'auchan', 'boissons', 'd', 201, 0, 10.6, 0.6, 0.4, 0.03, 100),
	(17, 'boissons', 'oasis tropical', 'oasis', 'boissons', 'd', 163, 0, 0.91, 0, 0, 0, 12),
	(18, 'autres', 'jambon lz bon paris', 'herta', 'charcuterie', 'c', 445, 1, 0.5, 20, 0.5, 1.83, 0),
	(19, 'autres', 'knacki', 'herta', 'charcuterie', 'd', 1099, 9, 2, 12, 1.2, 1.83, 0),
	(20, 'autres', 'lardons natures', 'herta', 'charcuterie', 'd', 1036, 7.8, 0.4, 17, 0.5, 2.2, 0),
	(21, 'autres', 'rilettes de poulet roti en cocotte', 'bordeau chesnel', 'charcuterie', 'a', 1371, 0, 0, 16.3, 0, 0, 0),
	(22, 'autres', 'pate henaff', 'henaff', 'charcuterie', 'd', 1149, 9, 0.2, 17, 0, 1.5, 0),
	(23, 'autres', 'jambon de poulet naturre', 'herta', 'charcuterie', 'd', 440, 0.4, 0.5, 1.8, 0.4, 1.8, 0),
	(24, 'autres', 'danette noir extra', 'danone', 'desserts', 'c', 535, 2.3, 16.5, 3.7, 0, 0.19, 0),
	(25, 'autres', 'pom pot', 'materne', 'desserts', 'a', 293, 0.5, 13.7, 0.5, 0, 0.01, 96),
	(26, 'autres', 'le yaourt a la grecque nature', 'neslte', 'desserts', 'c', 443, 5.7, 3.5, 3.1, 0, 0.3, 0),
	(27, 'autres', 'fruits mixes', 'sojasun', 'desserts', 'b', 357, 0.3, 12, 3.7, 0.5, 0.08, 10),
	(28, 'autres', 'activia aux fruits', 'null', 'desserts', 'c', 397, 2.2, 12.6, 3.4, 0.2, 0.13, 10),
	(29, 'autres', 'pizza reine', 'sodebo', 'plat prepare', 'c', 758, 2.9, 2.1, 10.9, 0, 1.46, 0),
	(30, 'autres', 'pizza quatre fromages', 'dr oetker', 'plat prepare', 'd', 1125, 6.2, 2, 10.8, 1.6, 1.17, 11),
	(31, 'autres', 'cordon bleu de poulet', 'le gaulois', 'plat prepare', 'c', 1006, 3.6, 2.2, 13, 2.6, 1.4, 0),
	(32, 'autres', 'le ravioli pur boeuf', 'panzani', 'plat prepare', 'c', 370, 0.8, 1.4, 3.8, 0, 0.9, 6),
	(33, 'autres', 'mousline', 'maggi', 'plat prepare', 'a', 1468, 0.5, 2.9, 7.4, 6.6, 0.15, 0),
	(34, 'autres', 'gnocchi a poeler', 'lustucru', 'plat prepare', 'c', 771, 0.4, 0.2, 5, 0, 1.1, 0),
	(35, 'autres', 'veloute de neuf legumes', 'knorr', 'plat prepare', 'c', 184, 0.2, 2, 0.7, 1.1, 0.76, 40),
	(36, 'autres', 'steacks de soja a l\'indienne', 'sojasun', 'plat prepare', 'c', 748, 0.85, 5.2, 16.5, 7.6, 1.6, 0),
	(37, 'autres', 'surimi', 'fleury michon', 'produits de la mer', 'c', 481, 0.4, 3, 8, 0.5, 1.8, 0),
	(38, 'autres', 'filets de maqueraux a la moutarde', 'saupiquet', 'produits de la mer', 'd', 823, 3.1, 0.3, 11, 0.6, 1.3, 0),
	(39, 'autres', 'rillettes de saumon', 'petit navire', 'produits de la mer', 'c', 1093, 2, 0.3, 14.8, 2.1, 1, 0),
	(40, 'autres', 'poisson pane', 'croustibat', 'produits de la mer', 'c', 814, 1.1, 0.5, 12, 0.8, 0.93, 0),
	(41, 'autres', 'ketchup', 'heinz', 'sauce', 'd', 427, 0.1, 22.8, 1.2, 0, 1.8, 100),
	(42, 'autres', 'tomacouli', 'panzani', 'sauce', 'd', 427, 0.1, 22.8, 1.2, 0, 1.8, 100),
	(43, 'autres', 'mayonnaise', 'benedicta', 'sauce', 'e', 2685, 5.4, 1.3, 1.2, 0, 1.4, 0),
	(44, 'autres', 'pesto au basilic frais', 'barilla', 'sauce', 'd', 2176, 5.5, 4, 5, 2.2, 3.38, 0),
	(45, 'autres', 'carottes rapees', 'bonduelle', 'snacking', 'a', 320, 0.3, 4, 1, 3, 0.69, 81),
	(46, 'autres', 'pastabox jambon fromage', 'sodebo', 'snacking', 'c', 654, 3.4, 1.7, 8.6, 0, 0.9, 0),
	(47, 'autres', 'salade poulet marine', 'sodebo', 'snacking', 'c', 812, 2.1, 3.8, 6.2, 0, 0.5, 0);
/*!40000 ALTER TABLE `food` ENABLE KEYS */;

-- Export de la structure de la table bddjava. food_posts
CREATE TABLE IF NOT EXISTS `food_posts` (
  `userId` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `food_code` int(11) DEFAULT NULL,
  `idPost` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Fait le lien entre les aliments composant chaque recette et les utilisateurs plubliant ces recettes.';

-- Export de données de la table bddjava.food_posts : ~0 rows (environ)
/*!40000 ALTER TABLE `food_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `food_posts` ENABLE KEYS */;

-- Export de la structure de la table bddjava. posts
CREATE TABLE IF NOT EXISTS `posts` (
  `idPost` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `imageName` varchar(100) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  KEY `FK__users` (`id`),
  KEY `idPost` (`idPost`),
  CONSTRAINT `FK__users` FOREIGN KEY (`id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Users'' posts containing a title, a description, and a picture.';

-- Export de données de la table bddjava.posts : ~0 rows (environ)
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;

-- Export de la structure de la table bddjava. users
CREATE TABLE IF NOT EXISTS `users` (
  `login` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  KEY `Id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Export de données de la table bddjava.users : ~4 rows (environ)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`login`, `password`, `id`) VALUES
	('Seb', 'totoro', 2),
	('thierry', 'paul', 3),
	('marc', 'james', 4),
	('Bil', 'Bob', 5);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
