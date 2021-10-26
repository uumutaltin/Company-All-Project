-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 27 Eki 2021, 00:20:00
-- Sunucu sürümü: 10.4.20-MariaDB
-- PHP Sürümü: 7.3.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `companyall`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `advertisement`
--

CREATE TABLE `advertisement` (
  `aid` int(11) NOT NULL,
  `adesc` int(11) NOT NULL,
  `advertheight` varchar(255) NOT NULL,
  `advertimage` varchar(255) DEFAULT NULL,
  `advertlink` varchar(255) NOT NULL,
  `advertwidth` varchar(255) NOT NULL,
  `astatus` bit(1) DEFAULT NULL,
  `atitle` varchar(255) NOT NULL,
  `dateend` datetime NOT NULL,
  `datestart` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `advertisement`
--

INSERT INTO `advertisement` (`aid`, `adesc`, `advertheight`, `advertimage`, `advertlink`, `advertwidth`, `astatus`, `atitle`, `dateend`, `datestart`) VALUES
(3, 5, '250', '623879aa-09fb-4111-858b-9614bf56725d.png', 'https://www.bilgeadam.com', '150', b'1', 'Bilge Adam', '2021-10-19 00:00:00', '2021-10-18 00:00:00'),
(4, 9, '200', '7d81561f-d9ac-45f5-bd1c-5590874e2900.jpg', 'https://www.sekerbank.com.tr/', '50', b'1', 'Şekerbank', '2021-10-20 00:00:00', '2021-10-19 00:00:00'),
(5, 8, '200', 'db643794-a643-486d-a75c-2954f579d3de.png', 'https://www.java.com', '100', b'1', 'Java', '2021-10-28 00:00:00', '2021-10-20 00:00:00');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `announcement`
--

CREATE TABLE `announcement` (
  `aid` int(11) NOT NULL,
  `adate` datetime DEFAULT NULL,
  `adetail` varchar(255) NOT NULL,
  `astatus` bit(1) DEFAULT NULL,
  `atitle` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `announcement`
--

INSERT INTO `announcement` (`aid`, `adate`, `adetail`, `astatus`, `atitle`) VALUES
(4, '2021-10-22 15:31:33', '<p>İçerik</p>', b'1', 'Turan Duyuru'),
(5, '2021-10-22 16:27:34', '<p>duyuru - edit</p>', b'0', 'Umut duyuru - edit'),
(6, '2021-10-22 16:51:20', '<p>Duyuru 1</p>', b'1', 'Duyuru 1'),
(7, '2021-10-22 16:51:27', '<p>Duyuru 2</p>', b'1', 'Duyuru 2'),
(8, '2021-10-22 16:51:34', '<p>Duyuru 3</p>', b'1', 'Duyuru 3'),
(9, '2021-10-22 16:51:40', '<p>Duyuru 4</p>', b'1', 'Duyuru 4');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `contents`
--

CREATE TABLE `contents` (
  `cid` int(11) NOT NULL,
  `cdate` datetime DEFAULT NULL,
  `cdescription` varchar(255) NOT NULL,
  `cdetail` varchar(255) NOT NULL,
  `cstatus` bit(1) DEFAULT NULL,
  `ctitle` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `contents`
--

INSERT INTO `contents` (`cid`, `cdate`, `cdescription`, `cdetail`, `cstatus`, `ctitle`) VALUES
(12, '2021-10-19 01:43:22', 'İçerik Kısa Açıklama 1', '<p>İçerik Detaylı Açıklama 1</p>', b'1', 'İçerik Başlık 1'),
(13, '2021-10-19 01:50:44', 'İçerik Başlık 2', '<p>İçerik Başlık 2</p>', b'1', 'Deneme 2'),
(14, '2021-10-19 01:50:55', 'İçerik Başlık 3', '<p>İçerik Başlık 3</p>', b'1', 'Elastic 3'),
(15, '2021-10-19 02:27:24', 'İçerik Başlık 2', '<p>İçerik Başlık 2</p>', b'1', 'İçerik Başlık 2'),
(16, '2021-10-19 02:27:33', 'İçerik Başlık 3', '<p>İçerik Başlık 3</p>', b'0', 'İçerik Başlık 3'),
(17, '2021-10-19 02:27:43', 'İçerik Başlık 4', '<p>İçerik Başlık 4</p>', b'1', 'İçerik Başlık 4'),
(18, '2021-10-19 02:44:31', 'İçerik Başlık 8', '<p>İçerik Başlık 8</p>', b'1', 'İçerik Başlık 8'),
(19, '2021-10-19 02:44:48', 'İçerik Başlık 10', '<p>İçerik Başlık 10</p>', b'1', 'İçerik Başlık 10');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `customer`
--

CREATE TABLE `customer` (
  `cid` int(11) NOT NULL,
  `ban` bit(1) DEFAULT NULL,
  `cname` varchar(255) NOT NULL,
  `csurname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mobile_phone` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `customer`
--

INSERT INTO `customer` (`cid`, `ban`, `cname`, `csurname`, `email`, `mobile_phone`) VALUES
(2, b'0', 'Umut', 'Altın', 'umut@mail.com', '05365987'),
(3, b'1', 'Kullanıcıyı', 'Banla', 'ban@mail.com', 'xxxx7');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `gallery`
--

CREATE TABLE `gallery` (
  `gid` int(11) NOT NULL,
  `gdetail` varchar(255) DEFAULT NULL,
  `gname` varchar(255) NOT NULL,
  `gstatus` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `gallery`
--

INSERT INTO `gallery` (`gid`, `gdetail`, `gname`, `gstatus`) VALUES
(14, 'Yusuf acıklama', 'Yusuf Galeri', b'0'),
(15, 'Galeri Açıklama', 'Umut\'un Maymun Galerisi', b'1'),
(16, 'Yeni Turan Galeri - Edit', 'Turan Galeri - Edit', b'1');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `gallery_gallery_images`
--

CREATE TABLE `gallery_gallery_images` (
  `gallery_gid` int(11) NOT NULL,
  `gallery_images_giid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `gallery_gallery_images`
--

INSERT INTO `gallery_gallery_images` (`gallery_gid`, `gallery_images_giid`) VALUES
(15, 23),
(16, 24),
(16, 26);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `gallery_image`
--

CREATE TABLE `gallery_image` (
  `giid` int(11) NOT NULL,
  `giname` varchar(255) DEFAULT NULL,
  `gisubtitle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `gallery_image`
--

INSERT INTO `gallery_image` (`giid`, `giname`, `gisubtitle`) VALUES
(23, '12e29971-82c2-472b-b1ce-93533bd3cff0.jpg', 'Maymun öldü'),
(24, '8b2647b3-5b26-44eb-bba6-423431848566.jpg', 'Turan\'ın Rs7 si çok güzel'),
(26, '8ad6fe14-bae9-4831-b6fa-2abdca32b232.jpg', 'Audi A3');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `news`
--

CREATE TABLE `news` (
  `nid` int(11) NOT NULL,
  `ndate` datetime DEFAULT NULL,
  `ndescription` varchar(255) NOT NULL,
  `ndetail` varchar(255) NOT NULL,
  `newsimage` varchar(255) DEFAULT NULL,
  `nstatus` bit(1) DEFAULT NULL,
  `ntitle` varchar(255) NOT NULL,
  `news_category_ncid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `news`
--

INSERT INTO `news` (`nid`, `ndate`, `ndescription`, `ndetail`, `newsimage`, `nstatus`, `ntitle`, `news_category_ncid`) VALUES
(1, '2021-10-20 21:20:49', 'Egzotik Kuşu Kaçırdılar!', '<p>Egzotik kuş kaçırıldı. Yolda can verdi.</p>', 'b79814c9-a546-4e7e-9e02-03788f5bfe2f.jpg', b'1', 'Hayvan Kaçakçılığı', 5),
(2, '2021-10-20 21:24:47', 'Hırsızlık', '<p>Yurtdışına kaçtı.</p>', '0c87682e-048d-4a68-ad27-69929170d866.jpg', b'1', 'Tekstilci Serkan - Edit', 1),
(4, '2021-10-20 23:35:38', 'Tanıtım', '<p>Yeni telefon tanıtıldı.</p>', '49a6fb53-c808-4fbe-8404-72f1044a183d.jpg', b'1', 'Iphone', 4),
(5, '2021-10-21 00:23:53', 'Haber 1', '<p>Haber 1</p>', '740b11aa-a16e-45e6-958f-32aa98c1e826.jpg', b'0', 'Haber 1', 6),
(6, '2021-10-21 00:24:12', 'Haber 2', '<p>Haber 2</p>', '9f0ae214-c7a0-4009-8c5d-012078ccd991.jpg', b'0', 'Haber 2', 6),
(7, '2021-10-21 00:24:26', 'Haber 3', '<p>Haber 3</p>', '64cf4b1e-8865-478a-9e2f-409e3903e74a.jpg', b'0', 'Haber 3', 6);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `news_category`
--

CREATE TABLE `news_category` (
  `ncid` int(11) NOT NULL,
  `nctitle` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `news_category`
--

INSERT INTO `news_category` (`ncid`, `nctitle`) VALUES
(1, 'Tekstil'),
(2, 'Elektronik'),
(3, 'Araba'),
(4, 'Telefon'),
(5, 'Doğa'),
(6, 'Dummy Haber Data');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `options`
--

CREATE TABLE `options` (
  `oid` int(11) NOT NULL,
  `soption` varchar(255) NOT NULL,
  `vote_number` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `options`
--

INSERT INTO `options` (`oid`, `soption`, `vote_number`) VALUES
(1, 'İyi', 0),
(2, 'Kötü', 0),
(3, 'Kolay', 1),
(4, 'Zor', 0);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `orders`
--

CREATE TABLE `orders` (
  `oid` int(11) NOT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `customerid` int(11) DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `orderstatus` bit(1) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `orders`
--

INSERT INTO `orders` (`oid`, `adress`, `customerid`, `order_date`, `orderstatus`, `productid`) VALUES
(4, 'Şişli/İstanbul', 2, '2021-10-27 00:01:08', b'0', 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `product`
--

CREATE TABLE `product` (
  `pid` int(11) NOT NULL,
  `padress` varchar(255) NOT NULL,
  `pcampaign` int(11) NOT NULL,
  `pcampaigndetail` varchar(255) NOT NULL,
  `pcampaignname` varchar(255) NOT NULL,
  `pdetail` varchar(255) NOT NULL,
  `platitude` varchar(255) NOT NULL,
  `plongitude` varchar(255) NOT NULL,
  `pname` varchar(255) NOT NULL,
  `pprice` int(11) NOT NULL,
  `psdetail` varchar(255) NOT NULL,
  `ptype` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `product`
--

INSERT INTO `product` (`pid`, `padress`, `pcampaign`, `pcampaigndetail`, `pcampaignname`, `pdetail`, `platitude`, `plongitude`, `pname`, `pprice`, `psdetail`, `ptype`) VALUES
(1, 'Şişli/İstanbul', 1, '%15', 'Öğrenci İndirimi', 'North Face xl', '150', '250', 'Kazak', 350, 'Siyah Kazak', 2),
(2, 'Beşiktaş/İstanbul', 2, '%13', 'Online Satış İndirimi', 'Apple Macbook Pro MYD82TU-A M1, 13.3 inç ekran genişliği ve IPS özelliği sunuyor. 2560x1600 piksel ekran çözünürlüğü', '120', '150', 'Apple Macbook Pro', 13584, 'MYD82TU-A M1 8GB Ram 256GB SSD macOS 13 inç', 2);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `product_categories`
--

CREATE TABLE `product_categories` (
  `pcsid` int(11) NOT NULL,
  `pcategoriesname` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `product_categories`
--

INSERT INTO `product_categories` (`pcsid`, `pcategoriesname`) VALUES
(1, 'Tekstil'),
(2, 'Teknoloji');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `product_categories_pcategory`
--

CREATE TABLE `product_categories_pcategory` (
  `product_categories_pcsid` int(11) NOT NULL,
  `pcategory_pcid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `product_category`
--

CREATE TABLE `product_category` (
  `pcid` int(11) NOT NULL,
  `pcategoryname` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `product_category`
--

INSERT INTO `product_category` (`pcid`, `pcategoryname`) VALUES
(1, 'Tekstil'),
(2, 'Teknoloji');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `product_images`
--

CREATE TABLE `product_images` (
  `iid` int(11) NOT NULL,
  `image_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `product_images`
--

INSERT INTO `product_images` (`iid`, `image_name`) VALUES
(1, 'bc7098cf-e376-42c7-b5b4-a75f00fb8aeb.jpg'),
(2, 'b43bddd5-422b-4f3d-ba56-bfb265b95530.jpg'),
(3, '6558dc96-69a3-43d4-a2c3-1c38cd4267b7.png'),
(4, '68f044eb-69f3-477a-be01-ffa4f0f67251.jpg');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `product_pcategories`
--

CREATE TABLE `product_pcategories` (
  `product_pid` int(11) NOT NULL,
  `pcategories_pcid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `product_pcategories`
--

INSERT INTO `product_pcategories` (`product_pid`, `pcategories_pcid`) VALUES
(1, 1),
(2, 2);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `product_pimages`
--

CREATE TABLE `product_pimages` (
  `product_pid` int(11) NOT NULL,
  `pimages_iid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `product_pimages`
--

INSERT INTO `product_pimages` (`product_pid`, `pimages_iid`) VALUES
(1, 1),
(2, 2),
(2, 3),
(2, 4);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `role`
--

CREATE TABLE `role` (
  `rid` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `role`
--

INSERT INTO `role` (`rid`, `name`) VALUES
(1, 'ROLE_MVC'),
(2, 'ROLE_REST'),
(3, 'ROLE_MUSTERI');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `survey`
--

CREATE TABLE `survey` (
  `sid` int(11) NOT NULL,
  `stitle` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `survey`
--

INSERT INTO `survey` (`sid`, `stitle`) VALUES
(1, 'Değerlendirme'),
(2, 'Security');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `survey_options`
--

CREATE TABLE `survey_options` (
  `survey_sid` int(11) NOT NULL,
  `options_oid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `survey_options`
--

INSERT INTO `survey_options` (`survey_sid`, `options_oid`) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `survey_option_rest`
--

CREATE TABLE `survey_option_rest` (
  `soid` int(11) NOT NULL,
  `socid` int(11) DEFAULT NULL,
  `sooid` int(11) DEFAULT NULL,
  `sosid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `survey_option_rest`
--

INSERT INTO `survey_option_rest` (`soid`, `socid`, `sooid`, `sosid`) VALUES
(1, 1, 3, NULL);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `users`
--

CREATE TABLE `users` (
  `uid` int(11) NOT NULL,
  `adress` varchar(255) NOT NULL,
  `companyname` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `sector` int(11) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `token_expired` bit(1) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `users`
--

INSERT INTO `users` (`uid`, `adress`, `companyname`, `enabled`, `mail`, `password`, `phone`, `sector`, `surname`, `token_expired`, `username`) VALUES
(1, 'Kadiköy', 'BilgeAdam', b'1', 'umut@mail.com', '$2a$10$LzwT6U6IfZOPMC9ZZrx.8ej0GuyoBJ95Dh.oFkIcVdyDkYmPrQJpi', '0555684', 2, 'Altın', b'1', 'Umut - Edit'),
(2, 'Kadiköy', 'BilgeAdam', b'1', 'rest@mail.com', '$2a$10$p.RUPZ.ip287MFzBrFbwYukDVSDpUyZUjbqoql2G5vclLEhP7eGKS', '0555684', 2, 'REST', b'1', 'Rest Admin');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `users_roles`
--

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(2, 2),
(1, 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `user_like`
--

CREATE TABLE `user_like` (
  `lid` int(11) NOT NULL,
  `customerid` int(11) DEFAULT NULL,
  `likedate` datetime DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  `userpoint` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `user_like`
--

INSERT INTO `user_like` (`lid`, `customerid`, `likedate`, `productid`, `userpoint`) VALUES
(3, 2, '2021-10-26 23:23:49', 1, 4),
(4, 2, '2021-10-26 23:24:00', 2, 5),
(5, 3, '2021-10-26 23:24:08', 2, 1);

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `advertisement`
--
ALTER TABLE `advertisement`
  ADD PRIMARY KEY (`aid`);

--
-- Tablo için indeksler `announcement`
--
ALTER TABLE `announcement`
  ADD PRIMARY KEY (`aid`);

--
-- Tablo için indeksler `contents`
--
ALTER TABLE `contents`
  ADD PRIMARY KEY (`cid`);

--
-- Tablo için indeksler `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cid`),
  ADD UNIQUE KEY `UK_dwk6cx0afu8bs9o4t536v1j5v` (`email`);

--
-- Tablo için indeksler `gallery`
--
ALTER TABLE `gallery`
  ADD PRIMARY KEY (`gid`);

--
-- Tablo için indeksler `gallery_gallery_images`
--
ALTER TABLE `gallery_gallery_images`
  ADD UNIQUE KEY `UK_jmb2yf6rvu0x6wsv0lw51i4cg` (`gallery_images_giid`),
  ADD KEY `FKq3vbg2r0bie12b01fcw9ce5n5` (`gallery_gid`);

--
-- Tablo için indeksler `gallery_image`
--
ALTER TABLE `gallery_image`
  ADD PRIMARY KEY (`giid`);

--
-- Tablo için indeksler `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`nid`),
  ADD KEY `FKeomakpg1dwoejj7yyrp6jdk5c` (`news_category_ncid`);

--
-- Tablo için indeksler `news_category`
--
ALTER TABLE `news_category`
  ADD PRIMARY KEY (`ncid`);

--
-- Tablo için indeksler `options`
--
ALTER TABLE `options`
  ADD PRIMARY KEY (`oid`);

--
-- Tablo için indeksler `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`oid`);

--
-- Tablo için indeksler `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`pid`);

--
-- Tablo için indeksler `product_categories`
--
ALTER TABLE `product_categories`
  ADD PRIMARY KEY (`pcsid`);

--
-- Tablo için indeksler `product_categories_pcategory`
--
ALTER TABLE `product_categories_pcategory`
  ADD KEY `FK5povf3fmpu96rpw5yb0hilanw` (`pcategory_pcid`),
  ADD KEY `FK6rcly8xka1kw5tjueoumvw4mu` (`product_categories_pcsid`);

--
-- Tablo için indeksler `product_category`
--
ALTER TABLE `product_category`
  ADD PRIMARY KEY (`pcid`);

--
-- Tablo için indeksler `product_images`
--
ALTER TABLE `product_images`
  ADD PRIMARY KEY (`iid`);

--
-- Tablo için indeksler `product_pcategories`
--
ALTER TABLE `product_pcategories`
  ADD KEY `FKj2tohjocf6b4llspufxk4enn` (`pcategories_pcid`),
  ADD KEY `FKgpfq0ykp4vf2j39w2acstl281` (`product_pid`);

--
-- Tablo için indeksler `product_pimages`
--
ALTER TABLE `product_pimages`
  ADD UNIQUE KEY `UK_ntjn1l4rkie624a2krjil1fj7` (`pimages_iid`),
  ADD KEY `FKi7vqqhggi6pgyj8nn7rqmu0m0` (`product_pid`);

--
-- Tablo için indeksler `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`rid`);

--
-- Tablo için indeksler `survey`
--
ALTER TABLE `survey`
  ADD PRIMARY KEY (`sid`);

--
-- Tablo için indeksler `survey_options`
--
ALTER TABLE `survey_options`
  ADD UNIQUE KEY `UK_5hy9oe3gwmkl34eecvr2k639` (`options_oid`),
  ADD KEY `FKei87al1s19iqu1kiyaqclo4bm` (`survey_sid`);

--
-- Tablo için indeksler `survey_option_rest`
--
ALTER TABLE `survey_option_rest`
  ADD PRIMARY KEY (`soid`);

--
-- Tablo için indeksler `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`uid`);

--
-- Tablo için indeksler `users_roles`
--
ALTER TABLE `users_roles`
  ADD KEY `FKt4v0rrweyk393bdgt107vdx0x` (`role_id`),
  ADD KEY `FK2o0jvgh89lemvvo17cbqvdxaa` (`user_id`);

--
-- Tablo için indeksler `user_like`
--
ALTER TABLE `user_like`
  ADD PRIMARY KEY (`lid`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `advertisement`
--
ALTER TABLE `advertisement`
  MODIFY `aid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Tablo için AUTO_INCREMENT değeri `announcement`
--
ALTER TABLE `announcement`
  MODIFY `aid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Tablo için AUTO_INCREMENT değeri `contents`
--
ALTER TABLE `contents`
  MODIFY `cid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Tablo için AUTO_INCREMENT değeri `customer`
--
ALTER TABLE `customer`
  MODIFY `cid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `gallery`
--
ALTER TABLE `gallery`
  MODIFY `gid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Tablo için AUTO_INCREMENT değeri `gallery_image`
--
ALTER TABLE `gallery_image`
  MODIFY `giid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Tablo için AUTO_INCREMENT değeri `news`
--
ALTER TABLE `news`
  MODIFY `nid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Tablo için AUTO_INCREMENT değeri `news_category`
--
ALTER TABLE `news_category`
  MODIFY `ncid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Tablo için AUTO_INCREMENT değeri `options`
--
ALTER TABLE `options`
  MODIFY `oid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Tablo için AUTO_INCREMENT değeri `orders`
--
ALTER TABLE `orders`
  MODIFY `oid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Tablo için AUTO_INCREMENT değeri `product`
--
ALTER TABLE `product`
  MODIFY `pid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Tablo için AUTO_INCREMENT değeri `product_category`
--
ALTER TABLE `product_category`
  MODIFY `pcid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Tablo için AUTO_INCREMENT değeri `product_images`
--
ALTER TABLE `product_images`
  MODIFY `iid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Tablo için AUTO_INCREMENT değeri `role`
--
ALTER TABLE `role`
  MODIFY `rid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `survey`
--
ALTER TABLE `survey`
  MODIFY `sid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Tablo için AUTO_INCREMENT değeri `survey_option_rest`
--
ALTER TABLE `survey_option_rest`
  MODIFY `soid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Tablo için AUTO_INCREMENT değeri `users`
--
ALTER TABLE `users`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `user_like`
--
ALTER TABLE `user_like`
  MODIFY `lid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `gallery_gallery_images`
--
ALTER TABLE `gallery_gallery_images`
  ADD CONSTRAINT `FKq3vbg2r0bie12b01fcw9ce5n5` FOREIGN KEY (`gallery_gid`) REFERENCES `gallery` (`gid`),
  ADD CONSTRAINT `FKrtri9ey2p33mlcby4vahcm0sm` FOREIGN KEY (`gallery_images_giid`) REFERENCES `gallery_image` (`giid`);

--
-- Tablo kısıtlamaları `news`
--
ALTER TABLE `news`
  ADD CONSTRAINT `FKeomakpg1dwoejj7yyrp6jdk5c` FOREIGN KEY (`news_category_ncid`) REFERENCES `news_category` (`ncid`);

--
-- Tablo kısıtlamaları `product_categories_pcategory`
--
ALTER TABLE `product_categories_pcategory`
  ADD CONSTRAINT `FK5povf3fmpu96rpw5yb0hilanw` FOREIGN KEY (`pcategory_pcid`) REFERENCES `product_category` (`pcid`),
  ADD CONSTRAINT `FK6rcly8xka1kw5tjueoumvw4mu` FOREIGN KEY (`product_categories_pcsid`) REFERENCES `product_categories` (`pcsid`);

--
-- Tablo kısıtlamaları `product_pcategories`
--
ALTER TABLE `product_pcategories`
  ADD CONSTRAINT `FKgpfq0ykp4vf2j39w2acstl281` FOREIGN KEY (`product_pid`) REFERENCES `product` (`pid`),
  ADD CONSTRAINT `FKj2tohjocf6b4llspufxk4enn` FOREIGN KEY (`pcategories_pcid`) REFERENCES `product_category` (`pcid`);

--
-- Tablo kısıtlamaları `product_pimages`
--
ALTER TABLE `product_pimages`
  ADD CONSTRAINT `FK4cy1vx62rgcs929w7c9q84fcc` FOREIGN KEY (`pimages_iid`) REFERENCES `product_images` (`iid`),
  ADD CONSTRAINT `FKi7vqqhggi6pgyj8nn7rqmu0m0` FOREIGN KEY (`product_pid`) REFERENCES `product` (`pid`);

--
-- Tablo kısıtlamaları `survey_options`
--
ALTER TABLE `survey_options`
  ADD CONSTRAINT `FKei87al1s19iqu1kiyaqclo4bm` FOREIGN KEY (`survey_sid`) REFERENCES `survey` (`sid`),
  ADD CONSTRAINT `FKib0umdapen0uniq5qmt6lawk7` FOREIGN KEY (`options_oid`) REFERENCES `options` (`oid`);

--
-- Tablo kısıtlamaları `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`uid`),
  ADD CONSTRAINT `FKt4v0rrweyk393bdgt107vdx0x` FOREIGN KEY (`role_id`) REFERENCES `role` (`rid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
