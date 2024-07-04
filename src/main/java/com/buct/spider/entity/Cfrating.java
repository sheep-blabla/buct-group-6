package com.buct.spider.entity;

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
public class Cfrating implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 比赛id
     */
    private String cfContestId;

    /**
     * 比赛名称
     */
    private String cfContestName;

    /**
     * 用户名称
     */
    private String cfUserId;

    /**
     * 用户排名
     */
    private String cfRank;

    /**
     * 更新时间
     */
    private String cfUpdateTime;

    /**
     * 旧积分
     */
    private String cfOldRating;

    /**
     * 新积分
     */
    private String cfNewRating;

    /**
     * 解题数
     */
    private Integer cfAcNumber;

    /**
     * 补题数
     */
    private Integer cfScNumber;


}
