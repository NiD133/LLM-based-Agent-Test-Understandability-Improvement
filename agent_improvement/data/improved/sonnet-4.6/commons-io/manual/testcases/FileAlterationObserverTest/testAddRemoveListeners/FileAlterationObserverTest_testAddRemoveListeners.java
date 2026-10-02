package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationObserver.Builder;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testAddRemoveListeners extends AbstractMonitorTest {

    private static final String PATH_STRING_FIXTURE = "/foo";

    /**
     * Verifies that adding and removing listeners on a FileAlterationObserver
     * works correctly, including null-safety for both operations.
     *
     * <p>Covered scenarios:
     * <ol>
     *   <li>Adding a null listener is a no-op (listener list stays empty)</li>
     *   <li>Removing a null listener is a no-op (listener list stays empty)</li>
     *   <li>Adding a real listener registers it as the sole entry</li>
     *   <li>Removing that listener leaves the list empty again</li>
     * </ol>
     */
    @Test
    void testAddRemoveListeners() {
        final FileAlterationObserver observer = FileAlterationObserver.builder().setFile(PATH_STRING_FIXTURE).getUnchecked();

        // Adding null should silently do nothing — the listener list must stay empty
        observer.addListener(null);
        assertFalse(observer.getListeners().iterator().hasNext(),
                "Listener list should be empty after adding null");

        // Removing null should also silently do nothing — the listener list must stay empty
        observer.removeListener(null);
        assertFalse(observer.getListeners().iterator().hasNext(),
                "Listener list should still be empty after removing null");

        // Adding a real listener should make it the one and only registered listener
        final FileAlterationListenerAdaptor listener = new FileAlterationListenerAdaptor();
        observer.addListener(listener);
        final Iterator<FileAlterationListener> it = observer.getListeners().iterator();
        assertTrue(it.hasNext(), "Listener list should contain exactly one entry after addListener");
        assertEquals(listener, it.next(), "The registered listener should be the one that was added");
        assertFalse(it.hasNext(), "Listener list should contain no further entries beyond the one added");

        // Removing the registered listener should leave the list empty once more
        observer.removeListener(listener);
        assertFalse(observer.getListeners().iterator().hasNext(),
                "Listener list should be empty after removing the only listener");
    }
}
