import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.UIManager.*;

import static javax.swing.JOptionPane.PLAIN_MESSAGE;

public class SecretPanel extends JPanel {
    private JLabel lblHeader;
        private JLabel lblKey;

    private JTextArea txtIn;
    private JTextArea txtOut;
    private JTextArea txtKey;

    private JScrollPane spIn;
    private JScrollPane spOut;

    private JButton btnEncodeDecode;
    private JButton btnSwitch;
    private JButton btnReverse;

    private JSlider slider;

    private JRadioButton rbCaesar;
    private JRadioButton rbViginere;

    private ButtonGroup bg;

    private Color bgItem = new Color(100,151,177);
    private Color bgBtn = new Color(3,57,108);

    private Font font = new Font("DejaVu Sans Mono",Font.PLAIN,12);

    public SecretPanel() {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        UIManager.put("Button.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("RadioButton.font", font);
        UIManager.put("ScrollBar.font", font);

        setLayout(null);
        setBackground(new Color(179, 205, 224));

        lblHeader = new JLabel("Nazarii's SecretMessages GUI App");
        lblHeader.setFont(new Font("DejaVu Sans Mono",0,14));
        lblHeader.setBounds(170, 2, 300, 20);
        add(lblHeader);

        txtIn = new JTextArea();
        txtIn.setLineWrap(true);
        txtIn.setWrapStyleWord(true);
        txtIn.setBackground(bgItem);

        spIn = new JScrollPane(txtIn,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spIn.setBounds(10,25,565,125);
        add(spIn);

        txtKey = new JTextArea();
        txtKey.setBounds(345, 175, 50, 25);
        txtKey.setBackground(bgItem);
        add(txtKey);

        lblKey = new JLabel("Key:");
        lblKey.setBounds(315, 175, 30, 25);
        add(lblKey);

        txtOut = new JTextArea();
        txtOut.setLineWrap(true);
        txtOut.setWrapStyleWord(true);
        txtOut.setBackground(bgItem);

        spOut = new JScrollPane(txtOut,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spOut.setBounds(10,225,565,125);
        add(spOut);

        btnEncodeDecode = new JButton("Encode/\nDecode");
        btnEncodeDecode.setBounds(400, 162, 175, 25);
        btnEncodeDecode.setBackground(bgBtn);
        btnEncodeDecode.setForeground(new Color(179, 205, 224));
        add(btnEncodeDecode);
        rbCaesar = new JRadioButton("Caesar");
        rbCaesar.setBounds(10, 160, 75, 25);
        rbCaesar.setBackground(new Color(179, 205, 224));
        rbCaesar.setSelected(true);
        add(rbCaesar);
        rbViginere = new JRadioButton("Viginere");
        rbViginere.setBounds(10, 190, 80, 25);
        rbViginere.setBackground(new Color(179, 205, 224));
        add(rbViginere);
        bg = new ButtonGroup();
        bg.add(rbCaesar);
        bg.add(rbViginere);
        btnSwitch = new JButton("Switch");
        btnSwitch.setBounds(400, 192, 85, 20);
        btnSwitch.setBackground(bgBtn);
        btnSwitch.setForeground(new Color(179, 205, 224));
        add(btnSwitch);
        btnReverse = new JButton("Reverse");
        btnReverse.setBounds(490, 192, 85, 20);
        btnReverse.setBackground(bgBtn);
        btnReverse.setForeground(new Color(179, 205, 224));
        add(btnReverse);
        slider = new JSlider(-26, 26, 3);
        slider.setBackground(new Color(179, 205, 224));
        slider.setBounds(95, 170, 200, 40);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(13);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setPaintLabels(true);
        add(slider);
        rbViginere.addActionListener(e -> {
            if (rbViginere.isSelected()){
                slider.setVisible(false);
                btnReverse.setEnabled(false);
            }
        });
        rbCaesar.addActionListener(e -> {
            if (rbCaesar.isSelected()){
                slider.setVisible(true);
                btnReverse.setEnabled(true);
            }
        });
        slider.addChangeListener(e -> {
            txtKey.setText("" + slider.getValue());
            txtOut.setText(caesar(txtIn.getText(), slider.getValue()));
        });
        btnEncodeDecode.addActionListener(e -> {
            if (txtKey.getText().equals("") || !(rbCaesar.isSelected() || rbViginere.isSelected()))
                JOptionPane.showMessageDialog(null,
                        "Please, enter a key or choose cipher!", "Error", PLAIN_MESSAGE);
            else {
                if (rbCaesar.isSelected()) {
                    try {
                        txtOut.setText(caesar(txtIn.getText(), Integer.parseInt(txtKey.getText())));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Please, enter a digital key(0-26)!", "Error", PLAIN_MESSAGE);
                    }
                } else if (rbViginere.isSelected()) {
                    try {
                        Integer.parseInt(txtKey.getText());
                        JOptionPane.showMessageDialog(null,
                                "Please enter a word key(For example,\"bacon\")!", "Error", PLAIN_MESSAGE);
                    } catch (Exception ex) {
                        txtOut.setText("Encrypt: "+viginere(txtIn.getText(), txtKey.getText(),false)+"\n" +
                                "Decrypt: "+viginere(txtIn.getText(),txtKey.getText(),true));
                    }
                }
            }
        });
        btnReverse.addActionListener(e -> {
            if(rbCaesar.isSelected())
                txtOut.setText(reverse(txtOut.getText()));
            else if(rbViginere.isSelected()){

            }
        });
        txtKey.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(rbCaesar.isSelected()) {
                    try {
                        int key = Integer.parseInt(txtKey.getText());
                        while (key > 26) key -= 26;
                        slider.setValue(key);
                    } catch (Exception ex) {

                    }
                }
            }
        });
        btnSwitch.addActionListener(e -> {
            try {
                String temp = txtIn.getText();
                txtIn.setText(txtOut.getText());
                if (rbViginere.isSelected()) {
                    txtOut.setText(temp);
                } else if (rbCaesar.isSelected()) {
                    int keyVal = (-Integer.parseInt(txtKey.getText()));
                    txtKey.setText(Integer.toString(keyVal));
                    txtOut.setText(caesar(txtIn.getText(), keyVal));
                    slider.setValue(keyVal);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Enter the key and try " +
                        "again!", "Error", -1);
            }
        });
    }

