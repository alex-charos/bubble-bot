package gr.charos.bot.bubblebot;

import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import gr.charos.bot.bubblebot.mrhackman.Field;

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

	
	
	class Compass {
		MoveType xDirection;
		MoveType yDirection;
	}
}
