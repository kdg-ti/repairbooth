package be.kdg.repaircafe.config.orika.custom.convertors;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeConvertor extends BidirectionalConverter<LocalDateTime, LocalDateTime> {
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/orika/

    @Override
    public LocalDateTime convertTo(LocalDateTime source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
        return (LocalDateTime.from(source));
    }

    @Override
    public LocalDateTime convertFrom(LocalDateTime source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
        return (LocalDateTime.from(source));
    }
}
