package courtandrey.pravosudieapi;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class Scraper {
    private final SeleniumConnector connector;
    private final DecisionParser parser;
    private final SearchRequest request;
    private UrlCreator creator;

    public Decision getRandomDecision() {
        creator = new UrlCreator(request);
        int page = (int) (Math.random() * (getPageCount() - 1));
        creator.setPageCount(page);
        Document document = connector.connect(creator.createUrl(), 5);
        List<Decision> decisions = new ArrayList<>(parser.parse(document));
        int decisionIndex = (int) (Math.random() * (decisions.size() - 1));
        decisions.get(decisionIndex).setText(getText(decisions.get(decisionIndex).getUrl()));
        return decisions.get(decisionIndex);
    }
    
    int getPageCount() {
        return parser.parsePage(connector.connect(creator.createUrl(), 5));
    }

    String getText(String url) {
        if (url == null) return null;
        return connector.getText(url, 5);
    }

    public SearchRequest manageRequest() {
        return request;
    }

    public Scraper() {
        connector = new SeleniumConnector();
        parser = new DecisionParser();
        request = new SearchRequest();
    }
}