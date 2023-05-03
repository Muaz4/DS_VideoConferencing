/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package v_conf;

/**
 *
 * @author admin
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ScreenSharing {
    private Robot robot;
    private BufferedImage screenCapture;
    private JFrame screenFrame;

    public ScreenSharing() throws AWTException {
        robot = new Robot();
        screenFrame = new JFrame();
        screenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screenFrame.setVisible(true);
        screenFrame.setSize(new Dimension(800, 600));
        screenFrame.setResizable(false);
        startScreenCapture();
    }

    public void startScreenCapture() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // Capture the screen
                        screenCapture = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                        // Display the screen capture in a label
                        ImageIcon icon = new ImageIcon(screenCapture);
                        JLabel label = new JLabel(icon);
                        screenFrame.getContentPane().removeAll();
                        screenFrame.getContentPane().add(label);
                        screenFrame.pack();
                        // Delay to reduce CPU usage
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    public static void main(String[] args) throws AWTException, IOException {
        new ScreenSharing();
    }
}