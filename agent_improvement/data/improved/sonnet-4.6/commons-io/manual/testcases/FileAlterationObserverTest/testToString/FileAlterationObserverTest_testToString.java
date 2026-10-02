package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.monitor.FileAlterationObserver.Builder;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testToString extends AbstractMonitorTest {

    // A fixed path used to construct a File for toString() testing; the directory does not need to exist on disk.
    private static final String PATH_STRING_FIXTURE = "/foo";

    @Test
    void testToString() {
        final File observedDir = new File(PATH_STRING_FIXTURE);
        // Establish the directory on the builder once; subsequent calls only change the filter.
        final Builder builder = FileAlterationObserver.builder().setFile(observedDir);

        // When no explicit filter is provided the observer defaults to TrueFileFilter, whose toString() is "true".
        FileAlterationObserver observer = builder.getUnchecked();
        assertEquals(
            "FileAlterationObserver[file='" + observedDir.getPath() + "', true, listeners=0]",
            observer.toString(),
            "Default (TrueFileFilter) should appear as 'true' in toString()"
        );

        // After setting an explicit filter its class name replaces "true" in the toString() output.
        observer = builder.setFileFilter(CanReadFileFilter.CAN_READ).getUnchecked();
        assertEquals(
            "FileAlterationObserver[file='" + observedDir.getPath() + "', CanReadFileFilter, listeners=0]",
            observer.toString(),
            "Explicit filter name should appear in toString()"
        );

        // getDirectory() must return the same directory the observer was built for, regardless of the filter used.
        assertEquals(observedDir, observer.getDirectory());
    }
}
