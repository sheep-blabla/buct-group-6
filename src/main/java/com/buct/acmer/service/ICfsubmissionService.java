package com.buct.acmer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfrating;
import com.buct.acmer.entity.Cfsubmission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BUCT
 * @since 2024-07-09
 */
public interface ICfsubmissionService extends IService<Cfsubmission> {
    Page<Cfsubmission> selectSubmissionsByContestName(int currentPage, int pageSize, String fieldValue);
}
