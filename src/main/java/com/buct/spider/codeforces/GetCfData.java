package com.buct.spider.codeforces;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buct.spider.entity.*;
import com.buct.spider.mapper.*;
import com.buct.spider.util.HttpRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class GetCfData {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private CodeforcesMapper codeforcesMapper;
    @Resource
    private CfproblemMapper cfproblemMapper;
    @Resource
    private CfcontestMapper cfcontestMapper;
    @Resource
    private CfratingMapper cfratingMapper;
    @Resource
    private CfsubmissionMapper cfsubmissionMapper;
    /**
     * 获取cf比赛数据，cf数据为json格式返回，不需要解析html页面
     */

    //get
    public void getInfo(){
        List<Student> students = studentMapper.selectList(null);
        for (int i = 0; i < students.size(); i++) {
            String id = students.get(i).getStuCfId();
            if(id!=null && !id.equals("")){
                //获取json数据
                String data = HttpRequest.sendGet("https://codeforces.com/api/user.rating?handle=" + id);
                JSONObject jsonObject = JSONObject.parseObject(data);
                // 503则跳过该次访问
                if (jsonObject == null) continue;
                JSONArray result = jsonObject.getJSONArray("result");
                if(result.size()!=0){
                    JSONObject newOne = result.getJSONObject(result.size()-1);
                    //封装成codeforces对象
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Codeforces codeforces = new Codeforces();
                    codeforces.setCfId(newOne.getString("handle"));
                    codeforces.setCfContestId(newOne.getString("contestId"));
                    String ratingUpdateTimeSeconds = simpleDateFormat.format(new Date(newOne.getLong("ratingUpdateTimeSeconds")*1000));
                    codeforces.setCfDate(ratingUpdateTimeSeconds);
                    codeforces.setCfNewRating(newOne.getString("newRating"));
                    codeforces.setCfRank(newOne.getString("rank"));
                    codeforces.setCfContest(newOne.getString("contestName"));
                    codeforces.setCfSumContest(String.valueOf(result.size()));
                    codeforces.setCfOldRating(newOne.getString("oldRating"));
                    //存入数据库中
                    QueryWrapper<Codeforces> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("cf_id",codeforces.getCfId());
                    Codeforces exist = codeforcesMapper.selectById(codeforces.getCfId());
                    if(exist==null){
                        codeforcesMapper.insert(codeforces);
                    }else {
                        codeforcesMapper.update(codeforces,queryWrapper);
                    }
                } else {
                    Codeforces codeforces = new Codeforces();
                    codeforces.setCfId(id);
                    codeforces.setCfContest("-");
                    codeforces.setCfContestId("-");
                    codeforces.setCfDate("-");
                    codeforces.setCfRank("-");
                    codeforces.setCfNewRating("-");
                    codeforces.setCfOldRating("-");
                    codeforces.setCfSumContest("-");
                    //存入数据库中
                    QueryWrapper<Codeforces> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("cf_id",codeforces.getCfId());
                    Codeforces exist = codeforcesMapper.selectById(codeforces.getCfId());
                    if(exist==null){
                        codeforcesMapper.insert(codeforces);
                    }else {
                        codeforcesMapper.update(codeforces,queryWrapper);
                    }
                }

                /**
                 *
                 *  获取用户cf积分变化表,cfrating
                 *
                 */
                // 获取json数据
                String ratingData = HttpRequest.sendGet("https://codeforces.com/api/user.rating?handle=" + id);
                JSONObject ratingJsonObject = JSONObject.parseObject(ratingData);
                // 503则跳过该次访问
                if (ratingJsonObject == null) continue;
                JSONArray ratingResult = ratingJsonObject.getJSONArray("result");

                for(int j = 0; j <ratingResult.size(); j++) {
                    JSONObject ratingJson = ratingResult.getJSONObject(j);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    // 解析数据
                    String contestId = ratingJson.getString("contestId");
                    String contestName = ratingJson.getString("contestName");
                    String handle = ratingJson.getString("handle");
                    String rank = ratingJson.getString("rank");
                    String ratingUpdateTimeSeconds = simpleDateFormat.format(new Date(ratingJson.getLong("ratingUpdateTimeSeconds") * 1000));
                    String oldRating = ratingJson.getString("oldRating");
                    String newRating = ratingJson.getString("newRating");

                    // 封装成Cfrating对象
                    Cfrating cfrating = new Cfrating();
                    cfrating.setCfContestId(contestId);
                    cfrating.setCfContestName(contestName);
                    cfrating.setCfUserId(handle);
                    cfrating.setCfRank(rank);
                    cfrating.setCfUpdateTime(ratingUpdateTimeSeconds);
                    cfrating.setCfOldRating(oldRating);
                    cfrating.setCfNewRating(newRating);

                    // 存入数据库
                    QueryWrapper<Cfrating> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("cf_contest_id", contestId)
                            .eq("cf_user_id", handle);
                    Cfrating exist = cfratingMapper.selectOne(queryWrapper);
                    if(exist==null){
                        cfratingMapper.insert(cfrating);
                    }else {
                        cfratingMapper.update(cfrating,queryWrapper);
                    }
                }
            }
        }
    }

    /**
     *   获取cf题目信息
     */
    public void getProblem() {
        // 发送请求获取题目列表数据
        String data = HttpRequest.sendGet("https://codeforces.com/api/problemset.problems");
        JSONObject jsonObject = JSONObject.parseObject(data);
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray problems = result.getJSONArray("problems");

        // 遍历题目列表
        for (int i = 0; i < problems.size(); i++) {
            JSONObject problemJson = problems.getJSONObject(i);

            // 解析题目数据
            String contestId = problemJson.getString("contestId");
            String index = problemJson.getString("index");
            String name = problemJson.getString("name");
            String type = problemJson.getString("type");
            Integer points = problemJson.getInteger("points");
            Integer rating = problemJson.getInteger("rating");
            JSONArray tagsArray = problemJson.getJSONArray("tags");
            String tags = tagsArray.toJSONString();

            // 判断是否为空
            int ratingValue = -1;
            int pointsValue = -1;
            if (rating != null) {
                ratingValue = rating.intValue();
            }
            if (points != null) {
                pointsValue = points.intValue();
            }

            // 创建题目对象并设置数据
            Cfproblem problem = new Cfproblem();
            problem.setCfContestId(contestId);
            problem.setCfIndex(index);
            problem.setCfName(name);
            problem.setCfType(type);
            problem.setCfPoints(pointsValue);
            problem.setCfRating(ratingValue);
            problem.setCfTags(tags);

            // 存入数据库中
            QueryWrapper<Cfproblem> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cf_index", index)
                    .eq("cf_contest_id", contestId);

            Cfproblem exist = cfproblemMapper.selectOne(queryWrapper);
            if (exist == null) {
                // 不存在，进行插入操作
                cfproblemMapper.insert(problem);
            } else {
                // 已存在，进行更新操作
                cfproblemMapper.update(problem, queryWrapper);
            }
        }
    }

    /**
     * 获取cf竞赛信息
     */
    public void getContest() {
        String data = HttpRequest.sendGet("https://codeforces.com/api/contest.list?gym=false");
        JSONObject jsonObject = JSONObject.parseObject(data);
        JSONArray result = jsonObject.getJSONArray("result");

        // 遍历竞赛列表
        for(int i = 0; i < result.size(); i++) {
            JSONObject contestJson = result.getJSONObject(i);

            // 解析竞赛数据
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String contestId = contestJson.getString("id");
            String contestName = contestJson.getString("name");
            String contestType = contestJson.getString("type");
            String contestPhase = contestJson.getString("phase");
            String contestFrozen = contestJson.getString("frozen");
            Integer durationSeconds = contestJson.getInteger("durationSeconds");
            String contestStartTimeSeconds = simpleDateFormat.format(new Date(contestJson.getLong("startTimeSeconds") * 1000));
            Integer hour = durationSeconds / 60 / 60;
            Integer minutes = durationSeconds / 60 % 60;
            Integer seconds = durationSeconds % 60;
            String contestDurationSeconds = hour+":"+minutes+":"+seconds;
            Integer contestRelativeTimeSeconds = contestJson.getInteger("relativeTimeSeconds");
            Integer contestParticipantsnumber = 0;

            // 统计参赛人数
            if(contestPhase.equals("FINISHED")) {
                List<Student> students = studentMapper.selectList(null);
                for (int j = 0; j < students.size(); j++) {
                    String studentId = students.get(j).getStuCfId();
                    if (studentId != null && !studentId.equals("")) {

                        QueryWrapper<Cfrating> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("cf_contest_id", contestId)
                                .eq("cf_user_id", studentId);
                        Cfrating exist = cfratingMapper.selectOne(queryWrapper);
                        if(exist != null) {
                            contestParticipantsnumber++;
                        }
                    }
                }
            }

            // 创建竞赛对象并设置数据
            Cfcontest cfcontest = new Cfcontest();
            cfcontest.setCfContestId(contestId);
            cfcontest.setCfContestName(contestName);
            cfcontest.setCfContestType(contestType);
            cfcontest.setCfContestPhase(contestPhase);
            cfcontest.setCfContestFrozen(contestFrozen);
            cfcontest.setCfContestDurationseconds(contestDurationSeconds);
            cfcontest.setCfContestStarttimeseconds(contestStartTimeSeconds);
            cfcontest.setCfContestRelativetimeseconds(contestRelativeTimeSeconds);
            cfcontest.setCfContestParticipantsnumber(contestParticipantsnumber);

            // 存入数据库
            QueryWrapper<Cfcontest> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cf_contest_id", contestId);
            Cfcontest exist = cfcontestMapper.selectOne(queryWrapper);
            if (exist == null) {
                // 不存在，进行插入操作
                cfcontestMapper.insert(cfcontest);
            } else {
                // 已存在，进行更新操作
                cfcontestMapper.update(cfcontest, queryWrapper);
            }

        }
    }

    /**
     * 用户参与的比赛信息
     */
    public void getStatus() {

        List<Student> students = studentMapper.selectList(null);

        for (int i = 0; i < students.size(); i++) {
            String studentId = students.get(i).getStuCfId();
            if(studentId!=null && !studentId.equals("")) {
                QueryWrapper<Cfrating> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("cf_user_id", studentId);
                List<Cfrating> cfratingList = cfratingMapper.selectList(queryWrapper);

                for (int j = 0; j < cfratingList.size(); j++) {
                    String contestId = cfratingList.get(j).getCfContestId();
                    Integer acNumber = 0;
                    Integer scNumber = 0;
                    if (contestId!=null && !contestId.equals("")) {
                        String statusData = HttpRequest.sendGet("https://codeforces.com/api/contest.status?contestId=" + contestId + "&handle=" + studentId);
                        JSONObject statusJsonObject = JSONObject.parseObject(statusData);
                        // 503则跳过该次访问
                        if (statusJsonObject == null) continue;
                        JSONArray statusResult = statusJsonObject.getJSONArray("result");

                        for (int k = 0; k < statusResult.size(); k++) {
                            JSONObject resultObject = statusResult.getJSONObject(k);
                            JSONObject problems = resultObject.getJSONObject("problem");
                            JSONObject authors = resultObject.getJSONObject("author");

                            String participantType = authors.getString("participantType");
                            String verdict = resultObject.getString("verdict");

                            if (verdict.equals("OK") && participantType.equals("CONTESTANT")) {
                                acNumber++;
                            } else if (verdict.equals("OK") && participantType.equals("PRACTICE")) {
                                scNumber++;
                            }
                        }
                    }
                    Cfrating cfrating = new Cfrating();
                    cfrating.setCfAcNumber(acNumber);
                    cfrating.setCfScNumber(scNumber);

                    QueryWrapper<Cfrating> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("cf_user_id", studentId)
                            .eq("cf_contest_id", contestId);
                    Cfrating exits = cfratingMapper.selectOne(queryWrapper1);
                    if (exits != null) {
                        cfratingMapper.update(cfrating, queryWrapper1);
                    }
                }
            }
        }
    }


    /**
     * 用户的所有提交信息
     */
    public void getSubmission() {

        List<Student> students = studentMapper.selectList(null);

        for (int i = 0; i < students.size(); i++) {
            String studentId = students.get(i).getStuCfId();
            if (studentId != null && !studentId.equals("")) {

                String data = HttpRequest.sendGet("https://codeforces.com/api/user.status?handle=" + studentId);
                JSONObject jsonObject = JSONObject.parseObject(data);
                // 503则跳过该次访问
                if (jsonObject == null) continue;
                JSONArray result = jsonObject.getJSONArray("result");

                for (int j = 0; j <result.size(); j++) {
                    JSONObject submissionJson = result.getJSONObject(j);

                    String submissionId = submissionJson.getString("id");
                    String submissionDate = submissionJson.getString("creationTimeSeconds");
                    String contestId = submissionJson.getString("contestId");
                    String userId = studentId;
                    JSONObject problems = submissionJson.getJSONObject("problem");
                    String problemIndex = problems.getString("index");
                    String problemName = problems.getString("name");
                    String submissionLanguage = submissionJson.getString("programmingLanguage");
                    String verdict = submissionJson.getString("verdict");
                    JSONObject authors = submissionJson.getJSONObject("author");
                    String submissionType = authors.getString("participantType");

                    Cfsubmission cfsubmission = new Cfsubmission();
                    cfsubmission.setCfSubmissionId(submissionId);
                    cfsubmission.setCfSubmissionDate(submissionDate);
                    cfsubmission.setCfContestId(contestId);
                    cfsubmission.setCfUserId(userId);
                    cfsubmission.setCfProblemIndex(problemIndex);
                    cfsubmission.setCfProblemName(problemName);
                    cfsubmission.setCfSubmissionLanguage(submissionLanguage);
                    cfsubmission.setCfVerdict(verdict);
                    if(submissionType.equals("CONTESTANT"))
                        cfsubmission.setCfSubmissionType("现场提交");
                    else cfsubmission.setCfSubmissionType("补题");

                    QueryWrapper<Cfsubmission> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("cf_submission_id", cfsubmission.getCfSubmissionId());
                    Cfsubmission exists = cfsubmissionMapper.selectOne(queryWrapper);
                    if(exists == null) {
                        cfsubmissionMapper.insert(cfsubmission);
                    } else {
                        cfsubmissionMapper.update(cfsubmission, queryWrapper);
                    }
                }
            }
        }
    }



}
