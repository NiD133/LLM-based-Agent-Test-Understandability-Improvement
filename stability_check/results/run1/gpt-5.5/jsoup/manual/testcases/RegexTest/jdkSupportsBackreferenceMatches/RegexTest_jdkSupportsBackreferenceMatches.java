package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexTest_jdkSupportsBackreferenceMatches {
    private static final String BACKREFERENCE_TO_FIRST_WORD = "(\\w+)\\s+\\1";
    private static final String REPEATED_WORD_INPUT = "hello hello";

    private boolean originalUseRe2j;

    @BeforeEach
    void rememberOriginalRegexEnginePreference() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreOriginalRegexEnginePreference() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @Test
    void jdkSupportsBackreferenceMatches() {
        Regex.wantsRe2j(false);

        Regex regex = Regex.compile(BACKREFERENCE_TO_FIRST_WORD);
        Regex.Matcher matcher = regex.matcher(REPEATED_WORD_INPUT);

        assertTrue(matcher.find());
    }
}
