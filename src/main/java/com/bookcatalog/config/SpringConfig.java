package com.bookcatalog.config;

import com.bookcatalog.UUIDGenerator;
import com.bookcatalog.UUIDGeneratorImpl;
import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.model.Book;
import com.bookcatalog.model.Shelf;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:gmail.properties")
public class SpringConfig {
    @Bean
    public UUIDGenerator uuidGenerator(){
        return new UUIDGeneratorImpl();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(BookDto.class, Book.class, "replace")
                .addMappings(mapper -> mapper.skip(Book::setId));

        createUpdateMap(modelMapper, BookDto.class, Book.class)
                .addMappings(mapper -> mapper.when(s -> false).map(BookDto::getId, Book::setId));

        createUpdateMap(modelMapper, ShelfDto.class, Shelf.class);

        return modelMapper;
    }

    private <S, D> TypeMap<S, D> createUpdateMap(ModelMapper modelMapper, Class<S> from, Class<D> to) {
        return modelMapper.createTypeMap(from, to, "update")
                .setPropertyCondition(Conditions.isNotNull());
//                .addMappings(mapper -> mapper.skip(Book::setId));

    }
}
