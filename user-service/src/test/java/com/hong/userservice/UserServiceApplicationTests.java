package com.hong.userservice;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hong.common.redis.RedisUtil;
import com.hong.userservice.bean.User;
import com.hong.userservice.config.MyTransaction;
import com.hong.userservice.dao.UserDAO;
import com.hong.userservice.hystrix.QueryUserCommand;
import com.hong.userservice.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserServiceApplicationTests {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    private MyTransaction myTransaction;


    @Autowired
    private UserService userService;





    @Test
    void hystrixTest() {
        User user = new QueryUserCommand(userService).execute();
        System.out.println(user);
    }



    @Test
    void contextLoads() {
        userService.updateUser(34L, "5656780");
    }

    /**
     * 物理分页 limit
     */
    @Test
    void test2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDAO userDAO = sqlSession.getMapper(UserDAO.class);
        PageHelper.startPage(0, 1);
        List<User> userList = userDAO.queryList2();
        PageInfo<User> pageInfo = new PageInfo<User>(userList);
        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getTotal());
    }

    /**
     * 内存分页
     */
    @Test
    @Transactional
    void test6() {
        RowBounds rowBounds = new RowBounds(0, 1);
        List<User> userList = userDAO.queryList(rowBounds);
        System.out.println(userList);
    }

    @Test
    void test3() {
        String sql = "select id,name from user where id = ? ";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{34L}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User().setId(rs.getLong("id")).setName(rs.getString("name"));
            }
        });
        System.out.println(user);
    }


    @Test
    void test5() {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = myTransaction.begin();
            userDAO.updateUser(34L, "星期二");
            myTransaction.commit(transactionStatus);
        } catch (Exception e) {
            myTransaction.rollback(transactionStatus);
        }
    }


    @Test
    void test4() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            String sql = "select id,name from user where name = ? ";
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
//            Statement statement = conn.createStatement();
//            statement.executeQuery(sql);
            ps = conn.prepareStatement(sql);
            //prepareStatement 防止sql注入
            ps.setString(1, "'星期一' or 1=1");
            resultSet = ps.executeQuery();
            conn.commit();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User().setId(resultSet.getLong("id")).setName(resultSet.getString("name"));
                userList.add(user);
            }
            System.out.println(userList);
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
