package com.babbangona.commons.library.factory;

import com.babbangona.commons.library.dto.SessionDetail;

import java.io.Serial;
import java.io.Serializable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SessionDetailFactory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1738142805082711014L;
    private SessionDetail sessionDetail;

    synchronized
    public void setSessionDetail(SessionDetail sessionDetail) {
        this.sessionDetail = sessionDetail;
    }

    synchronized
    public SessionDetail getSessionDetail() {
        return this.sessionDetail;
    }
}
