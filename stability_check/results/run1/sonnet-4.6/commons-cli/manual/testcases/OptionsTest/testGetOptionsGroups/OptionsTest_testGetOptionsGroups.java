package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testGetOptionsGroups {

    @Test
    void testGetOptionsGroups() {
        final Options options = new Options();

        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(OptionBuilder.create('a'));
        optionGroup1.addOption(OptionBuilder.create('b'));

        final OptionGroup optionGroup2 = new OptionGroup();
        optionGroup2.addOption(OptionBuilder.create('x'));
        optionGroup2.addOption(OptionBuilder.create('y'));

        options.addOptionGroup(optionGroup1);
        options.addOptionGroup(optionGroup2);

        assertNotNull(options.getOptionGroups());
        assertEquals(2, options.getOptionGroups().size(),
                "Options should contain exactly the two option groups that were added");
    }
}
