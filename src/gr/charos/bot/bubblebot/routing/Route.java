package gr.charos.bot.bubblebot.routing;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import gr.charos.bot.bubblebot.MoveType;
import gr.charos.bot.bubblebot.Utils;
import gr.charos.bot.bubblebot.mrhackman.Field;

public class Route {
	private Field field;
	private Point me;
	private Point target;
	private Set<Point> positionsRejected = new HashSet<>();
	private Map<Point, Integer> countPositions = new HashMap<>();
	
	private Point prevPoint = null;
	

	public Route(Field field, Point me, Point target) {
		super();
		this.field = field;
		this.me = me;
		this.target = target;
	}

	public Queue<MoveType> getRouteToPoint() {
		Queue<MoveType> route = new LinkedList<>();
		int xShift = new Double(target.getX() - me.getX()).intValue();

		MoveType xDirection = null;
		if (xShift > 0) {
			xDirection = MoveType.RIGHT;
		} else if (xShift < 0) {
			xDirection = MoveType.LEFT;
		}
		int yShift = new Double(target.getY() - me.getY()).intValue();
		MoveType yDirection = null;
		if (yShift > 0) {
			yDirection = MoveType.DOWN;
		} else if (yShift < 0) {
			yDirection = MoveType.UP;
		}
		if (xDirection != null && !me.equals(target)) {
			route.addAll(makeXMoveRight(me, Math.abs(xShift), xDirection, yDirection));
			Queue<MoveType> tmp = new LinkedList<>(route);
			while (!tmp.isEmpty()) {
				me = Utils.positionAfterMove(me, tmp.poll());
			}
		}
		yShift = new Double(target.getY() - me.getY()).intValue();
		yDirection = null;
		if (yShift > 0) {
			yDirection = MoveType.DOWN;
		} else if (yShift < 0) {
			yDirection = MoveType.UP;
		}
		if (yDirection != null && !me.equals(target)) {
			route.addAll(makeXMoveRight(me, Math.abs(yShift), yDirection, xDirection));
		}

		return route;
	}

	private void logPosition(Point p) {
		if (!countPositions.containsKey(p)) {
			countPositions.put(p, 0);
		}
		Integer count = countPositions.get(p);
		++count;
		countPositions.put(p, count);
		if (count > 5) {
			positionsRejected.add(p);
		}
	}

	public Queue<MoveType> makeXMoveRight(Point me, int stepsOnAxis, MoveType direction, MoveType bias) {
		Point p = new Point(me);
		Queue<MoveType> route = new LinkedList<>();
		MoveType ch1 = null;
		MoveType ch2 = null;
		MoveType ch3 = null;
		MoveType ch4 = null;
		switch (direction) {
		case RIGHT:
			ch1 = MoveType.RIGHT;
			ch2 = bias == MoveType.UP ? MoveType.UP : MoveType.DOWN;
			ch3 = MoveType.LEFT;
			ch4 = bias == MoveType.UP ? MoveType.DOWN : MoveType.UP;
			break;
		case LEFT:
			ch1 = MoveType.LEFT;
			ch2 = bias == MoveType.UP ? MoveType.UP : MoveType.DOWN;
			ch3 = MoveType.RIGHT;
			ch4 = bias == MoveType.UP ? MoveType.DOWN : MoveType.UP;
			break;
		case UP:
			ch1 = MoveType.UP;
			ch2 = bias == MoveType.LEFT ? MoveType.LEFT : MoveType.RIGHT;
			ch3 = MoveType.DOWN;
			ch4 = bias == MoveType.LEFT ? MoveType.RIGHT : MoveType.LEFT;
			break;
		case DOWN:
			ch1 = MoveType.DOWN;
			ch2 = bias == MoveType.LEFT ? MoveType.LEFT : MoveType.RIGHT;
			ch3 = MoveType.UP;
			ch4 = bias == MoveType.LEFT ? MoveType.RIGHT : MoveType.LEFT;
			break;
		default:
			break;
		}
		
		logPosition(me);
		while (stepsOnAxis > 0) {

			Point pRight = Utils.positionAfterMove(p, ch1);
			if (!field.isPointValid(pRight) ||  pRight.equals(prevPoint)) {
				Point pUp = Utils.positionAfterMove(p, ch2);
				if (!field.isPointValid(pUp) ||  pUp.equals(prevPoint)) {
					Point pLeft = Utils.positionAfterMove(p, ch3);
					if (!field.isPointValid(pLeft) || pLeft.equals(prevPoint)) {
						Point pDown = Utils.positionAfterMove(p, ch4);
						if (!field.isPointValid(pDown) ||  pDown.equals(prevPoint)) {
							throw new RuntimeException("I am locked!!!!");
						} else {
							route.add(ch4);
							prevPoint = me;
							route.addAll(makeXMoveRight(pDown, stepsOnAxis, direction, bias));
							return route;
						}

					} else {
						route.add(ch3);
						prevPoint = me;
						route.addAll(makeXMoveRight(pLeft, stepsOnAxis + 1, direction, bias));
						return route;
					}
				} else {
					route.add(ch2);
					prevPoint = me;
					route.addAll(makeXMoveRight(pUp, stepsOnAxis, direction, bias));
					return route;
				}

			} else {
				route.add(ch1);
				prevPoint = me;
				p = pRight;
				stepsOnAxis--;
			}
		}
		return route;
	}

}
