package com.truestyle.controller;

import com.truestyle.entity.Quote;
import com.truestyle.service.QueteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/quote")
public class QuoteController {

    //делал ЮГ

    private static Logger log = LogManager.getLogger(TestController.class);
    @Autowired
    private QueteService queteService;

    /** Получить рандомную цитату
     *
     * @return вернет рандомную цитату, либо ошибку на стороне сервера
     */
    @GetMapping("/random")
    public Quote Quote(){
        return queteService.getRandomQuote();
    }

}
