package com.truestyle.service;

import com.truestyle.entity.Quote;
import com.truestyle.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueteService {

    @Autowired
    private QuoteRepository quoteRepository;

    public Quote getRandomQuote(){
        return quoteRepository.findOneRandom();
    }
}
