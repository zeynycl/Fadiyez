KAFE OTOMASYON VE STOK TAKİP SİSTEMİ
  Ders Adı: Nesne Tabanlı Programlama 
  Proje Adı: Kafe Otomasyonu (Fadiyez Kafe)
  Hazırlayanlar: 
    Zeynep Yücel 
    Dilara Kale 
    Ahmet Faruk Arda 
1. GİRİŞ VE PROJENİN AMACI
Günümüzde işletmelerin verimliliğini artırmak adına manuel süreçlerin dijitalleşmesi kaçınılmazdır. Bu projede, bir kafenin sipariş alma, satış yapma ve anlık stok takibi süreçlerini yönetebilen masaüstü tabanlı bir Kafe Otomasyon Sistemi geliştirilmiştir.
Projemizin temel amacı; personelin hızlı ve hatasız sipariş almasını sağlamak, satılan ürünlerin stoktan otomatik düşülmesini gerçekleştirerek stok yönetimini kolaylaştırmak ve Nesne Tabanlı Programlama dersinde öğrendiklerimizi gerçek bir senaryo üzerinde uygulamaktır.
2. KULLANILAN TEKNOLOJİLER VE ARAÇLAR
Projenin geliştirilmesinde aşağıdaki teknolojiler kullanılmıştır:
•	Programlama Dili: Java (JDK 21)
•	Arayüz (GUI): Java Swing & AWT kütüphaneleri
•	Veritabanı: MySQL
•	Veritabanı Bağlantısı: JDBC (Java Database Connectivity)
•	IDE: Eclipse 
3. SİSTEM MİMARİSİ VE TASARIM
Projemizi geliştirirken kodun sürdürülebilirliği ve tekrar kullanılabilirliği açısından Nesne Tabanlı Tasarım ilkelerine sadık kaldık.
3.1. UML Sınıf Yapısı ve OOP Prensipleri
Sistemimizde ürünleri modellemek için Kalıtım ve Polimorfizm yapısını kullandık:
•	IMenuItem (Interface): Tüm satılabilir ürünlerin sahip olması gereken temel yetenekleri (getBilgi, getFiyat) belirleyen bir sözleşme olarak tasarlandı.
•	Urun (Abstract Class): Ortak özellikleri (Ad, Fiyat, Stok, ID) tutan ve IMenuItem arayüzünü implemente eden soyut sınıftır. Bu sınıftan doğrudan nesne üretilmez, bir çatı görevi görür.
•	Alt Sınıflar (Concrete Classes): SicakIcecek, SogukIcecek, Tatli ve Atistirmalik sınıfları Urun sınıfından türetilmiştir. Her sınıf kendi getBilgi() metodunu ezerek (Override) polimorfik davranış sergiler (Örn: "Çay (Sıcak)" çıktısı üretmek gibi).
3.2. Veritabanı Tasarımı 
Veri kalıcılığını sağlamak için MySQL üzerinde iki temel tablo oluşturduk:
1.	kullanicilar Tablosu: Personel giriş güvenliği için kullanici_adi ve sifre bilgilerini tutar.
2.	urunler Tablosu: Ürünlerin detaylarını saklar. Sütunlarımız: idurunler (Primary Key), ad, kategori, fiyat, stok.
4. UYGULAMA DETAYLARI VE İŞLEYİŞ
4.1. Veritabanı Bağlantısı (baglama.java)
Kod tekrarını önlemek ve bağlantı yönetimini merkezileştirmek amacıyla Singleton yapısına benzer statik bir baglama sınıfı oluşturduk. getBaglanti() metodu ile uygulamanın herhangi bir yerinden DriverManager aracılığıyla veritabanına erişim sağladık.
4.2. Güvenli Giriş Sistemi (GirisEkrani.java)
Uygulama açılışında personeli karşılayan bu ekranda, girilen veriler SQL sorgusu ile veritabanında kontrol edilmektedir. Güvenlik açığı oluşturmamak adına ve SQL Injection riskine karşı PreparedStatement yapısı veya parametreli sorgulara zemin hazırlayan bir mantık kurguladık. Başarılı girişte ana panel açılırken, başarısız denemelerde kullanıcı uyarılmaktadır.
4.3. Dinamik Satış Ekranı (CafeOtomasyonu.java)
Burası projenin kalbidir. Ekranın çalışma mantığı şu şekildedir:
•	Dinamik Buton Oluşturma: Program açıldığında veritabanındaki urunler tablosu okunur. Her bir satır için ilgili sınıftan (Örn: Tatli) bir nesne üretilir (Factory Pattern benzeri bir yaklaşımla) ve ekrana buton olarak basılır.
•	HTML Destekli Arayüz: Butonların üzerindeki yazıların (Ürün adı, renkli fiyat, italik stok bilgisi) düzgün görünmesi için Java Swing butonlarında HTML etiketleri kullandık.
•	Sepet Mantığı: Seçilen ürünler bir ArrayList içinde tutulur ve DefaultTableModel kullanılarak JTable (Tablo) üzerinde kullanıcıya gösterilir.
4.4. Stok Yönetimi ve Algoritma
Projenin en kritik özelliği stok kontrolüdür. İki aşamalı bir kontrol mekanizması geliştirdik:
1.	RAM Üzerinde Kontrol (Sepete Ekleme): Kullanıcı bir ürünü sepete eklemek istediğinde, program o anki sepet içeriğini ve ürünün veritabanındaki stoğunu karşılaştırır. Stok yetersizse eklemeyi engeller.
2.	Veritabanı Üzerinde Kontrol (Satış Tamamlama): "Siparişi Tamamla" butonuna basıldığında, sepetteki her ürün için veritabanına bir UPDATE sorgusu gönderilir (stok = stok - 1).
5. KARŞILAŞILAN ZORLUKLAR VE ÇÖZÜMLER
Geliştirme sürecinde karşılaştığımız en büyük zorluk, farklı kategorideki ürünlerin tek bir liste içinde nasıl yönetileceğiydi. Bu sorunu, tüm alt sınıfları (Tatlı, İçecek vb.) kapsayan ArrayList<Urun> listesi oluşturarak ve Polimorfizm özelliğini kullanarak çözdük. Bu sayede tek bir döngü ile tüm ürünleri yönetebildik.
Ayrıca, Swing arayüzünde butonların stok azaldığında anlık olarak güncellenmesi gerekiyordu. Bunu da satış işlemi tamamlandıktan sonra verileriYukle() metodunu tekrar çağırıp arayüzü "repaint" (yeniden çizdirme) yaparak çözdük.
6. SONUÇ 
Bu proje ile teorik olarak öğrendiğimiz Java ve Veritabanı bilgilerini somut bir ürüne dönüştürme fırsatı bulduk. Sistemimiz şu an sorunsuz bir şekilde sipariş alıp stok düşebilmektedir.


