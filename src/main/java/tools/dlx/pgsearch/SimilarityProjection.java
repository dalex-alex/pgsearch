package tools.dlx.pgsearch;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

@SuppressWarnings("serial")
public class SimilarityProjection extends AggregateProjection {
    private final Object value;

    protected SimilarityProjection(String propertyName, Object value) {
        super("similarity", propertyName);
        this.value = value;
    }

    @Override
    public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return new Type[]{StandardBasicTypes.DOUBLE};
    }

    @Override
    protected List buildFunctionParameterList(Criteria criteria, CriteriaQuery criteriaQuery) {
        return Arrays.asList(criteriaQuery.getColumn(criteria, getPropertyName()), String.format("'%s'", value));
    }
}
