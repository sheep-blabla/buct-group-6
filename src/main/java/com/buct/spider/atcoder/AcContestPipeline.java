package com.buct.spider.atcoder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buct.spider.entity.Accontest;
import com.buct.spider.mapper.AccontestMapper;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class AcContestPipeline implements Pipeline {
    @Resource
    private AccontestMapper accontestMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> accontestMap = resultItems.getAll();
        for (Map.Entry<String, Object> entry : accontestMap.entrySet()) {
            if (entry.getValue() instanceof Accontest) {
                // 类型转换
                Accontest accontest = (Accontest) entry.getValue();
                QueryWrapper<Accontest> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("ac_contest_name",accontest.getAcContestName());
                Accontest exist = accontestMapper.selectOne(queryWrapper);
                if(exist==null){
                    accontestMapper.insert(accontest);
                }else {
                    accontestMapper.update(accontest,queryWrapper);
                }
            }
        }

    }
}