    public String caesar(String rawMsg, int keyVal){
        String result = "";
        int numKey = keyVal;
        while(keyVal>26) keyVal -= 26;
        while(keyVal<0) keyVal +=26;
        char key = (char)keyVal;
        for (int i = 0; i < rawMsg.length(); i++) {
            char input = rawMsg.charAt(i);
            if (input >= 'A' && input <= 'Z') {
                input += key;
                while (input > 'Z') input -= 26;
                while (input < 'A') input += 26;
            } else if (input >= 'a' && input <= 'z') {
                input += key;
                while (input > 'z') input -= 26;
                while (input < 'a') input += 26;
            } else if (input >= '0' && input <= '9') {
                input += (numKey % 10);
                while (input > '9') input -= 10;
                while (input < '0') input += 10;
            }

            result += input;
        }
        return result;
    }

    public static String viginere(String msg, String key, boolean decrypt) {
        String result = "";
        int loop = 0;
        int j = 0;
        if(decrypt==false) {
            for (int i = 0; i < msg.length(); i++) {
                if (j == key.length()) j = 0;
                char input = msg.charAt(i);
                char keyVal;
                keyVal = (char) (key.charAt(j) - 97);
                if (input >= 'a' && input <= 'z') {
                    input += keyVal;
                    while (input > 'z') input -= 26;
                    while (input < 'a') input += 26;
                    result += input;
                    j++;
                } else if (input >= 'A' && input <= 'Z') {
                    input += keyVal;
                    while (input > 'Z') input -= 26;
                    while (input < 'A') input += 26;
                    result += input;
                    j++;
                } else result += input;
            }
        }else if(decrypt==true){
            for (int i = 0; i < msg.length(); i++) {
                if (j == key.length()) j = 0;
                char input = msg.charAt(i);
                char keyVal;
                keyVal = (char) (key.charAt(j) - 97);
                if (input >= 'a' && input <= 'z') {
                    input -= keyVal;
                    while (input > 'z') input -= 26;
                    while (input < 'a') input += 26;
                    result += input;
                    j++;
                } else if (input >= 'A' && input <= 'Z') {
                    input -= keyVal;
                    while (input > 'Z') input -= 26;
                    while (input < 'A') input += 26;
                    result += input;
                    j++;
                } else result += input;
            }
        }
        return result;
    }

    public static String reverse(String msg){
        String result = "";
        for(int i = msg.length()-1; i >= 0;i--){
            result += msg.charAt(i);
        }
        return result;
    }
}
