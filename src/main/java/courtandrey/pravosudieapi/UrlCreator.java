package courtandrey.pravosudieapi;

class UrlCreator {
    private final SearchRequest request;
    private int pageCount;

    UrlCreator(SearchRequest request) {
        this.request = request;
    }

    void incrementPage() {
        ++pageCount;
    }

    String createUrl() {
        return null;
    }
}
