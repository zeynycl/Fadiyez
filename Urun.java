public class Urun {
    private int id; // YENİ: Veritabanındaki ID ile eşleşmesi için
    private String ad;
    private double fiyat;

    // Veritabanından çekerken ID'yi de alacağız
    public Urun(int id, String ad, double fiyat) {
        this.id = id;
        this.ad = ad;
        this.fiyat = fiyat;
    }
    
    // Sadece ismini ve fiyatını bildiğimiz yeni ürünler için (Opsiyonel)
    public Urun(String ad, double fiyat) {
        this(0, ad, fiyat); // ID yoksa 0 ata
    }

    public int getId() { return id; } // YENİ
    public String getAd() { return ad; }
    public double getFiyat() { return fiyat; }

    @Override
    public String toString() {
        return ad + " (" + fiyat + " TL)";
    }
}