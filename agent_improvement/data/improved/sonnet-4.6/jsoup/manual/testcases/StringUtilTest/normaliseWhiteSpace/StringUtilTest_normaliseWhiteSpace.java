package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_normaliseWhiteSpace {

    @Test
    public void onlyWhitespaceCollapsesToSingleSpace() {
        assertEquals(" ", normaliseWhitespace("    \r \n \r\n"));
    }

    @Test
    public void mixedWhitespaceAroundWordsCollapsesToSingleSpaces() {
        assertEquals(" hello there ", normaliseWhitespace("   hello   \r \n  there    \n"));
    }

    @Test
    public void stringWithNoWhitespaceIsUnchanged() {
        assertEquals("hello", normaliseWhitespace("hello"));
    }

    @Test
    public void newlineBetweenWordsBecomesSpace() {
        assertEquals("hello there", normaliseWhitespace("hello\nthere"));
    }
}
