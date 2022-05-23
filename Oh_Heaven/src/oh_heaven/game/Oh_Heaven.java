package oh_heaven.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import player.Player;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;
import properties.Properties;
import utils.Rank;
import utils.Suit;

@SuppressWarnings("serial")
public class Oh_Heaven extends CardGame {
	

  
  final String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};

  static public final int seed = 30006;
  static final Random random = new Random(seed);
  ArrayList<Player> players;
  Properties properties = new Properties();
  Round round;
  // return random Enum value
  public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
      int x = random.nextInt(clazz.getEnumConstants().length);
      return clazz.getEnumConstants()[x];
  }

  // return random Card from Hand
  public static Card randomCard(Hand hand){
      int x = random.nextInt(hand.getNumberOfCards());
      return hand.get(x);
  }
 
  // return random Card from ArrayList
  public static Card randomCard(ArrayList<Card> list){
      int x = random.nextInt(list.size());
      return list.get(x);
  }
  
  private void dealingOut(int nbPlayers, int nbCardsPerPlayer) {
	  Hand pack = deck.toHand(false);
	  // pack.setView(Oh_Heaven.this, new RowLayout(hideLocation, 0));
	  for (int i = 0; i < nbCardsPerPlayer; i++) {
		  for (int j=0; j < nbPlayers; j++) {
			  if (pack.isEmpty()) return;
			  Card dealt = randomCard(pack);
		      // System.out.println("Cards = " + dealt);
		      dealt.removeFromHand(false);
		      players.get(j).getHand().insert(dealt, false);
			  // dealt.transfer(hands[j], true);
		  }
	  }
  }
  
  public boolean rankGreater(Card card1, Card card2) {
	  return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
  }
	 

  private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  private final Location[] handLocations = {
			  new Location(350, 625),
			  new Location(75, 350),
			  new Location(350, 75),
			  new Location(625, 350)
	  };
  private final Location[] scoreLocations = {
			  new Location(575, 675),
			  new Location(25, 575),
			  new Location(575, 25),
			  // new Location(650, 575)
			  new Location(575, 575)
	  };
  private Actor[] scoreActors = {null, null, null, null };
  private final Location trickLocation = new Location(350, 350);
  private final Location textLocation = new Location(350, 450);
  private final int thinkingTime = 2000;

  private Location hideLocation = new Location(-500, - 500);
  private Location trumpsActorLocation = new Location(50, 50);
  private boolean enforceRules=false;

  public void setStatus(String string) { setStatusText(string); }


Font bigFont = new Font("Serif", Font.BOLD, 36);

private void initScore() {
	int nbPlayers = properties.getNbPlayers();
	 for (int i = 0; i < nbPlayers; i++) {
		 // scores[i] = 0;
		 String text = "[" + String.valueOf(players.get(i).getScore()) + "]" +
				 String.valueOf(players.get(i).getTrick()) + "/" + String.valueOf(players.get(i).getBid());
		 scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
		 addActor(scoreActors[i], scoreLocations[i]);
	 }
  }

private void updateScore(int player) {
	removeActor(scoreActors[player]);
	String text = "[" + String.valueOf(players.get(player).getScore()) + "]"
			+ String.valueOf(players.get(player).getTrick()) + "/" + String.valueOf(players.get(player).getBid());
	scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
	addActor(scoreActors[player], scoreLocations[player]);
}


private void updateScores() {
	int nbPlayers = properties.getNbPlayers();
	 for (int i = 0; i < nbPlayers; i++) {
		 players.get(i).setScore(properties.getMadeBidBonus());
	 }
}



