import java.util.ArrayList;
import java.util.List;

public class Siparis {
    private int siparisNo;
    private List<Urun> urunler;

    public Siparis(int siparisNo) {
        this.siparisNo = siparisNo;
        this.urunler = new ArrayList<>();
    }

    public void urunEkle(Urun urun) {
        urunler.add(urun);
    }

    public double toplamTutar() {
        double toplam = 0;
        for (Urun u : urunler) {
            toplam += u.getFiyat();
        }
        return toplam;
    }

    // YENİ VE ÇOK ÖNEMLİ:
    // Bu metot sayesinde veritabanına kaydederken listeyi alabileceksiniz.
    public List<Urun> getUrunler() {
        return urunler;
    }
    
    public int getSiparisNo() {
        return siparisNo;
    }
}