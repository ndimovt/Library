import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AdminLogin extends JDialog{
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton exitButton;
    private JPanel logfield;
    public AdminLogin(JFrame parent){
        super (parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setContentPane(logfield);
        setSize(new Dimension(520,450));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField1.getText();
                String pass = String.valueOf(passwordField1.getPassword());
                admin = authenticatedAdmin(name, pass);
                if(admin != null){
                    bookOperations bo = new bookOperations(null);
                    setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(null,"Please enter valid admin account","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    Admin admin;
    private Admin authenticatedAdmin(String adname, String adpass) {
        Admin admin = null;
        String name = textField1.getText();
        String pass = String.valueOf(passwordField1.getPassword());
        Admin ad = new Admin();
        adname = ad.getAdminName();
        adpass = ad.getAdminPass();
        if(name.equals(adname) && adpass.equals(pass)){
            admin = new Admin();
        }
        return admin;
    }
}
