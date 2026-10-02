package org.apache.commons.collections4.collection;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.CloneTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IndexedCollection_ESTest_test10 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that {@link IndexedCollection#addAll(Collection)} copies every element
     * from a source collection into the decorated collection and reports that a change
     * occurred.
     *
     * <p>The source is a unique-indexed collection holding the single value {@code 0}.
     * The target is an empty unique-indexed collection whose key transformer clones each
     * element. After {@code addAll}, the target's underlying list must contain {@code 0}
     * and {@code addAll} must return {@code true}.</p>
     */
    @Test(timeout = 4000)
    public void addAllCopiesElementsAndReportsChange() throws Throwable {
        // Source: a unique-indexed collection that initially holds the value 0.
        final Integer value = Integer.valueOf(0);
        final LinkedList<Integer> sourceBackingList = new LinkedList<Integer>();
        final Transformer<Integer, Integer> constantKey = new ConstantTransformer<Integer, Integer>(value);
        final IndexedCollection<Integer, Integer> source =
                IndexedCollection.uniqueIndexedCollection((Collection<Integer>) sourceBackingList, constantKey);
        sourceBackingList.add(value);

        // Target: an empty unique-indexed collection keyed by cloning each element.
        final LinkedList<Object> targetBackingList = new LinkedList<Object>();
        final Transformer<Object, Object> cloneKey = CloneTransformer.cloneTransformer();
        final IndexedCollection<Object, Object> target =
                IndexedCollection.uniqueIndexedCollection((Collection<Object>) targetBackingList, cloneKey);

        final boolean changed = target.addAll(source);

        assertTrue("addAll should copy 0 into the target's backing list", targetBackingList.contains(0));
        assertTrue("addAll should report that the target changed", changed);
    }
}
