package com.bookcatalog.config;

import com.bookcatalog.UUIDGenerator;
import com.bookcatalog.UUIDGeneratorImpl;
import com.bookcatalog.dto.BookDto;
import com.bookcatalog.model.Book;
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
        TypeMap<BookDto, Book> typeMap = modelMapper.createTypeMap(BookDto.class, Book.class);
        typeMap.addMappings(mapper -> mapper.skip(Book::setId));
        return modelMapper;
    }
}
