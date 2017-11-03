package gr.charos.bot.bubblebot.decider;

import gr.charos.bot.bubblebot.mrhackman.Field;
import gr.charos.bot.bubblebot.mrhackman.Move;

public interface MoveDecider {
	
	Move decide(Field f);

}
