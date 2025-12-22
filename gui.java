package Proje;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class gui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField kullanici_adi;
	private JPasswordField Sifre;
	ResultSet myrs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui frame = new gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 224));
		panel.setBounds(262, 89, 289, 289);
		contentPane.add(panel);
		panel.setLayout(null);
		
		kullanici_adi = new JTextField();
		kullanici_adi.setBounds(45, 65, 200, 29);
		panel.add(kullanici_adi);
		kullanici_adi.setColumns(10);
		
		Sifre = new JPasswordField();
		Sifre.setBounds(45, 146, 200, 27);
		panel.add(Sifre);
		
		JLabel adi = new JLabel("Kullanıcı Adı");
		adi.setFont(new Font("Times New Roman", Font.BOLD, 14));
		adi.setBounds(46, 43, 182, 20);
		panel.add(adi);
		
		JLabel sifre = new JLabel("Şifre");
		sifre.setFont(new Font("Times New Roman", Font.BOLD, 14));
		sifre.setBounds(45, 127, 160, 20);
		panel.add(sifre);
		
		JLabel bilgi = new JLabel("");
		bilgi.setBounds(10, 253, 269, 26);
		panel.add(bilgi);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 192, 203));
		panel_1.setBounds(145, 25, 516, 41);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SİSTEM GİRİŞ EKRANI");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 516, 41);
		panel_1.add(lblNewLabel);

		
		JButton Giris = new JButton("GİRİŞ");
		Giris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pass=new String (Sifre.getText());
				String name =kullanici_adi.getText();
				String sql="SELECT * FROM sistemgiris WHERE kullanici_adi='"+name+"'" ;
				try {
					myrs= baglama.bul(sql);
					while(myrs.next()) {
						if(name.equals(myrs.getString("kullanici_adi"))&& pass.equals(myrs.getString("sifre"))) {
							System.out.println("Giris Yapildi.");
							bilgi.setText("Giriş Yapildi.");
						}
						else {
							System.out.println("Giriş Yapilamadi.");
							bilgi.setText("Şifre veya Kullanıcı Adı hatalı.");
						}
						
					}
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		
		Giris.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		Giris.setBounds(73, 206, 137, 29);
		panel.add(Giris);
		

	}
}
