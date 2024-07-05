package com.buct.spider.config;

import com.buct.spider.atcoder.*;
import com.buct.spider.codeforces.GetCfData;
import com.buct.spider.entity.Student;
import com.buct.spider.mapper.StudentMapper;
import com.buct.spider.util.HttpRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ScheduleConfig {

    @Resource
    private ATProcess atProcess;
    @Resource
    private ATPipeline atPipeline;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private GetCfData getCfData;
    private AcContestProcess acContestProcess;
    @Resource
    private AcContestPipeline acContestPipeline;
    @Resource
    private AcSubmissionPipeline acSubmissionPipeline;

//    @Scheduled(fixedRate = 1000*60*60*24*7)   //定时器定义，设置执行时间

    //atcoder contest
    private void AcContestProcess(){
        Spider.create(acContestProcess)
                .addUrl("https://atcoder.jp/contests/")
                .addUrl("https://atcoder.jp/contests/archive")
                .addPipeline(acContestPipeline)
                .run();
    }


    @Scheduled(fixedRate = 1000*60*60*24*7)   //定时器定义，设置执行时间
    private void AtProcess() {

        List<Student> students = studentMapper.selectList(null);
        for (int i = 0; i < 1/*students.size()*/; i++) {
            String id = students.get(i).getStuAcId();
            if(id!=null && !id.equals("")){
                Spider.create(atProcess)
                        .addUrl("https://atcoder.jp/users/"+id+"/history")
                        .addPipeline(atPipeline)
                        .run();
            }
        }
    }




    @Scheduled(fixedRate = 1000*60*60*24*7)   //定时器定义，设置执行时间
    private void CfProcess() {
        getCfData.getInfo();
        getCfData.getStatus();
        getCfData.getContest();
    }


    @Scheduled(fixedRate =  1000*60*60*24*7)
    private void CfProblemProcess() { getCfData.getProblem(); }

    @Scheduled(fixedRate = 1000*60*60*24*7)
    private void CfSubmission() { getCfData.getSubmission(); }


}
