package org.jsoup.helper;

import org.jsoup.select.QueryParser;
import org.jsoup.select.Selector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Regex}, verifying that it correctly delegates to either the JDK
 * or re2j engine based on availability and configuration, and that callers always
 * receive a {@link ValidationException} on invalid patterns regardless of the engine.
 */
public class RegexTest {

    /** Saved so each test can restore the global re2j preference after it runs. */
    private boolean originalUseRe2j;

    @BeforeEach
    void saveRe2jPreference() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreRe2jPreference() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @ParameterizedTest(name = "useRe2j={0}")
    @ValueSource(booleans = {false, true})
    @DisplayName("compile() and Matcher.find() work correctly for both JDK and re2j engines")
    void regexMatchesDigitSequenceWithEitherEngine(boolean useRe2j) {
        Regex.wantsRe2j(useRe2j);
        // usingRe2j() also checks classpath availability, so the result equals
        // useRe2j only when the re2j library is actually present on the classpath
        assertEquals(useRe2j, Regex.usingRe2j());

        Regex regex = Regex.compile("(\\d+)");
        Regex.Matcher matcher = regex.matcher("12345");
        assertTrue(matcher.find());
    }

    @Test
    @DisplayName("JDK engine supports backreferences that the re2j engine cannot handle")
    void jdkEngineMatchesBackreference() {
        Regex.wantsRe2j(false);
        // \1 is a backreference to group 1 — only the JDK backtracking engine supports this
        Regex regex = Regex.compile("(\\w+)\\s+\\1");
        Regex.Matcher matcher = regex.matcher("hello hello");
        assertTrue(matcher.find());
    }

    @Test
    @DisplayName("re2j engine wraps its unsupported-backreference error as ValidationException")
    void re2jEngineRejectsBackreferenceWithValidationException() {
        Regex.wantsRe2j(true);
        // RE2J does not support backreferences; Regex.compile() must surface the
        // failure as ValidationException, not as the re2j-internal PatternSyntaxException
        assertThrows(ValidationException.class, () -> Regex.compile("(\\w+)\\s+\\1"));
    }

    @ParameterizedTest(name = "useRe2j={0}")
    @ValueSource(booleans = {false, true})
    @DisplayName("QueryParser wraps a malformed regex attribute selector as SelectorParseException for both engines")
    void queryParserWrapsMalformedRegexAsSelectorParseException(boolean useRe2j) {
        Regex.wantsRe2j(useRe2j);
        // The unclosed group makes the attribute value an invalid regex pattern
        String malformedQuery = "[attr~=(unclosed]";

        Selector.SelectorParseException ex = assertThrows(
            Selector.SelectorParseException.class,
            () -> QueryParser.parse(malformedQuery)
        );
        assertTrue(ex.getMessage().contains("Pattern syntax error"));
    }
}
