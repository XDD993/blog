package com.ddd.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author author
 * @since 2024-09-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
@ApiModel(value="Menu对象", description="菜单权限表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "是否为外链（0是 1否）")
    private Integer isFrame;

    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
    private String visible;

    @ApiModelProperty(value = "菜单状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    private String delFlag;

    @TableField(exist = false)
    private List<Menu> Children;


}
