package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForUpperLimitAndAppendedString {

    @Test
    void testAbbreviateForUpperLimitAndAppendedString() {
        // String without spaces: truncated at upper limit (5) and append string "-" is added
        assertEquals("01234-", WordUtils.abbreviate("0123456789", 0, 5, "-"));

        // String with a space before upper limit: truncated at the space, null append means no suffix
        assertEquals("012", WordUtils.abbreviate("012 3456789", 2, 5, null));

        // Upper limit of -1 means no limit: full string is returned, no abbreviation occurs
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 0, -1, ""));
    }
}
