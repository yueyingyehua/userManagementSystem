package cn.tsuki.managementSystem.conteroller;

import cn.tsuki.managementSystem.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsuki on 2017/4/23.
 */
@Controller
public class CrudController {
    private List<User> users = new ArrayList<User>();

    @RequestMapping("/userList") //请求url地址映射，类似Struts的action-mapping
    public ModelAndView userList(){
        ModelAndView mav = new ModelAndView();
        try {
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            String url = "jdbc:mysql://localhost/usersdb?user=root&password=123456";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM  account order by username");
            ResultSet rs = pstmt.executeQuery();
            users.clear();
            while (rs.next()) {
                int id = rs.getInt(1);
                String username = rs.getString(2);
                String password = "***";
                String createdTime = rs.getString(4);
                String locked = (rs.getInt(5) == 0) ? "" : "已锁定";
                Statement stmt = conn.createStatement();
                ResultSet rs2 = stmt.executeQuery("SELECT fullname, age, address from user_info where account_id = " + id);
                rs2.next();
                String fullname = rs2.getString(1);
                int age = rs2.getInt(2);
                String address = rs2.getString(3);
                User u = new User(id, username, password, fullname, age,
                        address, createdTime, locked);
                users.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        mav.addObject("users", users);
        mav.setViewName("userlist");
        return mav;
    }

    @RequestMapping("/Update")
    public String Update(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String fullname = user.getFullname();
        int age = user.getAge();
        String address = user.getAddress();
        int id = user.getId();

        try {
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            String url = "jdbc:mysql://localhost/usersdb?user=root&password=123456";
            Connection conn = DriverManager.getConnection(url);
            // 修改用户信息记录数据
            PreparedStatement pstmt = conn
                    .prepareStatement("update user_info set fullname=? , age=? ,address=? where account_id=?");
            pstmt.setString(1, fullname);
            pstmt.setInt(2, age);
            pstmt.setString(3, address);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            // 修改帐号记录数据
            pstmt = conn.prepareStatement("update account set username=? , password=? where id=?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            return "redirect:/userList.do"; //采用重定向方式跳转页面

        } catch (Exception e) {

        }
        return "index"; //跳转页面路径（默认为转发），
        // 该路径不需要包含spring-servlet配置文件中配置的前缀和后缀
    }

    @RequestMapping("/UpdateP")
    public ModelAndView UpdateP(@RequestParam("id") int id)throws Exception {
        //@RequestParam是指请求url地址映射中必须含有的参数（除非属性request=false）
        ModelAndView mav = new ModelAndView();

        Class.forName("org.gjt.mm.mysql.Driver").newInstance();
        String url =
                "jdbc:mysql://localhost/usersdb?user=root&password=123456";
        Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement("select fullname, age, address from user_info where account_id =?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        String fullname = rs.getString(1);
        int age = rs.getInt(2);
        String address = rs.getString(3);
        pstmt = conn.prepareStatement("select username, password, created_time,locked from account where id =?");
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();
        rs.next();String username = rs.getString(1);
        String password = rs.getString(2);
        String createdTime = rs.getString(3);
        String locked = (rs.getInt(4)== 0)? "":"未锁定";
        // 获取到用户信息，将其封装到一个Bean中，然后转到编辑页面
        User user = new User(id, username, password, fullname, age,
                address, createdTime, locked);

        mav.addObject("user", user);
        mav.setViewName("update");
        return mav;
    }

    @RequestMapping("/Delete")
    public String Delete(@RequestParam("id") int id) {
        try {
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            String url =
                    "jdbc:mysql://localhost/usersdb?user=root&password=123456";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement("DELETE from user_info WHERE account_id=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement("DELETE FROM account WHERE  id=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return "redirect:/userList.do";
        } catch (Exception e) {
            return "index";
        }
    }
    @RequestMapping("/Register")
    public String Register(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String fullname = user.getFullname();
        int age = user.getAge();
        String address = user.getAddress();

        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strNow = format.format(new java.util.Date());

        try {
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            String url =
                    "jdbc:mysql://localhost/usersdb?user=root&password=123456";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn
                    .prepareStatement("insert into account(username, password, created_time) values(?,?,?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, strNow);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement("select id from account where username=? and password=?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String accId = rs.getString(1);
                pstmt = conn.prepareStatement("insert into user_info(fullname, age, address, account_id) values(?,?,?,?)");
                pstmt.setString(1, fullname);
                pstmt.setInt(2, age);
                pstmt.setString(3, address);
                pstmt.setString(4, accId);
                pstmt.executeUpdate();
            }
            return "redirect:register_success.jsp";
        } catch (Exception e) {
            System.out.println("数据库访问错误：" + e.getMessage());
        }
        return "index";
    }

    @RequestMapping("/Login")
    public String Login(User user, HttpSession httpSession) {
        String username = user.getUsername();
        String password = user.getPassword();
        try {
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            String url = "jdbc:mysql://localhost/usersdb?user=root&password=123456";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement("SELECT id from account where username=? and password=?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String accId = rs.getString(1);
                pstmt = conn.prepareStatement("SELECT fullname,age,address from user_info where account_id=?");
                pstmt.setString(1, accId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    String fullname = rs.getString("fullname");
                    httpSession.setAttribute("FULLNAME", fullname);
                    return "redirect:/userList.do";
                }
            } else {
                return "login_failure";
            }
        } catch (Exception e) {

        }
        return "login_failure";
    }
}
