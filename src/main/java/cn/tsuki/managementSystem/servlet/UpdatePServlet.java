package cn.tsuki.managementSystem.servlet;

import cn.tsuki.managementSystem.bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by tsuki on 2017/4/21.
 */
/*
* 负责从数据库中取出相应的id的记录
* */

@WebServlet("/UpdatePServlet")
public class UpdatePServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdatePServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        int id = Integer.valueOf(request.getParameter("id"));
        try{
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
            // 转到userlist.jsp以显示用户清单
            request.setAttribute("user", user);
            // 下面不能使用response.sendRedirect("/useredit.jsp")，否则User对象传递就失效了
            getServletContext() .getRequestDispatcher("/useredit.jsp").forward(request, response);
        }
        catch(Exception e){
            out.println("<a href=\"javascript: window.history.back()\"> 返回</a>");
            out.println(e.toString());
            out.flush();
        }
    }
}
