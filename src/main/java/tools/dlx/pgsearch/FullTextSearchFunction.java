package tools.dlx.pgsearch;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

import java.util.List;

/**
 * SQL function that allows using the full text search postgress operator (@@). The function also applies
 * to_tsvector and to_tsquery to its first and second arguments respectively.
 */
public class FullTextSearchFunction implements SQLFunction {
    public boolean hasArguments() {
        return true;
    }

    public boolean hasParenthesesIfNoArguments() {
        return false;
    }

    public Type getReturnType(Type type, Mapping mapping) throws QueryException {
        return BooleanType.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public String render(Type type, List list, SessionFactoryImplementor sessionFactoryImplementor) throws QueryException {
        if (list == null || (list.size() != 2 && list.size() != 3)) {
            throw new IllegalArgumentException("Invalid number of parameters");
        }

        String searchColumn = (String) list.get(0);
        String searchTerm = (String) list.get(1);

        if (list.size() == 2) {
            return String.format("to_tsvector( %s ) @@ to_tsquery( %s )", searchColumn, searchTerm);
        } else {
            String config = (String) list.get(2);
            return String.format("to_tsvector( %s, %s ) @@ to_tsquery( %s, %s )", config, searchColumn, config, searchTerm);
        }
    }
}
