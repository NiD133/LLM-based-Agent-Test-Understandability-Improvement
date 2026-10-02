package org.jsoup.helper;

import org.jsoup.select.QueryParser;
import org.jsoup.select.Selector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class RegexTest_queryParserThrowsSelectorExceptionOnMalformedRegex {

    // track original setting
    private boolean originalUseRe2j;

    @BeforeEach
    void setUp() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void tearDown() {
        // restore original setting
        Regex.wantsRe2j(originalUseRe2j);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void queryParserThrowsSelectorExceptionOnMalformedRegex(boolean useRe2j) {
        Regex.wantsRe2j(useRe2j);
        String query = "[attr~=(unclosed]";
        boolean threw = false;
        try {
            QueryParser.parse(query);
        } catch (Selector.SelectorParseException e) {
            threw = true;
            assertTrue(e.getMessage().contains("Pattern syntax error"));
        }
        assertTrue(threw);
    }
}
