package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexTest_testRegexDelegates {
    private static final String DIGITS_CAPTURE_PATTERN = "(\\d+)";
    private static final String DIGITS_INPUT = "12345";

    private boolean originalUseRe2j;

    @BeforeEach
    void rememberRegexEnginePreference() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreRegexEnginePreference() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void testRegexDelegates(boolean useRe2j) {
        Regex.wantsRe2j(useRe2j);

        assertEquals(Regex.usingRe2j(), useRe2j);

        Regex regex = Regex.compile(DIGITS_CAPTURE_PATTERN);
        Regex.Matcher matcher = regex.matcher(DIGITS_INPUT);

        assertTrue(matcher.find());
    }
}
