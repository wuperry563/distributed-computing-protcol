/*
 *
 * This project focuses on the implementation of a distributed system, where
 * there are several nodes who communicate among each other via messages. Each
 * node generates a random value and adds its own value while passing the
 * message to the next node.
 */

import java.io.IOException;
import java.util.Random;

/**
 * Start class contains the main method, which is required to execute the
 * program. It generates a new random number before creating a new node and
 * assigns those numbers to the respective node
 *
 *
 * @variable id is the id of the node to be created
 * @variable config_path is the path of the configuration file from where the
 * data is fetched
 */
public class Start {

    private int id;
    private String config_path;


    public Start(int id, String config_path) {
        this.id = id;
        this.config_path = config_path;
    }

//    public void run() {
//        startNode(id);
//    }

    private void startNode(int id) {
        Node n = Node.getInstance(id);
        n.listen();
    }

    public static void main(String... args) throws IOException {
        int nodeNumber = Integer.parseInt(args[0]);
        String config_path;
        /**
         * To run the program manually without passing command line arguments
         * int no_of_node = 4;
         *
         */
        config_path = "config.txt";
        Parser.getInstance(config_path);
        System.out.println("lmao");
        Start start = new Start(nodeNumber, config_path);
        start.startNode(nodeNumber);
//        Thread t = new Thread(start);
//        t.start();
    }

}
