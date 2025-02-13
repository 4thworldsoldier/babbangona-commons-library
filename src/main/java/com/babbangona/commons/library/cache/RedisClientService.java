package com.babbangona.commons.library.cache;

import io.lettuce.core.Limit;
import io.lettuce.core.Range;
import io.lettuce.core.RedisClient;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisClientService {

    private RedisClient client;
    private StatefulRedisConnection<String, String> connection;

    @Value("${redisUrl}")
    private String redisUrl;

    @PostConstruct
    private void init() {
        connectToRedis();
    }

    @PreDestroy
    private void destroy() {
        closeConnectionToRedis();
    }

    private void connectToRedis() {
        client = RedisClient.create(redisUrl);
        connection = client.connect();
    }

    private void closeConnectionToRedis() {
        connection.close();
        if (client != null) {
            client.shutdown();
        }
    }

    public RedisCommands<String, String> getRedisCommand() {
        return connection.sync();
    }

    public String getRedisUrl() {
        return this.redisUrl;
    }

    public void setKeyValue(String key, String value) {
        getRedisCommand().set(key, value);
    }

    public void setKeyValueWithLongTimeout(String key, String value, long timeoutSeconds) {
        setKeyValue(key, value);
        setKeyTimeoutInSeconds(key, timeoutSeconds);
    }

    public void setKeyValueWithDateTimeout(String key, String value, Date expiryDate) {
        setKeyValue(key, value);
        setKeyTimeoutInDate(key, expiryDate);
    }

    public void setKeyTimeoutInSeconds(String key, Long seconds) {
        getRedisCommand().expire(key, seconds);
    }

    public void setKeyTimeoutInDate(String key, Date expiryDate) {
        getRedisCommand().expireat(key, expiryDate);
    }

    public String getValue(String key) {
        return getRedisCommand().get(key);
    }

    public void setSync(String key, String value) {
        getRedisCommand().set(key, value);
    }


    public void setSync(String key, String value, long timeoutSeconds) {
        setSync(key, value);
        setSyncKeyTimeoutSeconds(key, timeoutSeconds);
    }

    public void setSync(String key, String value, Date expiryDate) {
        setSync(key, value);
        setSyncKeyTimeoutDate(key, expiryDate);
    }
    private void setSyncKeyTimeoutSeconds(String key, Long seconds) {
        getRedisCommand().expire(key, seconds);
    }

    public void setSyncKeyTimeoutDate(String key, Date expiryDate) {
        getRedisCommand().expireat(key, expiryDate);
    }

    public void expireKey(String key) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        getRedisCommand().expireat(key, c.getTime());
    }

    public void deleteKey(String key) {
        getRedisCommand().del(key);
    }

    public void setStream(String key, Map<String, String> data, long timeoutInSeconds) {
        getRedisCommand().xadd(key, data);
        if (timeoutInSeconds > 0) {
            setKeyTimeoutInSeconds(key, timeoutInSeconds);
        }
    }

    public List<Map<String, String>> readStream(String key, Date startDate, Date endDate) {
        List<Map<String, String>> list = new ArrayList<>();
        List<StreamMessage<String, String>> entries = getRedisCommand().xrange(key, Range.create(String.valueOf(startDate.getTime()), String.valueOf(endDate.getTime())));
        entries.forEach((e) -> list.add(e.getBody()));
        return list;
    }

    public List<Map<String, String>> readLastXStream(String key, int x) {
        List<Map<String, String>> list = new ArrayList<>();
        List<StreamMessage<String, String>> entries = getRedisCommand().xrevrange(key, Range.unbounded(), Limit.create(-1, x));
        entries.forEach((e) -> list.add(e.getBody()));
        return list;
    }
}
