
import java.sql.*;
import javax.swing.JOptionPane;

public class Database {

    private Connection connection;

    public Database() {
    }

    public int insert(String query) {
        int x = 1;
        try {
            x = connection.createStatement().executeUpdate(query);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage(), "Oops", JOptionPane.ERROR_MESSAGE);
        }
        return x;

    }

    public int delete(String query) {
        return insert(query);
    }

    public int update(String query) {
        return insert(query);
    }

    public ResultSet read(String query) {
        ResultSet rs = null;

        try {

            rs = connection.createStatement().executeQuery(query);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error in Read Db: " + ex.getMessage(), "Oops", JOptionPane.ERROR_MESSAGE);
        }

        return rs;
    }

    public void openConnection() {

        //<editor-fold defaultstate="collapsed" desc="Connection1">
        try {

            String url = "jdbc:mysql://localhost/fyp3?connectTimeout=" + 2000;
            connection = DriverManager.getConnection(url, "root", "");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "While Opening Connection 1: " + ex.getMessage());
            ex.printStackTrace();
        }
        //</editor-fold>

    }

    public int closeConnection() {
        int x = 0;

        try {
            connection.close();

            x = 1;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error CLOSE CONN: " + ex.getMessage(), "Oops", JOptionPane.ERROR_MESSAGE);
        }

        return x;
    }

}
