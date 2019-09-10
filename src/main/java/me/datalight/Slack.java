package me.datalight;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.IOException;

class Slack {
    static SlackSession connect() throws IOException {
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession("");//ключ к слаку
        session.connect();
        return session;
    }
}
