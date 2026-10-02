package org.jsoup.helper;

import org.jsoup.select.QueryParser;
import org.jsoup.select.Selector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class RegexTest {
    private static final String CAPTURE_DIGITS_REGEX = "(\\d+)";
    private static final String DIGITS_INPUT = "12345";
    private static final String BACKREFERENCE_REGEX = "(\\w+)\\s+\\1";
    private static final String REPEATED_WORD_INPUT = "hello hello";
    private static final String MALFORMED_ATTRIBUTE_REGEX_QUERY = "[attr~=(unclosed]";

    private boolean originalUseRe2j;

    @BeforeEach
    void setUp() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void tearDown() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void testRegexDelegates(boolean useRe2j) {
        Regex.wantsRe2j(useRe2j);
        assertEquals(Regex.usingRe2j(), useRe2j);

        assertRegexFinds(CAPTURE_DIGITS_REGEX, DIGITS_INPUT);
    }

    @Test
    void jdkSupportsBackreferenceMatches() {
        Regex.wantsRe2j(false);

        assertRegexFinds(BACKREFERENCE_REGEX, REPEATED_WORD_INPUT);
    }

    @Test
    void re2jRejectsBackreferenceThrows() {
        Regex.wantsRe2j(true);

        assertThrows(ValidationException.class, () -> Regex.compile(BACKREFERENCE_REGEX));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void queryParserThrowsSelectorExceptionOnMalformedRegex(boolean useRe2j) {
        Regex.wantsRe2j(useRe2j);

        boolean threw = false;
        try {
            QueryParser.parse(MALFORMED_ATTRIBUTE_REGEX_QUERY);
        } catch (Selector.SelectorParseException e) {
            threw = true;
            assertTrue(e.getMessage().contains("Pattern syntax error"));
        }
        assertTrue(threw);
    }

    private static void assertRegexFinds(String pattern, String input) {
        Regex regex = Regex.compile(pattern);
        Regex.Matcher matcher = regex.matcher(input);

        assertTrue(matcher.find());
    }
}
