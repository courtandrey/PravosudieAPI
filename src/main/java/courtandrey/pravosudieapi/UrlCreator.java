package courtandrey.pravosudieapi;

class UrlCreator {
    private final SearchRequest request;
    private final String URL = "https://bsr.sudrf.ru/bigs/portal.html#{\"query\":\"$TEXT\",\"type\":\"QUERY\",\"mode\":\"SIMPLE\",\"sorts\":[{\"field\":\"score\",\"order\":\"desc\"}],\"simpleSearchFieldsBundle\":\"default\",\"noOrpho\":false,\"start\":$PAGE}";
    private int pageCount;

    UrlCreator(SearchRequest request) {
        this.request = request;
    }

    void incrementPage() {
        ++pageCount;
    }

    void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    String createUrl() {
        String url = "";
        if (request.getText() != null) {
            url = URL.replace("$TEXT", request.getText());
        }
        return url.replace("$PAGE", String.valueOf(pageCount * 10));
    }
}
