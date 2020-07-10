/*
 * @version 1.0
 *
 * This project focuses on the implementation of a distributed system, where
 * there are several nodes who communicate among each other via messages. Each
 * node generates a random value and adds its own value while passing the
 * message to the next node.
 */

import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Node class implements Runnable method and creates two threads, one for client
 * and the other for server. Server listens at the designated port while the
 * client is responsible for creating the token and connecting to the server via
 * socket connection
 *
 * @variable type indicates that whether the node is acting as a server or as a
 * client
 * @variable value is the random value of the token that is assigned to each
 * node
 * @variable server_socket is used to create socket for the server at which it
 * listens for a client
 * @variable identifier tells the node about its own identity
 * @variable port is the port number which the server_socket uses
 * @variable hostname is the host name of the node where it is running
 * @variable all_nodes is an array that stores the information of the whole
 * topology
 * @variable number_of_nodes is the total number of nodes in the distributed
 * system
 * @variable all_circuits contains all the circuit paths for which each node has
 * to generate a corresponding token
 * @variable config_file_path stores the address of the configuration file
 * @variable token is the object that contains all the required information that
 * is needed to be sent from one node to another
 */
public class Node {

    private static Node instance = null;
    private static int id = -1;
    public static int messagesSent = 0;
    private static final int SOCKET_TIMEOUT = 30000;
    private static final String MESSAGE_TO_SEND = "lmao";
    private static PrintWriter writer = null;

    private Node() {
    }

    public static Node getInstance(int id) throws Exception{
        if(instance == null){
            System.out.println("ID IS::" + id);
            Node.instance = new Node();
            instance.id = id;
            writer = new PrintWriter("config-" + id + ".out", "UTF-8");
            messagesSent = 0;
            initializeSockets();
        }
        return Node.instance;
    }

    private static void initializeSockets() {

    }

    //Check if messages are full first, otherwise stay passive.
    //If not full, then turn into client, random pick neighbors, and send those messages with MinSendDelay,
    //With Between minPerActive/maxPerActive messages
    public void activate() throws Exception{
        //should check if message is full? or "listen's" job?
        int min = Parser.instance.minPerActive;
        int max = Parser.instance.maxPerActive;
        int messagesToSend = ThreadLocalRandom.current().nextInt(min,1+max);
        Thread.sleep(10000);
        int index = 0;
        while(messagesSentLessThanMax() && index < messagesToSend){
            int nodeToMessage = getRandomNodeFromNeighbors();
            NodeInfo targetNode = Parser.nodes.get(nodeToMessage);
            SocketAddress socketAddress = new InetSocketAddress(targetNode.getHostName(),targetNode.getListenPort());
            SctpChannel client = SctpChannel.open();
            client.connect(socketAddress);
            try{
		        System.out.println("opening socket to write to " + targetNode.getHostName());
		        System.out.println(id+"will write to node"+ targetNode.getHostName());
                Channel.send(client,id+"");
            }
            catch(Exception e){
                System.out.println("Connection Exception");
//                e.printStackTrace();
            }
            finally{
                messagesSent++;
                messagesToSend++;
                //TODO: Print writer to config file
                client.close();
                Thread.sleep(Parser.minSendDelay);
            }
        }
        listen();
    }

    private int getRandomNodeFromNeighbors() {
        NodeInfo node = Parser.instance.nodes.get(id);
        List<Integer> neighbors = node.getNeighbors();
        int index = ThreadLocalRandom.current().nextInt(0,neighbors.size());
        int pickedNode = neighbors.get(index);
        return pickedNode;
    }

    public void listen() {
        boolean shouldClose = false;
        while(!shouldClose){
            try{
                Parser parser = Parser.getInstance("");
                NodeInfo node = parser.nodes.get(instance.id);
                int port = node.getListenPort();
                writer.println(instance.id+ "listening on "+ port);
                ServerSocket socket = new ServerSocket(port);
                System.out.println(id + "will listen on " +port);
                Thread.sleep(1000);
                Socket sock = socket.accept();
                sock.setSoTimeout(instance.SOCKET_TIMEOUT);
                BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String message = reader.readLine();
                writer.println("Message Received: " +message);
                System.out.println("Message Received: " +message);
                if(messagesSentLessThanMax()){
                    System.out.println(id+"terminating to activate node to send messages.");
                    shouldClose = true;
                    writer.close();
                    reader.close();
                    sock.close();
                    socket.close();
                    //TODO:move to active check?
                    instance.activate();
                }else{
                    Thread.sleep(5000);
                }

            } catch(Exception ex){
//                ex.printStackTrace();
            }
        }
    }

    private boolean messagesSentLessThanMax() {
        return Parser.instance.maxNumber > instance.messagesSent;
    }
}
