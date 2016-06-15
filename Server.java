import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server 
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
        //Otwarcie gniazda z okreslonym portem
        DatagramSocket datagramSocket = new DatagramSocket(listen_port);

        byte[] byteResponse = "OK".getBytes("utf8");

        while (true)
        {

            DatagramPacket recievedPacket = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);

            datagramSocket.receive(recievedPacket);

            int length = recievedPacket.getLength();
            String message_ = new String(recievedPacket.getData(), 0, length, "utf8");

            // Port i host, ktory wyslal nam zapytanie
            InetAddress address = recievedPacket.getAddress();
            int port = recievedPacket.getPort();

            System.out.println(message_);
            Thread.sleep(1); //oczekiwanie nie jest niezbedne


			byteResponse=message_.getBytes("utf8");
            DatagramPacket response = new DatagramPacket(byteResponse, byteResponse.length, address, port);
            System.out.println("Port= "+port);

            datagramSocket.send(response);

        }


    }
}
