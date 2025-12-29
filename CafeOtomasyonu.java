package ProjeDeneme;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
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
        // 1. Verileri veritabanından çek
        verileriYukle();

        // 2. Pencere Ayarları
        setTitle("Fadiyez Kafe");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- ÜST PANEL (KATEGORİLER) ---
        // Görseldeki tablo verilerine göre birebir kategori isimleri:
        JPanel pnlUst = new JPanel(new GridLayout(1, 4, 10, 0));
        pnlUst.setPreferredSize(new Dimension(0, 80));
        pnlUst.setBorder(new EmptyBorder(10, 10, 0, 10));

        String[] kategoriler = {"Tatlı", "Atıştırmalık", "Sıcak İçecek", "Soğuk İçecek"};
        for (String kat : kategoriler) {
            JButton btn = new JButton(kat);
            btn.setFont(new Font("Times New Roman", Font.BOLD, 14));
            btn.setBackground(new Color(52, 152, 219));
            btn.setForeground(Color.WHITE);
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
        btnOde.setBackground(new Color(39, 174, 96));
        btnOde.setForeground(Color.WHITE);
        btnOde.setPreferredSize(new Dimension(300, 0));
        btnOde.addActionListener(e -> satisiTamamla());
        
        pnlAlt.add(btnOde, BorderLayout.EAST);
        add(pnlAlt, BorderLayout.SOUTH);

        // Başlangıçta hepsini göster
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
                // Buton Tasarımı
                String htmlYazi = "<html><center>" 
                        + urun.getAd() + "<br>" 
                        + "<font size='4' color='green'>" + urun.getFiyat() + " TL</font><br>"
                        + "<i>Stok: " + urun.getStok() + "</i>"
                        + "</center></html>";

                JButton btn = new JButton(htmlYazi);
                btn.setPreferredSize(new Dimension(120, 120));
                btn.setBackground(Color.WHITE);
                
                // Stok kontrolü
                if (urun.getStok() <= 0) {
                    btn.setEnabled(false);
                    btn.setBackground(new Color(230, 230, 230));
                    btn.setText(urun.getAd() + "TÜKENDİ");
                }

                btn.addActionListener(e -> sepeteEkle(urun));
                pnlUrunler.add(btn);
            }
        }
        
        pnlUrunler.revalidate(); 
        pnlUrunler.repaint();
    }

    private void sepeteEkle(Urun urun) {
        long sepettekiSayi = sepetListesi.stream().filter(u -> u.getId() == urun.getId()).count();
        
        if (sepettekiSayi < urun.getStok()) {
            sepetListesi.add(urun);
            // UML'deki getBilgi() metodunu burada kullanıyoruz
            sepetModel.addRow(new Object[]{urun.getBilgi(), urun.getFiyat() + " TL"});
            
            toplamTutar += urun.getFiyat();
            lblToplam.setText("  TOPLAM: " + toplamTutar + " TL");
        } else {
            JOptionPane.showMessageDialog(this, "Stok Yetersiz!");
        }
    }
}