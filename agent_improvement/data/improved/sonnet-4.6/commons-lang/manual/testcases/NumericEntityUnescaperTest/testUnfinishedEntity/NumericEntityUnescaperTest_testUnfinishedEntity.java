package org.apache.commons.lang3.text.translate;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class NumericEntityUnescaperTest_testUnfinishedEntity extends AbstractLangTest {

    // A hex numeric entity whose closing semicolon is absent
    private static final String UNFINISHED_HEX_ENTITY = "Test &#x30 not test";

    /**
     * When semiColonOptional is set, an entity without a trailing semicolon
     * should still be translated to its corresponding Unicode character.
     */
    @Test
    void testUnfinishedEntity_semiColonOptional_parsesEntitySuccessfully() {
        NumericEntityUnescaper neu = new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional);
        String expected = "Test 0 not test";
        String result = neu.translate(UNFINISHED_HEX_ENTITY);
        assertEquals(expected, result, "Failed to support unfinished entities (i.e. missing semicolon)");
    }

    /**
     * With the default configuration (semiColonRequired), an entity missing its
     * semicolon should be left unchanged in the output.
     */
    @Test
    void testUnfinishedEntity_defaultConfig_leavesEntityUnchanged() {
        NumericEntityUnescaper neu = new NumericEntityUnescaper();
        String expected = UNFINISHED_HEX_ENTITY;
        String result = neu.translate(UNFINISHED_HEX_ENTITY);
        assertEquals(expected, result, "Failed to ignore unfinished entities (i.e. missing semicolon)");
    }

    /**
     * When errorIfNoSemiColon is set, translating an entity without a trailing
     * semicolon should throw an IllegalArgumentException.
     */
    @Test
    void testUnfinishedEntity_errorIfNoSemiColon_throwsIllegalArgumentException() {
        final NumericEntityUnescaper failingNeu =
                new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon);
        final String failingInput = "Test &#x30 not test";
        assertIllegalArgumentException(() -> failingNeu.translate(failingInput));
    }
}
