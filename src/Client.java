import java.net.*;
import java.io.*;

public class Client {
	
	Socket socket;
	BufferedReader br;// To read data
	PrintWriter out;// To write data
	
	public Client() {
		try {
			System.out.println("Sending request to server");
			socket=new Socket("192.168.7.8",7777);
			System.out.println("Connection done");
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream());
			startReading();
			startWriting();
			
		}catch(Exception e) {
			e.printStackTrace();
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
							System.out.println("Server terminated the chat");
							socket.close();
							break;
						}
						System.out.println("Server: "+msg);
					
					
				}
			} catch(Exception e) {
				System.out.println("Connection Closed");
				//e.printStackTrace();
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
				System.out.println("Connection Closed");
			} catch(Exception e) {
				e.printStackTrace();
			}
		};
		
		new Thread(r2).start();
	}
	

	public static void main(String[] args) {
		System.out.println("Client");
		new Client();
	}
}
