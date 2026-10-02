package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class CharSetTest_testGetInstance extends AbstractLangTest {

    @Test
    void testGetInstance() {
        assertSame(CharSet.EMPTY, CharSet.getInstance((String) null));
        assertSame(CharSet.EMPTY, CharSet.getInstance((String[]) null));
        assertSame(CharSet.EMPTY, CharSet.getInstance(null));
        assertSame(CharSet.EMPTY, CharSet.getInstance(""));

        assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("a-zA-Z"));
        assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("A-Za-z"));
        assertSame(CharSet.ASCII_ALPHA_LOWER, CharSet.getInstance("a-z"));
        assertSame(CharSet.ASCII_ALPHA_UPPER, CharSet.getInstance("A-Z"));
        assertSame(CharSet.ASCII_NUMERIC, CharSet.getInstance("0-9"));
    }
}
