package org.jsoup.helper;

import org.jsoup.select.QueryParser;
import org.jsoup.select.Selector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class RegexTest_re2jRejectsBackreferenceThrows {

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

    @Test
    void re2jRejectsBackreferenceThrows() {
        Regex.wantsRe2j(true);
        // backreference unsupported by RE2J
        String pattern = "(\\w+)\\s+\\1";
        assertThrows(ValidationException.class, () -> Regex.compile(pattern));
        // and not the rej2 PatternSyntaxException
    }
}
