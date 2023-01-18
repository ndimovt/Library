import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class FrontPage extends JFrame {
    private JButton adminLoginButton;
    private JComboBox<String> comboBox1;
    private JTextField textField1;
    private JPanel panel;
    private JButton exitButton;
    private JButton searchButton;
    private JButton resetButton;
    private JTextArea textArea1;
    public FrontPage() {
        setSize(750, 520);
        setContentPane(panel);
        setTitle("Front Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLogin al = new AdminLogin(null);
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textArea1.setText("");
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInfo();
            }
        });
        setVisible(true);
    }
    private void showInfo() {
        try {
            String box1 = (String) comboBox1.getSelectedItem();
            if(box1.equals("Book Name")){
                getAuthorInfo();
            } else if (box1.equals("Author Name")) {
                getBookInfo();
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }
    private void getAuthorInfo() {
        try{
            String word = textField1.getText();
            final String URL = "jdbc:mysql://localhost/biblioteka?serverTimezone=UTC";
            final String NAME = "root";
            final String PASSWORD = "sheeuser123456@";
            Connection con = DriverManager.getConnection(URL,NAME,PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM bookinformation WHERE book_name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,word);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                Author au = new Author();
                textArea1.setText(au.AuthorName = rs.getString("author_name"));
            }else{
                JOptionPane.showMessageDialog(null,"Book "+word+" is not available!","Error",JOptionPane.ERROR_MESSAGE);
            }
            con.close();
            st.close();
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
    }
    private void getBookInfo() {
        try{
            String word = textField1.getText();
            final String URL = "jdbc:mysql://localhost/biblioteka?serverTimezone=UTC";
            final String NAME = "root";
            final String PASSWORD = "sheeuser123456@";
            Connection con = DriverManager.getConnection(URL,NAME,PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM bookinformation WHERE author_name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,word);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                textArea1.append(rs.getString("book_name")+"\n");
            }if(word.isEmpty()){
                JOptionPane.showMessageDialog(null,"Please enter author name!","Error",JOptionPane.ERROR_MESSAGE);
            }
            con.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
