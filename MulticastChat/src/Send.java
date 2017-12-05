import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Send extends Thread{
	Usuarios u;
	
	public Send(Usuarios u) {
		this.u = u;
	}
	public void run() {
		
			DatagramSocket socket;
			try {
				socket = new DatagramSocket();					
				socket.setSoTimeout(5 * 1000);
				Scanner sc = new Scanner(System.in);
				System.out.println("Aguardando msg; ");
				String msg = sc.next();
				while(true)
				{
					synchronized (u) 
					{
						for(int indice = u.getListaUsuarios().size() -1; indice >= 0; indice-- )
						{
							try 
							{
								int porta = u.getListaUsuarios().get(indice);
								DatagramPacket pacote = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getLocalHost(), porta);
								socket.send(pacote);
								pacote = new DatagramPacket(new byte[1024], 1024);
								socket.receive(pacote);
							} 
							catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println("Removerrrrrrrrr: " + indice);
								u.Remove(indice);
							}
						}
						
					}
					try {
						sleep(3 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		
			} catch (SocketException e1) {
				e1.printStackTrace();
			}
	}
	
}
