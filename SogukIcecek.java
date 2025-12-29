package ProjeDeneme;

class SogukIcecek extends Urun {
    public SogukIcecek(int id, String ad, double fiyat, int stok) { 
    	super(id, ad, fiyat, stok); 
    	}
    @Override 
    public String getBilgi() {
    	return getAd() + " (Soğuk)"; 
    	}
}
