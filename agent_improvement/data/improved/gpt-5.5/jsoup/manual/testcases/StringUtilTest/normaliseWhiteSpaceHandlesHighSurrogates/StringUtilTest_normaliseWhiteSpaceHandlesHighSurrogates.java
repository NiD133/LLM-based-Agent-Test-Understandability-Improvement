package org.jsoup.internal;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_normaliseWhiteSpaceHandlesHighSurrogates {

    @Test
    public void normaliseWhiteSpaceHandlesHighSurrogates() {
        String textWithSurrogatePairAndRepeatedSpaces = "\ud869\udeb2\u304b\u309a  1";
        String expectedTextWithSingleWhitespace = "\ud869\udeb2\u304b\u309a 1";

        assertEquals(
            expectedTextWithSingleWhitespace,
            normaliseWhitespace(textWithSurrogatePairAndRepeatedSpaces)
        );

        String parsedText = Jsoup.parse(textWithSurrogatePairAndRepeatedSpaces).text();
        assertEquals(expectedTextWithSingleWhitespace, parsedText);
    }
}
