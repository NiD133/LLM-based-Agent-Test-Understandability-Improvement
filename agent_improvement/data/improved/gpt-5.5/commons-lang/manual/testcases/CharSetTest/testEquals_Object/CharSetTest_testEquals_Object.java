package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CharSetTest_testEquals_Object extends AbstractLangTest {

    @Test
    void testEquals_Object() {
        final CharSet explicitCharacters = CharSet.getInstance("abc");
        final CharSet sameExplicitCharacters = CharSet.getInstance("abc");
        final CharSet characterRange = CharSet.getInstance("a-c");
        final CharSet sameCharacterRange = CharSet.getInstance("a-c");
        final CharSet negatedCharacterRange = CharSet.getInstance("^a-c");
        final CharSet sameNegatedCharacterRange = CharSet.getInstance("^a-c");

        assertNotEquals(null, explicitCharacters);
        assertEquals(explicitCharacters, explicitCharacters);
        assertEquals(explicitCharacters, sameExplicitCharacters);
        assertNotEquals(explicitCharacters, characterRange);
        assertNotEquals(explicitCharacters, negatedCharacterRange);

        assertNotEquals(characterRange, explicitCharacters);
        assertEquals(characterRange, characterRange);
        assertEquals(characterRange, sameCharacterRange);
        assertNotEquals(characterRange, negatedCharacterRange);

        assertNotEquals(negatedCharacterRange, explicitCharacters);
        assertNotEquals(negatedCharacterRange, characterRange);
        assertEquals(negatedCharacterRange, negatedCharacterRange);
        assertEquals(negatedCharacterRange, sameNegatedCharacterRange);
    }
}
