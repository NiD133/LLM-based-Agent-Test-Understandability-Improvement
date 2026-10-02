package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class X5455_ExtendedTimestampTest_testMisc {

    /** The extended timestamp extra field under test. */
    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @Test
    void testMisc() throws Exception {
        // A freshly constructed field should not equal an arbitrary Object.
        assertNotEquals(xf, new Object());

        // Without any timestamps set, toString() should identify the field type
        // but must not mention Modify, Access, or Create sections.
        assertTrue(xf.toString().startsWith("0x5455 Zip Extra Field"));
        assertFalse(xf.toString().contains(" Modify:"));
        assertFalse(xf.toString().contains(" Access:"));
        assertFalse(xf.toString().contains(" Create:"));

        // Clone of an empty field must be equal and share the same hash code.
        Object clone = xf.clone();
        assertEquals(clone.hashCode(), xf.hashCode());
        assertEquals(xf, clone);

        // Populate all three timestamps and enable all timestamp flags (bits 0-2).
        xf.setModifyJavaTime(new Date(1111));
        xf.setAccessJavaTime(new Date(2222));
        xf.setCreateJavaTime(new Date(3333));
        xf.setFlags((byte) 7);

        // After timestamps are set, the field must differ from the empty clone.
        assertNotEquals(xf, clone);

        // toString() must now include all three timestamp sections.
        assertTrue(xf.toString().startsWith("0x5455 Zip Extra Field"));
        assertTrue(xf.toString().contains(" Modify:"));
        assertTrue(xf.toString().contains(" Access:"));
        assertTrue(xf.toString().contains(" Create:"));

        // Clone of the populated field must again be equal and share the same hash code.
        clone = xf.clone();
        assertEquals(clone.hashCode(), xf.hashCode());
        assertEquals(xf, clone);
    }
}
