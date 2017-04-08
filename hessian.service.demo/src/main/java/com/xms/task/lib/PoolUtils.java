package com.xms.task.lib;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public final class PoolUtils {

    public static <V> V doWorkInPool(final JedisPool pool, final PoolWork<V> work) {
        if (pool == null) {
            throw new IllegalArgumentException("pool must not be null");
        }
        if (work == null) {
            throw new IllegalArgumentException("work must not be null");
        }
        Jedis poolResource = null;
        final V result;
        try {
            poolResource = pool.getResource();
            result = work.doWork(poolResource);
        } finally {
            if (poolResource != null) {
                poolResource.close();
            }
        }
        return result;
    }

    public interface PoolWork<V> {
        V doWork(Jedis jedis);
    }

    private PoolUtils() {
    }
}