private void initBids(Suit trumps, int nextPlayer) {
	int total = 0;
	int nbPlayers = properties.getNbPlayers();
	int nbStartCards = properties.getNbStartCards();
	for (int i = nextPlayer; i < nextPlayer + nbPlayers; i++) {
		 int iP = i % nbPlayers;

		 players.get(iP).setBid(nbStartCards / 4 + random.nextInt(2));
		 total +=  players.get(iP).getBid() ;
	 }
	 if (total == nbStartCards) {  // Force last bid so not every bid possible
		 int iP = (nextPlayer + nbPlayers) % nbPlayers;
		 if (players.get(iP).getBid()== 0) {
			 players.get(iP).setBid(1);
		 } else {
			 if(random.nextBoolean()){
			 	players.get(iP).setBid(1);
			 }
			 else{
				 players.get(iP).setBid(0);
			 }
		 }
	 }
	// for (int i = 0; i < nbPlayers; i++) {
	// 	 bids[i] = nbStartCards / 4 + 1;
	//  }
 }

private Card selected;

private void initRound() {
	round = new Round();
	int nbPlayers = properties.getNbPlayers();
	int nbStartCards = properties.getNbStartCards();

		for (int i = 0; i < nbPlayers; i++) {
			   players.get(i).setHand( new Hand(deck));
		}
		dealingOut(nbPlayers, nbStartCards);
		 for (int i = 0; i < nbPlayers; i++) {
			 players.get(i).getHand().sort(Hand.SortType.SUITPRIORITY, true); //problems here

		 }
		 // Set up human player for interaction
		CardListener cardListener = new CardAdapter()  // Human Player plays card
			    {
			      public void leftDoubleClicked(Card card) { selected = card; players.get(0).getHand().setTouchEnabled(false); }
			    };
		players.get(0).getHand().addCardListener(cardListener);
		 // graphics
	    RowLayout[] layouts = new RowLayout[nbPlayers];
	    for (int i = 0; i < nbPlayers; i++) {
	      layouts[i] = new RowLayout(handLocations[i], properties.getHandWidth());
	      layouts[i].setRotationAngle(90 * i);
	      // layouts[i].setStepDelay(10);
			players.get(i).getHand().setView(this, layouts[i]);
			players.get(i).getHand().setTargetArea(new TargetArea(trickLocation));
			players.get(i).getHand().draw();
	    }
//	    for (int i = 1; i < nbPlayers; i++) // This code can be used to visually hide the cards in a hand (make them face down)
//	      hands[i].setVerso(true);			// You do not need to use or change this code.
	    // End graphics
 }

