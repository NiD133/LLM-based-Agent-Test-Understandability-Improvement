package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies the miscellaneous object-level behaviour of {@link X5455_ExtendedTimestamp}:
 * its {@code equals}/{@code hashCode}/{@code clone} contract and how its
 * {@code toString()} reflects the timestamps that are currently present.
 */
public class X5455_ExtendedTimestampTest_testMisc {

    /** Common prefix that {@code toString()} always emits, regardless of the timestamps present. */
    private static final String TO_STRING_PREFIX = "0x5455 Zip Extra Field";

    /** Flags byte with bit0 (modify), bit1 (access) and bit2 (create) all set. */
    private static final byte ALL_TIMESTAMPS_FLAG = (byte) 7;

    /** The extended timestamp field under test; freshly created before each test. */
    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @Test
    void testMisc() throws Exception {
        // A freshly created field is never equal to an unrelated object.
        assertNotEquals(xf, new Object());

        // With no timestamps set, toString() shows only the header and none of the time entries.
        assertTrue(xf.toString().startsWith(TO_STRING_PREFIX));
        assertFalse(xf.toString().contains(" Modify:"));
        assertFalse(xf.toString().contains(" Access:"));
        assertFalse(xf.toString().contains(" Create:"));

        // A clone of the empty field is equal to it and shares its hash code.
        Object emptyClone = xf.clone();
        assertEquals(emptyClone.hashCode(), xf.hashCode());
        assertEquals(xf, emptyClone);

        // Populate all three timestamps and enable all three flag bits.
        xf.setModifyJavaTime(new Date(1111));
        xf.setAccessJavaTime(new Date(2222));
        xf.setCreateJavaTime(new Date(3333));
        xf.setFlags(ALL_TIMESTAMPS_FLAG);

        // The populated field now differs from the earlier (empty) clone.
        assertNotEquals(xf, emptyClone);

        // toString() now reports every timestamp in addition to the header.
        assertTrue(xf.toString().startsWith(TO_STRING_PREFIX));
        assertTrue(xf.toString().contains(" Modify:"));
        assertTrue(xf.toString().contains(" Access:"));
        assertTrue(xf.toString().contains(" Create:"));

        // A clone taken after populating is again equal and shares the hash code.
        Object populatedClone = xf.clone();
        assertEquals(populatedClone.hashCode(), xf.hashCode());
        assertEquals(xf, populatedClone);
    }
}
