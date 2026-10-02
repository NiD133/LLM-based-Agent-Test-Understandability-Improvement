package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest_isWhitespace {
    private static final int[] HtmlWhitespaceCodePoints = {
        '\t',
        '\n',
        '\r',
        '\f',
        ' '
    };

    private static final int[] UnicodeSpacesNotTreatedAsHtmlWhitespace = {
        '\u00a0',
        '\u2000',
        '\u3000'
    };

    @Test
    public void isWhitespace() {
        for (int codePoint : HtmlWhitespaceCodePoints) {
            assertTrue(StringUtil.isWhitespace(codePoint));
        }

        for (int codePoint : UnicodeSpacesNotTreatedAsHtmlWhitespace) {
            assertFalse(StringUtil.isWhitespace(codePoint));
        }
    }
}
