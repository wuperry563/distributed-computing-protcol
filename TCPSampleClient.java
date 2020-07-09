import java.io.*;
import java.net.*;

public class TCPSampleClient
{
    public void go()
    {
        String message = "joe mamam";
        try
        {
            //Create a client socket and connect to server at 127.0.0.1 port 5000
            System.out.println("creating Socket");
            Socket clientSocket = new Socket("dc01",1232);
            //Read messages from server. Input stream are in bytes. They are converted to characters by InputStreamReader
            //Characters from the InputStreamReader are converted to buffered characters by BufferedReader
            
            System.out.println("creating writer");
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer.println(message);
            message = reader.readLine();
            writer.close();
	    System.out.println("oepning reader");
            //The method readLine is blocked until a message is received
            System.out.println("Server says:" + message);
            reader.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public static void main(String args[])
    {
        TCPSampleClient SampleClientObj = new TCPSampleClient();
        SampleClientObj.go();
    }
}
