import java.io.*;
import java.net.*;

public class CalculatorTCPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; 
        int port = 9090; 
    
        try (Socket socket = new Socket(serverAddress, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
            String expression;
    
        while (true) {
            System.out.print("Enter expression (num op num) or 'exit' to exit: ");
            expression = userInput.readLine();
            out.println(expression);
    
            if (expression.trim().equalsIgnoreCase("exit")) {
            System.out.println("Client close.");
            break;
            }
    
            String response = in.readLine();
            System.out.println(expression + " = " + response);
        }
        } catch (IOException e) {
        System.err.println("Error communicating with server: " + e.getMessage());
        }
    }
}
