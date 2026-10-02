package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

// Tests some deprecated classes (OptionBuilder).
@SuppressWarnings("deprecation")
public class OptionsTest_testGetOptionsGroups {

    /**
     * Verifies that every OptionGroup registered via {@link Options#addOptionGroup}
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

        // Register both groups on a fresh Options instance.
        final Options options = new Options();
        options.addOptionGroup(firstGroup);
        options.addOptionGroup(secondGroup);

        // Both groups must be returned.
        final java.util.Collection<OptionGroup> groups = options.getOptionGroups();
        assertNotNull(groups);
        assertEquals(2, groups.size());
    }
}
