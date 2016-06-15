import java.io.IOException;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;


public class klient 
{
	
    public static void main(String[] args) throws IOException, SocketException
    {
		message message_=new message("Witaj Świecie! :D\n"); //Tworzymy zmienną wiadomości
		
		message_.setFirstNod(Config.IP1,Config.PORT[0]); //ustalamy adres pierwszego węzła
		
		//message_.pushIp(Config.IP2,Config.PORT[2]); //oraz kolejne warstwy: adres i port serwera docelowego
		message_.pushIp(Config.IP2,Config.PORT[1]);						//  adres i port węzła końcowego
        
        byte[] stringContents = message_.text.getBytes("utf8"); //Pobranie strumienia bajtow z wiadomosci
        
        DatagramSocket socket = new DatagramSocket(); //Otwarcie gniazda 
		while(true)
		{
			DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
			sentPacket.setAddress(message_.address);
			sentPacket.setPort(message_.port);
			socket.send(sentPacket);
			System.out.println("Wysyłam: \n"+message_.text );

			DatagramPacket receivedPacket = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
			socket.setSoTimeout(2000);

			try
			{
				socket.receive(receivedPacket);
				int length = receivedPacket.getLength();
				String receivedMessage = new String(receivedPacket.getData(), 0, length, "utf8");
				System.out.println("Serwer: \n"+receivedMessage);
			}
			catch (SocketTimeoutException ste)
			{
				System.out.println("No response...");
			}
		}
    }
}
