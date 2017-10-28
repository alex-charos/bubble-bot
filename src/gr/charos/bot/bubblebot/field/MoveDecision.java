package gr.charos.bot.bubblebot.field;

import gr.charos.bot.bubblebot.move.Move;

public interface MoveDecision {
	
	Move decide(Field f);

}
