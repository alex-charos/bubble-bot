package gr.charos.bot.bubblebot.field;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import gr.charos.bot.bubblebot.MoveType;
import gr.charos.bot.bubblebot.Utils;
import gr.charos.bot.bubblebot.move.Move;

public class SnippetDecider implements MoveDecision {

	private Queue<MoveType> route = new LinkedList<>();

	@Override
	public Move decide(Field f) {
		if (route.isEmpty()) {
			Point me = f.getMyPosition();
			ArrayList<Point> snippets = f.getSnippetPositions();
			Point nearestSnippet = Utils.getNearestItem(me, snippets);
			try {
				route = Utils.getRouteToPoint(me, nearestSnippet, f);
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
