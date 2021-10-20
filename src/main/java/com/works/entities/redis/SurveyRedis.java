package com.works.entities.redis;

import com.works.entities.base.BaseSurvey;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("SurveyRedis")
public class SurveyRedis extends BaseSurvey {
}
