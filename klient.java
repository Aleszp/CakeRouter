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
        
		/*int i=0;
		int j=0;
		
		if(args.length != 2)
		{
			System.out.println("Użycie: klient [id_klienta] [id_trasy]");
			System.out.println("[id_klienta] - (0 lub 1) decyduje o wybrannym porcie docelowym, nie mogą jednocześnie pracować dwaj różni kilenci na tym samym porcie");
			System.out.println("[id_trasy] - (0 lub 1) decyduje o kolejności węzłów pośredniczących (są dwa węzły pośrednie i jeden końcowy, więc i tylko dwie trasy są możliwe)");
			System.exit(0);
		}
    
		try
		{
			i = Integer.parseInt(args[0]); //identyfikator klienta wczytywany z linii komend, decyduje o wykorzystywanym
			j = Integer.parseInt(args[1]); //identyfikator trasy wczytywany z linii komend, decyduje o kolejności węzłów pośreniczących
		}
		catch (NumberFormatException e) 
		{
			System.err.println("Argument" + args[0] + " must be an integer.");
			System.exit(1);
		}(=*/

		message message_=new message(); //Tworzymy zmienną wiadomości
	
		//if(j==0)	//wybór klienta wybiera nam również trasę
		{
			message_.setFirstNod(Config.IP1); //ustalamy adres pierwszego węzła
        
			message_.addText("Witaj Świecie! :D\n");  //dodajemy treść wiadomości
			message_.addFinalIpPort(Config.IP2,Config.SERVPORT[i]); //oraz kolejne warstwy: adres i port serwera docelowego
			message_.addNodIp(Config.IP2);							//						adres węzła końcowego
			//message_.addNodIp(Config.IP1);							//ewentualne dodatkowe węzły pośrednie
		}
		/*else
		{
			message_.setFirstNod(Config.IP2); //Tworzymy zmienną wiadomości z adresem pierwszego węzła
        
			message_.addText("Witaj Świecie! :D\n");  //dodajemy treść wiadomości
			message_.addFinalIpPort(Config.IP2,Config.SERVPORT[i]); //oraz kolejne warstwy: adres i port serwera docelowego
			message_.addNodIp(Config.IP2);							//						adres węzła końcowego
			//message_.addNodIp(Config.IP2);															//ewentualne dodatkowe węzły pośrednie
		}*/

        
        byte[] stringContents = message_.text.getBytes("utf8"); //Pobranie strumienia bajtow z wiadomosci
        DatagramSocket socket = new DatagramSocket(); //Otwarcie gniazda
		while(true)
		{
			DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
			sentPacket.setAddress(message_.firstNod);
			sentPacket.setPort(Config.NODPORT[i]);
			socket.send(sentPacket);
			System.out.println(message_.text );

			DatagramPacket receivedPacket = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
			socket.setSoTimeout(1000);

			try
			{
				socket.receive(receivedPacket);
				int length = receivedPacket.getLength();
				String receivedMessage = new String(receivedPacket.getData(), 0, length, "utf8");
				System.out.println("Serwer: "+receivedMessage);
			}
			catch (SocketTimeoutException ste)
			{
				System.out.println("No response...");
			}
		}
    }
}
