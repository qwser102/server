package com.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
	private ServerSocket serverSocket;
	 
    public Server() {
        try {
            System.out.println("正在启动服务端...");
            serverSocket = new ServerSocket(8088);
            System.out.println("服务端启动完毕!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void start() {
        try {
            while (true) {
                //System.out.println("等待客户端连接...");
                Socket socket = serverSocket.accept();//阻塞方法
                //System.out.println("一个客户端连接了!");

                Runnable handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
 
    private class ClientHandler implements Runnable {//建立类实现Runnable
        private Socket socket;
        private String host;//获取发送人的地址 先实例变量的初始化
 
        public ClientHandler(Socket socket) {//获取插件 定义属性
            this.socket = socket;
            host = socket.getInetAddress().getHostAddress();
        }
 
        public void run() {
            try {//获取读取输出的内容
                InputStream in = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
 
                String line;
 
                while ((line = br.readLine()) != null) {
                    System.out.println("receive msg: " + line + "from "+ host);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
 
        }
    }

}
