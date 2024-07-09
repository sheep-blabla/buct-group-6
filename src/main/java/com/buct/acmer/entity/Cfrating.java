package com.buct.acmer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author BUCT
 * @since 2024-07-09
 */
@Getter
@Setter
@ApiModel(value = "Cfrating对象", description = "")
public class Cfrating implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("比赛id")
    @TableId(value = "cf_contest_id", type = IdType.INPUT)
    private String cfContestId;

    @ApiModelProperty("比赛名称")
    private String cfContestName;

    @ApiModelProperty("用户名称")
    private String cfUserId;

    @ApiModelProperty("用户排名")
    private String cfRank;

    @ApiModelProperty("更新时间")
    private String cfUpdateTime;

    @ApiModelProperty("旧积分")
    private String cfOldRating;

    @ApiModelProperty("新积分")
    private String cfNewRating;

    @ApiModelProperty("解题数")
    private Integer cfAcNumber;

    @ApiModelProperty("补题数")
    private Integer cfScNumber;

    //外键学生班级姓名
    @TableField(exist = false)
    private String stuClass;

    @TableField(exist = false)
    private String stuName;

}
