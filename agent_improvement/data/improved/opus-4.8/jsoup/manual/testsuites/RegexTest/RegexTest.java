package org.jsoup.helper;

import org.jsoup.select.QueryParser;
import org.jsoup.select.Selector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link Regex}, the regular-expression abstraction that lets jsoup switch between the
 * RE2J engine and the JDK's backtracking engine.
 *
 * <p>The engine choice is driven by a JVM system property, so each test toggles it explicitly and
 * the original value is captured before and restored after every test to avoid leaking state.</p>
 */
public class RegexTest {

    /** The engine preference that was in effect before a test ran, restored afterwards. */
    private boolean originalUseRe2j;

    @BeforeEach
    void rememberEngineSetting() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreEngineSetting() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    /**
     * Regardless of which engine is requested, a simple, universally valid pattern should compile
     * and match. This verifies the delegation works equivalently for both engines.
     */
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void compilesAndMatchesWithEitherEngine(boolean useRe2j) {
        Regex.wantsRe2j(useRe2j);
        assertEquals(Regex.usingRe2j(), useRe2j);

        String digitGroupPattern = "(\\d+)";
        String input = "12345";

        Regex regex = Regex.compile(digitGroupPattern);
        Regex.Matcher matcher = regex.matcher(input);

        assertTrue(matcher.find());
    }

    /**
     * The JDK engine supports backreferences, so a pattern using {@code \1} compiles and matches
     * repeated text.
     */
    @Test
    void jdkEngineSupportsBackreferences() {
        Regex.wantsRe2j(false);

        String backreferencePattern = "(\\w+)\\s+\\1"; // \1 refers back to group 1
        String input = "hello hello";

        Regex regex = Regex.compile(backreferencePattern);
        Regex.Matcher matcher = regex.matcher(input);

        assertTrue(matcher.find());
    }

    /**
     * The RE2J engine does not support backreferences. Compiling such a pattern must surface a
     * jsoup {@link ValidationException} rather than leaking RE2J's own PatternSyntaxException.
     */
    @Test
    void re2jEngineRejectsBackreferences() {
        Regex.wantsRe2j(true);

        String backreferencePattern = "(\\w+)\\s+\\1"; // unsupported by RE2J

        assertThrows(ValidationException.class, () -> Regex.compile(backreferencePattern));
    }

    /**
     * A selector containing a malformed attribute regex should be reported through jsoup's selector
     * layer as a {@link Selector.SelectorParseException} whose message names the pattern error,
     * for either engine.
     */
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void malformedSelectorRegexReportsPatternSyntaxError(boolean useRe2j) {
        Regex.wantsRe2j(useRe2j);

        String queryWithUnclosedGroup = "[attr~=(unclosed]";

        boolean threw = false;
        try {
            QueryParser.parse(queryWithUnclosedGroup);
        } catch (Selector.SelectorParseException e) {
            threw = true;
            assertTrue(e.getMessage().contains("Pattern syntax error"));
        }
        assertTrue(threw);
    }
}
