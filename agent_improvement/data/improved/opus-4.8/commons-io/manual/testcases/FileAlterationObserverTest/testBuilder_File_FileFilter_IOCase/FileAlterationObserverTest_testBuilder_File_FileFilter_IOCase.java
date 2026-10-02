package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link FileAlterationObserver.Builder} applies the supplied directory,
 * file filter and {@link IOCase} to the observer it creates.
 */
public class FileAlterationObserverTest_testBuilder_File_FileFilter_IOCase extends AbstractMonitorTest {

    /** Directory the observer should be configured to watch. */
    private static final File DIRECTORY_TO_OBSERVE = new File("/foo");

    @Test
    void testBuilder_File_FileFilter_IOCase() {
        // Build an observer from a directory, a file filter and a case-sensitivity setting.
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(DIRECTORY_TO_OBSERVE)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .setIOCase(IOCase.INSENSITIVE)
                .getUnchecked();

        // Each builder setting should be reflected on the resulting observer.
        assertEquals(DIRECTORY_TO_OBSERVE, observer.getDirectory(), "directory");
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter(), "file filter");
        // INSENSITIVE case maps to the case-insensitive name comparator.
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator(), "comparator");
    }
}
