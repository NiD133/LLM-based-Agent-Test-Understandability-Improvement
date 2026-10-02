package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testGenerateTakingIntThrowsNullPointerException {

    @Test
    void testGenerateTakingIntThrowsNullPointerException() {
        // An array of size 2 with no explicit values contains two null elements.
        // filteredBy() stores them in the inclusive-predicate set, and generate()
        // later dereferences each predicate via predicate.test(), throwing NPE.
        final CharacterPredicate[] nullPredicates = new CharacterPredicate[2];

        assertThrowsExactly(NullPointerException.class, () -> {
            final RandomStringGenerator generator = RandomStringGenerator.builder()
                    .filteredBy(nullPredicates)
                    .get();
            generator.generate(18);
        });
    }
}
