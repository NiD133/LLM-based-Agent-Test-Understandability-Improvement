package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_String_FileFilter_IOCase extends AbstractMonitorTest {

    private static final String OBSERVED_PATH = "/foo";

    @Test
    void testBuilder_String_FileFilter_IOCase() {
        // Build an observer from a path string, a readable-only file filter, and case-insensitive comparison
        FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(OBSERVED_PATH)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .setIOCase(IOCase.INSENSITIVE)
                .getUnchecked();

        // The directory path should be stored as provided
        String actualPath = FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
        assertEquals(OBSERVED_PATH, actualPath);

        // The file filter should be stored as provided
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());

        // IOCase.INSENSITIVE should resolve to the case-insensitive name comparator
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
