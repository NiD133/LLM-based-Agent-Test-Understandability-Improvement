package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated {@link FileAlterationObserver#FileAlterationObserver(File, java.io.FileFilter)}
 * constructor, verifying that the directory and file filter passed in are exposed unchanged via
 * {@link FileAlterationObserver#getDirectory()} and {@link FileAlterationObserver#getFileFilter()}.
 */
public class FileAlterationObserverTest_testConstructor_File_FileFilter extends AbstractMonitorTest {

    /** Directory the observer is asked to watch. Need not exist on disk for this test. */
    private static final File OBSERVED_DIRECTORY = new File("/foo");

    @Test
    void testConstructor_File_FileFilter() {
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer =
                new FileAlterationObserver(OBSERVED_DIRECTORY, CanReadFileFilter.CAN_READ);

        assertEquals(OBSERVED_DIRECTORY, observer.getDirectory(),
                "Observer should report the directory it was constructed with");
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter(),
                "Observer should report the file filter it was constructed with");
    }
}
