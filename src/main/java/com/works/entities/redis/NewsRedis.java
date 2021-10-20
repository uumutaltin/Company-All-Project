package com.works.entities.redis;

import com.works.entities.base.BaseNews;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("NewsRedis")
public class NewsRedis extends BaseNews {
}
