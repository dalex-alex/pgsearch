package tools.dlx.pgsearch;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;

/**
 * A criterion representing the postgres similar (%) operator
 */
public class SimilarityExpression implements Criterion {
    private final String propertyName;
    private final String value;

    protected SimilarityExpression(String propertyName, String value) {
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
        return String.format("( %s ) %% ?", column);
    }

    @Override
    public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) {
        return new TypedValue[]{criteriaQuery.getTypedValue(criteria, propertyName, value)};
    }
}
