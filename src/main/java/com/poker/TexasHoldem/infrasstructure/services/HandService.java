package com.poker.TexasHoldem.infrasstructure.services;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.poker.TexasHoldem.api.dto.HandRequest;
import com.poker.TexasHoldem.api.dto.HandResponse;
import com.poker.TexasHoldem.utils.HandType;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class HandService {

    public HandResponse evaluateHands(HandRequest request) {
        String hand1 = request.getHand1();
        String hand2 = request.getHand2();

        HandType hand1Type = evaluateHand(hand1);
        HandType hand2Type = evaluateHand(hand2);
        log.info("Hand1Type: {}", hand1Type);
        log.info("Hand1: {}", hand1);
        log.info("Hand2: {}", hand2);
        log.info("Hand2Type: {}", evaluateHand(hand2));

        int comparison = hand1Type.compareTo(hand2Type);
        String winnerHand;
        HandType winnerHandType;
        List<String> compositionWinnerHand = Arrays.asList("tie");

        if (comparison > 0) {
            winnerHandType = hand1Type;
            compositionWinnerHand = validComposition(hand1, winnerHandType);
            winnerHand = "hand1";
        } else if (comparison < 0) {
            winnerHandType = hand2Type;
            compositionWinnerHand = validComposition(hand2, winnerHandType);
            winnerHand = "hand2";
        } else {
            if (HandType.HIGH_CARD.equals(hand1Type)) {
                log.info("Comparing hands");
                int compare = compareHands(hand1, hand2);
                if (compare > 0) {
                    winnerHand = "hand1";
                    winnerHandType = hand1Type;
                    compositionWinnerHand = compositionHighCard(hand1);
                } else if (compare < 0) {
                    winnerHand = "hand2";
                    winnerHandType = hand2Type;
                    compositionWinnerHand = compositionHighCard(hand2);
                } else {
                    winnerHand = "Tie";
                    winnerHandType = hand1Type;
                    compositionWinnerHand = Arrays.asList("tie");
                }
            } else {

                winnerHand = "Tie";
                winnerHandType = hand1Type;
                compositionWinnerHand = Arrays.asList("tie");

            }
        }
        
        return new HandResponse(winnerHand, winnerHandType.getHandType(), compositionWinnerHand);
    }

    // ------------------ Format compositions --------------------------------

    private List<String> validComposition(String winnerHand, HandType winnerHandType){
        List<String> compositionWinnerHand = new ArrayList<>();
        switch (winnerHandType) {
            case THREE_OF_A_KIND:
                compositionWinnerHand = compositionThreeOfAKind(winnerHand);
                break;
            case TWO_PAIR:
                compositionWinnerHand = compositionTwoPair(winnerHand);
                break;
            case ONE_PAIR:
                compositionWinnerHand = compositionOnePair(winnerHand);
                break;
            case STRAIGHT:
                compositionWinnerHand = compositionStraight(winnerHand);
                break;
            case HIGH_CARD:
            default:
        
                break;
        }
        return compositionWinnerHand;
    }

    private List<String> compositionHighCard(String winnerHand) {
        List<String> compositionWinnerHand;
        int[] values = extractValues(winnerHand);
        int mayor = values[0];
        for (int i = 0; i < 5; i++) {
            if (values[i] > mayor) {
                mayor = values[i];
            }
        }
        compositionWinnerHand = Arrays.asList(convertValueToString(mayor));
        return compositionWinnerHand;
    }

    private List<String> compositionOnePair(String winnerHand) {
        List<String> compositionWinnerHand = new ArrayList<>();
        
        int[] values = extractValues(winnerHand);
        Arrays.sort(values);
    
        // Buscar el par
        for (int i = 0; i < 4; i++) {
            if (values[i] == values[i + 1]) {
                compositionWinnerHand.add(convertValueToString(values[i]));
                break;
            }
        }
        return compositionWinnerHand;
    }

    private List<String> compositionTwoPair(String winnerHand) {
        List<String> compositionWinnerHand = new ArrayList<>();
        int[] values = extractValues(winnerHand);
        Arrays.sort(values);
        int pairsFound = 0;
    
        // Buscar los dos pares
        for (int i = 0; i < 4; i++) {
            if (values[i] == values[i + 1]) {
                compositionWinnerHand.add(convertValueToString(values[i]));
                pairsFound++;
                i++; // Saltar al siguiente par
            }
            if (pairsFound == 2) {
                break;
            }
        }
        return compositionWinnerHand.reversed();
    }

    private List<String> compositionThreeOfAKind(String winnerHand) {
        List<String> compositionWinnerHand = new ArrayList<>();
        int[] values = extractValues(winnerHand);
        Arrays.sort(values);
    
        // Buscar el valor que aparece tres veces
        for (int i = 0; i < 3; i++) {
            if (values[i] == values[i + 1] && values[i + 1] == values[i + 2]) {
                compositionWinnerHand.add(convertValueToString(values[i]));
                break;
            }
        }
        return compositionWinnerHand;
    }
    
    

    private List<String> compositionStraight(String winnerHand) {
        List<String> compositionWinnerHand = new ArrayList<>();
        int[] values = extractValues(winnerHand);
        Arrays.sort(values);
    
        // Añadir todas las cartas ya que son parte del Straight
        for (int value : values) {
            compositionWinnerHand.add(convertValueToString(value));
        }
        return compositionWinnerHand;
    }

    // ------------------ Valid Hand types --------------------------------

    private boolean isStraight(int[] values) {
        for (int i = 0; i < 4; i++) {
            if (values[i] + 1 != values[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private boolean isThreeOfAKind(int[] values) {
        return (values[0] == values[1] && values[1] == values[2]) ||
                (values[1] == values[2] && values[2] == values[3]) ||
                (values[2] == values[3] && values[3] == values[4]);
    }

    private boolean isTwoPair(int[] values) {
        return (values[0] == values[1] && values[2] == values[3]) ||
                (values[0] == values[1] && values[3] == values[4]) ||
                (values[1] == values[2] && values[3] == values[4]);
    }

    private boolean isOnePair(int[] values) {
        for (int i = 0; i < 4; i++) {
            if (values[i] == values[i + 1]) {
                return true;
            }
        }
        return false;
    }
    
    // ------------------ Utils --------------------------------

    private String convertValueToString(int value) {
        switch (value) {
            case 14: return "As";
            case 13: return "King";
            case 12: return "Queen";
            case 11: return "J";
            default: return String.valueOf(value);
        }
    }
    

    private HandType evaluateHand(String hand) {
        String[] cards = hand.split(" ");
        int[] values = new int[5];
        char[] suits = new char[5];

        for (int i = 0; i < 5; i++) {
            // Extraer el valor de la carta 
            String value = cards[i].substring(0, cards[i].length() - 1);
            // Extraer el palo de la carta (el último carácter)
            suits[i] = cards[i].charAt(cards[i].length() - 1);
            // Convertir el valor de la carta en un número entero
            values[i] = getValue(value);
        }

        Arrays.sort(values);

        log.info("Values: {}", values);

        if (isStraight(values)) {
            return HandType.STRAIGHT;
        } else if (isThreeOfAKind(values)) {
            return HandType.THREE_OF_A_KIND;
        } else if (isTwoPair(values)) {
            return HandType.TWO_PAIR;
        } else if (isOnePair(values)) {
            return HandType.ONE_PAIR;
        } else {
            return HandType.HIGH_CARD;
        }
    }

    private int getValue(String card) {
        log.info("Card: {}", card);
        try {
            switch (card) {
                case "A":
                    return 14;
                case "K":
                    return 13;
                case "Q":
                    return 12;
                case "J":
                    return 11;
                case "10":
                    return 10;
                default:
                    return Integer.parseInt(card);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid card value: " + card);
        }
        
    }

    private int compareHands(String hand1, String hand2) {
        int[] values1 = extractValues(hand1);
        int[] values2 = extractValues(hand2);

        // Ordenar las cartas de mayor a menor
        Arrays.sort(values1);
        Arrays.sort(values2);

        // Comparar cada valor de la mano desde la carta más alta
        for (int i = 4; i >= 0; i--) {
            if (values1[i] > values2[i]) {
                return 1; // hand1 gana
            } else if (values1[i] < values2[i]) {
                return -1; // hand2 gana
            }
        }

        return 0; // Son iguales
    }

    private int[] extractValues(String hand) {
        String[] cards = hand.split(" ");
        int[] values = new int[5];
        for (int i = 0; i < 5; i++) {
            String value = cards[i].substring(0, cards[i].length() - 1); // Extraer el valor
            values[i] = getValue(value); // Convertir a valor numérico
        }
        return values;
    }


}