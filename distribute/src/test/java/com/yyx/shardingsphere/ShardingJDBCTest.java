package com.yyx.shardingsphere;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyx.shardingsphere.entity.Course;
import com.yyx.shardingsphere.entity.Dict;
import com.yyx.shardingsphere.entity.User;
import com.yyx.shardingsphere.mapper.CourseMapper;
import com.yyx.shardingsphere.mapper.DictMapper;
import com.yyx.shardingsphere.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yinyuxin
 * @description
 * @date 2021/7/22 18:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingJDBCTest {

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private DictMapper dictMapper;
    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsertCourses() {
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            //course.setCid(Long.valueOf(i));
            course.setCname("name:"+i);
            course.setCstatus("1");
            course.setUserId(Long.valueOf((1000+i)+""));
            courseMapper.insert(course);
        }
    }

    @Test
    public void testQueryCourses() {
        QueryWrapper queryWrapper = new QueryWrapper();
        /*queryWrapper.ge("user_id", 1003);
        queryWrapper.le("user_id", 1005);*/
        queryWrapper.between("cid", 1003, 1005);
        List<Course> courses = courseMapper.selectList(queryWrapper);
        for (Course cours : courses) {
            System.out.println(JSONObject.toJSON(cours));
        }
    }

    @Test
    public void testQueryUserStatus() {
        Dict dict = new Dict();
        dict.setUstatus("0");
        dict.setUvalue("不正常");
        dictMapper.insert(dict);

        Dict dict1 = new Dict();
        dict1.setUstatus("1");
        dict1.setUvalue("正常");
        dictMapper.insert(dict1);

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUage(i);
            user.setUsername("用户"+i);
            user.setUstatus(i%2+"");
            userMapper.insert(user);
        }
        List<User> users = userMapper.queryUserStatus();
        users.forEach(user-> System.out.println(JSONObject.toJSONString(user)));
    }

    @Test
    public void testQueryUserStatus2() {
        List<User> users = userMapper.queryUserStatus();
        users.forEach(user-> System.out.println(JSONObject.toJSONString(user)));
    }
}
