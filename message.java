import java.net.InetAddress;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.DatagramPacket;
import java.net.DatagramPacket;
import java.util.Scanner;
import java.io.UnsupportedEncodingException;

public class message 
{
    public String text;
    public InetAddress address;
    public int port;
    
    public message(String text_)
    {
		text=String.format("%s%c", text_,'\0');
	}
	
	public message(DatagramPacket receivedPacket) throws UnsupportedEncodingException,UnknownHostException
	{
		text=new String(receivedPacket.getData(), 0, receivedPacket.getLength(), "utf8");
		pullIp();
	}
	
	public void setFirstNod(int targetIP[],int port_) throws IOException
    {
		String result = String.format("%d.%d.%d.%d", targetIP[0],targetIP[1],targetIP[2],targetIP[3]);
		address = InetAddress.getByName(result);
		port=port_;
	}
	
    public void pushIp(int ip[], int port)
    {
		text = String.format("%03d %03d %03d %03d %05d %s", ip[0],ip[1],ip[2],ip[3],port, text);
	} 
	
	public void pullIp() throws UnknownHostException
    {
		int addr[]=new int[4];
		Scanner scan = new Scanner(text);
		for(int ii=0;ii<4;ii++)
			addr[ii]=scan.nextInt();
			
		port=scan.nextInt();
		
		String result = String.format("%d.%d.%d.%d", addr[0],addr[1],addr[2],addr[3]);
		address = InetAddress.getByName(result);
		
		text=text.substring(22);
	} 
	
}
