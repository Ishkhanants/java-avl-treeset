package set.exceptions;

public class NonexistentItemException extends RuntimeException{
    public NonexistentItemException(){
        super("There is no such item in this set!");
    }
}
