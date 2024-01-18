package ke.co.nectar.token.domain.range;

public interface Range <T> {

    T getStartRange() ;
    T getEndRange() ;
    void setNoOfSteps(int noOfSteps) throws Exception ;
    T getStep(int stepCounter) ;
}
