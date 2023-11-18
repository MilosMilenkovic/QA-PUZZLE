import cards.AttackCard;
import cards.BoostAttackCard;
import cards.Card;
import cards.ProtectCard;
import org.junit.Test;
import player.Player;
import utility.Utility;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;



public class TestPlayer {

    @Test
    public void testTakeDamage() {
        new Utility();
        Player player = new Player(10, Utility.generateCards());
        player.takeDamage(1);
        assertEquals(9, player.getHealth());
    }

    @Test
    public void testDrawCard() {
        new Utility();
        Player player = new Player(10, Utility.generateCards());

        int initialDeckSize = player.getNumberOfCardsInDeck();
        int initialHandSize = player.getNumberOfCardsInHand();
        player.drawCard();

        assertEquals(initialDeckSize - 1, player.getNumberOfCardsInDeck());
        assertEquals(initialHandSize + 1, player.getNumberOfCardsInHand());

    }

    @Test
    public void testDrawCardEmptyDeck() {

        List<Card> cards = new ArrayList<>();
        Player player = new Player(10, cards);
        int initialHandSize = player.getNumberOfCardsInHand();


        try {
            player.drawCard();
        } catch (Exception ex) {
            assertFalse("Should not throw exception: " + ex, true);
        }


        assertEquals(0, player.getNumberOfCardsInDeck());
        assertEquals(initialHandSize, player.getNumberOfCardsInHand());
    }

    @Test
    public void testDrawInitialCards() {
        new Utility();
        Player player = new Player(10, Utility.generateCards());

        player.drawInitialCards();

        assertEquals(player.getInitialNumberOfCards(), player.getNumberOfCardsInHand());
    }

    @Test
    public void testPlayCard() {
        new Utility();
        Player player = new Player(10, Utility.generateCards());

        player.drawInitialCards();
        int initialHandSize = player.getNumberOfCardsInHand();
        List<Card> hand = player.getHand();
        Card cardToBePlayed = hand.get(0);
        player.playCard(cardToBePlayed.getNumber());

        assertEquals(initialHandSize - 1, player.getNumberOfCardsInHand());
    }

    @Test
    public void testPlayBoostAttackCard() {
        List<Card> cards = new ArrayList<>();
        cards.add(new BoostAttackCard());
        Player player = new Player(10, cards);
        int initialDamage = player.getDamage();
        Card cardToPlay = cards.get(0);

        player.drawCard();
        player.playCard(cardToPlay.getNumber());

        assertTrue("Boost should be +3 damage but it is not", player.getDamage() == initialDamage + 3);
        assertEquals(false, player.getAttackingStatus());


    }

    @Test
    public void testPlayAttackCard() {
        List<Card> cards = new ArrayList<>();
        cards.add(new AttackCard(3));
        Player player = new Player(10, cards);
        int initialDamage = player.getDamage();
        Card cardToPlay = cards.get(0);

        player.drawCard();
        player.playCard(cardToPlay.getNumber());


        assertTrue("Player's damage should increase after playing an AttackCard", player.getDamage() == (initialDamage + cardToPlay.getNumber()));
        assertTrue("Attacking status should be true after playing an AttackCard", player.getAttackingStatus());
    }

    @Test
    public void testPlayProtectCard() {
        List<Card> cards = new ArrayList<>();
        cards.add(new ProtectCard());
        Player player = new Player(10, cards);
        int initialHealth = player.getHealth();
        Card cardToPlay = cards.get(0);

        player.drawCard();
        player.playCard(cardToPlay.getNumber());
        player.takeDamage(5);

        assertEquals(initialHealth, player.getHealth());

    }

    @Test
    public void testPlayCardOfTypeNotInHand() {

        List<Card> cards = new ArrayList<>();
        BoostAttackCard boostCard = new BoostAttackCard();
        cards.add(boostCard);
        Player player = new Player(10, cards);


        try {
            player.playCard(1);
            assertTrue(true);
        } catch (Exception ex) {
            assertFalse("Should not throw exception: " + ex, true);
        }
    }

    @Test
    public void testShuffleDeck() {
        new Utility();
        Player player = new Player(10, Utility.generateCards());
        List<Card> deck = new ArrayList<>(player.getDeck());
        player.shuffleDeck();
        List<Card> shuffled=new ArrayList<>(player.getDeck());

        assertFalse(deck.equals(shuffled));


    }
}
