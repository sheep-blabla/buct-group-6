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
@ApiModel(value = "Cfsubmission对象", description = "")
public class Cfsubmission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("提交序号")
    @TableId(value = "cf_submission_id", type = IdType.INPUT)
    private String cfSubmissionId;

    @ApiModelProperty("提交时间")
    private String cfSubmissionDate;

    @ApiModelProperty("比赛名称id")
    private String cfContestId;

    @ApiModelProperty("用户名称")
    private String cfUserId;

    @ApiModelProperty("题目下标")
    private String cfProblemIndex;

    @ApiModelProperty("题目名称")
    private String cfProblemName;

    @ApiModelProperty("使用语言")
    private String cfSubmissionLanguage;

    @ApiModelProperty("判题结果")
    private String cfVerdict;

    @ApiModelProperty("提交方式(现场提交，补题)")
    private String cfSubmissionType;


    //外键学号姓名，比赛名字
    @TableField(exist = false)
    private String stuName;

    @TableField(exist = false)
    private String stuNo;

    @TableField(exist = false)
    private String cfContestName;


}
