package com.buct.acmer.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfrating;
import com.buct.acmer.entity.Cfsubmission;
import com.buct.acmer.mapper.CfratingMapper;
import com.buct.acmer.mapper.CfsubmissionMapper;
import com.buct.acmer.service.ICfsubmissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BUCT
 * @since 2024-07-09
 */
@Service
public class CfsubmissionServiceImpl extends ServiceImpl<CfsubmissionMapper, Cfsubmission> implements ICfsubmissionService {
    @Resource
    private CfsubmissionMapper cfsubmissionMapper;
    @Override
    public Page<Cfsubmission> selectSubmissionsByContestName(int currentPage, int pageSize, String fieldValue) {
        Page<Cfsubmission> page = new Page<>(currentPage, pageSize);
        return cfsubmissionMapper.selectSubmissionsByContestName(page, fieldValue);
    }

}
