package io.delta.flink.sink.internal;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

import io.delta.flink.internal.options.DeltaConfigOption;
import org.apache.flink.configuration.ConfigOption;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DeltaSinkOptionsTest {

    /**
     * This test checks if all ConfigOption fields from DeltaSourceOptions class were added to
     * {@link DeltaSinkOptions#USER_FACING_SINK_OPTIONS} or
     * {@link DeltaSinkOptions#INNER_SINK_OPTIONS} map.
     * <p>
     * This tests uses Java Reflection to get all static, public fields of type {@link ConfigOption}
     * from {@link DeltaSinkOptions}.
     */
    @Test
    public void testAllOptionsAreCategorized() {
        Field[] declaredFields = DeltaSinkOptions.class.getDeclaredFields();
        Set<Field> configOptionFields = new HashSet<>();
        for (Field field : declaredFields) {
            if (isPublicStatic(field) && isConfigOptionField(field)) {
                configOptionFields.add(field);
            }
        }

        assertThat(
            "Probably not all ConfigOption Fields were added to DeltaSinkOptions ",
            configOptionFields.size(),
            equalTo(
                DeltaSinkOptions.USER_FACING_SINK_OPTIONS.size()
                + DeltaSinkOptions.INNER_SINK_OPTIONS.size()));
    }

    private boolean isConfigOptionField(Field field) {
        return field.getType().equals(DeltaConfigOption.class);
    }

    private boolean isPublicStatic(Field field) {
        return isStatic(field.getModifiers()) && isPublic(field.getModifiers());
    }
}
