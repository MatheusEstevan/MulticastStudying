import java.io.IOException;
import java.net.DatagramPacket;

public class Receive extends Thread{
	
	Usuarios usuarios;
	Communication c;
	public Receive(Communication c,Usuarios users) {
		super();
		this.usuarios = users;
		this.c = c ;
	}
	
	public void run() {

		while(true)
		{
			try 
			{
				DatagramPacket pct = c.receiveUnicast(false);
				String conteudo = c.getData(pct);
				if( conteudo.equals("WELCOME") )
				{
					usuarios.Add(pct.getPort());
				}
				else 
				{
					System.out.println(conteudo);
				}
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
