package cn.tsuki.managementSystem.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by tsuki on 2017/4/20.
 */
@WebServlet("/LoginActionServlet")
public class LoginActionServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public LoginActionServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)throws ServletException,IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)throws ServletException,IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try{
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
                    session.setAttribute("FULLNAME", fullname);
                    getServletContext().getRequestDispatcher("/UserListActionServlet").forward(request, response);
                }
            }else {
                response.sendRedirect("login_failure.jsp");
            }
        } catch (Exception e) {
            out.println("未能正常登录");
            out.println("<a href=\"javascript: window.history.back()\">返回</a>");
            out.println(e.toString());
            out.flush();
            out.close();
        }
    }

}
