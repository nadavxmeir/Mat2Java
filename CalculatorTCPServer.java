import java.io.*;
import java.net.*;

public class CalculatorTCPServer {

  public static void main(String[] args) {
    int port = 9090;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Server is listening on port " + port + "...");
      try (Socket clientSocket = serverSocket.accept()) {

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        System.out.println("Client connected.");
        String expression;  

        while ((expression = in.readLine()) != null) {
          if(expression.trim().equalsIgnoreCase("exit")) {
            System.out.println("Client requested to close connection.");
            System.out.println("Client disconnected.");
            break;
          }
          System.out.println("Received expression: " + expression); 
          String resultString = calculateExpression(expression);
          out.println(resultString);
        }
      } catch (IOException e) {
          System.err.println("Error communicating with client or connection dropped: " + e.getMessage());
      }
      
    } catch (Exception e) {
        System.err.println("Could not start server on port " + port + ". Is another program using this port? " + e.getMessage());    
    }
    
  }
  public static String calculateExpression(String expression) {
    String[] parts = expression.split(" ");
    if (parts.length != 3) {
      return "Error: Invalid expression";
    }
    try {
      double operand1 = Double.parseDouble(parts[0]);
      String operator = parts[1];
      double operand2 = Double.parseDouble(parts[2]);
      double result;

      switch (operator) {
        case "+":
          result = operand1 + operand2;
          break;
        case "-":
          result = operand1 - operand2;
          break;
        case "*":
          result = operand1 * operand2;
          break;
        case "/":
          if (operand2 == 0) {
            return "Error: Division by zero";
          }
          result = operand1 / operand2;
          break;
        default:
          return "Error: Unsupported operator " + operator;
      }
      return String.valueOf(result);
    } catch (NumberFormatException e) {
        return "Error: Invalid expression";
    }
  }
}
