package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

// Tests deprecated OptionBuilder usage.
@SuppressWarnings("deprecation")
public class OptionsTest_testGetOptionsGroups {

    @Test
    void testGetOptionsGroups() {
        final Options options = new Options();

        final OptionGroup firstGroup = new OptionGroup();
        firstGroup.addOption(OptionBuilder.create('a'));
        firstGroup.addOption(OptionBuilder.create('b'));

        final OptionGroup secondGroup = new OptionGroup();
        secondGroup.addOption(OptionBuilder.create('x'));
        secondGroup.addOption(OptionBuilder.create('y'));

        options.addOptionGroup(firstGroup);
        options.addOptionGroup(secondGroup);

        assertNotNull(options.getOptionGroups());
        assertEquals(2, options.getOptionGroups().size());
    }
}
