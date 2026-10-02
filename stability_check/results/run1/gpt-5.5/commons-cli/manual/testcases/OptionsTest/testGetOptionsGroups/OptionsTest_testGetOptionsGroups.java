package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

// Tests deprecated OptionBuilder factory methods used by the original test.
@SuppressWarnings("deprecation")
public class OptionsTest_testGetOptionsGroups {

    @Test
    void testGetOptionsGroups() {
        final Options options = new Options();
        final OptionGroup firstGroup = optionGroupWithOptions('a', 'b');
        final OptionGroup secondGroup = optionGroupWithOptions('x', 'y');

        options.addOptionGroup(firstGroup);
        options.addOptionGroup(secondGroup);

        assertNotNull(options.getOptionGroups());
        assertEquals(2, options.getOptionGroups().size());
    }

    private OptionGroup optionGroupWithOptions(final char firstOption, final char secondOption) {
        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(OptionBuilder.create(firstOption));
        optionGroup.addOption(OptionBuilder.create(secondOption));
        return optionGroup;
    }
}
