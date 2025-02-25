/*
 * Copyright (2023) The Delta Lake Project Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.delta.kernel.defaults.utils;

import io.delta.kernel.data.Row;
import io.delta.kernel.types.*;

public class DefaultKernelTestUtils {
    private DefaultKernelTestUtils() {}

    /**
     * Returns a URI encoded path of the resource.
     *
     * @param resourcePath
     * @return
     */
    public static String getTestResourceFilePath(String resourcePath) {
        return "file:" +
            DefaultKernelTestUtils.class.getClassLoader().getResource(resourcePath).getFile();
    }

    public static Object getValueAsObject(Row row, int columnOrdinal) {
        // TODO: may be it is better to just provide a `getObject` on the `Row` to
        // avoid the nested if-else statements.
        final DataType dataType = row.getSchema().at(columnOrdinal).getDataType();

        if (row.isNullAt(columnOrdinal)) {
            return null;
        }

        if (dataType instanceof BooleanType) {
            return row.getBoolean(columnOrdinal);
        } else if (dataType instanceof ByteType) {
            return row.getByte(columnOrdinal);
        } else if (dataType instanceof ShortType) {
            return row.getShort(columnOrdinal);
        } else if (dataType instanceof IntegerType || dataType instanceof DateType) {
            return row.getInt(columnOrdinal);
        } else if (dataType instanceof LongType || dataType instanceof TimestampType) {
            return row.getLong(columnOrdinal);
        } else if (dataType instanceof FloatType) {
            return row.getFloat(columnOrdinal);
        } else if (dataType instanceof DoubleType) {
            return row.getDouble(columnOrdinal);
        } else if (dataType instanceof StringType) {
            return row.getString(columnOrdinal);
        } else if (dataType instanceof BinaryType) {
            return row.getBinary(columnOrdinal);
        } else if (dataType instanceof StructType) {
            return row.getStruct(columnOrdinal);
        } else if (dataType instanceof MapType) {
            return row.getMap(columnOrdinal);
        } else if (dataType instanceof ArrayType) {
            return row.getArray(columnOrdinal);
        }

        throw new UnsupportedOperationException(dataType + " is not supported yet");
    }
}
