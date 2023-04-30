/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ds_videoconferens;

/**
 *
 * @author admin
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class VideoServer {
    
    private static final int PORT = 5000;
    private static final String SERVER_IP = "10.194.109.15";
    private ArrayList<ObjectOutputStream> outputStreams = new ArrayList<ObjectOutputStream>();
    
    public static void main(String[] args) {
        new VideoServer().runServer();
    }
    
    public void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started and listening on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                outputStreams.add(output);
                new Thread(new ClientHandler(socket, output)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectOutputStream output;
        private ObjectInputStream input;
        
        public ClientHandler(Socket socket, ObjectOutputStream output) {
            this.socket = socket;
            this.output = output;
        }
        
        public void run() {
            try {
                input = new ObjectInputStream(socket.getInputStream());
                while (true) {
                    Object obj = input.readObject();
                    if (obj instanceof byte[]) {
                        byte[] data = (byte[]) obj;
                        System.out.println("Received video data from client: " + socket);
                        for (ObjectOutputStream outputStream : outputStreams) {
                            outputStream.writeObject(data);
                            outputStream.flush();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                    output.close();
                    socket.close();
                    outputStreams.remove(output);
                    System.out.println("Client disconnected: " + socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

