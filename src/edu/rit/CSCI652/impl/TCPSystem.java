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
//    int sendingPort;
    ServerI serverI;

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

                                        receiverSocket.close();

                                        if (messageStr != null)
                                        {
                                            Gson gson = new Gson();
                                            Message message = gson.fromJson(messageStr, Message.class);
                                            serverI.success(message, recieverIp, recieverPort);
                                        }

                                    } catch (IOException e)
                                    {
                                        e.printStackTrace();
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
            Socket receiverSocket = new Socket(inetAddress, port);
            printWriter = new PrintWriter(receiverSocket.getOutputStream());
            printWriter.println(line);
            printWriter.flush();
            receiverSocket.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}

