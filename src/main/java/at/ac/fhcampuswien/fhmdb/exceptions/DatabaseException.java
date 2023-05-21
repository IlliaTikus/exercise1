package at.ac.fhcampuswien.fhmdb.exceptions;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String message){
        super(message);
    }

    public DatabaseException(String message, Throwable cause){
        super(String.format("%s\n%s", message, cause.getMessage()), cause);
    }

}
