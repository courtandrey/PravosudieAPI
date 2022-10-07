package courtandrey.pravosudieapi;

import java.util.List;

public class Pravosudie {
    private final Scraper scraper;

    public Pravosudie() {
        scraper = new Scraper();
    }

    public Decision retrieveRandomDecisionMatchingText(String text) {
        scraper.manageRequest().setText(text);
        List<Decision> decisions = scraper.getPages(5);
        decisions = decisions.stream().filter(x -> x.getText() != null).toList();
        int random = (int) (Math.random() * decisions.size());
        return decisions.get(random);
    }
}