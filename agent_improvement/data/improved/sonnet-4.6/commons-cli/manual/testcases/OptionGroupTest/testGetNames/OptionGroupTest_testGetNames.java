package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testGetNames {

    @Test
    void testGetNames() {
        // A newly created group has no selection yet
        final OptionGroup optionGroup = new OptionGroup();
        assertFalse(optionGroup.isSelected());

        // After adding options 'a' and 'b', getNames() should return both keys
        optionGroup.addOption(OptionBuilder.create('a'));
        optionGroup.addOption(OptionBuilder.create('b'));

        assertNotNull(optionGroup.getNames(), "null names");
        assertEquals(2, optionGroup.getNames().size());
        assertTrue(optionGroup.getNames().contains("a"));
        assertTrue(optionGroup.getNames().contains("b"));
    }
}
