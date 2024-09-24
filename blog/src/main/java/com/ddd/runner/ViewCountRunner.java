package com.ddd.runner;

import com.ddd.domain.entity.Article;
import com.ddd.service.IArticleService;
import com.ddd.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
//实现该接口 可以做到在项目启动的时候就执行run方法的代码
public class ViewCountRunner implements CommandLineRunner {

	@Autowired
	private IArticleService articleService;
	@Autowired
	private RedisCache redisCache;

	//程序启动的时候就开始把数据库的文章浏览量写入到Redis里
	@Override
	public void run(String... args) throws Exception {
		//获取全部文章信息
		List<Article> list = articleService.list();
		Map<String, Integer> map = list.stream().collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
		//把查询到的id作为key，viewCount作为value，一起存入Redis
		redisCache.setCacheMap("article:viewCount",map);
	}
}
