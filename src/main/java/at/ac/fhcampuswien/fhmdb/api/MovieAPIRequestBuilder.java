package at.ac.fhcampuswien.fhmdb.api;

public class MovieAPIRequestBuilder {

    private StringBuilder builder;

    public MovieAPIRequestBuilder(String baseUrl) {
        builder = new StringBuilder(baseUrl);
    }

    public MovieAPIRequestBuilder query(String query) {
        appendParameter("query", query);
        return this;
    }

    public MovieAPIRequestBuilder genre(String genre) {
        appendParameter("genre", genre);
        return this;
    }

    public MovieAPIRequestBuilder releaseYear(String releaseYear) {
        appendParameter("releaseYear", releaseYear);
        return this;
    }

    public MovieAPIRequestBuilder ratingFrom(String rating) {
        appendParameter("ratingFrom", rating);
        return this;
    }

    private void appendParameter(String name, String value) {
        if (value != null) {
            if (builder.indexOf("?") == -1) {
                builder.append("?");
            } else {
                builder.append("&");
            }
            builder.append(name).append("=").append(value);
        }
    }

    public String build() {
        return builder.toString();
    }
}
