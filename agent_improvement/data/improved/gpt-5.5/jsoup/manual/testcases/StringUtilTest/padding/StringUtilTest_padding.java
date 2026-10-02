package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_padding {
    private static final String FIFTEEN_SPACES = "               ";
    private static final String TWENTY_SPACES = "                    ";
    private static final String TWENTY_ONE_SPACES = "                     ";
    private static final String THIRTY_SPACES = "                              ";
    private static final String FORTY_FIVE_SPACES = "                                             ";

    @Test
    public void padding() {
        assertDefaultPaddingIsCappedAtThirtySpaces();
        assertUnlimitedPaddingKeepsRequestedWidth();
        assertZeroMaxWidthReturnsNoPaddingOutsideMemoizedValues();
        assertExplicitMaxWidthCapsRequestedPadding();
    }

    private void assertDefaultPaddingIsCappedAtThirtySpaces() {
        assertEquals("", StringUtil.padding(0));
        assertEquals(" ", StringUtil.padding(1));
        assertEquals("  ", StringUtil.padding(2));
        assertEquals(FIFTEEN_SPACES, StringUtil.padding(15));
        assertEquals(THIRTY_SPACES, StringUtil.padding(45));
    }

    private void assertUnlimitedPaddingKeepsRequestedWidth() {
        assertEquals("", StringUtil.padding(0, -1));
        assertEquals(TWENTY_SPACES, StringUtil.padding(20, -1));
        assertEquals(TWENTY_ONE_SPACES, StringUtil.padding(21, -1));
        assertEquals(THIRTY_SPACES, StringUtil.padding(30, -1));
        assertEquals(FORTY_FIVE_SPACES, StringUtil.padding(45, -1));
    }

    private void assertZeroMaxWidthReturnsNoPaddingOutsideMemoizedValues() {
        assertEquals("", StringUtil.padding(0, 0));
        assertEquals("", StringUtil.padding(21, 0));
    }

    private void assertExplicitMaxWidthCapsRequestedPadding() {
        assertEquals("", StringUtil.padding(0, 30));
        assertEquals(" ", StringUtil.padding(1, 30));
        assertEquals("  ", StringUtil.padding(2, 30));
        assertEquals(FIFTEEN_SPACES, StringUtil.padding(15, 30));
        assertEquals(THIRTY_SPACES, StringUtil.padding(45, 30));
        assertEquals(5, StringUtil.padding(20, 5).length());
    }
}
