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
public class Acsubmission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 提交序号
     */
    @TableId(value = "ac_submission_id", type = IdType.INPUT)
    private String acSubmissionId;

    /**
     * 提交时间
     */
    private String acSubmissionDate;

    /**
     * 比赛名称
     */
    private String acContestName;

    /**
     * 用户名称
     */
    private String acUserId;

    /**
     * 题目下标
     */
    private String acProblemIndex;

    /**
     * 题目名称
     */
    private String acProblemName;

    /**
     * 使用语言
     */
    private String acSubmissionLanguage;

    /**
     * 判题结果
     */
    private String acVerdict;

    /**
     * 提交方式(现场提交，补题)
     */
    private String acSubmissionType;


}
