package com.example.dztest.utils.db.rollback.db.lettercase;

public interface CaseConversion {

    /**
     * Returns the name of the conversion.
     * @return The name of the conversion.
     */
    String getConversionName();

    /**
     * Converts the {@link String} in parameter to another.
     * @param value The {@link String} to convert.
     * @return The result.
     */
    String convert(String value);
}
