CREATE TABLE IF NOT EXISTS `student` (
    `stu_no` varchar(255) NOT NULL,
    `stu_name` varchar(255) DEFAULT NULL,
    `school` varchar(255) DEFAULT NULL,
    `grade` varchar(255) DEFAULT NULL,
    `stu_class` varchar(255) DEFAULT NULL,
    `stu_ac_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `stu_cf_id` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`stu_no`)
    );

CREATE TABLE IF NOT EXISTS `atcoder` (
    `ac_id` varchar(255) NOT NULL COMMENT 'id',
    `ac_date` varchar(255) DEFAULT NULL COMMENT '日期',
    `ac_contest` varchar(255) DEFAULT NULL COMMENT '比赛名称',
    `ac_rank` varchar(255) DEFAULT NULL COMMENT '排名',
    `ac_performance` varchar(255) DEFAULT NULL COMMENT '表现',
    `ac_newRating` varchar(255) DEFAULT NULL COMMENT '新积分',
    `ac_diff` varchar(255) DEFAULT NULL COMMENT '积分变化',
    `ac_count` varchar(255) DEFAULT NULL COMMENT '比赛次数',
    `ac_maxRating` varchar(255) DEFAULT NULL COMMENT '最高积分',
    PRIMARY KEY (`ac_id`)
    );

CREATE TABLE IF NOT EXISTS `codeforces` (
    `cf_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
    `cf_contest` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比赛名称',
    `cf_contest_id` varchar(255) DEFAULT NULL COMMENT '比赛名称id',
    `cf_rank` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比赛的排名',
    `cf_old_rating` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '旧积分',
    `cf_new_rating` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最新积分',
    `cf_sum_contest` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参与比赛的总数',
    `cf_date` varchar(255) DEFAULT NULL COMMENT '日期',
    PRIMARY KEY (`cf_id`) USING BTREE
    );

CREATE TABLE IF NOT EXISTS `cfsubmission` (
    `cf_submission_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提交序号',
    `cf_submission_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提交时间',
    `cf_contest_id` varchar(255) DEFAULT NULL COMMENT '比赛名称id',
    `cf_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名称',
    `cf_problem_index` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '题目下标',
    `cf_problem_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '题目名称',
    `cf_submission_language` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '使用语言',
    `cf_verdict` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '判题结果',
    `cf_submission_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提交方式(现场提交，补题)',
    PRIMARY KEY (`cf_submission_id`) USING BTREE
    );

CREATE TABLE IF NOT EXISTS `cfrating` (
    `cf_contest_id` varchar(255) NOT NULL COMMENT '比赛id',
    `cf_contest_name` varchar(255) DEFAULT NULL COMMENT '比赛名称',
    `cf_user_id` varchar(255) DEFAULT NULL COMMENT '用户名称',
    `cf_rank` varchar(255) DEFAULT NULL COMMENT '用户排名',
    `cf_update_time` varchar(255) DEFAULT NULL COMMENT '更新时间',
    `cf_old_rating` varchar(255) DEFAULT NULL COMMENT '旧积分',
    `cf_new_rating` varchar(255) DEFAULT NULL COMMENT '新积分',
    `cf_ac_number` int DEFAULT NULL COMMENT '解题数',
    `cf_sc_number` int DEFAULT NULL COMMENT '补题数',
    PRIMARY KEY (`cf_contest_id`) USING BTREE
    );

CREATE TABLE IF NOT EXISTS `cfproblem` (
    `cf_contest_id` varchar(255) NOT NULL COMMENT '比赛名称id',
    `cf_contest_name` varchar(255) DEFAULT NULL COMMENT '比赛名称',
    `cf_index` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '比赛题目索引',
    `cf_problem_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比赛题目名称',
    `cf_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比赛题目类型',
    `cf_points` int DEFAULT NULL COMMENT '比赛题目得分',
    `cf_rating` int DEFAULT NULL COMMENT '比赛题目评级',
    `cf_tags` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '比赛题目标签',
    `link_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比赛题目链接',
    PRIMARY KEY (`cf_contest_id`, `cf_index`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `cfcontest` (
    `cf_contest_id` varchar(255) NOT NULL COMMENT '比赛id',
    `cf_contest_name` varchar(255) DEFAULT NULL COMMENT '比赛名称',
    `cf_contest_type` varchar(255) DEFAULT NULL COMMENT '比赛类型',
    `cf_contest_phase` varchar(255) DEFAULT NULL COMMENT '比赛阶段',
    `cf_contest_frozen` tinyint DEFAULT NULL COMMENT '比赛是否冻结',
    `cf_contest_durationSeconds` varchar(255) DEFAULT NULL COMMENT '比赛持续时间',
    `cf_contest_startTimeSeconds` varchar(255) DEFAULT NULL COMMENT '比赛开始时间',
    `cf_contest_relativeTimeSeconds` int DEFAULT NULL COMMENT '比赛相对于现在的时间差',
    `cf_contest_participantsNumber` int DEFAULT NULL COMMENT '比赛参加人数',
    PRIMARY KEY (`cf_contest_id`)
    );
