package com.vifinancenews.utilities;

import io.github.cdimascio.dotenv.Dotenv;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnection {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String REDIS_HOST = dotenv.get("REDIS_HOST");
    private static final String REDIS_PASSWORD = dotenv.get("REDIS_PASSWORD");
    private static final int REDIS_PORT = 6380;

    private static final JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), REDIS_HOST, REDIS_PORT, true);

    public static Jedis getConnection(int databaseIndex) {
        Jedis jedis = jedisPool.getResource();
        if (REDIS_PASSWORD != null && !REDIS_PASSWORD.isEmpty()) {
            jedis.auth(REDIS_PASSWORD);
        }
        jedis.select(databaseIndex); // Select database for session or OTP
        return jedis;
    }
}
