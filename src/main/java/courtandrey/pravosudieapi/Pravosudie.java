package courtandrey.pravosudieapi;

public class Pravosudie {
    private static Scraper scraper;

    public Pravosudie() {
        if (scraper == null) {
            scraper = new Scraper();
        }
    }

    public Decision retrieveRandomDecisionMatchingText(String text) throws PravosudieApiException {
        try {
            scraper.manageRequest().setText(text);
            return scraper.getRandomDecision();
        } catch (Exception e) {
            throw new PravosudieApiException(e);
        }
    }
}