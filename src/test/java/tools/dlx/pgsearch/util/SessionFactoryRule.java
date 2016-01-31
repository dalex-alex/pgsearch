package tools.dlx.pgsearch.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class SessionFactoryRule implements MethodRule {
    private SessionFactory sessionFactory;
    private Transaction transaction;
    private Session session;

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                sessionFactory = createSession();
                session = sessionFactory.openSession();
                transaction = session.beginTransaction();

                try {
                    statement.evaluate();
                } finally {
                    transaction.rollback();
                    session.close();
                    sessionFactory.close();
                }
            }
        };
    }

    private SessionFactory createSession() {
        return new Configuration().configure().buildSessionFactory();
    }

    public Session getCurrentSession() {
        return session;
    }
}
