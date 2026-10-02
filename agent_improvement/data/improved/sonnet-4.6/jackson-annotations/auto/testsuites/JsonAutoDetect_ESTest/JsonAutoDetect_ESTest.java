package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
    resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest extends JsonAutoDetect_ESTest_scaffolding {

    /** Returns a mock JsonAutoDetect annotation where every visibility method returns null. */
    private JsonAutoDetect mockAnnotationWithAllNullVisibilities() {
        JsonAutoDetect annotation = mock(JsonAutoDetect.class, CALLS_REAL_METHODS);
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).creatorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).fieldVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).getterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).isGetterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).scalarConstructorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).setterVisibility();
        return annotation;
    }

    @Test(timeout = 4000)
    public void withOverrides_scalarConstructorOverrideAppliedWhileOthersUnchanged() throws Throwable {
        // Base value: all NONE except creator = PUBLIC_ONLY
        JsonAutoDetect.Value base = JsonAutoDetect.Value.construct(
            JsonAutoDetect.Visibility.NONE,
            JsonAutoDetect.Visibility.NONE,
            JsonAutoDetect.Visibility.NONE,
            JsonAutoDetect.Visibility.NONE,
            JsonAutoDetect.Visibility.PUBLIC_ONLY,
            JsonAutoDetect.Visibility.NONE);

        // Override: only SCALAR_CONSTRUCTOR set to PUBLIC_ONLY, rest DEFAULT
        JsonAutoDetect.Value scalarOverride = JsonAutoDetect.Value.construct(
            PropertyAccessor.SCALAR_CONSTRUCTOR, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        JsonAutoDetect.Value merged = base.withOverrides(scalarOverride);

        assertEquals(JsonAutoDetect.Visibility.NONE, merged.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, merged.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, base.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, merged.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarOverride.getIsGetterVisibility());
        assertFalse(merged.equals((Object) base));
        assertEquals(JsonAutoDetect.Visibility.NONE, merged.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, merged.getFieldVisibility());
    }

    @Test(timeout = 4000)
    public void withOverrides_fromNullAnnotation_setterOverrideProducesEqualResult() throws Throwable {
        JsonAutoDetect.Value fromNullAnnotation = JsonAutoDetect.Value.from(mockAnnotationWithAllNullVisibilities());

        JsonAutoDetect.Value withSetterOverride = fromNullAnnotation.withSetterVisibility(
            JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC);
        JsonAutoDetect.Value merged = fromNullAnnotation.withOverrides(withSetterOverride);

        assertNotSame(merged, fromNullAnnotation);
        assertTrue(merged.equals((Object) withSetterOverride));
    }

    @Test(timeout = 4000)
    public void merge_noOverridesBaseWithIsGetterOverride_appliesIsGetterAndLeavesOthersDefault() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.noOverrides();
        JsonAutoDetect.Value isGetterOverride = JsonAutoDetect.Value.construct(
            PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC);

        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(noOverrides, isGetterOverride);

        assertNotSame(merged, isGetterOverride);
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, merged.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getFieldVisibility());
    }

    @Test(timeout = 4000)
    public void merge_noOverridesBaseWithGetterOverride_appliesGetterAndLeavesOthersDefault() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.noOverrides();
        JsonAutoDetect.Value getterOverride = JsonAutoDetect.Value.construct(
            PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NON_PRIVATE);

        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(noOverrides, getterOverride);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, merged.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getScalarConstructorVisibility());
    }

    @Test(timeout = 4000)
    public void withScalarConstructorVisibility_anyOnDefault_changesOnlyScalarConstructorAndSetter() throws Throwable {
        JsonAutoDetect.Value withAnyScalar = JsonAutoDetect.Value.DEFAULT.withScalarConstructorVisibility(
            JsonAutoDetect.Visibility.ANY);

        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withAnyScalar.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withAnyScalar.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withAnyScalar.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withAnyScalar.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.ANY, withAnyScalar.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.ANY, withAnyScalar.getScalarConstructorVisibility());
    }

    @Test(timeout = 4000)
    public void withIsGetterVisibility_protectedAndPublicOnDefault_changesOnlyIsGetter() throws Throwable {
        JsonAutoDetect.Value withProtectedIsGetter = JsonAutoDetect.Value.DEFAULT.withIsGetterVisibility(
            JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC);

        assertEquals(JsonAutoDetect.Visibility.ANY, withProtectedIsGetter.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withProtectedIsGetter.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, withProtectedIsGetter.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withProtectedIsGetter.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withProtectedIsGetter.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, withProtectedIsGetter.getIsGetterVisibility());
    }

    @Test(timeout = 4000)
    public void withFieldVisibility_fromNullAnnotation_producesValueNotEqualToBase() throws Throwable {
        JsonAutoDetect.Value fromNullAnnotation = JsonAutoDetect.Value.from(mockAnnotationWithAllNullVisibilities());

        JsonAutoDetect.Value withPublicField = fromNullAnnotation.withFieldVisibility(
            JsonAutoDetect.Visibility.PUBLIC_ONLY);

        assertFalse(withPublicField.equals((Object) fromNullAnnotation));
    }

    @Test(timeout = 4000)
    public void equals_defaultVisibilityValueVsCreatorNoneValue_differsInCreator() throws Throwable {
        JsonAutoDetect.Value defaultVisibilityValue = JsonAutoDetect.Value.defaultVisibility();
        JsonAutoDetect.Value creatorNoneValue = JsonAutoDetect.Value.construct(
            PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE);

        defaultVisibilityValue.equals(creatorNoneValue);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneValue.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneValue.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneValue.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneValue.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneValue.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, creatorNoneValue.getCreatorVisibility());
    }

    @Test(timeout = 4000)
    public void equals_comparedToNull_returnsFalse() throws Throwable {
        JsonAutoDetect.Value creatorPublicOnly = JsonAutoDetect.Value.construct(
            PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        boolean result = creatorPublicOnly.equals((Object) null);

        assertFalse(result);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorPublicOnly.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorPublicOnly.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, creatorPublicOnly.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorPublicOnly.getScalarConstructorVisibility());
    }

    @Test(timeout = 4000)
    public void equals_sameInstance_returnsTrue() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.NO_OVERRIDES;

        boolean result = noOverrides.equals(noOverrides);

        assertTrue(result);
    }

    @Test(timeout = 4000)
    public void equals_comparedToDifferentType_returnsFalse() throws Throwable {
        JsonAutoDetect.Value fromNullAnnotation = JsonAutoDetect.Value.from(mockAnnotationWithAllNullVisibilities());

        boolean result = fromNullAnnotation.equals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC);

        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void readResolve_nonPredefinedValue_returnsEquivalentValue() throws Throwable {
        JsonAutoDetect.Value creatorNone = JsonAutoDetect.Value.construct(
            PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE);

        JsonAutoDetect.Value resolved = (JsonAutoDetect.Value) creatorNone.readResolve();

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolved.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, resolved.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolved.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolved.getSetterVisibility());
        assertNotNull(resolved);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolved.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolved.getGetterVisibility());
    }

    @Test(timeout = 4000)
    public void readResolve_noOverridesConstant_returnsDefaultSetter() throws Throwable {
        JsonAutoDetect.Value resolved = (JsonAutoDetect.Value) JsonAutoDetect.Value.NO_OVERRIDES.readResolve();

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolved.getSetterVisibility());
    }

    @Test(timeout = 4000)
    public void withOverrides_scalarConstructorMatchesBase_returnsSameInstance() throws Throwable {
        JsonAutoDetect.Visibility none = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value allNone = JsonAutoDetect.Value.construct(none, none, none, none, none, none);
        JsonAutoDetect.Value scalarNoneOverride = JsonAutoDetect.Value.construct(
            PropertyAccessor.SCALAR_CONSTRUCTOR, none);

        JsonAutoDetect.Value merged = allNone.withOverrides(scalarNoneOverride);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarNoneOverride.getGetterVisibility());
        assertSame(merged, allNone);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarNoneOverride.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarNoneOverride.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarNoneOverride.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, scalarNoneOverride.getScalarConstructorVisibility());
    }

    @Test(timeout = 4000)
    public void withOverrides_noOverridesWithItself_returnsDefaultGetterVisibility() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.NO_OVERRIDES;

        JsonAutoDetect.Value result = noOverrides.withOverrides(noOverrides);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, result.getGetterVisibility());
    }

    @Test(timeout = 4000)
    public void withOverrides_nullOverride_returnsSelf() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.noOverrides();

        JsonAutoDetect.Value result = noOverrides.withOverrides((JsonAutoDetect.Value) null);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, result.getFieldVisibility());
    }

    @Test(timeout = 4000)
    public void merge_bothNull_returnsNull() throws Throwable {
        JsonAutoDetect.Value result = JsonAutoDetect.Value.merge(
            (JsonAutoDetect.Value) null, (JsonAutoDetect.Value) null);

        assertNull(result);
    }

    @Test(timeout = 4000)
    public void merge_defaultValueWithItself_returnsPublicOnlyGetter() throws Throwable {
        JsonAutoDetect.Value def = JsonAutoDetect.Value.DEFAULT;

        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(def, def);

        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, merged.getGetterVisibility());
    }

    @Test(timeout = 4000)
    public void construct_allAccessorWithPublicOnly_setsCreatorToPublicOnly() throws Throwable {
        JsonAutoDetect.Value allPublicOnly = JsonAutoDetect.Value.construct(
            PropertyAccessor.ALL, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, allPublicOnly.getCreatorVisibility());
    }

    @Test(timeout = 4000)
    public void construct_noneAccessorWithPublicOnly_leavesAllVisibilitiesAsDefault() throws Throwable {
        JsonAutoDetect.Value noneAccessorValue = JsonAutoDetect.Value.construct(
            PropertyAccessor.NONE, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, noneAccessorValue.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, noneAccessorValue.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, noneAccessorValue.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, noneAccessorValue.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, noneAccessorValue.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, noneAccessorValue.getScalarConstructorVisibility());
    }

    @Test(timeout = 4000)
    public void construct_creatorAccessorWithNoneVisibility_onlySetsCreator() throws Throwable {
        JsonAutoDetect.Visibility none = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value allNone = JsonAutoDetect.Value.construct(none, none, none, none, none, none);
        JsonAutoDetect.Visibility fieldVis = allNone.getFieldVisibility(); // NONE

        JsonAutoDetect.Value creatorOnly = JsonAutoDetect.Value.construct(PropertyAccessor.CREATOR, fieldVis);

        assertEquals(JsonAutoDetect.Visibility.NONE, creatorOnly.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnly.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnly.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnly.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnly.getIsGetterVisibility());
    }

    @Test(timeout = 4000)
    public void construct_setterAccessorWithNonPrivate_onlySetsSetterVisibility() throws Throwable {
        JsonAutoDetect.Value setterNonPrivate = JsonAutoDetect.Value.construct(
            PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NON_PRIVATE);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, setterNonPrivate.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, setterNonPrivate.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, setterNonPrivate.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, setterNonPrivate.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, setterNonPrivate.getScalarConstructorVisibility());
    }

    @Test(timeout = 4000)
    public void construct_fieldAccessorWithAny_onlySetsFieldVisibility() throws Throwable {
        JsonAutoDetect.Value fieldAny = JsonAutoDetect.Value.construct(
            PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        assertEquals(JsonAutoDetect.Visibility.ANY, fieldAny.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, fieldAny.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, fieldAny.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, fieldAny.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, fieldAny.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, fieldAny.getGetterVisibility());
    }

    @Test(timeout = 4000)
    public void isVisible_protectedAndPublicVisibility_withHighModifierMember_returnsTrue() throws Throwable {
        JsonAutoDetect.Value value = JsonAutoDetect.Value.construct(
            JsonAutoDetect.Visibility.DEFAULT,
            JsonAutoDetect.Visibility.DEFAULT,
            JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC,
            JsonAutoDetect.Visibility.DEFAULT,
            JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC,
            JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC);
        assertNotNull(value);

        JsonAutoDetect.Visibility isGetterVis = value.getIsGetterVisibility();
        Member publicMember = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(2327).when(publicMember).getModifiers();

        boolean visible = isGetterVis.isVisible(publicMember);

        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, value.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getFieldVisibility());
        assertTrue(visible);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, value.getCreatorVisibility());
    }

    @Test(timeout = 4000)
    public void isVisible_nonPrivateVisibility_withPackagePrivateMember_returnsTrue() throws Throwable {
        Member packagePrivateMember = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(0).when(packagePrivateMember).getModifiers();

        boolean visible = JsonAutoDetect.Visibility.NON_PRIVATE.isVisible(packagePrivateMember);

        assertTrue(visible);
    }

    @Test(timeout = 4000)
    public void isVisible_nonPrivateVisibility_withPrivateMember_returnsFalse() throws Throwable {
        Member privateMember = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn((-1458)).when(privateMember).getModifiers();

        boolean visible = JsonAutoDetect.Visibility.NON_PRIVATE.isVisible(privateMember);

        assertFalse(visible);
    }

    @Test(timeout = 4000)
    public void isVisible_defaultVisibility_withNullMember_returnsFalse() throws Throwable {
        boolean visible = JsonAutoDetect.Visibility.DEFAULT.isVisible((Member) null);

        assertFalse(visible);
    }

    @Test(timeout = 4000)
    public void isVisible_noneVisibility_withNullMember_returnsFalse() throws Throwable {
        boolean visible = JsonAutoDetect.Visibility.NONE.isVisible((Member) null);

        assertFalse(visible);
    }

    @Test(timeout = 4000)
    public void isVisible_publicOnlyVisibility_withNullMember_throwsNullPointerException() throws Throwable {
        // PUBLIC_ONLY calls Modifier.isPublic(m.getModifiers()), which NPEs on a null member
        try {
            JsonAutoDetect.Visibility.PUBLIC_ONLY.isVisible((Member) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility", e);
        }
    }

    @Test(timeout = 4000)
    public void isVisible_protectedAndPublicVisibility_withPackagePrivateMember_returnsFalse() throws Throwable {
        Member packagePrivateMember = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0).when(packagePrivateMember).getModifiers();

        boolean visible = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC.isVisible(packagePrivateMember);

        assertFalse(visible);
    }

    @Test(timeout = 4000)
    public void isVisible_anyVisibility_withNullMember_returnsTrue() throws Throwable {
        boolean visible = JsonAutoDetect.Visibility.ANY.isVisible((Member) null);

        assertTrue(visible);
    }

    @Test(timeout = 4000)
    public void defaultVisibility_getterVisibilityIsPublicOnly() throws Throwable {
        JsonAutoDetect.Visibility getterVisibility = JsonAutoDetect.Value.defaultVisibility().getGetterVisibility();

        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, getterVisibility);
    }

    @Test(timeout = 4000)
    public void valueFor_returnsJsonAutoDetectClass() throws Throwable {
        JsonAutoDetect.Visibility none = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value allNone = JsonAutoDetect.Value.construct(none, none, none, none, none, none);

        allNone.valueFor();

        assertEquals(JsonAutoDetect.Visibility.NONE, allNone.getIsGetterVisibility());
    }

    @Test(timeout = 4000)
    public void toString_defaultValue_containsAllExpectedVisibilityNames() throws Throwable {
        String str = JsonAutoDetect.Value.DEFAULT.toString();

        assertEquals(
            "JsonAutoDetect.Value(fields=PUBLIC_ONLY,getters=PUBLIC_ONLY,isGetters=PUBLIC_ONLY,setters=ANY,creators=PUBLIC_ONLY,scalarConstructors=NON_PRIVATE)",
            str);
    }

    @Test(timeout = 4000)
    public void withGetterVisibility_sameValueAsExisting_returnsEqualValue() throws Throwable {
        JsonAutoDetect.Visibility none = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value allNone = JsonAutoDetect.Value.construct(none, none, none, none, none, none);
        assertNotNull(allNone);

        JsonAutoDetect.Visibility existingFieldVis = allNone.getFieldVisibility(); // NONE
        JsonAutoDetect.Value withSameGetter = allNone.withGetterVisibility(existingFieldVis);

        assertTrue(withSameGetter.equals((Object) allNone));
    }

    @Test(timeout = 4000)
    public void withScalarConstructorVisibility_sameAsDefaultValue_returnsSameInstance() throws Throwable {
        // DEFAULT already has scalarConstructor=NON_PRIVATE, so no change
        JsonAutoDetect.Value result = JsonAutoDetect.Value.DEFAULT.withScalarConstructorVisibility(
            JsonAutoDetect.Visibility.NON_PRIVATE);

        assertSame(result, JsonAutoDetect.Value.DEFAULT);
    }

    @Test(timeout = 4000)
    public void withCreatorVisibility_thenWithOverridesDefault_returnsDefaultInstance() throws Throwable {
        JsonAutoDetect.Value withNonPrivateCreator = JsonAutoDetect.Value.DEFAULT.withCreatorVisibility(
            JsonAutoDetect.Visibility.NON_PRIVATE);
        assertNotNull(withNonPrivateCreator);

        // DEFAULT overrides every non-DEFAULT field, restoring the default state
        JsonAutoDetect.Value merged = withNonPrivateCreator.withOverrides(JsonAutoDetect.Value.DEFAULT);

        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withNonPrivateCreator.getFieldVisibility());
        assertSame(merged, JsonAutoDetect.Value.DEFAULT);
        assertEquals(JsonAutoDetect.Visibility.ANY, withNonPrivateCreator.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, withNonPrivateCreator.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withNonPrivateCreator.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withNonPrivateCreator.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, withNonPrivateCreator.getScalarConstructorVisibility());
    }

    @Test(timeout = 4000)
    public void defaultVisibility_scalarConstructorVisibilityIsNonPrivate() throws Throwable {
        JsonAutoDetect.Visibility scalarVisibility = JsonAutoDetect.Value.defaultVisibility().getScalarConstructorVisibility();

        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, scalarVisibility);
    }

    @Test(timeout = 4000)
    public void defaultValue_setterVisibilityIsAny() throws Throwable {
        JsonAutoDetect.Visibility setterVisibility = JsonAutoDetect.Value.DEFAULT.getSetterVisibility();

        assertEquals(JsonAutoDetect.Visibility.ANY, setterVisibility);
    }

    @Test(timeout = 4000)
    public void noOverrides_creatorVisibilityIsDefault() throws Throwable {
        JsonAutoDetect.Visibility creatorVisibility = JsonAutoDetect.Value.noOverrides().getCreatorVisibility();

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorVisibility);
    }
}
