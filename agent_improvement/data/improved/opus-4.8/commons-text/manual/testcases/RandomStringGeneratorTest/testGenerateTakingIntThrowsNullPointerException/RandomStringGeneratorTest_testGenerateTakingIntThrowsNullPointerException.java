package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testGenerateTakingIntThrowsNullPointerException {

    /**
     * When a generator is filtered by an array that contains {@code null} predicates,
     * {@link RandomStringGenerator#generate(int)} must throw a {@link NullPointerException}
     * because each generated code point is tested against every (here {@code null}) predicate.
     */
    @Test
    void testGenerateTakingIntThrowsNullPointerException() {
        // An array whose elements default to null: two null CharacterPredicates.
        final CharacterPredicate[] predicatesContainingNull = new CharacterPredicate[2];

        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .filteredBy(predicatesContainingNull)
                .get();

        // Generating any non-empty string dereferences a null predicate and fails.
        assertThrowsExactly(NullPointerException.class, () -> generator.generate(18));
    }
}
