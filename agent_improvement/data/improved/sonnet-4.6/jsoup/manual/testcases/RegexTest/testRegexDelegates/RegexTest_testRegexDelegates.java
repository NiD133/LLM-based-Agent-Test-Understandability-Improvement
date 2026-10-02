package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link Regex} correctly delegates to the underlying regex engine
 * (JDK or re2j) based on the {@code wantsRe2j} preference flag, and that a
 * compiled pattern can still match input regardless of which engine is active.
 */
public class RegexTest_testRegexDelegates {

    // Captured before each test so it can be restored afterwards, preventing
    // side-effects between tests and across the wider test suite.
    private boolean originalUseRe2j;

    @BeforeEach
    void saveOriginalRe2jPreference() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreOriginalRe2jPreference() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    /**
     * Runs with {@code useRe2j = false} (JDK engine) and {@code useRe2j = true}
     * (re2j engine if available).  In both cases the test asserts that:
     * <ol>
     *   <li>{@link Regex#usingRe2j()} reports the engine that was requested.</li>
     *   <li>A pattern compiled via {@link Regex#compile} produces a matcher that
     *       successfully finds a match in the given input.</li>
     * </ol>
     */
    @ParameterizedTest(name = "useRe2j={0}")
    @ValueSource(booleans = { false, true })
    void testRegexDelegates(boolean useRe2j) {
        // Configure which regex engine Regex should delegate to for this run.
        Regex.wantsRe2j(useRe2j);

        // Confirm the engine-selection flag is reflected by usingRe2j().
        assertEquals(useRe2j, Regex.usingRe2j(),
            "usingRe2j() should reflect the engine preference set by wantsRe2j()");

        // A simple digit-capturing pattern used to exercise the compiled regex.
        String digitPattern = "(\\d+)";
        String allDigitInput = "12345";

        Regex compiledPattern = Regex.compile(digitPattern);
        Regex.Matcher matcher = compiledPattern.matcher(allDigitInput);

        // The matcher must find the digit sequence regardless of which engine is active.
        assertTrue(matcher.find(),
            "Matcher should find digits in \"" + allDigitInput + "\" using pattern \"" + digitPattern + "\"");
    }
}
