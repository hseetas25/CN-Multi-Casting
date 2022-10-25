//imports
import java.net.*;
import java.io.*;

public class MultiCastReceive {
	
	public static final int MAX_LEN  = 1024;  /* max receive buffer */
	public static final int MIN_PORT = 1024;  /* min network port */
	public static final int MAX_PORT = 65535; /* max network port */
	
	public static void main(String argv[]) {
		
		InetAddress mcAddress=null; /* multicast address Ex: 224.168.1.124 */
	    int mcPort=0;               /* multicast port */
	    boolean done=false;         /* variable for send loop */
	
	     /* validate number of arguments whether Multicast address and port number have received */
	    if (argv.length != 2) {

			System.out.println("Usage: MultiCastReceive: " + "<Multicast IP> <Multicast Port>");
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
	
	    /* validate address argument is Multicast IP */
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
	    	
			System.out.println("Invalid Port Number " + mcPort);
			System.out.println("Port should be in range " + MIN_PORT+ " to " + MAX_PORT);
			System.exit(1);
	      
	    }
	
	    try {
	    	
	    	MulticastSocket sock = new MulticastSocket(mcPort);
	        sock.setReuseAddress(true);
	        sock.joinGroup(mcAddress);
	        while (!done) {

				byte[] buf = new byte[MAX_LEN];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				sock.receive(packet);
				System.out.println("Received " + packet.getLength() +" bytes from " + packet.getAddress() + ": " + new String(packet.getData(),0,packet.getLength()));
			
			}
	        sock.leaveGroup(mcAddress);
	        sock.close();
	        
	    }
	    catch (IOException e) {
	    	
	    	System.err.println(e.toString());
	        System.exit(1);
	      
	    }

	}
	
}
