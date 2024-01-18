package ke.co.nectar.token.domain;


public interface Entity {
    default String getName() {
        return Class.class.getName();
    }
}
