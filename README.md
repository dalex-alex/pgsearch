# pgsearch
Postgres dialect for Hibernate with full text search functions

This adds support for the full text search and similarity operator (@@ and %)to Hibernate Postgres dialect.
Inspired by:
 * [Postgres full-text search is Good Enough!](http://rachbelaid.com/postgres-full-text-search-is-good-enough/)
 * [Use PostgreSQL Full Text Search With HQL](http://java-talks.blogspot.ro/2014/04/use-postgresql-full-text-search-with-hql.html)
 * [Hibernate and Postgres FTS](https://metabroadcast.com/blog/hibernate-and-postgres-fts)

To use, the full text search extensions should be added to your database
 ```
CREATE EXTENSION IF NOT EXISTS fuzzystrmatch;
CREATE EXTENSION IF NOT EXISTS pg_trgm;
 ```

# Functions

The dialect adds the following functions:
 * ```fql(column, searchTerm)``` - this translates to ```ts_vector(column) @@ to_query(searchTerm)```
 * ```similarity``` the Postgres ```similarity``` function

# Example

```
@Entity
@Table(name = "client")
public class Post {
    @Id Integer id;
    @Column String title;
    @Column String content;
    @Formula("title || coalesce(content, '')")
    String document;
```

To query using HQL:
```
session.createQuery("from Post where fts(document, 'searchTerm') = true").list();
```

To query using the Criteria API:
```
session.createCriteria(Post.class)
        .add(SearchRestrictions.fts("document", "searchTerm"))
        .list();
```

# Tests

To run the (very few) tests, a Postgres local database called ```dlx``` with user and password ```dlx``` is required.