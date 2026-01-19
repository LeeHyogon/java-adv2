package chat.server;

import network.tcp.v6.ServerV6;
import network.tcp.v6.SessionManagerV6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class Server {
    private final int port;
    private final CommandManager commandManager;
    private final SessionManager sessionManager;

    private ServerSocket serverSocket;

    public Server(int port, CommandManager commandManager, SessionManager sessionManager) {
        this.port = port;
        this.commandManager = commandManager;
        this.sessionManager = sessionManager;
    }

    public void start() throws IOException {
        log("서버 시작" + commandManager.getClass());
        ServerSocket serverSocket = new ServerSocket(port);
        log("서버 소켓 시작 - 리스닝 포트: " + port);

        //셧다운 훅 등록
        addShutdownHook(serverSocket);

        //프로그램 작동
        running(serverSocket);

    }

    private void addShutdownHook(ServerSocket serverSocket) {
        ShutdownHook shutdownHook = new ShutdownHook(serverSocket, sessionManager);
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook, "shutdown"));
    }

    private void running(ServerSocket serverSocket) {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                log("소켓 연결: " + socket);

                Session session = new Session(socket, commandManager, sessionManager);
                Thread thread = new Thread(session);
                thread.start();
            }
        } catch (IOException e) {
            log("서버 소켓 종료: " + e);
        }
    }

    static class ShutdownHook implements Runnable {
        private final ServerSocket serverSocket;
        private final SessionManager sessionManager;

        ShutdownHook(ServerSocket serverSocket, SessionManager sessionManager) {
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
