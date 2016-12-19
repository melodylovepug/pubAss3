package app;

import com.googlecode.objectify.ObjectifyService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class topN extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int num = Integer.parseInt(request.getParameter("num"));
        List <wordData> wordStats = ObjectifyService.ofy()
                .load()
                .type(wordData.class)
                .ancestor("ancestor1")
                .limit(num)
                .order("count")
                .list();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        for(wordData w : wordStats) {
            out.println(w.word);
        }
        out.close();
    }


}

