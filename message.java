import java.net.InetAddress;
import java.io.IOException;
import java.net.DatagramPacket;

public class message 
{
    public String text;
    public InetAddress address;
    public int port;
    
    public message(String text_)
    {
		text=String.format("%s%c", text_,'\0');;
	}
	
	public message(DatagramPacket recievedPacket)
	{
		
	}
	
	public void setFirstNod(int target[],int port_) throws IOException
    {
		String result = String.format("%d.%d.%d.%d", target[0],target[1],target[2],target[3],port);
		address = InetAddress.getByName(result);
		port=port_;
	}
	
    public void PushIp(int ip[], int port)
    {
		text = String.format("%03d %03d %03d %03d %05d %s", ip[0],ip[1],ip[2],ip[3],port, text);
	} 
	
	public void PullIp()
    {
		
	} 
	
}
