import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class QuoteUDPServer {

    public static void main(String[] args) {
        DatagramSocket socket = null;
        int port = 8080;
        try {
            socket = new DatagramSocket(port);
            System.out.println("UDP Quote Server listening on port " + port + "...");
            byte[] receiveData = new byte[1024];
            ArrayList<String> quotes = new ArrayList<>();
            quotes.add("\"He died like a dog.\"");
            quotes.add("\"If you have to shoot, shoot. don't talk.\"");
            quotes.add("\"Frankly My Dear, I Don't Give a Damn.\"");
            quotes.add("\"Hasta la vista, baby.\"");
            quotes.add("\"Say hello to my little friend!\"");

            while(true){
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

                if (message.equalsIgnoreCase("GET")) {
                     System.out.println("Request received: " + message);
                    Random rnd = new Random();
                    int index = rnd.nextInt(quotes.size());
                    String selected = quotes.get(index);

                    byte[] responseData = selected.getBytes();
                    DatagramPacket response = new DatagramPacket(responseData, responseData.length, receivePacket.getAddress(), receivePacket.getPort());
                    socket.send(response);
                }
                else if(message.equalsIgnoreCase("exit")) {
                    System.out.println("Request received: " + message);
                    System.out.println("Server Shutting down.");
                    socket.close();
                    break;
                }
                else {
                    System.out.println("Invalid request received");
                    String errorMsg = "Invalid request. Use GET or exit.";
                    byte[] responseData = errorMsg.getBytes();

                    DatagramPacket response =
                            new DatagramPacket(responseData, responseData.length,
                                    receivePacket.getAddress(), receivePacket.getPort());

                    socket.send(response);
                } 
            } 


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
