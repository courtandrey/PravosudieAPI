package courtandrey.pravosudieapi;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class Scraper {
    private final SeleniumConnector connector;
    private final DecisionParser parser;
    private final SearchRequest request;

    public List<Decision> getPages(Integer limit) {
        if (limit != null && limit <= 0) throw new IllegalArgumentException();
        UrlCreator creator = new UrlCreator(request);
        List<Decision> decisions = new ArrayList<>();
        List<Decision> newDecisions;
        do {
            creator.incrementPage();
            Document newDocument = connector.connect(creator.createUrl(), 5);
            newDecisions = new ArrayList<>(parser.parse(newDocument));
            if (limit != null) {
                --limit;
            }
        } while (decisions.addAll(newDecisions) || (limit != null && limit == 0));
        decisions.forEach(x -> x.setText(getText(x.getUrl())));
        return decisions;
    }

    String getText(String url) {
        if (url == null) return null;
        Document document = connector.connect(url, 5);
        return parser.parseText(document);
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