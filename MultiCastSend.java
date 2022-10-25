//imports
import java.net.*;
import java.io.*;

public class MultiCastSend {

    public static final int MIN_PORT = 1024;  /* min network port */
    public static final int MAX_PORT = 65535; /* max network port */

    public static void main(String argv[]) {

        InetAddress mcAddress=null;  /* multicast address Ex: 224.168.1.124 */ 
        int mcPort=0;                /* multicast port */
        int ttl=1;                   /* time to live */
        BufferedReader stdin;        /* input from keyboard */
        String sendString;           /* string to be sent */
        byte[] sendBytes;            /* bytes to be sent */

    /* validate number of arguments whether Multicast address and port number have received */
    if (argv.length != 2) {
      
        System.out.println("Usage: MultiCastSend: " +"<Multicast IP> <Multicast Port>");
        System.out.println();
        System.out.println("Please prive Multicast address and Port number");
        System.exit(1);
      
    }

    /* validate the multicast address argument */
    try {

        mcAddress = InetAddress.getByName(argv[0]);
    	
    }
    catch (UnknownHostException e) {

        System.err.println(argv[0] + " is not a valid IP address");
        System.out.println();
        System.out.println("Ensure that you enter a correct IP Address.");
        System.out.println(e.toString());
        System.exit(1);
      
    }

    /* validate address argument is a multicast IP */
    if (! mcAddress.isMulticastAddress()) {

        System.err.println(mcAddress.getHostAddress() + " is not a multicast IP address.");
        System.out.println("Ensure that you enter a correct MultiCast Address.");
        System.exit(1);
      
    }

    try {
        
        mcPort = Integer.parseInt(argv[1]);
      
    }
    catch (NumberFormatException e) {
    	
        System.out.println(argv[1] + "is an invalid port number ");
        System.out.println("Ensure that you enter a correct Port Number.");
        System.out.println();
        System.out.println(e.toString());
        System.exit(1);
	    
    }

    if ((mcPort < MIN_PORT) || (mcPort > MAX_PORT)) {
    	
    	  System.out.println("Invalid port number " + mcPort);
        System.out.println("Port should be in range " + MIN_PORT + " to " + MAX_PORT);
        System.exit(1);
      
    }

    try {
    	
    	  MulticastSocket sock = new MulticastSocket();
        sock.setTimeToLive(ttl);
        stdin=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Begin typing (return to send," + " ctrl-C to quit):");
        while ((sendString=stdin.readLine()) != null) {
        	
            sendBytes=sendString.getBytes();
            DatagramPacket packet = new DatagramPacket(sendBytes, sendBytes.length, mcAddress, mcPort);
            sock.send(packet);
        
      }
      sock.close();
    }
    
    catch (IOException e) {
      System.err.println(e.toString());
      System.exit(1);
    }

  }
  
}
