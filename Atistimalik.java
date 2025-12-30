package Fadiyez;

class Atistirmalik extends Urun {
    public Atistirmalik(int id, String ad, double fiyat, int stok) {
    	super(id, ad, fiyat, stok); 
    	}
    
    @Override 
    public String getBilgi() { 
    	return getAd() + " (Atıştırmalık)"; 
    	}
}
