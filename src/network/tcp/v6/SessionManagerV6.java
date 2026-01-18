package network.tcp.v6;

import java.util.ArrayList;
import java.util.List;

//동시성 처리
/*
 * 1. add랑 remove가 동시에 호출되는 경우
 * 2. closeAll을 하는데 remove랑 겹치는 경우
 * ->synchronized
 */
public class SessionManagerV6 {
    private List<SessionV6> sessions = new ArrayList<>();

    public synchronized void add(SessionV6 session) {
        sessions.add(session);
    }

    public synchronized void remove(SessionV6 session) {
        sessions.remove(session);
    }

    public synchronized void closeAll() {
        for (SessionV6 session : sessions) {
            session.close();
        }
        sessions.clear();
    }
}
