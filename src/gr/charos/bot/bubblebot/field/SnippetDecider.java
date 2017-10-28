package gr.charos.bot.bubblebot.field;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gr.charos.bot.bubblebot.MoveType;
import gr.charos.bot.bubblebot.Utils;
import gr.charos.bot.bubblebot.move.Move;

public class SnippetDecider implements MoveDecision {

	private Point lastPos;
	@Override
	public Move decide(Field f) {
		Point me = f.getMyPosition();

		ArrayList<Point> snippets = f.getSnippetPositions();
		
		Point nearestSnippet = Utils.getNearestItem(me, snippets);
		ArrayList<MoveType> possibleMoves =f.getValidMoveTypes();
		Move m =new Move(MoveType.PASS);
		if (me.getX() > nearestSnippet.getX() && possibleMoves.contains(MoveType.LEFT)) {
			m =new Move(MoveType.LEFT);
		} else if (me.getY() > nearestSnippet.getY() && possibleMoves.contains(MoveType.UP)) {
			m =new Move(MoveType.UP);
		} else if (me.getX() < nearestSnippet.getX()&& possibleMoves.contains(MoveType.RIGHT)) {
			m =new Move(MoveType.RIGHT);
		} else if (me.getY() < nearestSnippet.getY()&& possibleMoves.contains(MoveType.DOWN)) {
			m =new Move(MoveType.DOWN);
		} else {
			Map<Point, MoveType> moves = new HashMap<>();
			
			for (MoveType mt : possibleMoves) {
				Point p = Utils.positionAfterMove(me, mt);
				if (lastPos==null || !lastPos.equals(p)) {
					moves.put(p, mt);	
				}
				
			}
			Point toGo = Utils.getNearestItem(nearestSnippet, moves.keySet());
			lastPos = me;
			m = new Move(moves.get(toGo));
			
		}
		

		
		return m;
	}
	
	

}
