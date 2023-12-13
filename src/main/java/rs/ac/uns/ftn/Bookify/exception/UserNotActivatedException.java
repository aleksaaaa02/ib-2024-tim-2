package rs.ac.uns.ftn.Bookify.exception;

public class UserNotActivatedException extends RuntimeException {
    private String message;
    public UserNotActivatedException (String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
