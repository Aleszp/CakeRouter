import java.io.IOException;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;


public class Node 
{
	
	public static void main(String[] args) throws Exception
    {
		if(args.length != 1)
		{
			System.out.println("Użycie: Node [port]");
			System.out.println("[port] - (1024-65535) decyduje o wybrannym porcie na którym będzie nasłuchiwać, zaleca się użycie wartości >8000");
			System.exit(0);
		}
		int listen_port=Integer.parseInt(args[0]);
		if(listen_port<1024 || listen_port>65535)
		{
			System.out.println("Nieprawidłowy numer portu.");
			System.exit(0);
		}
		int me[]={127,0,0,1};
		
        //Otwarcie gniazda z okreslonym portem (dane od klienta/poprzedniego węzła)
        DatagramSocket socketIn = new DatagramSocket(listen_port);

        while (true)
        {
            DatagramPacket recievedPacket = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
            socketIn.receive(recievedPacket);

            int length = recievedPacket.getLength();
            String message_ = new String(recievedPacket.getData(), 0, length, "utf8");

            // Port i host, ktory wyslal nam zapytanie
            InetAddress clientAddress = recievedPacket.getAddress();
            int clientPort = recievedPacket.getPort();

            System.out.println(message_); //debuggowanie
            message msg=new message(message_);
            msg.pullIp();
            System.out.println(msg.text); //debuggowanie
           
            //gniazdo dla danych przekazywanych dalej
			DatagramSocket socketOut = new DatagramSocket();
			byte[] stringContents = msg.text.getBytes("utf8");
			
			DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
			sentPacket.setAddress(msg.address);
			sentPacket.setPort(msg.port);
			socketOut.send(sentPacket);
			
			DatagramPacket receivedPacket = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
			socketOut.setSoTimeout(800);

			try
			{
				socketOut.receive(receivedPacket);
				DatagramPacket tobBeSent=receivedPacket;
				tobBeSent.setAddress(clientAddress);
				tobBeSent.setPort(clientPort);
				socketIn.send(tobBeSent);
				
				length = recievedPacket.getLength();
				String receivedMessage = new String(receivedPacket.getData(), 0, length, "utf8");
				System.out.println("Odpowiedź: "+receivedMessage);
			}
			catch (SocketTimeoutException ste)
			{
				System.out.println("No response...");
				byte[] byteResponse=String.format("Failed on %d.%d.%d.%d:%d", me[0],me[1],me[2],me[3],listen_port).getBytes("utf8");
				DatagramPacket response = new DatagramPacket(byteResponse, byteResponse.length, clientAddress, clientPort);
				socketIn.send(response);
			}        

        }
    }
}

