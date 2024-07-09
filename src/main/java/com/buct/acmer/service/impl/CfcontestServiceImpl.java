package com.buct.acmer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfcontest;
import com.buct.acmer.entity.Student;
import com.buct.acmer.mapper.CfcontestMapper;
import com.buct.acmer.service.ICfcontestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BUCT
 * @since 2024-07-09
 */
@Service
public class CfcontestServiceImpl extends ServiceImpl<CfcontestMapper, Cfcontest> implements ICfcontestService {
    @Resource
    private CfcontestMapper cfcontestMapper;

    public Page<Cfcontest> selectAllOrderByStartTimeDesc(int currentPage, int pageSize) {
        Page<Cfcontest> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Cfcontest> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("cf_contest_startTimeSeconds");
        return cfcontestMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Page<Cfcontest> selectByContestId(int currentPage, int pageSize, String cf_contest_id) {
        Page<Cfcontest> page = new Page<>(currentPage, pageSize);
        return cfcontestMapper.selectByContestId(page, cf_contest_id);
    }
}
