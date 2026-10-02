package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testAddOptions2X {

    /**
     * Adding an {@link Options} instance to itself must fail, because every option it
     * already contains is, by definition, a duplicate key. {@link Options#addOptions(Options)}
     * rejects duplicates by throwing an {@link IllegalArgumentException}.
     */
    @Test
    void testAddOptions2X() {
        // Build an Options instance that holds a mix of grouped and standalone options.
        final Options options = new Options();

        final OptionGroup mutuallyExclusiveGroup = new OptionGroup();
        mutuallyExclusiveGroup.addOption(Option.builder("a").get());
        mutuallyExclusiveGroup.addOption(Option.builder("b").get());
        options.addOptionGroup(mutuallyExclusiveGroup);

        options.addOption(Option.builder("X").get());
        options.addOption(Option.builder("y").get());

        // Re-adding the same Options to itself duplicates every existing key.
        assertThrows(IllegalArgumentException.class, () -> options.addOptions(options));
    }
}
