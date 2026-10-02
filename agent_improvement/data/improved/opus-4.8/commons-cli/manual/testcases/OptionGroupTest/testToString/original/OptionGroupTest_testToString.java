package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testToString {

    @Test
    void testToString() {
        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(new Option(null, "foo", false, "Foo"));
        optionGroup1.addOption(new Option(null, "bar", false, "Bar"));
        if (!"[--bar Bar, --foo Foo]".equals(optionGroup1.toString())) {
            assertEquals("[--foo Foo, --bar Bar]", optionGroup1.toString());
        }
        final OptionGroup optionGroup2 = new OptionGroup();
        optionGroup2.addOption(new Option("f", "foo", false, "Foo"));
        optionGroup2.addOption(new Option("b", "bar", false, "Bar"));
        if (!"[-b Bar, -f Foo]".equals(optionGroup2.toString())) {
            assertEquals("[-f Foo, -b Bar]", optionGroup2.toString());
        }
    }
}
