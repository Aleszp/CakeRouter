import java.net.InetAddress;
import java.io.IOException;

public class message 
{
    public String text;
    public InetAddress firstNod;
    
    public message()
    {
		text="";
	}
	
	public void setFirstNod(int target[]) throws IOException
    {
		String result = String.format("%d.%d.%d.%d", target[0],target[1],target[2],target[3]);
		firstNod = InetAddress.getByName(result);
	}
    
    public void addText(String text_)
    {
		text = String.format("%s%s%c", text, text_,'\0');
	} 
	
    public void addFinalIpPort(int ip[], int port)
    {
		text = String.format("%03d %03d %03d %03d %05d %s", ip[0],ip[1],ip[2],ip[3],port, text);
	} 
	
	public void addNodIp(int ip[])
    {
        text = String.format("%03d %03d %03d %03d %s", ip[0],ip[1],ip[2],ip[3], text);
	} 
}
