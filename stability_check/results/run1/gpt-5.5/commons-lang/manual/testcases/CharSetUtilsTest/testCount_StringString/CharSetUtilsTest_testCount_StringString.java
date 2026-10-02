package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testCount_StringString extends AbstractLangTest {

    @Test
    void testCount_StringString() {
        assertEquals(0, CharSetUtils.count(null, (String) null), "Null input and null set should count as empty");
        assertEquals(0, CharSetUtils.count(null, ""), "Null input should count as empty even with an empty set");
        assertEquals(0, CharSetUtils.count("", (String) null), "Empty input should count as empty with a null set");
        assertEquals(0, CharSetUtils.count("", ""), "Empty input and empty set should count as empty");
        assertEquals(0, CharSetUtils.count("", "a-e"), "Empty input should count as empty even with a populated set");

        assertEquals(0, CharSetUtils.count("hello", (String) null), "Null set should count no characters");
        assertEquals(0, CharSetUtils.count("hello", ""), "Empty set should count no characters");

        assertEquals(1, CharSetUtils.count("hello", "a-e"), "Only 'e' is in the range a-e");
        assertEquals(3, CharSetUtils.count("hello", "l-p"), "Two 'l' characters and one 'o' are in the range l-p");
    }
}
