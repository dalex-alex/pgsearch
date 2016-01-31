package tools.dlx.pgsearch;

import org.hibernate.criterion.Criterion;

public class SearchRestrictions {
    /**
     * Uses the PostgreSQL '%' restriction for similarity checking
     *
     * @param propertyName The name of the property
     * @param value        The value to use in comparison
     * @return The Criterion
     * @see Criterion
     */
    public static Criterion similar(String propertyName, Object value) {
        return new SimilarityExpression(propertyName, String.format("'%s'", value));
    }

    /**
     * Uses the PostgreSQL '@@' restriction for full text search. This applies the ts_vector function the property
     * name.
     *
     * @param propertyName The name of the property
     * @param value        The value to use in comparison
     * @return The Criterion
     * @see Criterion
     */
    public static Criterion fts(String propertyName, Object value) {
        return new FullTextSearchExpression(propertyName, String.format("'%s'", value));
    }
}
