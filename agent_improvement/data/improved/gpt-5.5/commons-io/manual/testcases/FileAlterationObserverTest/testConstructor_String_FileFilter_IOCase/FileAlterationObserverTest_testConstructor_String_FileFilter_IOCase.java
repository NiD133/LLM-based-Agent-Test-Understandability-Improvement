package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_String_FileFilter_IOCase extends AbstractMonitorTest {

    private static final String PATH_STRING_FIXTURE = "/foo";

    private String directoryPathWithUnixSeparators(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testConstructor_String_FileFilter_IOCase() {
        final String observedDirectoryName = PATH_STRING_FIXTURE;
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(observedDirectoryName, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);

        assertEquals(observedDirectoryName, directoryPathWithUnixSeparators(observer));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
