package com.buct.acmer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfcontest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buct.acmer.entity.Student;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BUCT
 * @since 2024-07-09
 */
public interface ICfcontestService extends IService<Cfcontest> {
    Page<Cfcontest> selectByContestId(int currentPage, int pageSize, String cf_contest_id);
}
