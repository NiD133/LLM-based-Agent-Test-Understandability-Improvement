package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testCreateConverterFromCharsAndEquals {

    @Test
    void testCreateConverterFromCharsAndEquals() {
        // Build a converter whose entire alphabet consists of '+' (duplicates are ignored
        // by the implementation, so the two-element array collapses to a single symbol).
        Character[] plusAlphabet = { '+', '+' };
        AlphabetConverter converter = AlphabetConverter.createConverterFromChars(
                plusAlphabet, plusAlphabet, plusAlphabet);

        // A primitive char autoboxes to Character, which is not an AlphabetConverter —
        // equals() must return false for any non-AlphabetConverter argument.
        char nonConverterArgument = '+';
        assertFalse(converter.equals(nonConverterArgument));
    }
}
