package chat_server;

import java.net.*; 
import java.io.*; 

class ClientHandler extends Thread{
    protected Socket clientSocket;
    protected int clientNo;
    protected String clientName;
    protected PrintWriter out;
    protected BufferedReader in;
    
    ClientHandler(Socket clientSocket, int clientNo){
        this.clientSocket = clientSocket;
        this.clientNo = clientNo;
    }
    
    @Override
    public void run(){
        try{
            // Create data input and output streams
            out = new PrintWriter(clientSocket.getOutputStream(),true);    
            in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));

            this.clientName = in.readLine();
            System.out.println(this.clientName+" is connected!\n");
            
            while(true){
                // Receive message from client
                String input = in.readLine(); // read message
                
                // end loop 
                if (input.equals("end-of-session")){ 
                    System.out.println(this.clientName+" Disconnected!!\n");
                    break;
                }
                
                String receiverName = getReceiverName(input); 
                String message = getMessage(input);
                System.out.println("Received: \"" + input + "\" from " + this.clientName + ".\n");
                
                // Send message to the receiver client
                if(ChatServer.send(receiverName, message, this.clientName)) 
                    System.out.println("Sent: \"" + message + "\" to " + receiverName + ".\n");
                else System.out.println("Message not sent! No such user as " + receiverName + ".\n");
            }
        }catch(Exception e){}
    }
    
    // seperate the user name from the sender client's input
    private String getReceiverName(String input){
        String receiverName = ""; 
        for(int i = 0; i<input.length(); i++){
            if(input.charAt(i)==':') break;
            receiverName += input.charAt(i); 
        }
        return receiverName.trim();
    }
    
    // seperate the message from the sender client's input
    private String getMessage(String input){
        String msg = ""; 
        boolean ok = false;
        for(int i = 0; i<input.length(); i++){
            if(input.charAt(i)==':') {
                ok = true;
                i++;
            }
            if(ok) msg += input.charAt(i); 
        }
        return msg.trim();
    }
}