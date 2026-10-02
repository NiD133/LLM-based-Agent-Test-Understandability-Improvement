package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Options#getOptionGroups()} returns every distinct
 * {@link OptionGroup} that has been registered on an {@link Options} instance.
 */
// OptionBuilder is deprecated but still used here to build the test options.
@SuppressWarnings("deprecation")
public class OptionsTest_testGetOptionsGroups {

    @Test
    void testGetOptionsGroups() {
        // Build a first group containing the short options -a and -b.
        final OptionGroup firstGroup = new OptionGroup();
        firstGroup.addOption(OptionBuilder.create('a'));
        firstGroup.addOption(OptionBuilder.create('b'));

        // Build a second group containing the short options -x and -y.
        final OptionGroup secondGroup = new OptionGroup();
        secondGroup.addOption(OptionBuilder.create('x'));
        secondGroup.addOption(OptionBuilder.create('y'));

        // Register both groups on the Options instance.
        final Options options = new Options();
        options.addOptionGroup(firstGroup);
        options.addOptionGroup(secondGroup);

        // getOptionGroups() must report exactly the two groups that were added.
        assertNotNull(options.getOptionGroups());
        assertEquals(2, options.getOptionGroups().size());
    }
}
