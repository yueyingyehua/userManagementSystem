package cn.tsuki.managementSystem.servlet;

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

/**
 * Created by tsuki on 2017/4/21.
 */

@WebServlet("/UpdateActionServlet")
public class UpdateActionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateActionServlet() {
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
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String age = request.getParameter("age");
        String address = request.getParameter("address");
        try{
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            String url = "jdbc:mysql://localhost/usersdb?user=root&password=123456";
            Connection conn = DriverManager.getConnection(url);
            // 修改用户信息记录数据
            PreparedStatement pstmt = conn
                    .prepareStatement("update user_info set fullname=? , age=? ,address=? where account_id=?");
            pstmt.setString(1, fullname);pstmt.setString(2, age);
            pstmt.setString(3, address);
            pstmt.setString(4, id);
            pstmt.executeUpdate();
            // 修改帐号记录数据
            pstmt = conn.prepareStatement("update account set username=? , password=? where id=?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, id);
            pstmt.executeUpdate();
            // 通过UserListActionServlet获取用户清单
            // 这里换成response.sendRedirect("/UserListActionServlet")也可以
            getServletContext()
                    .getRequestDispatcher("/UserListActionServlet")
                    .forward(request, response);
        }catch(Exception e){
            out.println(e.toString());
            out.flush();
        }
    }
}
