package com.caerus.ticketservice.configure;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class CaseInsensitiveEnumConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnum<>(targetType);
    }

    private record StringToEnum<T extends Enum<T>>(Class<T> enumType) implements Converter<String, T> {

        @Override
        public T convert(String source) {
            if (source == null || source.isBlank()) {
                return null;
            }
            return Enum.valueOf(enumType, source.trim().toUpperCase());
        }
    }
}