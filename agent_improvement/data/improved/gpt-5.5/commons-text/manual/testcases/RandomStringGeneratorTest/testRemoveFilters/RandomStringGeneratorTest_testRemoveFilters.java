package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testRemoveFilters {

    private static final int GENERATED_LENGTH = 100;
    private static final char FILTERED_CHARACTER = 'a';
    private static final char RANGE_START = 'a';
    private static final char RANGE_END = 'z';

    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == FILTERED_CHARACTER;

    @Test
    void testRemoveFilters() {
        final RandomStringGenerator.Builder builder = RandomStringGenerator.builder()
                .withinRange(RANGE_START, RANGE_END)
                .filteredBy(A_FILTER);

        builder.filteredBy();

        final String generated = builder.get().generate(GENERATED_LENGTH);
        for (final char character : generated.toCharArray()) {
            if (character != FILTERED_CHARACTER) {
                // filter was successfully removed
                return;
            }
        }
        fail("Filter appears to have remained in place");
    }
}
