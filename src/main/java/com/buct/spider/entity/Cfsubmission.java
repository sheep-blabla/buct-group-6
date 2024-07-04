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
public class Cfsubmission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 提交序号
     */
    @TableId(value = "cf_submission_id", type = IdType.INPUT)
    private String cfSubmissionId;

    /**
     * 提交时间
     */
    private String cfSubmissionDate;

    /**
     * 比赛id
     */
    private String cfContestId;

    /**
     * 用户名称
     */
    private String cfUserId;

    /**
     * 题目下标
     */
    private String cfProblemIndex;

    /**
     * 题目名称
     */
    private String cfProblemName;

    /**
     * 使用语言
     */
    private String cfSubmissionLanguage;

    /**
     * 判题结果
     */
    private String cfVerdict;

    /**
     * 提交方式(现场提交，补题)
     */
    private String cfSubmissionType;


}
