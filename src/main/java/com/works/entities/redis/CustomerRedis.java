package com.works.entities.redis;

import com.works.entities.base.BaseCustomer;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Customer")
public class CustomerRedis  extends BaseCustomer {
}
