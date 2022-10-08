package courtandrey.pravosudieapi;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

class DecisionParser {
    public List<Decision> parse(Document document) {
        Elements cases = document.getElementsByClass("bgs-result");
        List<Decision> decisions = new ArrayList<>();
        for (Element _case:cases) {
            if (_case.text().contains("Подождите, Ваш запрос выполняется")) continue;
            Decision decision = new Decision();
            decision.setId(_case.getElementsByClass("resultHeader openCardLink").text());
            decision.setDate(_case.getElementsByAttributeValue("data-comment", "Дата поступления").text());
            decision.setUrl(_case.getElementsByClass("resultHeader openCardLink").get(0).attr("href"));
            decisions.add(decision);
        }
        return decisions;
    }

    public int parsePage(Document document) {
        Elements pages = document.getElementsByClass("pageNumTD");
        return Integer.parseInt(pages.get(pages.size() - 1).text().replace(" ", ""));
    }
}
