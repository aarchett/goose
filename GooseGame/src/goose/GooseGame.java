package goose;


import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class GooseGame {
	
	private GooseGame() {
	}
	
	private static class GooseGameLazyHolder {
		static final GooseGame gooseGame = new GooseGame();
	}
	
	public static GooseGame getInstance() {
		return GooseGameLazyHolder.gooseGame;
	}
	
	
	public Set<Player> players = new LinkedHashSet<Player>();
	
	public Set<Player> getPlayers() {
		return players;
	}

	private boolean addPlayer(Player player) {
		return players.add(player);
	}
	
	public boolean addPlayer(String playerName) {
		Player newPlayer = new Player(playerName);
		boolean added = addPlayer(newPlayer);
		if (added) Board.getInstance().setPlayerAtStartingPosition(newPlayer);
		return added;
	}
	
	public void removeAllPlayers() {
		players.clear();
	}
	
	public void setPlayers(Collection<Player> players) {
		this.players.clear();
		this.players.addAll(players);
	}
	
	public Player getPlayer(String playerName) {
		return players.stream().filter(p->p.getName().equals(playerName)).findFirst().orElse(null);
	}

}
