package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.compress.AbstractTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class X5455_ExtendedTimestampTest_testWriteReadRoundtrip {

    /** Header ID used to look up the X5455 extra field from a ZIP entry. */
    private static final ZipShort X5455 = new ZipShort(0x5455);

    /** The X5455 extra-field instance under test, recreated before each test. */
    private X5455_ExtendedTimestamp xf;

    @TempDir
    private File tmpDir;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @AfterEach
    public void removeTempFiles() {
        if (tmpDir != null) {
            AbstractTest.forceDelete(tmpDir);
        }
    }

    /**
     * Writes a ZIP archive containing a single entry with an X5455 extended-timestamp
     * extra field, then reads the archive back and confirms that the modify time
     * survived the write-read cycle without change.
     */
    @Test
    void testWriteReadRoundtrip() throws IOException {
        // Build the expected modify date: September 24, 1997, 15:10:02 (local time)
        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1997, Calendar.SEPTEMBER, 24, 15, 10, 2);
        final Date modifyDate = calendar.getTime();

        final File output = new File(tmpDir, "write_rewrite.zip");

        // --- Write phase: create a ZIP entry carrying the X5455 modify-time field ---
        try (OutputStream out = Files.newOutputStream(output.toPath());
             ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(out)) {
            final ZipArchiveEntry entry = new ZipArchiveEntry("foo");
            xf.setModifyJavaTime(modifyDate);
            xf.setFlags((byte) 1); // bit 0 = modify time present
            entry.addExtraField(xf);
            zipOut.putArchiveEntry(entry);
            zipOut.closeArchiveEntry();
        }

        // --- Read phase: open the archive and verify the extra field round-tripped ---
        try (ZipFile zf = ZipFile.builder().setFile(output).get()) {
            final ZipArchiveEntry entry = zf.getEntry("foo");
            final X5455_ExtendedTimestamp ext =
                    (X5455_ExtendedTimestamp) entry.getExtraField(X5455);
            assertNotNull(ext, "X5455 extra field must be present on the entry");
            assertTrue(ext.isBit0_modifyTimePresent(), "modify-time bit (bit 0) must be set");
            assertEquals(modifyDate, ext.getModifyJavaTime(),
                    "modify time must survive the write-read cycle unchanged");
        }
    }
}
