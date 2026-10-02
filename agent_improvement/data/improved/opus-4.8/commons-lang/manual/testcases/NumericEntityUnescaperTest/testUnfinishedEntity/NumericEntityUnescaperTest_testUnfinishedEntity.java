package org.apache.commons.lang3.text.translate;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests how {@link NumericEntityUnescaper} handles an "unfinished" numeric entity,
 * i.e. one that is missing its trailing semicolon.
 *
 * <p>The shared input contains the hex entity {@code &#x30} (which decodes to the
 * character {@code '0'}) without a closing semicolon. The unescaper's behaviour for
 * this case is governed by the {@link NumericEntityUnescaper.OPTION} it is configured
 * with.</p>
 */
@Deprecated
public class NumericEntityUnescaperTest_testUnfinishedEntity extends AbstractLangTest {

    /** Input text containing the hex entity {@code &#x30} with no closing semicolon. */
    private static final String INPUT_WITH_UNFINISHED_ENTITY = "Test &#x30 not test";

    @Test
    void testUnfinishedEntity() {
        // semiColonOptional: decode the entity even though the semicolon is missing
        // (&#x30 -> '0', the character '0').
        final NumericEntityUnescaper optionalSemicolon =
                new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional);
        assertEquals(
                "Test 0 not test",
                optionalSemicolon.translate(INPUT_WITH_UNFINISHED_ENTITY),
                "Failed to support unfinished entities (i.e. missing semicolon)");

        // Default configuration: leave the unfinished entity untouched.
        final NumericEntityUnescaper defaultBehaviour = new NumericEntityUnescaper();
        assertEquals(
                INPUT_WITH_UNFINISHED_ENTITY,
                defaultBehaviour.translate(INPUT_WITH_UNFINISHED_ENTITY),
                "Failed to ignore unfinished entities (i.e. missing semicolon)");

        // errorIfNoSemiColon: reject the unfinished entity with an IllegalArgumentException.
        final NumericEntityUnescaper errorOnMissingSemicolon =
                new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon);
        assertIllegalArgumentException(
                () -> errorOnMissingSemicolon.translate(INPUT_WITH_UNFINISHED_ENTITY));
    }
}
