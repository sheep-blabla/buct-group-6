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
public class Cfproblem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 比赛名称id
     */
    private String cfContestId;

    /**
     * 比赛题目索引
     */
    private String cfIndex;

    /**
     * 比赛题目名称
     */
    private String cfName;

    /**
     * 比赛题目类型
     */
    private String cfType;

    /**
     * 比赛题目得分
     */
    private Integer cfPoints;

    /**
     * 比赛题目评级
     */
    private Integer cfRating;

    /**
     * 比赛题目标签
     */
    private String cfTags;


}
