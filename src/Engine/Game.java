package Engine;

import java.util.ArrayList;

/* Skeleton code for the Game class*/

public class Game {
    Strategy strategy;
    Casino casino;
    Shoe shoe;
    double initialAmountWagered, actualAmountWagered, totalProfit;

    public Game(Strategy strategy, Casino casino, Shoe shoe, double initialAmountWagered) {
        this.strategy = strategy;
        this.casino = casino;
        this.shoe = shoe;
        this.initialAmountWagered = initialAmountWagered;
        this.actualAmountWagered = initialAmountWagered;
        this.totalProfit = 0.0;
    }

    public Game() {
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public void setInitialAmountWagered(double wager) {
        this.initialAmountWagered = wager;
    }

    public void setActualAmountWagered(double value) {
        actualAmountWagered = value;
    }

    public double getActualAmountWagered() {
        return actualAmountWagered;
    }

    public double getInitialAmountWagered() {
        return initialAmountWagered;
    }

    public double getProfit() {
        return totalProfit;
    }

    /* Returns the dealer's hidden card after the initial deal*/
    Card distributeCards(Hand dealerHand, Hand playerHand) {
        Card hidden;
        hit(playerHand);
        hidden = hit(dealerHand);
        hit(playerHand);
        hit(dealerHand);
        return hidden;
    }

    Card hit(Hand hand) {
        Card card = shoe.removeTopCard();
        hand.addCard(card);
        //System.out.println(card.getRank());
        return card;
    }

    public String won(Hand playerHand, Hand dealerHand) {
        // call playerHand.getValue(), dealerVisibleHand.getValue()
        int playerValue;
        int dealerValue;
        String results = "tie";

        // set best case player hand
        int[] playerValueArray = playerHand.getValues();
        if (playerValueArray.length == 2)
            playerValue = getBetterHand(playerValueArray[0], playerValueArray[1]);
        else
            playerValue = playerValueArray[0];

        // set best case dealer hand
        int[] dealerValueArray = dealerHand.getValues();
        if (dealerValueArray.length == 2)
            dealerValue = getBetterHand(dealerValueArray[0], dealerValueArray[1]);
        else
            dealerValue = dealerValueArray[0];

        if (playerHand.isBusted())
            results = "false";
        else if (dealerHand.isBusted())
            results = "true";
        else if (playerValue > dealerValue)
            results = "true";
        else if (playerValue < dealerValue)
            results = "false";
        else if (playerHand.twentyOne() && !playerHand.blackjack() && dealerHand.blackjack())
            results = "false";
        else if (dealerHand.twentyOne() && !dealerHand.blackjack() && playerHand.blackjack())
            results = "true";

        return results;
    }


    private double calculateProfit(String won, Hand playerHand) {
        double profit;
        double blackjackMultiplier = casino.getBlackjackMultiplier();
        if (won.equals("true")) {
            if (playerHand.blackjack()) {
                profit = playerHand.getAmountWagered() * blackjackMultiplier;
            } else {
                profit = playerHand.getAmountWagered();
            }
        } else if (won.equals("tie")) {
            return 0;
        } else {
            //loss
            profit = (-1) * playerHand.getAmountWagered();
        }

        //System.out.println("Profit = " + profit);
        return profit;
    }


    private int getBetterHand(int hand1, int hand2) {
        if (hand1 <= 21 && hand2 <= 21)
            return hand1 > hand2 ? hand1 : hand2;
        else
            return hand1 > hand2 ? hand2 : hand1;
    }

    ArrayList<Card> removeTopNCards(int numberCards) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < numberCards; i++) {
            cards.add(shoe.removeTopCard());
        }
        return cards;
    }

    void completeDealerHand(Hand dealerHand, Card dealerHiddenCard) {
        //System.out.println("Complete dealer hand");
        while (!reachedN(dealerHand, 17)) {
            hit(dealerHand);
        }
        if (dealerHand.softSeventeen()) {
            hit(dealerHand);
        }
    }

    boolean reachedN(Hand hand, int n) {
        boolean reached = false;
        int[] values = hand.getValues();
        for (int x : values) {
            if (x >= n) {
                reached = true;
            }
        }
        return reached;
    }

    // returns the number of hands that are not busted
    private int numberActiveHands(ArrayList<Hand> playerHands) {
        int count = 0;
        for (Hand hand : playerHands) {
            if (hand.active()) {
                ++count;
            }
        }
        return count;
    }

    private void stand(Hand hand) {
        hand.freeze();
    }

    // returns a hand if it splits, returns null otherwise
    public Hand playHand(Hand playerHand, Hand dealerHand, Card dealerHiddenCard) {
        Hand newHand = null;
        String move;

        //if (playerHand.isBusted()) {
        //}
        move = BasicStrategy.nextMove(playerHand, dealerHiddenCard);
        //move = this.strategy.nextMove();
        //System.out.println("Move = " + move);

        if (move.equals("S")) {
            stand(playerHand);
            //playerHand.freeze();
        } else if (move.equals("D")) {
            setActualAmountWagered(getActualAmountWagered() + playerHand.getAmountWagered()); //reflects doubling the amount wagered for the hand.
            playerHand.setAmountWagered(playerHand.getAmountWagered() * 2);
            hit(playerHand);
            playerHand.freeze();
        } else if (move.equals("P")) {
            Hand[] splitHands = playerHand.split();
            if (splitHands != null) {
                playerHand = splitHands[0];
                newHand = splitHands[1];
                hit(newHand);
                setActualAmountWagered(getActualAmountWagered() + newHand.getAmountWagered());
                //System.out.println("Amount wagered for hands" + playerHand.amountWagered + " " + newHand.amountWagered);
            }
            hit(playerHand); //If can't split then hit.
        } else {
            hit(playerHand); //move was HIT
        }

        return newHand;
    }

    public void play() {
        Hand dealerHand = new Hand(new ArrayList<Card>(), initialAmountWagered);
        ArrayList<Hand> playerHands;
        playerHands = new ArrayList<Hand>();
        playerHands.add(new Hand(new ArrayList<Card>(), initialAmountWagered));
        Card dealerHiddenCard;
        dealerHiddenCard = distributeCards(dealerHand, playerHands.get(0));

        while (numberActiveHands(playerHands) > 0) {
            for (int i = 0; i < playerHands.size(); i++) {
                if (playerHands.get(i).active()) {
                    Hand newHand = playHand(playerHands.get(i), dealerHand, dealerHiddenCard);
                    if (newHand != null) {
                        playerHands.add(i + 1, newHand);
                        i++;
                    }
                }

            }
        }
        completeDealerHand(dealerHand, dealerHiddenCard);
        String won;
        for (Hand playerHand : playerHands) {
            won = won(playerHand, dealerHand);
            //System.out.println("Won is: " + won);
            totalProfit += calculateProfit(won, playerHand);
        }

        //System.out.println("Total profit = " + totalProfit);
        //System.out.println("Wager = " + actualAmountWagered);

    }

}
