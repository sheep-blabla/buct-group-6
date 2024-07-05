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
 * @since 2024-07-05
 */
@Getter
@Setter
public class Accontest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 比赛名称
     */
    @TableId(value = "ac_contest_name", type = IdType.INPUT)
    private String acContestName;

    /**
     * 比赛类型
     */
    private String acContestType;

    /**
     * 比赛持续时间
     */
    private String acContestDurationseconds;

    /**
     * 比赛开始时间
     */
    private String acContestStarttimeseconds;

    /**
     * 比赛参加人数
     */
    private Integer acContestParticipantsnumber;

    /**
     * 比赛状态
     */
    private String acContestPhase;


}
