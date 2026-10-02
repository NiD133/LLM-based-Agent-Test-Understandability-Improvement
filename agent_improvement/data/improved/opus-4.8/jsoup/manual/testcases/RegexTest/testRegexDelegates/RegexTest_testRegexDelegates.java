package org.jsoup.helper;

import org.jsoup.select.QueryParser;
import org.jsoup.select.Selector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link Regex} compiles a pattern and matches input correctly,
 * regardless of whether the re2j engine is requested or not.
 */
public class RegexTest_testRegexDelegates {

    /** The "useRe2j" system property in effect before this test ran, restored afterwards. */
    private boolean originalUseRe2jSetting;

    @BeforeEach
    void rememberOriginalSetting() {
        originalUseRe2jSetting = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreOriginalSetting() {
        Regex.wantsRe2j(originalUseRe2jSetting);
    }

    /**
     * Runs once with re2j disabled and once with re2j requested. In both cases the
     * compiled regex must find the numeric group inside the input string.
     */
    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void testRegexDelegates(boolean requestRe2j) {
        // Apply the requested engine preference and confirm it was recorded.
        Regex.wantsRe2j(requestRe2j);
        assertEquals(Regex.usingRe2j(), requestRe2j);

        // A pattern that captures one or more digits, and an all-digit input it should match.
        String digitsPattern = "(\\d+)";
        String input = "12345";

        Regex regex = Regex.compile(digitsPattern);
        Regex.Matcher matcher = regex.matcher(input);

        assertTrue(matcher.find(), "Expected the digit pattern to match the input");
    }
}
