package com.ddd.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddd.constants.SystemCanstants;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Comment;
import com.ddd.domain.entity.SysUser;
import com.ddd.domain.vo.CommentVo;
import com.ddd.domain.vo.PageVo;
import com.ddd.enums.AppHttpCodeEnum;
import com.ddd.handler.exception.SystemException;
import com.ddd.mapper.CommentMapper;
import com.ddd.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddd.service.ISysUserService;
import com.ddd.utils.BeanCopyUtils;
import com.ddd.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-19
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

	@Autowired
	private ISysUserService userService;


	@Override
	public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
		//先查询出父评论 条件为rootId为-1要带有文章ID
		QueryWrapper<Comment> wrapper = new QueryWrapper<>();
		wrapper.eq(SystemCanstants.ARTICLE_COMMENT.equals(commentType),"article_id",articleId);
		wrapper.eq("root_id",-1);
		wrapper.eq("type",commentType);
		Page<Comment> page = new Page<>(pageNum,pageSize);
		page(page, wrapper);
		//属性拷贝
		List<CommentVo> vo = copyToCommentList(page.getRecords());

		//查询子评论
		for (CommentVo commentVo : vo) {
			List<CommentVo> children =getChildren(commentVo.getId());
			commentVo.setChildren(children);
		}

		PageVo pageVo = new PageVo();
		pageVo.setRows(vo);
		pageVo.setTotal(page.getTotal());
		return ResponseResult.okResult(pageVo);
	}

	private List<CommentVo> getChildren(Long id) {
		QueryWrapper<Comment> wrapper = new QueryWrapper<>();
		wrapper.eq("root_id",id);
		List<Comment> list = list(wrapper);
		List<CommentVo> commentVos = copyToCommentList(list);
		return commentVos;
	}

	private List<CommentVo> copyToCommentList(List<Comment> records) {
		List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(records, CommentVo.class);
		List<CommentVo> collect = commentVos.stream().map(obj -> {
			Long toCommentUserId = obj.getToCommentUserId();
			if (toCommentUserId!=-1){
				SysUser commentUser = userService.getById(toCommentUserId);
				if (commentUser != null) {
					obj.setToCommentUserName(commentUser.getNickName());
				}
			}
			SysUser sysUser = userService.getById(obj.getCreateBy());
			if (sysUser != null) {
				obj.setUserName(sysUser.getNickName());
			}
			return obj;
		}).collect(Collectors.toList());
		return collect;
	}

	@Override
	public ResponseResult addComment(Comment comment) {
		if (!StringUtils.hasText(comment.getContent())){
			throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
		}
		//这里缺少的一些时间参数 还有用户信息会通过mybatisPlus自动填充
		save(comment);
		return ResponseResult.okResult();
	}
}
