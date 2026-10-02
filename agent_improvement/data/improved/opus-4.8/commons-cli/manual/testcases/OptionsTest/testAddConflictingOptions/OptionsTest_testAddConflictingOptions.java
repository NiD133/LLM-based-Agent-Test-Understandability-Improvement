package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Options#addOptions(Options)} rejects sources that share an
 * option key with the destination.
 */
public class OptionsTest_testAddConflictingOptions {

    /**
     * Builds an {@link Options} from a group of options plus any number of standalone options.
     */
    private static Options optionsWith(final OptionGroup group, final String... standaloneOpts) {
        final Options options = new Options();
        options.addOptionGroup(group);
        for (final String opt : standaloneOpts) {
            options.addOption(Option.builder(opt).get());
        }
        return options;
    }

    @Test
    void testAddConflictingOptions() {
        // Destination options own the keys: a, b (grouped) and x, y (standalone).
        final OptionGroup destinationGroup = new OptionGroup();
        destinationGroup.addOption(Option.builder("a").get());
        destinationGroup.addOption(Option.builder("b").get());
        final Options destination = optionsWith(destinationGroup, "x", "y");

        // Source options reuse keys x and b, which already exist in the destination.
        final OptionGroup sourceGroup = new OptionGroup();
        sourceGroup.addOption(Option.builder("x").type(Integer.class).get());
        sourceGroup.addOption(Option.builder("b").type(Integer.class).get());
        final Options source = optionsWith(sourceGroup, "c");

        // Merging conflicting options must fail.
        assertThrows(IllegalArgumentException.class, () -> destination.addOptions(source));
    }
}
