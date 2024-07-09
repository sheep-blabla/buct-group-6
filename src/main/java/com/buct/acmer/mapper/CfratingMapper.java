package com.buct.acmer.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfrating;
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
public interface CfratingMapper extends BaseMapper<Cfrating> {

    //通过比赛名查找参加的学生
    @Select("SELECT stu.stu_name, stu.stu_class, cf.cf_new_rating, cf.cf_old_rating, " +
            "cf.cf_contest_name,cf.cf_rank, cf.cf_ac_number, cf_sc_number " +
            "FROM student stu " +
            "JOIN cfrating cf ON stu.stu_cf_id = cf.cf_user_id " +
            "WHERE cf.cf_contest_name = #{cf_contest_name}")
    Page<Cfrating> selectParticipantsByContestName(Page<Cfrating> page, @Param("cf_contest_name") String cf_contest_name);

    //通过学生名查找该学生参加的比赛
    @Select("SELECT stu.stu_name, stu.stu_class, cf.cf_new_rating, cf.cf_old_rating, " +
            "cf.cf_contest_name,cf.cf_rank, cf.cf_ac_number, cf_update_time " +
            "FROM student stu " +
            "JOIN cfrating cf ON stu.stu_cf_id = cf.cf_user_id " +
            "WHERE stu.stu_name = #{stu_name}")
    Page<Cfrating> selectContestByStuName(Page<Cfrating> page, @Param("stu_name") String stu_name);


}
