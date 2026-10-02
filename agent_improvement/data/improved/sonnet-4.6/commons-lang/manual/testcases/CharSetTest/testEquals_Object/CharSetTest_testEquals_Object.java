package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CharSet.equals(Object)")
public class CharSetTest_testEquals_Object extends AbstractLangTest {

    // Three distinct CharSet specifications, each created twice to allow same-spec cross-instance checks
    private CharSet abc;      // individual characters: a, b, c
    private CharSet abc2;     // same spec as abc, different instance
    private CharSet atoc;     // range notation: a through c
    private CharSet atoc2;    // same spec as atoc, different instance
    private CharSet notatoc;  // negated range: everything except a through c
    private CharSet notatoc2; // same spec as notatoc, different instance

    @BeforeEach
    void setUp() {
        abc      = CharSet.getInstance("abc");
        abc2     = CharSet.getInstance("abc");
        atoc     = CharSet.getInstance("a-c");
        atoc2    = CharSet.getInstance("a-c");
        notatoc  = CharSet.getInstance("^a-c");
        notatoc2 = CharSet.getInstance("^a-c");
    }

    @Test
    @DisplayName("is not equal to null")
    void testEquals_notEqualToNull() {
        assertNotEquals(null, abc);
    }

    @Test
    @DisplayName("individual-char set equals itself and an equivalent instance, but not range or negated-range sets")
    void testEquals_individualCharSet() {
        assertEquals(abc, abc);           // reflexive
        assertEquals(abc, abc2);          // same spec, different instance
        assertNotEquals(abc, atoc);       // different spec: chars vs range
        assertNotEquals(abc, notatoc);    // different spec: chars vs negated range
    }

    @Test
    @DisplayName("range set equals itself and an equivalent instance, but not individual-char or negated-range sets")
    void testEquals_rangeCharSet() {
        assertNotEquals(atoc, abc);       // different spec: range vs chars
        assertEquals(atoc, atoc);         // reflexive
        assertEquals(atoc, atoc2);        // same spec, different instance
        assertNotEquals(atoc, notatoc);   // different spec: range vs negated range
    }

    @Test
    @DisplayName("negated-range set equals itself and an equivalent instance, but not individual-char or range sets")
    void testEquals_negatedRangeCharSet() {
        assertNotEquals(notatoc, abc);    // different spec: negated range vs chars
        assertNotEquals(notatoc, atoc);   // different spec: negated range vs range
        assertEquals(notatoc, notatoc);   // reflexive
        assertEquals(notatoc, notatoc2);  // same spec, different instance
    }
}
