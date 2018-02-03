package edu.rit.CSCI652.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

public class UDPSystem
{
//    static UDPSystem instance;

    private DatagramSocket socket;
    private boolean running;
    private String eventManagerIP;
    private boolean receiving;
    ServerI agent;
//    MulticastSender server;
//    String multicastAddress;
//    ControllerI controller;

    public UDPSystem()
    {
        try
        {
            socket = new DatagramSocket(6789);
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
        System.out.println("Connected.");
        receiving = false;
    }

    public UDPSystem(int port)
    {
        try
        {
            socket = new DatagramSocket(port);
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
        System.out.println("Connected.");
        receiving = false;
    }

//    public static UDPSystem getInstance() throws IOException
//    {
//        if(instance == null)
//        {
//            instance = new UDPSystem();
//        }
//        return instance;
//    }

    public void sendEcho(String msg, String iPAddress) throws IOException
    {
        byte[] buf = msg.getBytes();
        InetAddress address = InetAddress.getByName(iPAddress);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 6789);
        socket.send(packet);
    }
    public void sendEcho(String msg, List<String> iPAddresses) throws IOException
    {
        byte[] buf;
        for(String address: iPAddresses)
        {
            sendEcho(msg, address);
//            InetAddress inetAddress = InetAddress.getByName(address);
//            buf = msg.getBytes();
//            DatagramPacket packet = new DatagramPacket(buf, buf.length, inetAddress, 6789);
//            socket.send(packet);
        }
    }

    //For localhost
    public void sendEchoLocal(String msg, int port) throws IOException
    {
        byte[] buf = msg.getBytes();
        InetAddress address = InetAddress.getByName("localhost");
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
    }

    public void sendEchoLocal(String msg, List<Integer> ports) throws IOException
    {
        byte[] buf;
        for(int port: ports)
        {
           sendEchoLocal(msg, port);
        }
    }

    public void close()
    {
        socket.close();
    }

    public void getMessages(ServerI serverI)
    {
        if (!receiving)
        {
            receiving = true;
            Thread thread = new Thread()
            {
                @Override
                public void run()
                {
                    running = true;
                    while (running)
                    {
                        byte[] buf = new byte[256];
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);
                        try
                        {
                            socket.receive(packet);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        InetAddress address = packet.getAddress();
                        int port = packet.getPort();
                        packet = new DatagramPacket(buf, buf.length, address, port);
                        String received = new String(packet.getData(), 0, packet.getLength());

                        new Thread()
                        {
                            @Override
                            public void run()
                            {
                                serverI.success(received);
                            }
                        }.start();

                        if (received.equals("end"))
                        {
                            running = false;
                            continue;
                        }
                    }
                    socket.close();
                }
            };
            thread.start();
        }
    }

    public DatagramSocket getSocket()
    {
        return socket;
    }

    public void setSocket(DatagramSocket socket)
    {
        this.socket = socket;
    }

    public boolean isRunning()
    {
        return running;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    public String getEventManagerIP()
    {
        return eventManagerIP;
    }

    public void setEventManagerIP(String eventManagerIP)
    {
        this.eventManagerIP = eventManagerIP;
    }
}