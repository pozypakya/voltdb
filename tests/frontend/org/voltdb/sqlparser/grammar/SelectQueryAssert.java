package org.voltdb.sqlparser.grammar;

import static java.lang.String.format;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.voltdb.sqlparser.semantics.grammar.Projection;
import org.voltdb.sqlparser.semantics.grammar.SelectQuery;

/**
 * {@link SelectQuery} specific assertions - Generated by CustomAssertionGenerator.
 */
public class SelectQueryAssert extends
        AbstractAssert<SelectQueryAssert, SelectQuery> {

    /**
     * Creates a new </code>{@link SelectQueryAssert}</code> to make assertions on actual SelectQuery.
     * @param actual the SelectQuery we want to make assertions on.
     */
    public SelectQueryAssert(SelectQuery actual) {
        super(actual, SelectQueryAssert.class);
    }

    /**
     * An entry point for SelectQueryAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly : <code>assertThat(mySelectQuery)</code> and get specific assertion with code completion.
     * @param actual the SelectQuery we want to make assertions on.
     * @return a new </code>{@link SelectQueryAssert}</code>
     */
    public static SelectQueryAssert assertThat(SelectQuery actual) {
        return new SelectQueryAssert(actual);
    }

    /**
     * Verifies that the actual SelectQuery's projections contains the given Projection elements.
     * @param projections the given elements that should be contained in actual SelectQuery's projections.
     * @return this assertion object.
     * @throws AssertionError if the actual SelectQuery's projections does not contain all given Projection elements.
     */
    public SelectQueryAssert hasProjections(Projection... projections) {
        // check that actual SelectQuery we want to make assertions on is not null.
        isNotNull();

        // check that given Projection varargs is not null.
        if (projections == null)
            throw new AssertionError(
                    "Expecting projections parameter not to be null.");

        // check with standard error message (see commented below to set your own message).
        Assertions.assertThat(actual.getProjections()).contains(projections);

        // uncomment the 4 lines below if you want to build your own error message :
        // WritableAssertionInfo assertionInfo = new WritableAssertionInfo();
        // String errorMessage = "my error message";
        // assertionInfo.overridingErrorMessage(errorMessage);
        // Iterables.instance().assertContains(assertionInfo, actual.getTeamMates(), teamMates);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual SelectQuery has no projections.
     * @return this assertion object.
     * @throws AssertionError if the actual SelectQuery's projections is not empty.
     */
    public SelectQueryAssert hasNoProjections() {
        // check that actual SelectQuery we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = format(
                "\nExpected :\n  <%s>\nnot to have projections but had :\n  <%s>",
                actual, actual.getProjections());

        // check
        if (!actual.getProjections().isEmpty())
            throw new AssertionError(errorMessage);

        // return the current assertion for method chaining
        return this;
    }

}
