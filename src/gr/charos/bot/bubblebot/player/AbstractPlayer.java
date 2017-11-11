package gr.charos.bot.bubblebot.player;

public abstract class AbstractPlayer {
	protected String name;
	protected int snippets;

	 public AbstractPlayer(String playerName) {
	        this.name = playerName;
	    }

	public void setSnippets(int snippets) {
		this.snippets = snippets;
	}

	public int getSnippets() {
		return this.snippets;
	}
	

    public String getName() {
        return this.name;
    }

}
