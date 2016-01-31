package tools.dlx.pgsearch;

import org.hibernate.dialect.PostgreSQL9Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.ObjectType;
import org.hibernate.type.StandardBasicTypes;

public class Dialect extends PostgreSQL9Dialect {
    public Dialect() {
        super();
        registerFunction("fts", new FullTextSearchFunction());
        registerFunction("ts_rank", new StandardSQLFunction("ts_rank", StandardBasicTypes.DOUBLE));
        registerFunction("to_tsquery", new StandardSQLFunction("to_tsquery", ObjectType.INSTANCE));
        registerFunction("to_tsvector", new StandardSQLFunction("to_tsvector", ObjectType.INSTANCE));
        registerFunction("similarity", new StandardSQLFunction("similarity", StandardBasicTypes.DOUBLE));
    }
}
