package network.practice.p1;

import network.tcp.v6.SessionV6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ServerP1 {
    private static final int PORT = 12345;

    public static void main(String[] args) {

        log("서버 시작");
        SessionManagerP1 sessionManager = new SessionManagerP1();

        try (ServerSocket serverSocket = new ServerSocket(PORT); ){
            log("서버 소켓 시작 - 리스닝 포트: " + PORT);
            ShutdownHook shutdonwHook = new ShutdownHook(serverSocket, sessionManager);
            Runtime.getRuntime().addShutdownHook(new Thread(shutdonwHook, "shutdown"));


            while (true) {
                Socket socket = serverSocket.accept(); //블로킹
                log("소켓 연결" + socket);

                SessionP1 session = new SessionP1(socket, sessionManager);
                Thread thread = new Thread(session);
                thread.start();
            }
        } catch (IOException e) {
            log("서버소켓 종료" + e);
        }

    }


    static class ShutdownHook implements Runnable {
        private final ServerSocket serverSocket;
        private final SessionManagerP1 sessionManager;

        public ShutdownHook(ServerSocket serverSocket, SessionManagerP1 sessionManager) {
            this.serverSocket = serverSocket;
            this.sessionManager = sessionManager;
        }

        @Override
        public void run() {
            log("shutdownHook 실행");
            sessionManager.closeAll();
            try {
                serverSocket.close();

                Thread.sleep(1000); // 자원정리대기
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("e = " + e);
            }
        }
    }
}
