package gr.charos.bot.bubblebot;

import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.management.RuntimeErrorException;

import gr.charos.bot.bubblebot.field.Field;

public class Utils {

	public static Point getNearestItem(Point myPosition, Collection<Point> points) {
		if (!points.isEmpty()) {
			Point nearest = points.iterator().next();
			double distance = calculateDistance(myPosition, nearest);
			for (Point p : points) {
				double tmpDis = calculateDistance(myPosition, p);
				if (tmpDis < distance) {
					nearest = p;
					distance = tmpDis;
				}

			}

			return nearest;
		}
		return myPosition;
	}

	public static double calculateDistance(Point p1, Point p2) {
		return Math.hypot(Math.abs(p1.getX() - p2.getX()), Math.abs(p1.getY() - p2.getY()));
	}

	public static Point positionAfterMove(Point p1, MoveType m) {
		switch (m) {
		case UP:
			return new Point(new Double(p1.getX()).intValue(), new Double(p1.getY() - 1).intValue());
		case DOWN:
			return new Point(new Double(p1.getX()).intValue(), new Double(p1.getY() + 1).intValue());
		case LEFT:
			return new Point(new Double(p1.getX() - 1).intValue(), new Double(p1.getY()).intValue());
		case RIGHT:
			return new Point(new Double(p1.getX() + 1).intValue(), new Double(p1.getY()).intValue());
		default:
			break;
		}

		return p1;
	}

	public static Queue<MoveType> getRouteToPoint(Point me, Point targetLocation, Field field) {
		Queue<MoveType> route = new LinkedList<>();
		Integer adjucentX = new Double(me.getX() > targetLocation.getX() ? me.getX() - 1
				: me.getX() < targetLocation.getX() ? me.getX() + 1 : me.getX()).intValue();
		Integer adjucentY = new Double(me.getY() > targetLocation.getY() ? me.getY() - 1
				: me.getY() < targetLocation.getY() ? me.getY() + 1 : me.getY()).intValue();

		/*
		 * if ( me.getX() > adjucentX ) { route.add(MoveType.LEFT); } else if
		 * (me.getX() < adjucentX) { route.add(MoveType.RIGHT); }
		 */

		return makeXMoveRight(me, new Double(targetLocation.getX() - me.getX()).intValue(), field, new HashSet<>());
	}

	private static Queue<MoveType> makeXMoveRight(Point me, int stepsOnXAxis, Field field,
			Set<Point> positionsRejected) {
		Point p = new Point(me);
		Queue<MoveType> route = new LinkedList<>();

		positionsRejected.add(p);
		while (stepsOnXAxis > 0) {

			Point pRight = positionAfterMove(p, MoveType.RIGHT);
			if (!field.isPointValid(pRight) || positionsRejected.contains(pRight)) {
				positionsRejected.add(pRight);
				Point pUp = positionAfterMove(p, MoveType.UP);
				if (!field.isPointValid(pUp) || positionsRejected.contains(pUp)) {
					positionsRejected.add(pUp);
					Point pLeft = positionAfterMove(p, MoveType.LEFT);
					if (!field.isPointValid(pLeft) || positionsRejected.contains(pLeft)) {
						positionsRejected.add(pLeft);
						Point pDown = positionAfterMove(p, MoveType.DOWN);
						if (!field.isPointValid(pDown) || positionsRejected.contains(pDown)) {
							throw new RuntimeException("I am locked!!!!");
						} else {
							route.add(MoveType.DOWN);
							route.addAll(makeXMoveRight(pDown, stepsOnXAxis, field, positionsRejected));
							return route;
						}

					} else {
						route.add(MoveType.LEFT);
						route.addAll(makeXMoveRight(pLeft, stepsOnXAxis + 1, field, positionsRejected));
						return route;
					}
				} else {
					route.add(MoveType.UP);
					route.addAll(makeXMoveRight(pUp, stepsOnXAxis, field, positionsRejected));
					return route;
				}

			} else {
				route.add(MoveType.RIGHT);
				p = pRight;
				stepsOnXAxis--;
			}
		}
		return route;
	}
}
