package com.truestyle.repository;

import com.truestyle.entity.Quote;
import com.truestyle.entity.Stuff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface QuoteRepository  extends CrudRepository<Quote, Long> {

    @Query(value = "select * from quotes order by random() limit 1",
            nativeQuery = true)
    Quote findOneRandom();

}
