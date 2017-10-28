package gr.charos.bot.bubblebot.test;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import gr.charos.bot.bubblebot.MoveType;
import gr.charos.bot.bubblebot.Utils;

public class NearestPositionTest {

	
	@Test
	public void testPositionAfterMove() {
		Point p1 = new Point(0,0);
		
		Point pUp = Utils.positionAfterMove(p1,MoveType.UP);
		Point pDown = Utils.positionAfterMove(p1,MoveType.DOWN);
		Point pLeft = Utils.positionAfterMove(p1,MoveType.LEFT);
		Point pRight = Utils.positionAfterMove(p1,MoveType.RIGHT);
		Point pPass = Utils.positionAfterMove(p1,MoveType.PASS);
		
		assertEquals (p1.getX(), pUp.getX(),0);
		assertEquals (p1.getY()-1, pUp.getY(),0);
		

		assertEquals (p1.getX(), pDown.getX(),0);
		assertEquals (p1.getY()+1, pDown.getY(),0);
		
		

		assertEquals (p1.getX()-1, pLeft.getX(),0);
		assertEquals (p1.getY(), pLeft.getY(),0);
		

		assertEquals (p1.getX()+1, pRight.getX(),0);
		assertEquals (p1.getY(), pRight.getY(),0);
		

		assertEquals (p1.getX(), pPass.getX(),0);
		assertEquals (p1.getY(), pPass.getY(),0);
		
	}
	
	@Test
	public void testDistance() {
		Point p1 = new Point(0,0);
		
		Point p2 = new Point(1,1);
		
		assertEquals(Math.sqrt(2), Utils.calculateDistance(p1, p2), 0);
	}
	
	@Test
	public void testNearest() {
		Point p1 = new Point(0,0);
		
		Point p2 = new Point(1,1);
		Point p3 = new Point(2,1);
		Point p4 = new Point(1,2);
		Point p5 = new Point(3,3);
		
		List<Point> ps = new ArrayList<>();
		
		ps.add(p3);
		ps.add(p4);
		ps.add(p2);
		ps.add(p5);
		
		assertEquals(p2, Utils.getNearestItem(p1, ps));
	}
}
