package edu.rit.CSCI652;

import edu.rit.CSCI652.impl.ServerI;
import edu.rit.CSCI652.impl.UDPSystem;

import java.io.IOException;

/**
 * Created by Ruzan on 2/3/2018.
 */
public class ReceiverTester
{
    public static void main(String ag[])
    {
        int port = 6789;
        if (ag.length == 1)
        {
            port = Integer.parseInt(ag[0]);
        }
        UDPSystem udpSystem = new UDPSystem(port);
        udpSystem.getMessages(new ServerI()
        {
            @Override
            public void success(String message)
            {
                System.out.println("Received: " + message);
            }
        });
    }
}
