import javax.swing.JFrame;

public class SecretMessagesGUI extends JFrame {
    SecretMessagesGUI(){
        setTitle("Nazarii's SecretMessages GUI App");
        setSize(600,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().add(new SecretPanel());
        setLocationRelativeTo(null);
    }

    public static void main(String[] args){
        SecretMessagesGUI theApp = new SecretMessagesGUI();
        theApp.setVisible(true);
    }
}
