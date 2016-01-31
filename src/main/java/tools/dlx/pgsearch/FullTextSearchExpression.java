package tools.dlx.pgsearch;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;

/**
 * A criterion representing the postgres full text search (@@) operator. The function also applies the ts_vector
 * and ts_query functions to its first and second arguments respectively.
 */
public class FullTextSearchExpression implements Criterion {
    private final String propertyName;
    private final String value;

    protected FullTextSearchExpression(String propertyName, String value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
        String[] columns = criteriaQuery.findColumns(propertyName, criteria);
        if (columns.length != 1) {
            throw new HibernateException("Only single-column properties allowed");
        }

        String column = columns[0];
        return String.format("to_tsvector( %s ) @@ to_tsquery( ? )", column);
    }

    @Override
    public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) {
        return new TypedValue[]{criteriaQuery.getTypedValue(criteria, propertyName, value)};
    }
}