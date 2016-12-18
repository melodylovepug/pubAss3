package app;

import com.googlecode.objectify.ObjectifyService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class process extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String message = request.getParameter("content");
        processMessage(message);
    }

    public void processMessage(String content) {
        wordData data;
        String ancestorName = "ancestor1";

        String[] splitMessage = content.split(" ");
        boolean newSource = true;
        for (int i = 0; i < splitMessage.length; i++) {
            String word = splitMessage[i];
            if (!isStopWord(word)) {
                //Filter wordFilter = new FilterPredicate("word", FilterOperator.EQUAL, word);
                wordData wordInfo = ofy().load().type(wordData.class).id(word).now();
                if (wordInfo == null) {//(wordInfo.size() == 0){
                    data = new wordData(ancestorName, word, 1, 1);
                    newSource = false;
                } else {
                    data = wordInfo;
                    int count = data.count;
                    count++;
                    if (newSource) {
                        int sources = data.sources;
                        sources++;
                        newSource = false;
                        data.sources = sources;
                    }
                    data.count = count;
                }
                ObjectifyService.ofy().save().entity(data).now();
            }
        }
    }

    public boolean isStopWord(String word) {
        //String AncestorName = "ancestor2";
        boolean response = false;
        String[] stopWords = {"a", "the", "this", "that", "or"};
        for (String s : stopWords) {
            if (s.equals(word)) {
                response = true;
            }
        }
        return response;
    }


//use the following query to select the top n word counts:
/*
    public List topN(num) {
        return List < wordData > wordStats = ObjectifyService.ofy()
                .load()
                .type(wordData.class)
                .ancestor(ancestor1)
                .limit(num)
                .order("count")
                .list();
    }*/

}

