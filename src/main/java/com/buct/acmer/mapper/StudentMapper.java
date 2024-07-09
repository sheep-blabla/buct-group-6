package com.buct.acmer.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Student;
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
 * @since 2022-06-14
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    @Select("SELECT stu_no, stu_name, stu_class, cf_new_rating, cf_sum_contest " +
            "FROM student AS s, codeforces AS c " +
            "WHERE s.stu_cf_id = c.cf_id")
    Page<Student> selectStudent(Page<Student> page);

    @Select("SELECT stu_no, stu_name, stu_class, cf_new_rating, cf_sum_contest " +
            "FROM student AS s, codeforces AS c " +
            "WHERE cf_id= #{stuNo} and stu_name=#{stuName}")
    Page<Student> selectByVal(Page<Student> page, @Param("stuNo") String stuNo,@Param("stuName") String stuName);

}
