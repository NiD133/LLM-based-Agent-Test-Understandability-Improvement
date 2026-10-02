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

        final OptionGroup alphabeticOptions = new OptionGroup();
        alphabeticOptions.addOption(OptionBuilder.create('a'));
        alphabeticOptions.addOption(OptionBuilder.create('b'));

        final OptionGroup xAndYOptions = new OptionGroup();
        xAndYOptions.addOption(OptionBuilder.create('x'));
        xAndYOptions.addOption(OptionBuilder.create('y'));

        options.addOptionGroup(alphabeticOptions);
        options.addOptionGroup(xAndYOptions);

        assertNotNull(options.getOptionGroups());
        assertEquals(2, options.getOptionGroups().size());
    }
}
