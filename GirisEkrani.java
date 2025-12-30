package Fadiyez; 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane; 
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GirisEkrani extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField kullanici_adi;
	private JPasswordField Sifre;
	ResultSet myrs;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GirisEkrani frame = new GirisEkrani();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GirisEkrani() {
		
		setTitle("Fadiyez Kafe");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        
		setBounds(100, 100, 1191, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 224));
		panel.setBounds(245, 117, 700, 330);
		contentPane.add(panel);
		panel.setLayout(null);
		
		kullanici_adi = new JTextField();
		kullanici_adi.setBounds(248, 73, 200, 29);
		panel.add(kullanici_adi);
		kullanici_adi.setColumns(10);
		
		Sifre = new JPasswordField();
		Sifre.setBounds(248, 154, 200, 27);
		panel.add(Sifre);
		
		JLabel adi = new JLabel("Kullanıcı Adı");
		adi.setFont(new Font("Times New Roman", Font.BOLD, 16));
		adi.setBounds(249, 51, 182, 20);
		panel.add(adi);
		
		JLabel sifre = new JLabel("Şifre");
		sifre.setFont(new Font("Times New Roman", Font.BOLD, 16));
		sifre.setBounds(248, 135, 160, 20);
		panel.add(sifre);
		
		JLabel bilgi = new JLabel("");
		bilgi.setBounds(10, 253, 269, 26);
		panel.add(bilgi);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 192, 203));
		panel_1.setBounds(0, 25, 1177, 62);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SİSTEM GİRİŞ EKRANI");
		lblNewLabel.setBounds(0, 10, 1177, 41);
		panel_1.add(lblNewLabel);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JButton Giris = new JButton("GİRİŞ");
		

		Giris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String pass = new String(Sifre.getPassword());
				String name = kullanici_adi.getText();
				
				String sql = "SELECT * FROM kullanicilar WHERE kullanici_adi='" + name + "'";
				
				try {
					myrs = baglama.bul(sql);
					
					boolean girisBasarili = false; 
					
					while(myrs.next()) {
						
						if(name.equals(myrs.getString("kullanici_adi")) && pass.equals(myrs.getString("sifre"))) {
							
							
							bilgi.setText("Giriş Başarılı");
							
							CafeOtomasyonu anaEkran = new CafeOtomasyonu();
							anaEkran.setVisible(true);
							
							dispose(); 
							
							girisBasarili = true;
							break; 
						}
					}
					
					if(!girisBasarili) {
						bilgi.setText("Kullanıcı adı veya şifre hatalı!");
						JOptionPane.showMessageDialog(null, "Giriş Başarısız! Lütfen bilgileri kontrol edin.");
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Veritabanı Hatası: " + e1.getMessage());
				}
			}
		});
		// ---------------------------
		
		Giris.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		Giris.setBounds(266, 215, 165, 44);
		panel.add(Giris);
		
		setLocationRelativeTo(null);
	}
}
