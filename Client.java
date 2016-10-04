package chat_server;

import java.util.Scanner;
import java.net.*;
import java.io.*;

class Client{
    public static void main(String[] args)  throws IOException {
        
        Scanner scan = new Scanner(System.in);
        String serverName, clientName;
        int serverPort;
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
        
        System.out.println("Welcome to CSE336 Client!\n");
        System.out.print("Please enter the server name: ");
        serverName = scan.nextLine();
        System.out.println("\n");
        System.out.print("Please enter the server port: ");
        serverPort = scan.nextInt();
        System.out.println("\n");
        
        try{
            clientSocket = new Socket(serverName, serverPort);             
            out = new PrintWriter(clientSocket.getOutputStream(), true);             
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));    
        }catch (UnknownHostException e) { 
            System.err.println("Don't know about host: " + serverName); 
            System.exit(1); 
        } catch (IOException e) { 
            System.err.println("Couldn't get I/O for the connection to: " + serverName); 
            System.exit(1); 
        }
        
        BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
        String userInput=null,serverResponse=null;
        
        // validate unique username 
//        System.out.print("Please enter Username: ");
//        while((clientName = stdIn.readLine())!=null){
//            if(!ChatServer.isSameUsername(clientName)){
//                out.println(clientName);
//                System.out.println("\n");
//                break;
//            }
//            System.out.println("Please enter Username again: ");
//        } 
        
        // replace by lines up
        
        System.out.print("Please enter Username: ");
        clientName = stdIn.readLine();
        System.out.println("\n");
        out.println(clientName);
        
        System.out.print("Please enter text (\"end-of-session\" to quit): ");
        while (true) {
            if(!(userInput = stdIn.readLine()).isEmpty()){
                System.out.println("");
                out.println(userInput);
                // end loop 
                if (userInput.equals("end-of-session")){
                    System.out.println("Goodbye!\n");
                    break;
                }
                userInput = null;
            }
            if(!(serverResponse = in.readLine()).isEmpty()){
                System.out.println("\n" + serverResponse + "\n");
                serverResponse=null;
            }
            System.out.print("Please enter text: ");
        }
        out.close();
        in.close();
        clientSocket.close(); 
    }
    
    // validate unique username
//    public static boolean isSameUsername(String userName){
//        //ChatServer CS = new ChatServer();
//        ClientHandler client[] = ChatServer.client;
//        for (int i=0; i<client.length; i++) {
//            if (client[i] != null && client.clientName.compareToIgnoreCase(userName)>0) {
//                return true;
//            }
//        }
//        return false;
//    }
}