package com.buct.acmer.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfrating;
import com.buct.acmer.mapper.CfratingMapper;
import com.buct.acmer.service.ICfratingService;
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
public class CfratingServiceImpl extends ServiceImpl<CfratingMapper, Cfrating> implements ICfratingService {
    @Resource
    private CfratingMapper cfratingMapper;

    @Override
    public Page<Cfrating> selectParticipantsByContestName(int currentPage, int pageSize, String fieldValue) {
        Page<Cfrating> page = new Page<>(currentPage, pageSize);
        return cfratingMapper.selectParticipantsByContestName(page, fieldValue);
    }

    @Override
    public Page<Cfrating> selectContestByStuName(int currentPage, int pageSize, String fieldValue) {
        Page<Cfrating> page = new Page<>(currentPage, pageSize);
        return cfratingMapper.selectContestByStuName(page, fieldValue);
    }

}
