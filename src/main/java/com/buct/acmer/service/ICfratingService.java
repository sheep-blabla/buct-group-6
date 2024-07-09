package com.buct.acmer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfrating;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BUCT
 * @since 2024-07-09
 */
public interface ICfratingService extends IService<Cfrating> {
    Page<Cfrating> selectParticipantsByContestName(int currentPage, int pageSize, String fieldValue);
    Page<Cfrating> selectContestByStuName(int currentPage, int pageSize, String fieldValue);
}
