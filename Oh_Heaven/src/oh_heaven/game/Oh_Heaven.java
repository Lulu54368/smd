package oh_heaven.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

import player.Human;
import player.NPC;
import player.Player;
import properties.PropertiesLoader;
import properties.Property;
import utils.Utils;
import utils.Rank;
import utils.Suit;

@SuppressWarnings("serial")
public class Oh_Heaven extends CardGame {

  final String[] trumpImage = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};

  private void dealingOut(Hand[] hands, int nbPlayers, int nbCardsPerPlayer) {
	  Hand pack = deck.toHand(false);
	  // pack.setView(Oh_Heaven.this, new RowLayout(hideLocation, 0));
	  for (int i = 0; i < nbCardsPerPlayer; i++) {
		  for (int j=0; j < nbPlayers; j++) {
			  if (pack.isEmpty()) return;
			  Card dealt = Utils.randomCard(pack);
		      // System.out.println("Cards = " + dealt);
		      dealt.removeFromHand(false);
		      hands[j].insert(dealt, false);
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
  private final Actor[] scoreActors = {null, null, null, null };
  private final Location trickLocation = new Location(350, 350);
  private final Location textLocation = new Location(350, 450);
  private final int thinkingTime = 2000;
  private Hand[] hands;
  private final Location hideLocation = new Location(-500, - 500);
  private final Location trumpsActorLocation = new Location(50, 50);
  private final boolean enforceRules=false;

  public void setStatus(String string) { setStatusText(string); }
  private List<Player> players = new ArrayList<>();

  Font bigFont = new Font("Serif", Font.BOLD, 36);

private void initScore(int nbPlayers) {
	 for (int i = 0; i < nbPlayers; i++) {
		 // scores[i] = 0;
		 String text = "[" + players.get(i).getScore() + "]"
				 	+ players.get(i).getTrick() + "/" + players.get(i).getBid();
		 scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
		 addActor(scoreActors[i], scoreLocations[i]);
	 }
  }

private void updateScore(int player) {
	removeActor(scoreActors[player]);
	String text = "[" + players.get(player).getScore() + "]" +
			players.get(player).getTrick() + "/" + players.get(player).getBid();
	scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
	addActor(scoreActors[player], scoreLocations[player]);
}

private void updateScores(int nbPlayers, int madeBidBonus) {
	 for (int i = 0; i < nbPlayers; i++) {
		 players.get(i).setScore(madeBidBonus);
	 }
}

private void initTricks(int nbPlayers) {
	 for (int i = 0; i < nbPlayers; i++) {
		players.get(i).setTrick(0);
	 }
}

private void initBids(int nextPlayer, int nbPlayers, int nbStartCards) {
	int total = 0;
	for (int i = nextPlayer; i < nextPlayer + nbPlayers; i++) {
		 int iP = i % nbPlayers;
		 players.get(iP).setBid(nbStartCards / 4 + Utils.random.nextInt(2));
		 total += players.get(iP).getBid();
	 }
	if (total == nbStartCards) {  // Force last bid so not every bid possible
		int iP = (nextPlayer + nbPlayers) % nbPlayers;
		if (players.get(iP).getBid() == 0) {	players.get(iP).setBid(1);
		} else {
			players.get(iP).setBid(players.get(iP).getBid() + (Utils.random.nextBoolean() ? -1 : 1));
		}
	}

 }

private void initRound(int nbPlayers, int nbStartCards, int handWidth) {
	hands = new Hand[nbPlayers];
	for (int i = 0; i < nbPlayers; i++) {
		hands[i] = new Hand(deck);
		players.get(i).setHand(hands[i]);
	}
	dealingOut(hands, nbPlayers, nbStartCards);
	for (int i = 0; i < nbPlayers; i++) {
		hands[i].sort(Hand.SortType.SUITPRIORITY, true);
	}

	// Set up human player for interaction
	for(Player player: players){
		if(player instanceof Human){
			((Human) player).setCardListener();
		}
	}
	// graphics
	RowLayout[] layouts = new RowLayout[nbPlayers];
	for (int i = 0; i < nbPlayers; i++) {
		layouts[i] = new RowLayout(handLocations[i], handWidth);
		layouts[i].setRotationAngle(90 * i);
		// layouts[i].setStepDelay(10);
		hands[i].setView(this, layouts[i]);
		hands[i].setTargetArea(new TargetArea(trickLocation));
		hands[i].draw();
	}
//	    for (int i = 1; i < nbPlayers; i++) // This code can be used to visually hide the cards in a hand (make them face down)
//	      hands[i].setVerso(true);			// You do not need to use or change this code.
	    // End graphics
 }

private void playRound(int nbPlayers, int nbStartCards, int trickWidth) {
	// Select and display trump suit
	final Suit trumps = Utils.randomEnum(Suit.class);
	final Actor trumpsActor = new Actor("sprites/"+trumpImage[trumps.ordinal()]);
	addActor(trumpsActor, trumpsActorLocation);
	// End trump suit
	Hand trick;
	int winner;
	Card winningCard;
	Suit lead;
	int nextPlayer = Utils.random.nextInt(nbPlayers); // randomly select player to lead for this round
	initBids(nextPlayer, nbPlayers, nbStartCards);

	Round round = new Round(trumps);
	Card selected;

    // initScore();
    for (int i = 0; i < nbPlayers; i++) updateScore(i);
	for (int i = 0; i < nbStartCards; i++) {
		trick = new Hand(deck);

		// Lead with selected card
    	selected = players.get(nextPlayer).getSelectedCard(round);
		trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
		trick.draw();
		selected.setVerso(false);

		// No restrictions on the card being lead
		lead = (Suit) selected.getSuit();
		selected.transfer(trick, true); // transfer to trick (includes graphic effect)
		winner = nextPlayer;
		winningCard = selected;

		round.setLead(lead);
		round.setWinner(winner);
		round.setWinningCard(winningCard);
		round.setTricks(nextPlayer, selected);

		// End Lead
		for (int j = 1; j < nbPlayers; j++) {
			if (++nextPlayer >= nbPlayers) nextPlayer = 0;  // From last back to first

			selected = players.get(nextPlayer).getSelectedCard(round);
			//players.get(nextPlayer).getHand().sort(Hand.SortType.RANKPRIORITY, true);
	        // Follow with selected card
			trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
			trick.draw();
			selected.setVerso(false);  // In case it is upside down
			// Check: Following card must follow suit if possible
			if (selected.getSuit() != lead && hands[nextPlayer].getNumberOfCardsWithSuit(lead) > 0) {
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
			System.out.println("winning: " + winningCard);
			System.out.println(" played: " + selected);
			// System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + (13 - winningCard.getRankId()));
			// System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " + (13 -    selected.getRankId()));
			if ( // beat current winner with higher card
					(selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
					  // trumped when non-trump was winning
					 (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
					 System.out.println("NEW WINNER");
					 winner = nextPlayer;
					 winningCard = selected;
				 }
			// End Follow
			round.setLead(lead);
			round.setWinner(winner);
			round.setWinningCard(winningCard);
			round.setTricks(nextPlayer, selected);
		}
		round.setLead(null);
		round.setWinningCard(null);
		delay(600);
		trick.setView(this, new RowLayout(hideLocation, 0));
		trick.draw();
		nextPlayer = winner;
		setStatusText("Player " + nextPlayer + " wins trick.");
		players.get(nextPlayer).setTrick(players.get(nextPlayer).getTrick()+1);
		updateScore(nextPlayer);
	}
	removeActor(trumpsActor);
}

  public Oh_Heaven(Property property)
  {
	super(700, 700, 30);
    setTitle("Oh_Heaven (V" + property.getVersion() + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
    setStatusText("Initializing...");

    // initialise the player
    players = property.configPlayer(deck);

    initScore(property.getNbPlayers());
    for (int i=0; i < property.getNbRounds(); i++) {
      initTricks(property.getNbPlayers());
      initRound(property.getNbPlayers(), property.getNbStartCards(), property.getHandWidth());
      playRound(property.getNbPlayers(), property.getNbStartCards(), property.getTrickWidth());
      updateScores(property.getNbPlayers(), property.getMadeBidBonus());
    }
	  for (int i=0; i < property.getNbPlayers(); i++) updateScore(i);

    int maxScore = 0;
    for (int i = 0; i < property.getNbPlayers(); i++){
    	if (players.get(i).getScore() > maxScore) maxScore = players.get(i).getScore();
	}

    Set <Integer> winners = new HashSet<Integer>();

    for (int i = 0; i < property.getNbPlayers(); i++){
    	if (players.get(i).getScore() == maxScore) winners.add(i);
	}
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
		properties = PropertiesLoader.loadPropertiesFile(null);
	} else {
		properties = PropertiesLoader.loadPropertiesFile(args[0]);
	}

	// read and set properties
	Property property = new Property();
	property.setNbStartCards(Integer.parseInt(properties.getProperty(String.format("nbStartCards"))));
	property.setNbRounds(Integer.parseInt(properties.getProperty(String.format("rounds"))));

	ArrayList<String> playTypes = new ArrayList<String>();
	for(int i=0; i < property.getNbPlayers(); i++){
		String playerType = properties.getProperty(String.format("players.%d", i));
		playTypes.add(playerType);
	}
	property.setPlayerTypes(playTypes);

    new Oh_Heaven(property);
  }

}
