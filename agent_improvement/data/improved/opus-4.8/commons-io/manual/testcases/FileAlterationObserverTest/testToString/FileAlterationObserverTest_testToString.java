package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.monitor.FileAlterationObserver.Builder;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link FileAlterationObserver#toString()}.
 */
public class FileAlterationObserverTest_testToString extends AbstractMonitorTest {

    /** Arbitrary directory path used to build the observers under test. */
    private static final String OBSERVED_DIRECTORY_PATH = "/foo";

    /**
     * Verifies that {@code toString()} reports the observed directory, the active file filter and
     * the listener count, and that the observed directory is exposed via {@code getDirectory()}.
     */
    @Test
    void testToString() {
        final File observedDirectory = new File(OBSERVED_DIRECTORY_PATH);
        final Builder builder = FileAlterationObserver.builder();

        // With no explicit file filter, the observer falls back to the "true" filter,
        // which toString() renders as "true".
        final FileAlterationObserver observerWithDefaultFilter = builder.setFile(observedDirectory).getUnchecked();
        assertEquals(
                "FileAlterationObserver[file='" + observedDirectory.getPath() + "', true, listeners=0]",
                observerWithDefaultFilter.toString());

        // With an explicit file filter, toString() renders the filter's own toString() ("CanReadFileFilter").
        final FileAlterationObserver observerWithReadFilter = builder.setFileFilter(CanReadFileFilter.CAN_READ).getUnchecked();
        assertEquals(
                "FileAlterationObserver[file='" + observedDirectory.getPath() + "', CanReadFileFilter, listeners=0]",
                observerWithReadFilter.toString());

        assertEquals(observedDirectory, observerWithReadFilter.getDirectory());
    }
}
