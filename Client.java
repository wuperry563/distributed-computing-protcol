import java.io.*;
import java.net.*;

/**
 * Client class creates an object which then connects to the server by creating
 * a TCP socket
 *
 * @variable client_socket is used to connect to the server socket
 * @variable in is used to receive the token that has been sent by the client
 * @variable out is used to send the token to the next server
 * @variable client_token receives the token that has been passed to it as an
 * argument in its constructor
 */
public class Client {

    private Socket client_socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Token client_token;

    /**
     * Client constructor instantiates the client object
     *
     * @param t is the token that the Client class receives during its object
     * creation
     */
    public Client(Token t) {
        client_token = t;
    }

    /**
     * getToken method returns the token for the Client object
     *
     * @return token is returned
     */
    public Token getToken() {
        return client_token;
    }

    /**
     * connect method takes the host name and port number as its arguments so
     * that it can connect to the corresponding server
     *
     * @param hostname host name of the server
     * @param port port number at which the server is listening
     * @throws IOException
     */
    public void connect(String hostname, int port) throws IOException {
        try {
            client_socket = new Socket(hostname, port);
            out = new ObjectOutputStream(client_socket.getOutputStream());
            out.writeObject(client_token);
	    client_socket.close();
        } catch (IOException e) {
            System.out.println("Server " + hostname + " is not responding");
            System.out.println("Trying again after 3 sec");
            long start = System.currentTimeMillis();
            long end = start + 2 * 1000; // 2 seconds
            while (System.currentTimeMillis() < end) {
                // run
            }
            this.connect(hostname, port);
        }
    }
}
