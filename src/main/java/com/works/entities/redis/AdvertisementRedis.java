package com.works.entities.redis;

import com.works.entities.base.BaseAdvertisement;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("AdvertisementRedis")
public class AdvertisementRedis extends BaseAdvertisement {
}
