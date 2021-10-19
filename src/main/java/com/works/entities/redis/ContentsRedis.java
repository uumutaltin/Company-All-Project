package com.works.entities.redis;

import com.works.entities.base.BaseContents;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("ContentsRedis")
public class ContentsRedis extends BaseContents {
}
