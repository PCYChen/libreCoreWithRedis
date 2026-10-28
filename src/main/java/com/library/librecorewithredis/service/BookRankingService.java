package com.library.librecorewithredis.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
public class BookRankingService {

    private static final String LEADERBOARD_KEY = "book:search:leaderboard";

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 紀錄搜尋動作：將該圖書的搜尋次數 +1
     */
    public void incrementSearchCount(String bookId) {
        redisTemplate.opsForZSet().incrementScore(LEADERBOARD_KEY, bookId, 1);
    }

    /**
     * 取得熱門搜尋排行榜 Top N
     */
    public Set<ZSetOperations.TypedTuple<String>> getTopSearchBooks(long topN) {
        // ZREVRANGE: 從高到低排序，取得索引 0 至 topN-1
        return redisTemplate.opsForZSet().reverseRangeWithScores(LEADERBOARD_KEY, 0, topN - 1);
    }
}
