package gr.charos.bot.bubblebot;

import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

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
		return p1.distance(p2);
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
		int xShift = new Double( targetLocation.getX() - me.getX()).intValue();
		
		MoveType xDirection = null;
		if (xShift >0) {
			xDirection = MoveType.RIGHT;
		} else if (xShift<0) {
			xDirection = MoveType.LEFT;
		}
		int yShift = new Double( targetLocation.getY() - me.getY()).intValue();
		MoveType yDirection = null;
		if (yShift >0) {
			yDirection = MoveType.DOWN;
		} else if (yShift<0) {
			yDirection = MoveType.UP;
		}
		if (xDirection !=null && !me.equals(targetLocation)) {
			route.addAll(makeXMoveRight(me, Math.abs(xShift), xDirection,yDirection, field, new HashSet<>()));
			Queue<MoveType> tmp = new LinkedList<>(route);
			while (!tmp.isEmpty()) {
				me = positionAfterMove(me, tmp.poll());
			}
		}
		  yShift = new Double( targetLocation.getY() - me.getY()).intValue();
		  yDirection = null;
		if (yShift >0) {
			yDirection = MoveType.DOWN;
		} else if (yShift<0) {
			yDirection = MoveType.UP;
		}
		if (yDirection !=null && !me.equals(targetLocation)) {
			route.addAll(makeXMoveRight(me, Math.abs(yShift), yDirection, xDirection, field, new HashSet<>()));
		}
		//return makeXMoveRight(me, new Double(targetLocation.getX() - me.getX()).intValue(), field, new HashSet<>());
		return route;
	}

	
	
	public static Queue<MoveType> makeXMoveRight(Point me, int stepsOnAxis, MoveType direction, MoveType bias, Field field,
			Set<Point> positionsRejected) {
		Point p = new Point(me);
		Queue<MoveType> route = new LinkedList<>();
		MoveType ch1 = null;
		MoveType ch2 = null;
		MoveType ch3 = null;
		MoveType ch4 = null;
		switch (direction) {
		case RIGHT:
			ch1 = MoveType.RIGHT;
			ch2 = bias== MoveType.UP ?MoveType.UP: MoveType.DOWN;
			ch3 = MoveType.LEFT;
			ch4 = bias== MoveType.UP ?MoveType.DOWN: MoveType.UP;
			break;
		case LEFT:
			ch1 = MoveType.LEFT;
			ch2 = bias== MoveType.UP ?MoveType.UP: MoveType.DOWN;
			ch3 = MoveType.RIGHT;
			ch4 = bias== MoveType.UP ?MoveType.DOWN: MoveType.UP;
			break;
		case UP:
			ch1 = MoveType.UP;
			ch2 = bias== MoveType.LEFT ?MoveType.LEFT: MoveType.RIGHT;
			ch3 = MoveType.DOWN;
			ch4 = bias== MoveType.LEFT ?MoveType.RIGHT: MoveType.LEFT;
			break;
		case DOWN:
			ch1 = MoveType.DOWN;
			ch2 = bias== MoveType.LEFT ?MoveType.LEFT: MoveType.RIGHT;
			ch3 = MoveType.UP;
			ch4 = bias== MoveType.LEFT ?MoveType.RIGHT: MoveType.LEFT;
			break;
		default:
			break;
		}
		
		 
		positionsRejected.add(p);
		while (stepsOnAxis > 0) {

			Point pRight = positionAfterMove(p, ch1);
			if (!field.isPointValid(pRight) || positionsRejected.contains(pRight)) {
				positionsRejected.add(pRight);
				Point pUp = positionAfterMove(p, ch2);
				if (!field.isPointValid(pUp) || positionsRejected.contains(pUp)) {
					positionsRejected.add(pUp);
					Point pLeft = positionAfterMove(p,ch3);
					if (!field.isPointValid(pLeft) || positionsRejected.contains(pLeft)) {
						positionsRejected.add(pLeft);
						Point pDown = positionAfterMove(p, ch4);
						if (!field.isPointValid(pDown) || positionsRejected.contains(pDown)) {
							throw new RuntimeException("I am locked!!!!");
						} else {
							route.add(ch4);
							route.addAll(makeXMoveRight(pDown, stepsOnAxis,direction,bias, field,  positionsRejected));
							return route;
						}

					} else {
						route.add(ch3);
						route.addAll(makeXMoveRight(pLeft, stepsOnAxis + 1,direction, bias,field, positionsRejected));
						return route;
					}
				} else {
					route.add(ch2);
					route.addAll(makeXMoveRight(pUp, stepsOnAxis,direction,bias, field, positionsRejected));
					return route;
				}

			} else {
				route.add(ch1);
				p = pRight;
				stepsOnAxis--;
			}
		}
		return route;
	}
	
	class Compass {
		MoveType xDirection;
		MoveType yDirection;
	}
}
