package gr.charos.bot.bubblebot.decider;

import gr.charos.bot.bubblebot.move.Move;
import gr.charos.bot.bubblebot.mrhackman.Field;

public interface MoveDecider {
	
	Move decide(Field f);

}
