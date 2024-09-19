package com.ddd.service;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author author
 * @since 2024-09-19
 */
public interface ICommentService extends IService<Comment> {

	ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

	ResponseResult addComment(Comment comment);
}
