package org.jsoup.helper;

import org.jsoup.select.QueryParser;
import org.jsoup.select.Selector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexTest_queryParserThrowsSelectorExceptionOnMalformedRegex {

    private boolean originalUseRe2j;

    @BeforeEach
    void setUp() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void tearDown() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @ParameterizedTest(name = "useRe2j={0}")
    @ValueSource(booleans = {false, true})
    @DisplayName("QueryParser throws SelectorParseException for malformed regex, regardless of regex engine")
    void queryParserThrowsSelectorExceptionOnMalformedRegex(boolean useRe2j) {
        Regex.wantsRe2j(useRe2j);
        String malformedRegexQuery = "[attr~=(unclosed]";

        Selector.SelectorParseException thrown = assertThrows(
            Selector.SelectorParseException.class,
            () -> QueryParser.parse(malformedRegexQuery),
            "Expected SelectorParseException for malformed regex query"
        );

        assertTrue(thrown.getMessage().contains("Pattern syntax error"),
            "Exception message should describe a pattern syntax error");
    }
}
