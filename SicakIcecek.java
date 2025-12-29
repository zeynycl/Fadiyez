package ProjeDeneme;

class SicakIcecek extends Urun {
    public SicakIcecek(int id, String ad, double fiyat, int stok){
    	super(id, ad, fiyat, stok); 
    }
    @Override 
    public String getBilgi() {  
    	return getAd() + " (Sıcak)"; 
    }
}
