package com.ddd.controller;


import com.ddd.constants.SystemCanstants;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Comment;
import com.ddd.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-09-19
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private ICommentService commentService;

	/**
	 * 查询出文件的评论
	 * @param articleId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/commentList")
	public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
		ResponseResult result = commentService.commentList(SystemCanstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
		return result;
	}

	/**
	 * 新增评论
	 * @param comment
	 * @return
	 */
	@PostMapping()
	public ResponseResult addComment(@RequestBody Comment comment){
		return commentService.addComment(comment);

	}


	@GetMapping("/linkCommentList")
	public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
		ResponseResult result = commentService.commentList(SystemCanstants.LINK_COMMENT,null,pageNum,pageSize);
		return result;
	}

}
