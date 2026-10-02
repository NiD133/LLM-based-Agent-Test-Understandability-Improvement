package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Verifies that when the RE2J engine is enabled, compiling a regex that uses a
 * feature RE2J does not support (a backreference) is reported as a jsoup
 * {@link ValidationException} rather than leaking RE2J's own
 * PatternSyntaxException.
 */
public class RegexTest_re2jRejectsBackreferenceThrows {

    /** RE2J does not support backreferences, so this pattern is invalid under RE2J. */
    private static final String PATTERN_WITH_BACKREFERENCE = "(\\w+)\\s+\\1";

    /** The "use RE2J" setting before the test ran, so it can be restored afterwards. */
    private boolean originalUseRe2j;

    @BeforeEach
    void rememberOriginalRe2jSetting() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreOriginalRe2jSetting() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @Test
    void compilingBackreferenceWithRe2jThrowsValidationException() {
        Regex.wantsRe2j(true);

        assertThrows(ValidationException.class, () -> Regex.compile(PATTERN_WITH_BACKREFERENCE));
    }
}
