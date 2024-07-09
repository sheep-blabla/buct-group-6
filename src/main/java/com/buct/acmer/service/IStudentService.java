package com.buct.acmer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BUCT
 * @since 2022-06-14
 */
public interface IStudentService extends IService<Student> {
    Page<Student> selectStudent(int currentPage, int pageSize);
    Page<Student> selectByVal(int currentPage, int pageSize, String stuNo, String stuName);
}
