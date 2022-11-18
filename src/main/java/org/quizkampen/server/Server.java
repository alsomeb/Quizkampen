package org.quizkampen.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public Server(){

        try {
            ServerSocket serverSocket = new ServerSocket(1234);


        Socket socket = serverSocket.accept();
        System.out.println("ServerStart");



        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public static void main(String[] args)  {



        }

    }

