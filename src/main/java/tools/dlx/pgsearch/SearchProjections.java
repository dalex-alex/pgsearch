package tools.dlx.pgsearch;

import org.hibernate.criterion.Projection;

public class SearchProjections {
    public static Projection similarity(String propertyName, Object value) {
        return new SimilarityProjection(propertyName, value);
    }
}
