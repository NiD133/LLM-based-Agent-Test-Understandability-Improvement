package org.apache.commons.lang3.text.translate;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class NumericEntityUnescaperTest_testUnfinishedEntity extends AbstractLangTest {

    private static final String UNFINISHED_HEX_ENTITY = "Test &#x30 not test";
    private static final String TRANSLATED_UNFINISHED_HEX_ENTITY = "Test \u0030 not test";

    @Test
    void testUnfinishedEntity() {
        final NumericEntityUnescaper semicolonOptionalUnescaper =
                new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional);
        final String translatedEntity = semicolonOptionalUnescaper.translate(UNFINISHED_HEX_ENTITY);
        assertEquals(
                TRANSLATED_UNFINISHED_HEX_ENTITY,
                translatedEntity,
                "Failed to support unfinished entities (i.e. missing semicolon)");

        final NumericEntityUnescaper semicolonRequiredUnescaper = new NumericEntityUnescaper();
        final String ignoredEntity = semicolonRequiredUnescaper.translate(UNFINISHED_HEX_ENTITY);
        assertEquals(
                UNFINISHED_HEX_ENTITY,
                ignoredEntity,
                "Failed to ignore unfinished entities (i.e. missing semicolon)");

        final NumericEntityUnescaper failingUnescaper =
                new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon);
        assertIllegalArgumentException(() -> failingUnescaper.translate(UNFINISHED_HEX_ENTITY));
    }
}
