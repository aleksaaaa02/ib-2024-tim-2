package rs.ac.uns.ftn.Bookify.exception;

public class UserIsBlockedException extends RuntimeException{
    private String message;
    public UserIsBlockedException (String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
