package at.ac.fhcampuswien.fhmdb.exceptions;

public class MovieApiException extends RuntimeException{

    public MovieApiException(String message){
        super(message);
    }

    public MovieApiException(String message, Throwable cause){
        super(String.format("%s\n%s", message, cause.getMessage()), cause);
    }

}
