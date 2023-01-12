package ukr.roma.spring.TestApp.util;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException (String msg){
        super(msg);
    }
}
