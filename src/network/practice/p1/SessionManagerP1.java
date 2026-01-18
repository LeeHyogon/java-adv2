package network.practice.p1;



import java.util.ArrayList;
import java.util.List;

public class SessionManagerP1 {
    private List<SessionP1> sessions = new ArrayList<>();

    public synchronized void add(SessionP1 session) {
        sessions.add(session);
    }

    public synchronized void remove(SessionP1 session) {
        sessions.remove(session);
    }

    public synchronized void closeAll() {
        for (SessionP1 session : sessions) {
            session.close();
        }
        sessions.clear();
    }

    public synchronized List<SessionP1> getSessions() {
        return sessions;
    }
}
