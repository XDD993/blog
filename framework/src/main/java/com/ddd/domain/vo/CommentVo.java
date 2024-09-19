package com.ddd.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
	private Long id;

	@ApiModelProperty(value = "文章id")
	private Long articleId;

	@ApiModelProperty(value = "根评论id")
	private Long rootId;

	@ApiModelProperty(value = "评论内容")
	private String content;

	@ApiModelProperty(value = "所回复的目标评论的userid")
	private Long toCommentUserId;

	@ApiModelProperty(value = "所回复的目标评论的用户名")
	private String toCommentUserName;

	@ApiModelProperty(value = "回复目标评论id")
	private Long toCommentId;

	private Long createBy;

	private Date createTime;

	private Long updateBy;

	private String userName;

	private List<CommentVo> children;
}
