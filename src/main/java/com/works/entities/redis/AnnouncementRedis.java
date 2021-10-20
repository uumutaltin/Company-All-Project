package com.works.entities.redis;

import com.works.entities.base.BaseAnnouncement;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("AnnouncementRedis")
public class AnnouncementRedis extends BaseAnnouncement {
}
