package com.buct.acmer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@ApiModel(value = "Cfproblem对象", description = "")
public class Cfproblem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("比赛名称id")
        private String cfContestId;

    @ApiModelProperty("比赛名称")
    private String cfContestName;

    @ApiModelProperty("比赛题目索引")
    private String cfIndex;

    @ApiModelProperty("比赛题目名称")
    private String cfProblemName;

    @ApiModelProperty("比赛题目类型")
    private String cfType;

    @ApiModelProperty("比赛题目得分")
    private Integer cfPoints;

    @ApiModelProperty("比赛题目评级")
    private Integer cfRating;

    @ApiModelProperty("比赛题目标签")
    private String cfTags;

    @ApiModelProperty("比赛题目链接")
    private String linkUrl;


}
