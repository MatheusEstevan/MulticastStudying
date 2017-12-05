import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Random;
import java.util.Scanner;

public class Mensageiro {

	Random rand = new Random();
	int i = rand.nextInt(100);
	Usuarios users;
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Usuarios usuarios = new Usuarios();
		Communication communication = new Communication();
		
		Send tAYA = new Send(usuarios);
		Receive tIAA = new Receive(communication,usuarios);
		tIAA.start();
		tAYA.start();

		System.out.println("Processo: " + communication.getID());
		communication.sendMulticast("JOIN");
		
		while(true)
		{
			try 
			{
				DatagramPacket pct = communication.receiveMulticast();
				if( pct.getPort() != communication.getID() )
				{
					if( !usuarios.Contains(pct.getPort()) )
					{
						usuarios.Add(pct.getPort());
						communication.sendUnicast("WELCOME", pct.getAddress(), pct.getPort());
					}
				}
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
