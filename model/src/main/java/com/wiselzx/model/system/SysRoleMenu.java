package com.wiselzx.model.system;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(description = "角色菜单")
@TableName("sys_role_menu")
public class SysRoleMenu  {
	
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long id;

	@TableField("create_time")
	private Date createTime;

	@TableField("update_time")
	private Date updateTime;

	@TableField("is_deleted")
	private Integer isDeleted;

	@TableField(exist = false)
	private Map<String,Object> param = new HashMap<>();
	@ApiModelProperty(value = "角色id")
	@TableField("role_id")
	private Long roleId;

	@ApiModelProperty(value = "菜单id")
	@TableField("menu_id")
	private Long menuId;

}

