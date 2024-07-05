package com.buct.spider.atcoder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buct.spider.entity.Acrating;
import com.buct.spider.mapper.AcratingMapper;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

@Component
public class AcSubmissionPipeline implements Pipeline {

    @Resource
    private AcratingMapper acratingMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {

        Acrating acrating = resultItems.get("data");
        QueryWrapper<Acrating> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ac_contest_name",acrating.getAcContestName())
                .eq("ac_user_id",acrating.getAcUserId());
        Acrating exist = acratingMapper.selectOne(queryWrapper);
        if (exist == null) {
            acratingMapper.insert(acrating);
        } else {
            acratingMapper.update(acrating, queryWrapper);
        }
    }

}
