package se.steam.trellov2.repository.model.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;

import static java.sql.Date.valueOf;

@Converter(autoApply = true)
public final class DateAttributeConverter implements AttributeConverter<LocalDate, java.sql.Date> {

    @Override
    public java.sql.Date convertToDatabaseColumn(LocalDate date) {
        if (date != null)
            return valueOf(date);
        return null;
    }

    @Override
    public LocalDate convertToEntityAttribute(java.sql.Date date) {
        if (date != null)
            return date.toLocalDate();
        return null;
    }
}
