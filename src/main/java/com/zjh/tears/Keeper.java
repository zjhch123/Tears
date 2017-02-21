package com.zjh.tears;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Created by zhangjiahao on 2017/2/21.
 */
public class Keeper {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8008);
            socket.getOutputStream().write("shutdown".getBytes());
            System.out.println("Server closed at " + new Date());
            System.out.println("Bye~");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
