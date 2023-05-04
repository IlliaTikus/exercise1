package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genre = new Label();
    private final Label year = new Label();
    private final Label rating = new Label();
    private final Label actors = new Label();
    private final HBox details = new HBox(genre, year, rating);
    private final VBox layout = new VBox(title, detail, actors, details);

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );
            String genreWithoutBrackets = movie.getGenres().toString().replaceAll("\\[|\\]", "");
            genre.setText(genreWithoutBrackets);
            year.setText(String.valueOf(movie.getYear()));
            rating.setText(String.valueOf(movie.getRating()));
            actors.setText(movie.getActors().toString().replaceAll("\\[|\\]", ""));


            // color scheme
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            genre.getStyleClass().add("text-white");
            genre.getStyleClass().add("font-style: italic");
            year.getStyleClass().add("text-white");
            rating.getStyleClass().add("text-white");
            actors.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            genre.setFont(Font.font("Arial", 12));
            genre.setStyle("-fx-font-style: italic;");
            year.setFont(Font.font("Arial", 12));
            rating.setFont(Font.font("Arial", 12));
            actors.setFont(Font.font("Arial", 12));

            layout.setPadding(new Insets(15));

            layout.spacingProperty().set(10);
            details.spacingProperty().set(20);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            setGraphic(layout);
        }
    }
}

