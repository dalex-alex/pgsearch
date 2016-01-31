package tools.dlx.pgsearch;

import org.junit.Rule;
import org.junit.Test;

import tools.dlx.pgsearch.util.SessionFactoryRule;

public class HsqlQueriesTest {
    @Rule
    public final SessionFactoryRule sf = new SessionFactoryRule();

    @Test
    public void singleColumnQuery() {
        sf.getCurrentSession().createQuery("select addressLine from AddressEntity " +
                "where fts(addressLine, 'york') = true").list();
    }

    @Test
    public void differentLanguageSearch() {
        sf.getCurrentSession().createQuery("select addressLine from AddressEntity " +
                "where fts(addressLine, 'york', 'english') = true").list();
    }

    @Test
    public void multipleColumnsQuery() {
        sf.getCurrentSession().createQuery("select addressLine from AddressEntity " +
                "where fts(addressLine || coalesce(country, ''), 'york') = true").list();
    }

    @Test
    public void aliasQuery() {
        sf.getCurrentSession().createQuery("select name from ClientEntity as c " +
            "where fts(c.address.addressLine, 'york') = true").list();
    }

    @Test
    public void formulaColumn() {
        sf.getCurrentSession().createQuery("from AddressEntity where fts(document, 'york') = true").list();
    }

    @Test
    public void formulaColumnWithAlias() {
        sf.getCurrentSession().createQuery("select name from ClientEntity as c " +
                "where fts(c.address.document, 'york') = true").list();
    }

    @Test
    public void similarSingleColumn() {
        sf.getCurrentSession().createQuery("from AddressEntity where similarity(document, 'york') > 0.5").list();
    }
}
