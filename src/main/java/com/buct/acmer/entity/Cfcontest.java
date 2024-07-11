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
@ApiModel(value = "Cfcontest对象", description = "")
public class Cfcontest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("比赛id")
    @TableId(value = "cf_contest_id", type = IdType.INPUT)
    private String cfContestId;

    @ApiModelProperty("比赛名称")
    private String cfContestName;

    @ApiModelProperty("比赛类型")
    private String cfContestType;

    @ApiModelProperty("比赛阶段")
    private String cfContestPhase;

    @ApiModelProperty("比赛是否冻结")
    private String cfContestFrozen;

    @ApiModelProperty("比赛持续时间")
    private String cfContestDurationseconds;

    @ApiModelProperty("比赛开始时间")
    private String cfContestStarttimeseconds;

    @ApiModelProperty("比赛相对于现在的时间差")
    private Integer cfContestRelativetimeseconds;

    @ApiModelProperty("比赛参加人数")
    private Integer cfContestParticipantsnumber;


}
