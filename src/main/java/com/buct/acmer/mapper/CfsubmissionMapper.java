package com.buct.acmer.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfrating;
import com.buct.acmer.entity.Cfsubmission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author BUCT
 * @since 2024-07-09
 */
@Mapper
public interface CfsubmissionMapper extends BaseMapper<Cfsubmission> {
    //按比赛名查找提交信息
    @Select("SELECT stu.stu_name, stu.stu_no, cb.cf_submission_id, cb.cf_submission_date, " +
            "cb.cf_problem_index,cb.cf_problem_name, cb.cf_submission_language, cf_verdict, ct.cf_contest_name " +
            "FROM cfsubmission cb " +
            "JOIN student stu ON stu.stu_cf_id = cb.cf_user_id " +
            "JOIN cfcontest ct ON ct.cf_contest_id=cb.cf_contest_id "+
            "WHERE ct.cf_contest_name = #{cf_contest_name}")
    Page<Cfsubmission> selectSubmissionsByContestName(Page<Cfsubmission> page, @Param("cf_contest_name") String cf_contest_name);


}
