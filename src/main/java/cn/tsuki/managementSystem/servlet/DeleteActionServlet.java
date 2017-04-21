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
import java.sql.Statement;

/**
 * Created by tsuki on 2017/4/21.
 */

@WebServlet("/DeleteActionServlet")
public class DeleteActionServlet extends HttpServlet{
    private static final long serrialVersionUID = 1L;

    public DeleteActionServlet(){
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
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        try{
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            String url =
                    "jdbc:mysql://localhost/usersdb?user=root&password=123456";
            Connection conn = DriverManager.getConnection(url);
            //因为设置了关联删除 所以account 中的记录被删除 userInfo表中数据也会被删除
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("delete from account where id=" + id);
            response.sendRedirect("UserListActionServlet");
        } catch (Exception e) {
            out.println("删除失败！ <br/>");
            out.println("<a href=\"javascript: window.history.back()\"> 返回</a>");
            out.println(e.toString());
            out.flush();
        }
    }
}
