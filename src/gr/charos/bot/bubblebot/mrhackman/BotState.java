package gr.charos.bot.bubblebot.mrhackman;

import java.util.HashMap;

import gr.charos.bot.bubblebot.AbstractBotState;

public class BotState extends AbstractBotState {
	private HashMap<String, Player> players;

	public BotState() {
		super();
		this.players = new HashMap<>();
	}

	public HashMap<String, Player> getPlayers() {
		return this.players;
	}

}
