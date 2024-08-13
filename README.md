# Poker Texas Holdem Onboard Test

## Overview
This project involves creating a web service to solve the Texas Holdem exercise as per the given instructions.

## Problem Description
The task is to compare pairs of poker hands and determine the winner, identifying the type of winning hand and the cards that contributed to the win.

**Examples:**
- "First hand wins with a high card, the winning card was the Ace."
- "Second hand wins with a pair, the winning card was the King."

**Note:** The above text is illustrative; the actual result should be returned as an object evaluated through unit tests.

## Poker Rules Description
A poker deck contains 52 cards, each with a suit (Clubs, Diamonds, Hearts, Spades) and a value (2 through Ace). Hand rankings from lowest to highest include High Card, One Pair, Two Pair, Three of a Kind, Straight, Flush, Full House, Four of a Kind, Straight Flush, and Royal Flush.

## Instructions
- Create a public GitHub repository with this project (not a fork).
- Implement the web service to evaluate poker hands according to the specified rules.
- Use any programming language and framework, ensuring that the web service passes the provided Postman test collection.
- The service must determine the winning hand, the type of hand, and the specific cards contributing to the win.

**Request Structure:**
```
{
  "hand1": {
    "type": "string"
  },
  "hand2": {
    "type": "string"
  }
}
```
**Response Structure:**
```
{
  "winnerHand": {
    "type": "string"
  },
  "winnerHandType": {
    "type": "string"
  },
  "compositionWinnerHand": {
    "type": "array"
  }
}

```
## Deliverables
- Public GitHub repository URL with the solution.
- URL of the deployed web service linked to the repository.

**Evaluation Criteria:**
- Functionality of the web services.
- Passing the associated tests.
- Code style and design.
- Logic implementation.
- Unit tests (bonus points).
- Additional validation rules (bonus points).

## Note
Only 5 of the 10 hand types need to be implemented, with a preference for implementing them in order, such as Royal Flush, Straight Flush, Four of a Kind, Full House, and Flush.
