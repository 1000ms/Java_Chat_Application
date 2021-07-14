import java.net.*;
import java.io.*;

public class Server {
	
	ServerSocket server	;
	Socket socket; // To store the client socket object
	BufferedReader br;// To read data
	PrintWriter out;// To write data
	
	public Server() {
		try {
			server=new ServerSocket(7777);
			System.out.println("Server is ready to accept connection");
			System.out.println("Waiting");
			socket = server.accept();//to accept client's request, client sends an object of socket class
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream());
			startReading();
			startWriting();
			
		} catch (Exception e) {
			e.printStackTrace();//method of throwable class to print the throwable along with line number and class where exception has occurred.
		}
	}
	
	public void startReading() {
		//thread to continuously read data coming from the client
		Runnable r1=()->{
			System.out.println("Reading started");
			
			try {
				while(true) {
					
						String msg=br.readLine();// the message from the client
						if(msg.equals("exit")) {
							System.out.println("Client has terminated the chat");
							socket.close();
							break;
						}
						System.out.println("Client: "+msg);
					
				}
			}catch (Exception e) {
				//e.printStackTrace();
				System.out.println("Connection Closed");
			}
		};
		
		new Thread(r1).start();// to start the thread
	}
	
	public void startWriting() {
		//thread to take user input and send it to the client
		Runnable r2=()->{
			System.out.println("Writer Started");
			
			try {
				while(!socket.isClosed()) {
					
						BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));// To take input from the console
						String content=br1.readLine();
						
						out.println(content);// sent to client
						out.flush();
						if(content.equals("exit")) {
							socket.close();
							break;
						}
						
				
				}
			
			}catch (Exception e) {
				//e.printStackTrace();
				System.out.println("Connection Closed");
			}
		};
		
		new Thread(r2).start();
	}
	
	public static void main(String[] args) {
		System.out.println("SERVER");
		new Server();
	}
}
