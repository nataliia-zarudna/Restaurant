package model;

/**
 * Created by Nataliia on 27.07.2015.
 */
public class ModelException extends Exception {

    public ModelException() {
        super();
    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelException(Throwable cause) {
        super(cause);
    }
}
