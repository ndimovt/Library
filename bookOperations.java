import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class bookOperations extends JDialog{
    private JTextField textField1;
    private JPanel pane;
    private JTextField textField2;
    private JButton addBookButton;
    private JButton exitButton;
    private JButton removeBookButton;
    private JButton resetButton;
    public bookOperations (JFrame parent){
        super(parent);
        setTitle("Operations");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setMinimumSize(new Dimension(740,520));
        setContentPane(pane);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBook();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textField2.setText("");
            }
        });
        removeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });
        setVisible(true);
    }
    private void removeBook() {
        try{
            final String URL = "jdbc:mysql://localhost/biblioteka?serverTimezone=UTC";
            final String NAME = "root";
            final String PASSWORD = "sheeuser123456@";

            String bookname = textField1.getText();
            if(bookname.isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter book name","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            Connection con = DriverManager.getConnection(URL,NAME,PASSWORD);
            Statement st = con.createStatement();
            String del = "DELETE FROM bookinformation WHERE book_name ='"+bookname+"'";
            st.executeUpdate(del);
            con.close();
            st.close();
            JOptionPane.showMessageDialog(null,"Book removed successfully","Message",JOptionPane.INFORMATION_MESSAGE);
        }catch (SQLException sqe){
            JOptionPane.showMessageDialog(null,"Can't connect to database!","Error",JOptionPane.ERROR_MESSAGE);
            sqe.printStackTrace();
        }
    }
    private void createBook(){
        String bookname = textField1.getText();
        String authorname = textField2.getText();
        if(bookname.isEmpty() || authorname.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please enter correct info","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        book = addBook(bookname,authorname);
    }
    public Book book;
    private Book addBook(String bname, String autname) {
        Book book = null;
        final String URL = "jdbc:mysql://localhost/biblioteka?serverTimezone=UTC";
        final String NAME = "root";
        final String PASSWORD = "sheeuser123456@";
        try{
            Connection con = DriverManager.getConnection(URL,NAME,PASSWORD);
            Statement st = con.createStatement();
            String sql = "INSERT INTO bookinformation (book_name, author_name)" + "VALUES(?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,bname);
            pst.setString(2,autname);
            int addedrows = pst.executeUpdate();
            if(addedrows > 0){
                book = new Book();
                book.bookName = bname;
                book.authorName = autname;
                JOptionPane.showMessageDialog(null,"Book added successfully to database", "Message",JOptionPane.INFORMATION_MESSAGE);
            }
            con.close();
            st.close();
        }catch (SQLException sqe){
            JOptionPane.showMessageDialog(null,"Can't connect to database!","Error",JOptionPane.ERROR_MESSAGE);
            sqe.printStackTrace();
        }
        return book;
    }
}
