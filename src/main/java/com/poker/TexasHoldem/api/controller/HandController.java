package com.poker.TexasHoldem.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poker.TexasHoldem.api.dto.HandRequest;
import com.poker.TexasHoldem.api.dto.HandResponse;
import com.poker.TexasHoldem.infrasstructure.services.HandService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/poker/validation")
@Slf4j
public class HandController {

    @Autowired
    private HandService handService;

    @PostMapping
    public HandResponse evaluatePokerHands(@RequestBody HandRequest request) {
        log.info("Request received: {}", request);
        return handService.evaluateHands(request);
    }
}
