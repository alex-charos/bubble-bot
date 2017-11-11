package gr.charos.bot.bubblebot.misshackman;

import java.util.HashMap;

import gr.charos.bot.bubblebot.AbstractBotState;

public class BotState extends AbstractBotState {
	private HashMap<String, BomberPlayer> players;

	BotState() {
		super();
		this.players = new HashMap<>();
	}

	public HashMap<String, BomberPlayer> getPlayers() {
		return this.players;
	}

}
