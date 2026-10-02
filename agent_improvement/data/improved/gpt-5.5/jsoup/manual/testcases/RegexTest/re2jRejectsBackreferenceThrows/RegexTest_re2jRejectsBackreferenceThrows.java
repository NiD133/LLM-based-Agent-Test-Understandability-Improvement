package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegexTest_re2jRejectsBackreferenceThrows {
    private static final String BACKREFERENCE_PATTERN = "(\\w+)\\s+\\1";

    private boolean originalWantsRe2j;

    @BeforeEach
    void rememberOriginalRe2jPreference() {
        originalWantsRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreOriginalRe2jPreference() {
        Regex.wantsRe2j(originalWantsRe2j);
    }

    @Test
    void re2jRejectsBackreferenceThrows() {
        Regex.wantsRe2j(true);

        assertThrows(
            ValidationException.class,
            () -> Regex.compile(BACKREFERENCE_PATTERN)
        );
    }
}
