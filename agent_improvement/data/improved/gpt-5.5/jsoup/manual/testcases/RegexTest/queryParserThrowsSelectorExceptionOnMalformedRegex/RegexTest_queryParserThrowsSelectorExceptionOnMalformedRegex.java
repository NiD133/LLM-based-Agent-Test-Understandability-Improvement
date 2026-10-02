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
    private static final String MALFORMED_REGEX_QUERY = "[attr~=(unclosed]";
    private static final String PATTERN_SYNTAX_ERROR_MESSAGE = "Pattern syntax error";

    private boolean originalUseRe2j;

    @BeforeEach
    void rememberOriginalRegexEnginePreference() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreOriginalRegexEnginePreference() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void queryParserThrowsSelectorExceptionOnMalformedRegex(boolean useRe2j) {
        Regex.wantsRe2j(useRe2j);

        Selector.SelectorParseException exception = assertThrows(
                Selector.SelectorParseException.class,
                () -> QueryParser.parse(MALFORMED_REGEX_QUERY)
        );

        assertTrue(exception.getMessage().contains(PATTERN_SYNTAX_ERROR_MESSAGE));
    }
}
