package chat_server;

import java.net.*; 
public class ChatServer{  
    public static ClientHandler client[] = new ClientHandler[99];
    public static void main(String[] args) {
        int clientNo=0,i=0;
        try {
            System.out.println("Welcome to CSE336 Chat Server!\n");
            ServerSocket serverSocket = new ServerSocket(25000); 
            System.out.println("Server is running on port 25000\n");
            
            while (true) {
                clientNo++;
                Socket clientSocket = serverSocket.accept(); 
                ClientHandler CH = new ClientHandler(clientSocket,clientNo); 
                client[i] = CH;
                CH.start();
                i++;
            }
        } catch (Exception e) {} 
    }   
    
    public static boolean send(String receiverName, String message, String sender){
        for(int i=0;i<client.length;i++){
            if(client[i].clientName.equals(receiverName)){
                client[i].out.println(sender + ": " + message);
                return true;
            }
        }
        return false;
    }
}