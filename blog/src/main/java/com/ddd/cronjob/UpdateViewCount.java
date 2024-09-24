package com.ddd.cronjob;

import com.ddd.domain.entity.Article;
import com.ddd.service.IArticleService;
import com.ddd.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
//定时同步redis和数据库里的文章浏览量
public class UpdateViewCount {

	@Autowired
	private RedisCache redisCache;
	@Autowired
	private IArticleService articleService;

	@Scheduled(cron = "0/10 * * * * ?")
	public void updateViewCount(){
		Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");
		List<Article> list = cacheMap.entrySet().stream().map(obj -> {
			 return new Article(Long.valueOf(obj.getKey()), obj.getValue().longValue());
		}).collect(Collectors.toList());
		articleService.updateBatchById(list);
	}
}
