package edu.rit.CSCI652;

import edu.rit.CSCI652.impl.ServerI;
import edu.rit.CSCI652.impl.UDPSystem;

import java.net.InetAddress;
import java.nio.channels.ScatteringByteChannel;
import java.util.*;

import java.io.IOException;

/**
 * Created by Ruzan on 2/3/2018.
 */
public class SenderTester
{

    public static void main(String ag[])
    {
        UDPSystem udpSystem = new UDPSystem(7777);
        try
        {
            Scanner sc = new Scanner(System.in);
            udpSystem.sendEchoLocal(sc.nextLine(), 6789);
            System.out.println("Sent 1");
            List<Integer> ports = new ArrayList<>();
            ports.add(6790);
            ports.add(6791);
            udpSystem.sendEchoLocal(sc.nextLine(), ports);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
