package org.jsoup.helper;

import org.jsoup.select.QueryParser;
import org.jsoup.select.Selector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexTest_queryParserThrowsSelectorExceptionOnMalformedRegex {

    /** A selector query whose regex value "(unclosed" has an unbalanced parenthesis. */
    private static final String MALFORMED_REGEX_QUERY = "[attr~=(unclosed]";

    /** Remembers the re2j preference so each test can restore it afterwards. */
    private boolean originalUseRe2j;

    @BeforeEach
    void rememberRe2jSetting() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreRe2jSetting() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    /**
     * Parsing a selector whose regex is malformed must fail with a SelectorParseException,
     * regardless of which regex engine (re2j or the JDK engine) is selected.
     */
    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void queryParserThrowsSelectorExceptionOnMalformedRegex(boolean useRe2j) {
        Regex.wantsRe2j(useRe2j);

        Selector.SelectorParseException thrown = assertThrows(
            Selector.SelectorParseException.class,
            () -> QueryParser.parse(MALFORMED_REGEX_QUERY));

        assertTrue(thrown.getMessage().contains("Pattern syntax error"));
    }
}
