/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package gr.charos.bot.bubblebot;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import gr.charos.bot.bubblebot.decider.MoveDecider;
import gr.charos.bot.bubblebot.decider.impl.SnippetDecider;
import gr.charos.bot.bubblebot.mrhackman.Move;

/**
 * bot.BotStarter
 * 
 * Magic happens here. You should edit this file, or more specifically
 * the doMove() method to make your bot do more than random moves.
 * 
 * @author Jim van Eeden - jim@riddles.io
 */

public class BotStarter {

	private Random rand;

	private MoveDecider decier;
	private BotStarter() {
		this.rand = new Random();
		this.decier = new SnippetDecider();
	}

	/**
	 * Does a move action. Edit this to make your bot smarter.
	 * @param state The current state of the game
	 * @return A Move object
	 */
	public Move doMove(BotState state) {
		ArrayList<MoveType> validMoveTypes = state.getField().getValidMoveTypes();

		
		Point me = state.getField().getMyPosition();
		
		ArrayList<Point> enemies = state.getField().getEnemyPositions();
		
				
		if (validMoveTypes.size() <= 0) return new Move(); // No valid moves, pass

		

		return decier.decide(state.getField());
	}

 	public static void main(String[] args) {
 		System.out.println("Bubble bot starting");
 		BotParser parser = new BotParser(new BotStarter());
 		parser.run();
 	}
 }
