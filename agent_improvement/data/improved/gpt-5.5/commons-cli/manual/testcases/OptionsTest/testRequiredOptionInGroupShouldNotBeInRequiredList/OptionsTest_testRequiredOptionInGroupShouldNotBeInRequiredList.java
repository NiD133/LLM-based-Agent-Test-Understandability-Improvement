package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class OptionsTest_testRequiredOptionInGroupShouldNotBeInRequiredList {

    @Test
    void testRequiredOptionInGroupShouldNotBeInRequiredList() {
        final String optionKey = "a";
        final Option option = new Option(optionKey, "along", false, "Option A");
        option.setRequired(true);

        final Options options = new Options();
        options.addOption(option);

        assertTrue(options.getRequiredOptions().contains(optionKey));

        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(option);
        options.addOptionGroup(optionGroup);

        assertFalse(options.getOption(optionKey).isRequired());
        assertFalse(options.getRequiredOptions().contains(optionKey), "Option in group shouldn't be in required options list.");
    }
}
