package Fadiyez;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class CafeOtomasyonu extends JFrame {

    private ArrayList<Urun> tumUrunler = new ArrayList<>();
    private ArrayList<Urun> sepetListesi = new ArrayList<>();
    private double toplamTutar = 0.0;

    private JPanel pnlUrunler;
    private DefaultTableModel sepetModel;
    private JLabel lblToplam;
    
    private static final long serialVersionUID = 1L;

    public CafeOtomasyonu() {
        
        verileriYukle();

        // Pencere Ayarları
        setTitle("Fadiyez Kafe");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- ÜST PANEL (KATEGORİLER) ---
        JPanel pnlUst = new JPanel(new GridLayout(1, 4, 10, 0));
        pnlUst.setPreferredSize(new Dimension(0, 80));
        pnlUst.setBorder(new EmptyBorder(10, 10, 0, 10));

        String[] kategoriler = {"Tatlı", "Atıştırmalık", "Sıcak İçecek", "Soğuk İçecek"};
        for (String kat : kategoriler) {
            JButton btn = new JButton(kat);
            btn.setFont(new Font("Times New Roman", Font.BOLD, 20));
            btn.setBackground(new Color(255, 255, 224));
            btn.setForeground(Color.BLACK);
            btn.addActionListener(e -> urunleriListele(kat));
            pnlUst.add(btn);
        }
        add(pnlUst, BorderLayout.NORTH);

        // --- SOL PANEL (SEPET) ---
        JPanel pnlSol = new JPanel(new BorderLayout());
        pnlSol.setPreferredSize(new Dimension(350, 0));
        pnlSol.setBorder(BorderFactory.createTitledBorder("SEPET"));

        sepetModel = new DefaultTableModel(new String[]{"Ürün", "Fiyat"}, 0);
        JTable tblSepet = new JTable(sepetModel);
        tblSepet.setRowHeight(30);
        
        pnlSol.add(new JScrollPane(tblSepet), BorderLayout.CENTER);
        add(pnlSol, BorderLayout.WEST);
        
        JButton btnSil = new JButton("SEÇİLİ ÜRÜNÜ ÇIKAR");
        btnSil.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnSil.setBackground(Color.DARK_GRAY); 
        btnSil.setForeground(Color.WHITE);
        btnSil.setPreferredSize(new Dimension(0, 40)); 

 
        btnSil.addActionListener(e -> sepettenCikar(tblSepet));

        pnlSol.add(btnSil, BorderLayout.SOUTH); 

        add(pnlSol, BorderLayout.WEST);

        // --- ORTA PANEL (ÜRÜNLER) ---
        pnlUrunler = new JPanel(new GridLayout(0, 3, 10, 10));
        pnlUrunler.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(pnlUrunler), BorderLayout.CENTER);

        // --- ALT PANEL (ÖDEME) ---
        JPanel pnlAlt = new JPanel(new BorderLayout());
        pnlAlt.setPreferredSize(new Dimension(0, 100));
        pnlAlt.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.LIGHT_GRAY));

        lblToplam = new JLabel("  TOPLAM: 0.00 TL");
        lblToplam.setFont(new Font("Times New Roman", Font.BOLD, 20));
        pnlAlt.add(lblToplam, BorderLayout.CENTER);

        JButton btnOde = new JButton("SİPARİŞİ TAMAMLA");
        btnOde.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnOde.setBackground(new Color(255, 192, 203));
        btnOde.setForeground(Color.WHITE);
        btnOde.setPreferredSize(new Dimension(300, 0));
        btnOde.addActionListener(e -> satisiTamamla());
        
        pnlAlt.add(btnOde, BorderLayout.EAST);
        add(pnlAlt, BorderLayout.SOUTH);
        
        JButton btnYenile = new JButton("STOKLARI YENİLE");
        btnYenile.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnYenile.setBackground(new Color(135, 206, 250)); 
        btnYenile.setForeground(Color.WHITE);
        btnYenile.setPreferredSize(new Dimension(220, 0));

        btnYenile.addActionListener(e -> stoklariFullle()); 

        pnlAlt.add(btnYenile, BorderLayout.WEST);

        urunleriListele("Hepsi");
    }

    // --- VERİTABANI: VERİ ÇEKME ---
    private void verileriYukle() {
        tumUrunler.clear();
        try (Connection conn = baglama.getBaglanti();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM urunler")) {

            while (rs.next()) {
              
                int id = rs.getInt("idurunler"); 
                String ad = rs.getString("ad");
                String kat = rs.getString("kategori");
                double fiyat = rs.getDouble("fiyat");
                int stok = rs.getInt("stok");

                Urun urun = null;
                
                
                if (kat.equals("Tatlı")) urun = new Tatli(id, ad, fiyat, stok);
                else if (kat.equals("Sıcak İçecek")) urun = new SicakIcecek(id, ad, fiyat, stok);
                else if (kat.equals("Soğuk İçecek")) urun = new SogukIcecek(id, ad, fiyat, stok);
                else if (kat.equals("Atıştırmalık")) urun = new Atistirmalik(id, ad, fiyat, stok);

                if (urun != null) {
                    tumUrunler.add(urun);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Veri Yükleme Hatası:\n" + e.getMessage());
        }
    }

 
    private void satisiTamamla() {
        if (sepetListesi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sepet boş!");
            return;
        }

        try (Connection conn = baglama.getBaglanti()) {
            
            String sql = "UPDATE urunler SET stok = stok - 1 WHERE idurunler = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Urun urun : sepetListesi) {
                pstmt.setInt(1, urun.getId());
                pstmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Satış Başarılı! Stoklar Güncellendi.");
            
            
            sepetListesi.clear();
            sepetModel.setRowCount(0);
            toplamTutar = 0;
            lblToplam.setText("  TOPLAM: 0.00 TL");
            
            
            verileriYukle();
            urunleriListele("Hepsi");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Satış Hatası:\n" + e.getMessage());
        }
    }

 
    private void urunleriListele(String filtreKategori) {
        pnlUrunler.removeAll(); 

        for (Urun urun : tumUrunler) {
            boolean goster = false;
            
           
            if (filtreKategori.equals("Hepsi")) goster = true;
            else if (filtreKategori.equals("Tatlı") && urun instanceof Tatli) goster = true;
            else if (filtreKategori.equals("Sıcak İçecek") && urun instanceof SicakIcecek) goster = true;
            else if (filtreKategori.equals("Soğuk İçecek") && urun instanceof SogukIcecek) goster = true;
            else if (filtreKategori.equals("Atıştırmalık") && urun instanceof Atistirmalik) goster = true;

            if (goster) {
            	
            	JPanel urunKarti = new JPanel();
            	urunKarti.setPreferredSize(new Dimension(120,120));
            	urunKarti.setLayout(new BoxLayout(urunKarti , BoxLayout.Y_AXIS));
            	urunKarti.setBackground(Color.WHITE);
            	urunKarti.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            	if(urun.getStok()<= 0) {
            		urunKarti.setBackground(new Color(240, 24, 240));
            	}
            	JLabel lblAd =new JLabel(urun.getAd());
            	lblAd.setFont(new Font("Times New Roman", Font.BOLD, 20));
            	lblAd.setAlignmentX(Component.CENTER_ALIGNMENT);
            	
            	JLabel lblFiyat = new JLabel(urun.getFiyat()+ " TL");
            	lblFiyat.setFont(new Font("Times New Roman", Font.BOLD, 16));
                lblFiyat.setForeground(new Color(34, 139, 34));
                lblFiyat.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                String stokYazisi = (urun.getStok() <= 0) ? "TÜKENDİ" : "Stok: " + urun.getStok() + " ";
                JLabel lblStok = new JLabel(stokYazisi);
                lblStok.setFont(new Font("Times New Roman", Font.ITALIC, 12));
                lblStok.setForeground(Color.GRAY);
                if(urun.getStok()<= 0)
                	lblStok.setForeground(Color.RED);
                lblStok.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                urunKarti.add(Box.createVerticalGlue());
                urunKarti.add(lblAd);
                urunKarti.add(Box.createVerticalStrut(5));
                urunKarti.add(lblFiyat);
                urunKarti.add(Box.createVerticalStrut(5));
                urunKarti.add(lblStok);
                urunKarti.add(Box.createVerticalGlue());
                
                if (urun.getStok()> 0) {
                	urunKarti.setCursor((new Cursor(Cursor.HAND_CURSOR)));
                	urunKarti.addMouseListener(new java.awt.event.MouseAdapter() {
                		@Override
                		public void mouseClicked(java.awt.event.MouseEvent evt) {
                			sepeteEkle(urun);
                		}
                		@Override
                		public void mouseEntered(java.awt.event.MouseEvent evt) {
                			urunKarti.setBackground(new Color(230, 240, 255));
                		}
                		@Override
                		public void mouseExited(java.awt.event.MouseEvent evt) {
                			urunKarti.setBackground(Color.WHITE);
                		}
                	});
                }
                
                pnlUrunler.add(urunKarti);
                   
            }
        }
        
        pnlUrunler.revalidate(); 
        pnlUrunler.repaint();
    }

    private void sepeteEkle(Urun urun) {
        long sepettekiSayi = sepetListesi.stream().filter(u -> u.getId() == urun.getId()).count();
        
        if (sepettekiSayi < urun.getStok()) {
            sepetListesi.add(urun);
            sepetModel.addRow(new Object[]{urun.getBilgi(), urun.getFiyat() + " TL"});
            
            toplamTutar += urun.getFiyat();
            lblToplam.setText("  TOPLAM: " + toplamTutar + " TL");
        } else {
            JOptionPane.showMessageDialog(this, "Stok Yetersiz!");
        }
    }
    private void stoklariFullle() {
        int secim = JOptionPane.showConfirmDialog(this, 
                "Stoklar güncellenecek.\n Onaylıyor musunuz?", 
                "Stok Yenileme", JOptionPane.YES_NO_OPTION);

        if (secim == JOptionPane.YES_OPTION) {
            try (Connection conn = baglama.getBaglanti()) {
                
                String sql = "UPDATE urunler SET stok = hedef_stok"; 
                
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Depo doldu! Stoklar yenilendi.");
                

                verileriYukle();
                urunleriListele("Hepsi");

            } catch (SQLException e) {
 
            }
        }
    }
    private void sepettenCikar(JTable tblSepet) {
        int seciliSatir = tblSepet.getSelectedRow();

        if (seciliSatir != -1) {
            
            Urun silinecekUrun = sepetListesi.get(seciliSatir);
            
            toplamTutar -= silinecekUrun.getFiyat();
            lblToplam.setText("  TOPLAM: " + toplamTutar + " TL");

            sepetListesi.remove(seciliSatir); 
            sepetModel.removeRow(seciliSatir); 

        } else {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için listeden bir ürün seçin!");
        }
    }
}