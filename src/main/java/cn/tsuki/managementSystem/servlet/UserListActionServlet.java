package cn.tsuki.managementSystem.servlet;

import cn.tsuki.managementSystem.bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsuki on 2017/4/21.
 */

@WebServlet("/UserListActionServlet")
public class UserListActionServlet extends HttpServlet {
    private static final long  serialVersionUID = 1L;

    public UserListActionServlet(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        List<User> users = new ArrayList<User>();
        PrintWriter out = response.getWriter();
        try {
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();

            String url = "jdbc:mysql://localhost/usersdb?user=root&password=123456";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM  account order by username");
            ResultSet rs = pstmt.executeQuery();
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
            out.println(e.toString());
            out.flush();
        }
        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/userlist.jsp").forward(request, response);
    }
}
