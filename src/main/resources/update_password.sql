-- 更新zhangsan用户的密码为加密后的123456
UPDATE emp 
SET password = '$2a$10$EbJ/ltk1DECoe8yz2Yg0cuakk3yoK9B/75gvLNkTmLwnkkWcqhwnG'
WHERE username = 'zhangsan';

-- 如果您有其他用户也需要更新，可以添加更多的UPDATE语句
-- UPDATE emp 
-- SET password = '$2a$10$EbJ/ltk1DECoe8yz2Yg0cuakk3yoK9B/75gvLNkTmLwnkkWcqhwnG'
-- WHERE username = '其他用户名';


