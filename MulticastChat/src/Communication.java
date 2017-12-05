
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Communication 
{
	private int portaGrupo = 5000;
	private String enderecoGrupo = "224.0.0.1";
	private DatagramSocket unicastSocket;
	private MulticastSocket multicastSocket;
	
	public Communication() throws IOException
	{
		unicastSocket = new DatagramSocket();
		multicastSocket = new MulticastSocket(portaGrupo);
		multicastSocket.joinGroup(InetAddress.getByName(enderecoGrupo));
	}
	
	public Communication(int porta) throws IOException
	{
		unicastSocket = new DatagramSocket(porta);
		multicastSocket = new MulticastSocket(portaGrupo);
		multicastSocket.joinGroup(InetAddress.getByName(enderecoGrupo));
	}
	
	public void sendUnicast(String mensagem, InetAddress endereco, int porta) throws IOException
	{
		DatagramPacket pacote = new DatagramPacket(mensagem.getBytes(), mensagem.length(), endereco, porta);
		unicastSocket.send(pacote);
	}
	
	public void sendMulticast(String mensagem) throws IOException
	{
		DatagramPacket pacote = new DatagramPacket(mensagem.getBytes(), mensagem.length(), InetAddress.getByName(enderecoGrupo), portaGrupo);
		unicastSocket.send(pacote);
	}
	
	public DatagramPacket receiveUnicast(boolean timeOut) throws IOException
	{
		DatagramPacket pacote = new DatagramPacket(new byte[1024], 1024);
		int time = 0;
		if( timeOut )
		{
			time = 2 * 1000;
		}
		unicastSocket.setSoTimeout(time);
		unicastSocket.receive(pacote);
		return pacote;
	}
	
	public DatagramPacket receiveMulticast() throws IOException
	{
		DatagramPacket pacote = new DatagramPacket(new byte[1024], 1024);
		multicastSocket.receive(pacote);
		return pacote;
	}
	
	public String getData(DatagramPacket pacote)
	{
		return new String(pacote.getData(),pacote.getOffset(), pacote.getLength());
	}
	
	public int getID()
	{
		return unicastSocket.getLocalPort();
	}
}
