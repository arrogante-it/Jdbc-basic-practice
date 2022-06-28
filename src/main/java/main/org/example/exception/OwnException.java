package main.org.example.exception;

public class OwnException extends RuntimeException{
    public OwnException(String message){
        super(message);
    }
}
