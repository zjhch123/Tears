package com.zjh.tears.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class SocketObject {
    private Socket socket;

    private OutputStream os;
    private BufferedReader br;

    private Request request;
    private Response response;

    public SocketObject(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public BufferedReader openBufferedReader() throws IOException {
        if (this.br == null) {
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return this.br;
        } else {
            return this.br;
        }
    }

    public OutputStream openOutputStream() throws IOException {
        if (os == null) {
            this.os = socket.getOutputStream();
            return this.os;
        } else {
            return this.os;
        }
    }

    public BufferedReader getBufferedReader() {
        return this.br;
    }

    public OutputStream getOutputStream() {
        return this.os;
    }

    public void read() throws IOException {
        if (this.br == null) {
            this.openBufferedReader();
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
            if (line.length() == 0) break;
        }
        while (br.ready()) {
            char ch = (char) br.read();
            sb.append(ch);
        }
        this.getRequest().setRequestSource(sb.toString());
    }

    public void response(byte[] data) throws IOException {
        if (this.os == null) {
            this.openOutputStream();
        }
        os.write(data);
        os.flush();
    }

    public void destory() {
        try {
            if (this.br != null) br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (this.os != null) os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (this.socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setResponse(Response res) {
        this.response = res;
    }
    public Response getResponse() {
        return this.response;
    }
    public void setRequest(Request req) {this.request = req;}
    public Request getRequest() {
        if(this.request == null)
            this.request = new Request();
        return this.request;
    }

    public byte[] getResponseSource() {
        return this.response.getBytes();
    }
}