private void playRound() {
	int nbPlayers = properties.getNbPlayers();
	int nbStartCards = properties.getNbStartCards();
	Hand trick;
	// Select and display trump suit
		final Suit trumps = randomEnum(Suit.class);
		final Actor trumpsActor = new Actor("sprites/"+trumpImage[trumps.ordinal()]);
	    addActor(trumpsActor, trumpsActorLocation);
	// End trump suit

	int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round
	initBids(trumps, nextPlayer);
    // initScore();
    for (int i = 0; i < nbPlayers; i++) updateScore(i);
	for (int i = 0; i < nbStartCards; i++) {
		trick = new Hand(deck);
    	selected = null;
    	// if (false) {
        if (0 == nextPlayer) {  // Select lead depending on player type
			players.get(0).getHand().setTouchEnabled(true);
    		setStatus("Player 0 double-click on card to lead.");
    		while (null == selected) delay(100);
        } else {
    		setStatusText("Player " + nextPlayer + " thinking...");
            delay(thinkingTime);
            selected = randomCard(players.get(nextPlayer).getHand());
        }
        // Lead with selected card
	        trick.setView(this, new RowLayout(trickLocation,
					(trick.getNumberOfCards()+2)*properties.getTrickWidth()));
			trick.draw();
			selected.setVerso(false);
			// No restrictions on the card being lead
			round.setLead((utils.Suit) selected.getSuit());
			selected.transfer(trick, true); // transfer to trick (includes graphic effect)
			round.setWinner(nextPlayer);
			round.setWinningCard(selected);
		// End Lead
		for (int j = 1; j < nbPlayers; j++) {
			if (++nextPlayer >= nbPlayers) nextPlayer = 0;  // From last back to first
			selected = null;
			// if (false) {
	        if (0 == nextPlayer) {
				players.get(0).getHand().setTouchEnabled(true);
	    		setStatus("Player 0 double-click on card to follow.");
	    		while (null == selected) delay(100);
	        } else {
		        setStatusText("Player " + nextPlayer + " thinking...");
		        delay(thinkingTime);
		        selected = randomCard(players.get(nextPlayer).getHand());
	        }
	        // Follow with selected card
		        trick.setView(this, new RowLayout(trickLocation,
						(trick.getNumberOfCards()+2)*properties.getTrickWidth()));
				trick.draw();
				selected.setVerso(false);  // In case it is upside down
				// Check: Following card must follow suit if possible
					if (selected.getSuit() != round.getLead()&&
							players.get(nextPlayer).getHand().getNumberOfCardsWithSuit(round.getLead()) > 0) {
						 // Rule violation
						 String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
						 System.out.println(violation);
						 if (enforceRules) 
							 try {
								 throw(new BrokeRuleException(violation));
								} catch (BrokeRuleException e) {
									e.printStackTrace();
									System.out.println("A cheating player spoiled the game!");
									System.exit(0);
								}  
					 }
				// End Check
				 selected.transfer(trick, true); // transfer to trick (includes graphic effect)
				 System.out.println("winning: " + round.getWinningCard());
				 System.out.println(" played: " + selected);
				 // System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + (13 - winningCard.getRankId()));
				 // System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " + (13 -    selected.getRankId()));
				 if ( // beat current winner with higher card
					 (selected.getSuit() == round.getWinningCard().getSuit()
							 && rankGreater(selected, round.getWinningCard())) ||
					  // trumped when non-trump was winning
					 (selected.getSuit() == trumps && round.getWinningCard().getSuit() != trumps)) {
					 System.out.println("NEW WINNER");
					 round.setWinner(nextPlayer);
					 round.setWinningCard(selected);
				 }
			// End Follow
		}
		delay(600);
		trick.setView(this, new RowLayout(hideLocation, 0));
		trick.draw();		
		nextPlayer = round.getWinner();
		setStatusText("Player " + nextPlayer + " wins trick.");
		players.get(nextPlayer).setTrick(players.get(nextPlayer).getTrick()+1);

		updateScore(nextPlayer);
	}
	removeActor(trumpsActor);
}

  public Oh_Heaven()
  {


	super(700, 700, 30);
	int nbPlayers = properties.getNbPlayers();
    setTitle("Oh_Heaven (V" +properties.getVersion() + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
    setStatusText("Initializing...");
	players = (ArrayList<Player>) properties.configPlayer();
    initScore();
    for (int i=0; i <properties.getNbRounds(); i++) {

      initRound();
      playRound();
      updateScores();
    };
    for (int i=0; i <nbPlayers; i++) updateScore(i);
    int maxScore = 0;
    for (int i = 0; i <nbPlayers; i++) if (players.get(i).getScore() > maxScore) maxScore = players.get(i).getScore() ;
    Set <Integer> winners = new HashSet<Integer>();
    for (int i = 0; i <nbPlayers; i++) if (players.get(i).getScore()  == maxScore) winners.add(i);
    String winText;
    if (winners.size() == 1) {
    	winText = "Game over. Winner is player: " +
    			winners.iterator().next();
    }
    else {
    	winText = "Game Over. Drawn winners are players: " +
    			String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toSet()));
    }
    addActor(new Actor("sprites/gameover.gif"), textLocation);
    setStatusText(winText);
    refresh();
  }

  public static void main(String[] args)
  {
	// System.out.println("Working Directory = " + System.getProperty("user.dir"));
	final Properties properties;
	if (args == null || args.length == 0) {
	//  properties = PropertiesLoader.loadPropertiesFile(null);
	} else {
	//      properties = PropertiesLoader.loadPropertiesFile(args[0]);
	}
    new Oh_Heaven();
  }

}
