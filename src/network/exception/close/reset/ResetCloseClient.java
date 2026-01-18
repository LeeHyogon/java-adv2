package network.exception.close.reset;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import static util.MyLogger.log;

public class ResetCloseClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 12345);
        log("소켓 연결: " + socket);
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        // client <- server: FIN
        Thread.sleep(1000); //서버가 close() 호출할때 까지 잠시 대기

        // client -> server: PUSH [1] 무시하고 메세지날림
        output.write(1);

        // client <- server : RST 비정상 상황이므로 강제 종료
        Thread.sleep(1000); // RST 메시지 전송 대기

        try {
            //java.net.SocketException: 현재 연결은 사용자의 호스트 시스템의 소프트웨어의 의해 중단되었습니다 at network.exception.close.reset.ResetCloseClient.main
            int read = input.read();
            System.out.println("read = " + read);
        } catch (SocketException e ) {
            e.printStackTrace();
        }

        try {
            //java.net.SocketException: 현재 연결은 사용자의 호스트 시스템의 소프트웨어의 의해 중단되었습니다 at network.exception.close.reset.ResetCloseClient.main
            output.write(1);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
