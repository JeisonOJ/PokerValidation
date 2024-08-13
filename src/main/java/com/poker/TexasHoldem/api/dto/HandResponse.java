package com.poker.TexasHoldem.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HandResponse {
    private String winnerHand;
    private String winnerHandType;
    private List<String> compositionWinnerHand;
}
