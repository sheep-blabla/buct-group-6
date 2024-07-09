package com.buct.acmer.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfcontest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buct.acmer.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author BUCT
 * @since 2024-07-09
 */
@Mapper
public interface CfcontestMapper extends BaseMapper<Cfcontest> {


    //按cfid查询比赛
    @Select("SELECT cf.cf_contest_name, cf.cf_contest_startTimeSeconds, " +
            "cf.cf_contest_durationSeconds, cf.cf_contest_participantsNumber " +  // 在这里添加一个空格
            "FROM cfcontest cf " +
            "WHERE cf.cf_contest_id = #{cf_contest_id}")
    Page<Cfcontest> selectByContestId(Page<Cfcontest> page, @Param("cf_contest_id") String cf_contest_id);


}
