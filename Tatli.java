package Fadiyez;

class Tatli extends Urun {
    public Tatli(int id, String ad, double fiyat, int stok) {
    	super(id, ad, fiyat, stok); 
    	}
    
    @Override 
    public String getBilgi() {
    	return getAd() + " (Tatlı)"; 
    	}
}
