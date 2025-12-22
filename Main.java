public class Main {
    public static void main(String[] args) {
        // Ürünleri tanımlayalım (Veritabanı olmadığı için ID'ye 0 verdik şimdilik)
        Urun cay = new Urun(1, "Çay", 15.0);
        Urun kahve = new Urun(2, "Latte", 55.0);
        Urun tost = new Urun(3, "Kaşarlı Tost", 60.0);

        // Kasayı açıyoruz
        Kasa kasa = new Kasa();

        System.out.println("--- KAFE SİSTEMİ AÇILDI ---\n");

        // --- Müşteri 1 ---
        Siparis musteri1 = kasa.yeniSiparisBaslat();
        musteri1.urunEkle(cay);
        musteri1.urunEkle(tost);
        
        // Ödeme al
        System.out.println("Müşteri 1 (" + musteri1.getSiparisNo() + ") Toplam: " + musteri1.toplamTutar());
        kasa.odemeAl(musteri1);


        // --- Müşteri 2 ---
        Siparis musteri2 = kasa.yeniSiparisBaslat();
        musteri2.urunEkle(kahve);
        musteri2.urunEkle(kahve); 
        
        // Ödeme al
        System.out.println("Müşteri 2 (" + musteri2.getSiparisNo() + ") Toplam: " + musteri2.toplamTutar());
        kasa.odemeAl(musteri2);

        // --- GÜN SONU ---
        // Artık bu kod hata vermeyecek:
        kasa.gunSonuRaporu();
    }
}