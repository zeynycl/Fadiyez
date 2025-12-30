package Fadiyez;

abstract class Urun implements IMenuItem {
	
    private String ad;
    private double fiyat;
    

    private int id;
    private int stok;

    public Urun(int id, String ad, double fiyat, int stok) {
        this.id = id;
        this.ad = ad;
        this.fiyat = fiyat;
        this.stok = stok;
    }

    @Override
    public double getFiyat() {
        return fiyat;
    }

    public String getAd() { 
    	return ad;
    }
    public int getId() { 
    	return id;
    }
    public int getStok() { 
    	return stok; 
    }
}