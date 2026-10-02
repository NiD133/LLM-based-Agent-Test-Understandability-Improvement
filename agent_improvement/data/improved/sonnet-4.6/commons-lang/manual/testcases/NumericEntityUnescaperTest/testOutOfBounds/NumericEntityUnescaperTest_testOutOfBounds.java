package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Verifies that {@link NumericEntityUnescaper} returns the input unchanged when
 * the string ends mid-entity — i.e. the numeric entity prefix is present but the
 * input runs out of characters before a code point can be determined.
 *
 * <p>The four out-of-bounds prefixes tested are:
 * <ul>
 *   <li>{@code &}   — entity start with nothing following</li>
 *   <li>{@code &#}  — decimal entity start but no digit characters follow</li>
 *   <li>{@code &#x} — hex entity start (lowercase x) but no hex digits follow</li>
 *   <li>{@code &#X} — hex entity start (uppercase X) but no hex digits follow</li>
 * </ul>
 */
@Deprecated
public class NumericEntityUnescaperTest_testOutOfBounds extends AbstractLangTest {

    @ParameterizedTest(name = "input ending with incomplete entity prefix \"{0}\" is returned unchanged")
    @ValueSource(strings = {"Test &", "Test &#", "Test &#x", "Test &#X"})
    void testOutOfBounds(final String inputWithIncompleteEntity) {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
        assertEquals(
                inputWithIncompleteEntity,
                unescaper.translate(inputWithIncompleteEntity),
                "Input with an incomplete numeric entity at the end must pass through unchanged");
    }
}
