package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

// Tests some deprecated classes (OptionBuilder).
@SuppressWarnings("deprecation")
public class OptionsTest_testGetOptionsGroups {

    /**
     * Verifies that every distinct OptionGroup added to an {@link Options} instance
     * is reported back by {@link Options#getOptionGroups()}.
     */
    @Test
    void testGetOptionsGroups() {
        // Group 1 bundles the short options -a and -b.
        final OptionGroup firstGroup = new OptionGroup();
        firstGroup.addOption(OptionBuilder.create('a'));
        firstGroup.addOption(OptionBuilder.create('b'));

        // Group 2 bundles the short options -x and -y.
        final OptionGroup secondGroup = new OptionGroup();
        secondGroup.addOption(OptionBuilder.create('x'));
        secondGroup.addOption(OptionBuilder.create('y'));

        // Register both groups with the Options container.
        final Options options = new Options();
        options.addOptionGroup(firstGroup);
        options.addOptionGroup(secondGroup);

        // getOptionGroups() must return a non-null collection holding exactly the two groups.
        final int expectedGroupCount = 2;
        assertNotNull(options.getOptionGroups());
        assertEquals(expectedGroupCount, options.getOptionGroups().size());
    }
}
