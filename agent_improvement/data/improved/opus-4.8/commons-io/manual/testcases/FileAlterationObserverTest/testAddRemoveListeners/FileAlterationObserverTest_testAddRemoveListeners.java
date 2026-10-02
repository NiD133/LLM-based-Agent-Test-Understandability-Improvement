package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link FileAlterationObserver} correctly registers and unregisters
 * file system listeners through {@link FileAlterationObserver#addListener} and
 * {@link FileAlterationObserver#removeListener}.
 */
public class FileAlterationObserverTest_testAddRemoveListeners extends AbstractMonitorTest {

    /** Arbitrary directory path used to build the observer under test. */
    private static final String OBSERVED_DIRECTORY = "/foo";

    /**
     * Asserts that the observer currently has no registered listeners.
     *
     * @param observer the observer to inspect.
     */
    private static void assertNoListeners(final FileAlterationObserver observer) {
        assertFalse(observer.getListeners().iterator().hasNext(),
                "Observer should have no registered listeners");
    }

    @Test
    void testAddRemoveListeners() {
        final FileAlterationObserver observer =
                FileAlterationObserver.builder().setFile(OBSERVED_DIRECTORY).getUnchecked();

        // A null listener is ignored, so adding or removing one leaves the list empty.
        observer.addListener(null);
        assertNoListeners(observer);
        observer.removeListener(null);
        assertNoListeners(observer);

        // Adding a real listener registers it as the single entry in the list.
        final FileAlterationListenerAdaptor listener = new FileAlterationListenerAdaptor();
        observer.addListener(listener);
        final Iterator<FileAlterationListener> listeners = observer.getListeners().iterator();
        assertTrue(listeners.hasNext(), "Observer should expose the added listener");
        assertEquals(listener, listeners.next(), "Registered listener should match the one added");
        assertFalse(listeners.hasNext(), "Only one listener should be registered");

        // Removing that listener empties the list again.
        observer.removeListener(listener);
        assertNoListeners(observer);
    }
}
