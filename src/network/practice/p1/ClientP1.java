package network.practice.p1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class ClientP1 {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        //try블록 밖에서 자원해제를 위해 선언
        log("클라이언트 시작");

        try (Socket socket = new Socket("localhost",PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream()))   {
            socket.setSoTimeout(3000);
            log("소켓 연결: " + socket);

            //수신 쓰레드 생성
            Thread readThread = new Thread(new ReadHandler(input), "ReadThread");
            readThread.start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("전송 문자: ");
                String toSend = scanner.nextLine();

                output.writeUTF(toSend);
                log("client -> server: " + toSend);

                if (toSend.equals("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            log("client 종료: " + e);
        }finally {
            log("client 종료");
        }
    }

    static class ReadHandler implements Runnable {
        private final DataInputStream input;

        ReadHandler(DataInputStream input) {
            this.input = input;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String received = input.readUTF();
                    log("client <- server: " + received);
                }
            } catch (IOException e) {
                log("read Thread 종료");
            }
        }
    }
}
