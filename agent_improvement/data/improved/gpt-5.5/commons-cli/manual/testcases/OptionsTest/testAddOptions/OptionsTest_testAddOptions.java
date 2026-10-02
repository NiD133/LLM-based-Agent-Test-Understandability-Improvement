package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OptionsTest_testAddOptions {

    @Test
    void testAddOptions() {
        final Options sourceOptions = createOptionsWithGroupAndStandaloneOptions();
        final Options targetOptions = new Options();

        targetOptions.addOptions(sourceOptions);

        assertEquals(sourceOptions.getOptionGroups(), targetOptions.getOptionGroups());
        assertArrayEquals(sourceOptions.getOptions().toArray(), targetOptions.getOptions().toArray());
    }

    private Options createOptionsWithGroupAndStandaloneOptions() {
        final Options options = new Options();
        options.addOptionGroup(createOptionGroup("a", "b"));
        options.addOption(Option.builder("X").get());
        options.addOption(Option.builder("y").get());
        return options;
    }

    private OptionGroup createOptionGroup(final String firstOption, final String secondOption) {
        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(Option.builder(firstOption).get());
        optionGroup.addOption(Option.builder(secondOption).get());
        return optionGroup;
    }
}
