package cn.tsuki.managementSystem.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by tsuki on 2017/4/20.
 */
@WebServlet("/RegisterActionServlet")
public class RegisterActionServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public RegisterActionServlet() {
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
        response.setContentType("text/html; charset=utf-8");

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullname = request.getParameter("fullname");
        String age = request.getParameter("age");
        String address = request.getParameter("address");

        Format fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strNow = fmt.format(new java.util.Date());
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
                pstmt.setString(2, age);
                pstmt.setString(3, address);
                pstmt.setString(4, accId);
                pstmt.executeUpdate();
            }
            response.sendRedirect("register_success.jsp");


        } catch (Exception e) {
            out.println("很遗憾，注册失败！<br/>");
            out.println("<a href=\"javascript: window.history.back()\">返回</a>");
            out.println(e.toString());
            out.flush();
        }
    }
}
