package courtandrey.pravosudieapi;

import java.util.List;

public class Pravosudie {
    private final Scraper scraper;

    public static void main(String[] args) {
        System.out.println((new Pravosudie()).retrieveRandomDecisionMatchingText("аниме").getText());
    }

    public Pravosudie() {
        scraper = new Scraper();
    }

    public Decision retrieveRandomDecisionMatchingText(String text) {
        try {
            scraper.manageRequest().setText(text);
            return scraper.getRandomDecision();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}