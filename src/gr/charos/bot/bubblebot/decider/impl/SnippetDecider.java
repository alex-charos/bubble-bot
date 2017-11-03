package gr.charos.bot.bubblebot.decider.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import gr.charos.bot.bubblebot.MoveType;
import gr.charos.bot.bubblebot.Utils;
import gr.charos.bot.bubblebot.decider.MoveDecider;
import gr.charos.bot.bubblebot.mrhackman.Field;
import gr.charos.bot.bubblebot.mrhackman.Move;
import gr.charos.bot.bubblebot.routing.Route;


public class SnippetDecider implements MoveDecider {

	private Queue<MoveType> route = new LinkedList<>();

	@Override
	public Move decide(Field f) {
		if (route.isEmpty()) {
			Point me = f.getMyPosition();
			ArrayList<Point> snippets = f.getSnippetPositions();
			Point nearestSnippet = Utils.getNearestItem(me, snippets);
			try {
				Route r = new Route(f, me, nearestSnippet);
				route = r.getRouteToPoint();
			} catch (Exception ex) {

			}
		}
		MoveType mt = route.peek();
		if (f.getValidMoveTypes().contains(mt)) {
			mt = route.poll();
		} else {
			mt = MoveType.PASS;
		}
		return new Move(mt);
	}

}
