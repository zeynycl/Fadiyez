package ProjeDeneme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class baglama {
	static Connection mycon;
	static Statement stm;
	static final String DB_URL="jdbc:mysql://localhost:3306/projedeneme";
	static final String USER ="root";
	static final String PASS= "15052005";
	
	static Connection getBaglanti() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
	
	static ResultSet yap() throws SQLException {
		ResultSet mySet=null;
		mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/projedeneme", "root" , "15052005");
		stm=mycon.createStatement();
		return mySet;
		}

	static void ekle(String sql) throws SQLException {
		stm.executeUpdate(sql);
	}
	static void update(String sql) throws SQLException {
		stm.executeUpdate(sql);
	}
	static void delete(String sql) throws SQLException {
		stm.executeUpdate(sql);
	}
	static ResultSet bul(String sql) throws SQLException {
		ResultSet myrs=null;
		mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/projedeneme", "root" , "15052005");
		stm=mycon.createStatement();
		myrs=stm.executeQuery(sql);
		return myrs;
	}
	
}
