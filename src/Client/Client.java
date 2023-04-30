/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

/**
 *
 * @author admin
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Client {

    private static final int PORT = 8000;
    private static final String SERVER_ADDRESS = "10.194.109.15";
    private static final int FRAME_WIDTH = 640;
    private static final int FRAME_HEIGHT = 480;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private JFrame frame;
    private JLabel videoLabel;

    private ArrayList<BufferedImage> frameList = new ArrayList<>();
    private int currentFrameIndex = 0;

    private ScheduledExecutorService scheduledExecutorService;

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        initializeGUI();
        connectToServer();

       
    }

    private void initializeGUI() {

        frame = new JFrame();
        frame.setTitle("Video Conferencing - Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        videoLabel = new JLabel();
        videoLabel.setBorder(BorderFactory.createEtchedBorder());
        videoLabel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel videoPanel = new JPanel(new GridLayout());
        videoPanel.add(videoLabel);
        
             JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        // Action to perform when Start button is clicked
    }
});
               JButton stopButton = new JButton("Stop");
       stopButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Action to perform when Stop button is clicked
    }
});

        JScrollPane scrollPane = new JScrollPane(videoPanel);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        frame.add(stopButton);
        frame.add(startButton);
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }}}
   

   