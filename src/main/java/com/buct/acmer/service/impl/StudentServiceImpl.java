package com.buct.acmer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Codeforces;
import com.buct.acmer.entity.Student;
import com.buct.acmer.mapper.StudentMapper;
import com.buct.acmer.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BUCT
 * @since 2022-06-14
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    @Resource
    private StudentMapper studentMapper;
    @Override
    public Page<Student>selectStudent(int currentPage, int pageSize) {
        Page<Student> studentPage = new Page<>(currentPage, pageSize);
        return studentMapper.selectStudent(studentPage);
    }

    @Override
    public Page<Student> selectByVal(int currentPage, int pageSize, String stuNo, String stuName) {
        Page<Student> page = new Page<>(currentPage, pageSize);
        return studentMapper.selectByVal(page, stuNo, stuName);
    }

}
