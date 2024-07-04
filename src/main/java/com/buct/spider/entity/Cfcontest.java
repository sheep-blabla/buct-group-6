package com.buct.spider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author BUCT
 * @since 2024-07-04
 */
@Getter
@Setter
public class Cfcontest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 比赛id
     */
    @TableId(value = "cf_contest_id", type = IdType.INPUT)
    private String cfContestId;

    /**
     * 比赛名称
     */
    private String cfContestName;

    /**
     * 比赛类型
     */
    private String cfContestType;

    /**
     * 比赛阶段
     */
    private String cfContestPhase;

    /**
     * 比赛是否冻结
     */
    private String cfContestFrozen;

    /**
     * 比赛持续时间
     */
    private String cfContestDurationseconds;

    /**
     * 比赛开始时间
     */
    private String cfContestStarttimeseconds;

    /**
     * 比赛相对于现在的时间差
     */
    private Integer cfContestRelativetimeseconds;

    /**
     * 比赛参加人数
     */
    private Integer cfContestParticipantsnumber;


}
