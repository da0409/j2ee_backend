-- =====================================================================
-- 5. 活动预置数据（规范文档 5.6 / 12.1 第五点：三人共用同一份活动数据）
--    状态由后端根据 date 与当前时间计算，不存状态字段
-- =====================================================================
INSERT INTO activities (title, category, image, date, location, description, organizer) VALUES
('校园篮球比赛', '体育', '/images/basketball.jpg',
 DATE_ADD(CURDATE(), INTERVAL 5 DAY) + INTERVAL 14 HOUR, '学校体育馆',
 '学院篮球联赛决赛，欢迎各学院同学到场助威。', '体育学院');

INSERT INTO activities (title, category, image, date, location, description, organizer) VALUES
('人工智能前沿讲座', '学术', '/images/ai-lecture.jpg',
 DATE_ADD(CURDATE(), INTERVAL 2 DAY) + INTERVAL 19 HOUR, '图书馆报告厅',
 '邀请校外专家介绍大模型与人工智能的最新发展，讲座后可自由交流。', '计算机学院');

INSERT INTO activities (title, category, image, date, location, description, organizer) VALUES
('校园歌手大赛', '文艺', '/images/singing.jpg',
 DATE_ADD(CURDATE(), INTERVAL 10 DAY) + INTERVAL 18 HOUR, '大学生活动中心',
 '一年一度的校园歌手大赛总决赛，现场设观众投票环节。', '校团委');

INSERT INTO activities (title, category, image, date, location, description, organizer) VALUES
('图书馆志愿整理活动', '志愿服务', '/images/volunteer.jpg',
 DATE_ADD(CURDATE(), INTERVAL 1 DAY) + INTERVAL 9 HOUR, '图书馆一层大厅',
 '协助图书馆整理图书与引导读者，可计入志愿服务时长。', '青年志愿者协会');

INSERT INTO activities (title, category, image, date, location, description, organizer) VALUES
('春季运动会', '体育', '/images/sports-meeting.jpg',
 DATE_SUB(CURDATE(), INTERVAL 7 DAY) + INTERVAL 8 HOUR, '田径场',
 '全校春季运动会，包含田径、趣味运动等多个项目。', '校体育运动委员会');

INSERT INTO activities (title, category, image, date, location, description, organizer) VALUES
('创新创业分享会', '学术', '/images/startup.jpg',
 DATE_SUB(CURDATE(), INTERVAL 20 DAY) + INTERVAL 15 HOUR, '创新创业中心',
 '优秀校友分享创业经历与项目孵化经验。', '创新创业学院');

INSERT INTO activities (title, category, image, date, location, description, organizer) VALUES
('周末电影放映', '其他', '/images/movie.jpg',
 DATE_ADD(CURDATE(), INTERVAL 3 DAY) + INTERVAL 19 HOUR, '学生活动中心小剧场',
 '免费放映经典影片，设映后交流环节。', '校学生会');

INSERT INTO activities (title, category, image, date, location, description, organizer) VALUES
('英语角交流活动', '其他', '/images/english-corner.jpg',
 DATE_ADD(CURDATE(), INTERVAL 4 DAY) + INTERVAL 16 HOUR, '外语楼 201 教室',
 '每周一次的英语角，主题为校园生活与跨文化交流。', '外国语学院');

-- =====================================================================
-- 6. 报名关系预置数据（便于演示报名人数 / 我的活动）
--    student01 已报名：校园篮球比赛、人工智能前沿讲座
--    student02 已报名：校园篮球比赛
-- =====================================================================
INSERT INTO user_activity (user_id, activity_id, joined_at)
SELECT u.id, a.id, NOW()
FROM users u, activities a
WHERE u.username = 'student01' AND a.title IN ('校园篮球比赛', '人工智能前沿讲座');

INSERT INTO user_activity (user_id, activity_id, joined_at)
SELECT u.id, a.id, NOW()
FROM users u, activities a
WHERE u.username = 'student02' AND a.title = '校园篮球比赛';
