import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ManagerChatter extends JFrame implements Runnable{
    private JPanel mainPanel;
    private JTextField textSvPort;
    private JTabbedPane tabbedPane;
    ServerSocket srvSocket = null;
    BufferedReader bf = null;
    Thread t;
    public ManagerChatter(){
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setSize(750, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        int severPort = Integer.parseInt(textSvPort.getText());
        try {
            srvSocket = new ServerSocket(severPort);
        }catch (Exception e){
            e.printStackTrace();
        }
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (true){
            try {
                Socket aStaffSocket = srvSocket.accept();
                if (aStaffSocket != null){
                    bf = new BufferedReader(new InputStreamReader(aStaffSocket.getInputStream()));
                    String s = bf.readLine();
                    int pos = s.indexOf(": ");
                    String staffName = s.substring(pos+1);
                    ChatPanel p = new ChatPanel(aStaffSocket, "Manager", staffName);
                    tabbedPane.add(staffName, p);
                    p.updateUI();
                }
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ManagerChatter();
    }
}
