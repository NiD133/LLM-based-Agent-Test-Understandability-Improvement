package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that when re2j is enabled, compiling a regex with a backreference throws
 * jsoup's own {@link ValidationException} rather than re2j's internal PatternSyntaxException,
 * keeping jsoup's public API consistent regardless of which regex engine is active.
 */
public class RegexTest_re2jRejectsBackreferenceThrows {

    private boolean originalUseRe2j;

    @BeforeEach
    void setUp() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void tearDown() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @Test
    void re2jRejectsBackreferenceThrows() {
        // Enable re2j, which does not support backreferences
        Regex.wantsRe2j(true);

        // Backreference pattern: matches a word followed by the same word (e.g. "hello hello").
        // re2j rejects backreferences because it enforces linear-time matching.
        String backReferencePattern = "(\\w+)\\s+\\1";

        // jsoup must wrap re2j's rejection as a ValidationException (not re2j's PatternSyntaxException)
        assertThrows(ValidationException.class, () -> Regex.compile(backReferencePattern));
    }
}
