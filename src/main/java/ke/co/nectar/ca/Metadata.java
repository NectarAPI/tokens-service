package ke.co.nectar.ca;

import java.util.HashMap;

public interface Metadata {
    HashMap<String, Object> getMetadata();
    Object getProperty(String property) ;
}
