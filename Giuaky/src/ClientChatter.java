import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientChatter extends JFrame{
    private JPanel mainPanel;
    private JTextField portText;
    private JTextField ipText;
    private JButton btnconnect;
    private JTextField staffText;
    Socket mngSocket = null;
    String mngIP = "";
    int mngPort = 0;
    String staffName = "";
    BufferedReader bf = null;
    DataOutputStream os = null;
    OutputThread t = null;


    public ClientChatter(){
        setContentPane(mainPanel);
        setSize(750, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JFrame thisFrame = this;
        btnconnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mngIP = ipText.getText();
                mngPort = Integer.parseInt(portText.getText());
                staffName = staffText.getText();
                try {
                    mngSocket = new Socket(mngIP, mngPort);
                    if(mngSocket != null){
                        ChatPanel p = new ChatPanel(mngSocket, staffName, "Manager");
                        thisFrame.getContentPane().add(p);
                        p.getTxtMessages().append("Manager is running");
                        p.updateUI();
                        bf = new BufferedReader(new InputStreamReader(mngSocket.getInputStream()));
                        os = new DataOutputStream(mngSocket.getOutputStream());
                        os.writeBytes(staffName);
                        os.write(13);os.write(10);
                        os.flush();}
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new ClientChatter();
    }
}
