package gr.charos.bot.bubblebot.field;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import gr.charos.bot.bubblebot.MoveType;
import gr.charos.bot.bubblebot.Utils;
import gr.charos.bot.bubblebot.move.Move;

public class SnippetDecider implements MoveDecision {

	private Stack<Point> route = new Stack<>();
	Map<Point, Map<Point,Integer>> quest = new HashMap<>();
	@Override
	public Move decide(Field f) {
		Point me = f.getMyPosition();
		
		ArrayList<Point> snippets = f.getSnippetPositions();
		
		Point nearestSnippet = Utils.getNearestItem(me, snippets);
		if (quest.get(nearestSnippet) == null ) {
			quest.put(nearestSnippet, new HashMap<>());
		}
		if (quest.get(nearestSnippet).get(me) == null) {
			quest.get(nearestSnippet).put(me, 0);
		}
		Integer soFar = quest.get(nearestSnippet).get(me);
		quest.get(nearestSnippet).put(me, ++soFar);
		ArrayList<MoveType> possibleMoves =f.getValidMoveTypes();
		Move m =new Move(MoveType.PASS);
		if (me.getX() > nearestSnippet.getX() && possibleMoves.contains(MoveType.LEFT) 
				&& (route.isEmpty() || !Utils.positionAfterMove(me, MoveType.LEFT).equals(route.peek()))) {
			m =new Move(MoveType.LEFT);
		} else if (me.getY() > nearestSnippet.getY() && possibleMoves.contains(MoveType.UP) && (route.isEmpty() || !Utils.positionAfterMove(me, MoveType.UP).equals(route.peek()))) {
			m =new Move(MoveType.UP);
		} else if (me.getX() < nearestSnippet.getX() && possibleMoves.contains(MoveType.RIGHT)&& (route.isEmpty() || !Utils.positionAfterMove(me, MoveType.RIGHT).equals(route.peek()))) {
			m =new Move(MoveType.RIGHT);
		} else if (me.getY() < nearestSnippet.getY() && possibleMoves.contains(MoveType.DOWN)&& (route.isEmpty() || !Utils.positionAfterMove(me, MoveType.DOWN).equals(route.peek()))) {
			m =new Move(MoveType.DOWN);
		} else {
			Map<Point, MoveType> moves = new HashMap<>();
			
			for (MoveType mt : possibleMoves) {
				Point p = Utils.positionAfterMove(me, mt);
				moves.put(p, mt);
			}
			
			Map<Point,Integer> nextMovesCounts = new HashMap<>();
			for (Point p : moves.keySet()) {
				Integer i =quest.get(nearestSnippet).get(p);
				if (i==null) {
					i=0;
				}
				nextMovesCounts.put(p, i);
			}
			
			
			//Point toGo = Utils.getNearestItem(nearestSnippet, filtered);
			Point toGo = getPointWithLessTravels(nextMovesCounts);
 			m = new Move(moves.get(toGo));
			
		}
		

		route.push(me);
		return m;
	}
	
	
	private Point getPointWithLessTravels(Map<Point,Integer> route) {
		Map.Entry<Point, Integer> lowest = null;
		for (Map.Entry<Point, Integer> entry : route.entrySet()) {
			if (lowest == null || lowest.getValue() < entry.getValue()) {
				lowest = entry;
			}
		}
		
		return lowest.getKey();
	}
	
}
