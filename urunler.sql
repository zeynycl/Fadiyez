'CREATE TABLE `urunler` (
  `idurunler` int NOT NULL,
  `ad` varchar(45) DEFAULT NULL,
  `kategori` varchar(45) DEFAULT NULL,
  `fiyat` decimal(10,2) DEFAULT NULL,
  `stok` int DEFAULT ''50'',
  `hedef_stok` int DEFAULT ''50'',
  PRIMARY KEY (`idurunler`)
) '
