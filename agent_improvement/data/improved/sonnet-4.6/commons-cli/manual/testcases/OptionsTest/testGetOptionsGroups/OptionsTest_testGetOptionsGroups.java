package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;

import org.junit.jupiter.api.Test;

// Tests some deprecated classes (OptionBuilder)
@SuppressWarnings("deprecation")
public class OptionsTest_testGetOptionsGroups {

    @Test
    void testGetOptionsGroups() {
        // Build two mutually exclusive option groups and register them
        final Options options = new Options();

        final OptionGroup groupAB = new OptionGroup();
        groupAB.addOption(OptionBuilder.create('a'));
        groupAB.addOption(OptionBuilder.create('b'));

        final OptionGroup groupXY = new OptionGroup();
        groupXY.addOption(OptionBuilder.create('x'));
        groupXY.addOption(OptionBuilder.create('y'));

        options.addOptionGroup(groupAB);
        options.addOptionGroup(groupXY);

        // Both groups must be returned by getOptionGroups()
        final Collection<OptionGroup> registeredGroups = options.getOptionGroups();
        assertNotNull(registeredGroups);
        assertEquals(2, registeredGroups.size());
    }
}
