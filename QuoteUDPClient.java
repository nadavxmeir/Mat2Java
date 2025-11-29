import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class QuoteUDPClient {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.print("Enter GET for quote or exit to quit: ");
            String message = sc.nextLine();
            byte[] data = message.getBytes();
            DatagramPacket packet =
            new DatagramPacket(data, data.length, serverAddress, 8080);
            socket.send(packet);

            
            if (message.equalsIgnoreCase("exit")) {
                System.out.println("Client finished.");
                break;
            }

            
            byte[] buffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);

            socket.receive(response);

            
            String quote = new String(response.getData(), 0, response.getLength());
            if (quote.startsWith("Invalid request\n")) {
                System.out.println(quote);
            } 
            else System.out.println("Quote received: " + quote + "\n");
            }

        socket.close();
    }
}
