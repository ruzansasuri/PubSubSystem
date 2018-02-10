package edu.rit.CSCI652.impl;

import com.google.gson.Gson;
import edu.rit.CSCI652.demo.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.HashMap;
import java.util.List;

/**
 * @author Thomas Binu
 * @author Ruzan Sasuri
 * @author Amol Gaikwad
 *         <p>
 *         Utility class to handle TCP
 */
public class TCPSystem
{


    boolean close = false;
    int port;
    ServerI serverI;
//    HashMap<String, Socket> sockerMap = new HashMap<String, Socket>();
//    HashMap<String, String> socketMap = new HashMap<>();


    public TCPSystem(int port)
    {
        this.port = port;
    }


    public void sendMessage(Message message, String ipAddress, int port) throws IOException
    {

        Gson gson = new Gson();
        String messageStr = gson.toJson(message);
        sendToClient(messageStr, ipAddress, port);
    }

//    public void sendMessageLocal(Message message, int port) throws IOException {
//
//
//        System.out.println(port);
//        Gson gson = new Gson();
//        String messageStr = gson.toJson(message);
//        sendToClient(messageStr, Integer.toString(port));
//    }


    public void getMessages(ServerI serverI)
    {

        this.serverI = serverI;

        new Thread()
        {
            @Override
            public void run()
            {

                try
                {
                    ServerSocket serverSocket = new ServerSocket(port);
                    System.out.println("Connected.");
                    while (!close)
                    {
                        Socket receiverSocket = serverSocket.accept();

                        if (receiverSocket.isConnected())
                        {

                            Thread thread = new Thread()
                            {
                                @Override
                                public void run()
                                {

                                    String recieverIp = receiverSocket.getInetAddress().getHostAddress();
                                    int recieverPort = receiverSocket.getPort();
                                    Logging.print("receiver port:" + port);


                                    try
                                    {
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(receiverSocket.getInputStream()));
                                        String messageStr = bufferedReader.readLine();
                                        //todo
                                        receiverSocket.close();
//                                        sockerMap.put(Integer.toString(recieverPort), receiverSocket);
                                        if (messageStr != null)
                                        {
//                                            new Thread() {
//                                                @Override
//                                                public void run() {
                                            Gson gson = new Gson();
                                            Message message = gson.fromJson(messageStr, Message.class);
                                            serverI.success(message, recieverIp, recieverPort);
//                                                }
//                                            }.start();

                                        }

                                    } catch (IOException e)
                                    {
                                        e.printStackTrace();

                                        //todo
//                                        sockerMap.remove(recieverPort);

                                    }
                                }
                            };

                            thread.start();

                        }

                    }
                } catch (IOException e)
                {
                    System.out.println("I/O error: " + e);
                }
            } // run end

        }.start();

    }


    public void close()
    {
        close = true;
    }

    public void sendToClient(String line, String ipAddress, int port)
    {

        PrintWriter printWriter;
        try
        {
            InetAddress inetAddress = Inet4Address.getByName(ipAddress);
            System.out.println(ipAddress);
            Socket receiverSocket = new Socket(ipAddress, port);//sockerMap.get(ipAddress);
            printWriter = new PrintWriter(receiverSocket.getOutputStream());
            printWriter.println(line);
            printWriter.flush();
            //sockerMap.get(ipAddress).close();
            //printWriter.close();
            receiverSocket.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


//    public void connectToServer(String ip, int port, ServerI serverI)
//    {
//        InetAddress inetAddress;
//        this.serverI = serverI;
//
//        try
//        {
//            if (ip.equals("localhost"))
//                inetAddress = InetAddress.getLocalHost();
//            else
//                inetAddress = Inet4Address.getByName(ip);
//
//            System.out.println(port);
//            Socket socket = new Socket(inetAddress, port);
//
//            if (socket.isConnected())
//            {
////                sockerMap.put(Integer.toString(port), socket);
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////                new Thread() {
////                    @Override
////                    public void run() {
////                        while (!close) {
//                String messageStr = null;
//                try
//                {
//                    messageStr = bufferedReader.readLine();
//                    if (messageStr != null)
//                    {
//
//                        Gson gson = new Gson();
//                        Message message = gson.fromJson(messageStr, Message.class);
//                        serverI.success(message, ip, port);
//                    }
//                } catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//
//            }
////                }.start();
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }


}

