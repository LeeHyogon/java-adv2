package network.practice.p1;

import network.tcp.SocketCloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static util.MyLogger.log;

public class SessionP1 implements Runnable{
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManagerP1 sessionManager;
    private String name;
    private boolean closed = false;

    public SessionP1(Socket socket, DataInputStream input, DataOutputStream output, SessionManagerP1 sessionManager, String name) {
        this.socket = socket;
        this.input = input;
        this.output = output;
        this.sessionManager = sessionManager;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public DataInputStream getInput() {
        return input;
    }

    public SessionP1(Socket socket, SessionManagerP1 sessionManager) throws IOException {
        this.socket = socket;
        this.input =  new DataInputStream(socket.getInputStream());
        this.output =  new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        /*
         * join을 했을때만 넣어주는걸로 변경 this.sessionManager.add(this);
         */
        this.name = null;
    }

    // 가입 수행하려고 할때 close되면 sessionManager안에 지워지지 않고 남아있을 것 같은데 확인 필요
    public synchronized void join(String name) {
        this.name = name;
        this.sessionManager.add(this);
        try {
            this.output.writeUTF("join success: " + name);
        } catch (IOException e) {
            log("join error");
        }
    }

    public void setName(String name) {
        this.name = name;
        try {
            this.output.writeUTF("change name success: " + name);
        } catch (IOException e) {
            log("change name error");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                //클라이언트로부터 문자 받기
                String received = input.readUTF();
                log("client -> server: " + received);

                if (received.startsWith("/join|")) {
                    String[] parts = received.split("\\|",2);
                    String name = parts[1];
                    join(name);
                }
                else if (received.startsWith("/message|")) {
                    String[] parts = received.split("\\|",2);
                    String toSend = parts[1];
                    List<SessionP1> sessions = sessionManager.getSessions();
                    for (SessionP1 session : sessions) {
                        DataOutputStream sessionOutput = session.getOutput();
                        sessionOutput.writeUTF(toSend);
                    }
                }
                else if (received.startsWith("/change|")) {
                    String[] parts = received.split("\\|",2);
                    String name = parts[1];
                    setName(name);
                }
                else if (received.equals("/users")) {
                    List<SessionP1> sessions = sessionManager.getSessions();

                    StringBuilder sb = new StringBuilder();
                    sb.append("전체 사용자 목록: ");

                    for (SessionP1 session : sessions) {
                        sb.append(session.getName()).append(", ");
                    }

                    this.output.writeUTF(sb.toString());
                }
                else {
                    continue;
                }
            }
        } catch (IOException e) {
            log(e);
        } finally {
            sessionManager.remove(this);
            close();
        }
    }

    // 세션 종료시, 서버 종료시 동시에 호출될 수 있다.
    public synchronized void close() {
        // 두번 호출될 수 있음
        if (closed) {
            return;
        }
        SocketCloseUtil.closeAll(socket, input, output);
        closed = true;
        log("연결 종료: " + socket + " isClosed: " + socket.isClosed());
    }
}
