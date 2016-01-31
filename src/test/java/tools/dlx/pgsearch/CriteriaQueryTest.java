package tools.dlx.pgsearch;

import org.junit.Rule;
import org.junit.Test;
import tools.dlx.pgsearch.util.AddressEntity;
import tools.dlx.pgsearch.util.ClientEntity;
import tools.dlx.pgsearch.util.SessionFactoryRule;

import static org.hibernate.criterion.Projections.alias;

public class CriteriaQueryTest {
    @Rule
    public final SessionFactoryRule sf = new SessionFactoryRule();

    @Test
    public void testFullTextSearchRestriction() {
        sf.getCurrentSession().createCriteria(AddressEntity.class)
                .add(SearchRestrictions.fts("addressLine", "york"))
                .list();
    }

    @Test
    public void testFullTextSearchRestrictionsWithFormula() {
        sf.getCurrentSession().createCriteria(ClientEntity.class)
                .createAlias("address", "address")
                .add(SearchRestrictions.fts("address.document", "york"))
                .list();
    }

    @Test
    public void testFullTextSearchRestrictionsWithAlias() {
        sf.getCurrentSession().createCriteria(ClientEntity.class)
                .createAlias("address", "address")
                .add(SearchRestrictions.fts("address.addressLine", "york"))
                .list();
    }

    @Test
    public void testSimilarity() {
        sf.getCurrentSession().createCriteria(AddressEntity.class)
                .setProjection(alias(SearchProjections.similarity("addressLine", "york"), "addressMatch"))
                .add(SearchRestrictions.similar("addressLine", "york")).list();
    }

    @Test
    public void testSimilarityWithFormula() {
        sf.getCurrentSession().createCriteria(AddressEntity.class)
                .setProjection(alias(SearchProjections.similarity("document", "york"), "addressMatch"))
                .add(SearchRestrictions.similar("document", "york")).list();
    }
}
