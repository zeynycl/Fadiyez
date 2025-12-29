package ProjeDeneme;

abstract class Urun implements IMenuItem {
    // UML'deki private değişkenler
    private String ad;
    private double fiyat;
    
    // Veritabanı için eklediğimiz değişkenler (UML'de görünmese de şart)
    private int id; // Veritabanındaki 'idurunler' sütunu için
    private int stok;

    public Urun(int id, String ad, double fiyat, int stok) {
        this.id = id;
        this.ad = ad;
        this.fiyat = fiyat;
        this.stok = stok;
    }

    // UML'deki getFiyat metodu
    @Override
    public double getFiyat() {
        return fiyat;
    }

    // Getter Metotları
    public String getAd() { return ad; }
    public int getId() { return id; }
    public int getStok() { return stok; }
}