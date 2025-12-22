public class Kasa {
    private int gunlukSiparisSayaci; 
    private double kasadakiPara;     

    public Kasa() {
        this.gunlukSiparisSayaci = 1; 
        this.kasadakiPara = 0.0;
    }

    // Yeni sipariş oluşturur
    public Siparis yeniSiparisBaslat() {
        Siparis s = new Siparis(gunlukSiparisSayaci);
        gunlukSiparisSayaci++; 
        return s;
    }

    // Ödeme alır ve kasaya ekler
    public void odemeAl(Siparis siparis) {
        double tutar = siparis.toplamTutar();
        
        if (tutar > 0) {
            kasadakiPara += tutar; 
            System.out.println(">> Ödeme Alındı: " + tutar + " TL");
        }
    }

    // --- YARIN GUI İÇİN LAZIM OLACAK METOTLAR ---
    public double getKasadakiPara() {
        return kasadakiPara;
    }

    public int getToplamSiparisSayisi() {
        return gunlukSiparisSayaci - 1;
    }

    // --- MAIN DOSYASINDAKİ HATAYI GİDEREN METOT ---
    // Bu metot konsola yazdırmak içindir.
    public void gunSonuRaporu() {
        System.out.println("\n--------------------------------");
        System.out.println(">>> GÜN SONU RAPORU <<<");
        System.out.println("Toplam Satılan Sipariş: " + (gunlukSiparisSayaci - 1));
        System.out.println("Kasadaki Toplam Para: " + kasadakiPara + " TL");
        System.out.println("--------------------------------");
    }
}