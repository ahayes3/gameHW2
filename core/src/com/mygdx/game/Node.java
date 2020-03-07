package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public class Node
{
	private short weight;
	private Coord coord;
	private Node up,right,left,down,tele;
	private int teleNum;
	private boolean selected;
	
	public Node(String s,Coord coord)
	{
		selected =false;
		this.coord = coord;
		if (s.charAt(0) >= 49 && s.charAt(0) <= 57)
		{
			weight = Short.parseShort(s);
			tele = null;
			teleNum=-1;
		}
		else if (s.charAt(0) == 116)
		{
			weight = 0;
			teleNum = Integer.parseInt(s.substring(1));
		}
		else if (s.charAt(0) == 102)
		{
			weight = -1;
			tele = null;
			teleNum = -1;
		}
		up=left=right=down=null;
	}
	public Node(short weight, Coord coord)
	{
		this.weight = weight;
		this.coord = coord;
		up=left=right=down=null;
		tele = null;
		teleNum = -1;
	}
	public void select()
	{
		selected = true;
	}
	public void deselect()
	{
		selected = false;
	}
	public boolean isSelected()
	{
		return selected;
	}
	public int getWidth()
	{
		if(right == null)
			return 1;
		else
			return 1+right.getWidth();
	}
	public int getDepth()
	{
		if(down == null)
			return 1;
		else
			return 1+down.getDepth();
	}
	public Node getNext()
	{
		if(right!=null)
			return right;
		else if(getNode(0,coord.y+1)!=null)
			return getNode(0,coord.y+1);
		else
			return null;
	}
	public int getWeight()
	{
		return weight;
	}
	public Coord getCoord()
	{
		return coord;
	}
	public int getTeleNum()
	{
		return teleNum;
	}
	public Node getTele()
	{
		return tele;
	}
	public Array<Node> getNeighbors()
	{
		Array<Node> out = new Array<>();
		out.add(up);
		out.add(right);
		out.add(down);
		out.add(left);
		out.add(tele);
		for(Iterator<Node> iter = out.iterator();iter.hasNext();)
		{
			Node next = iter.next();
			if(next == null)
				iter.remove();
		}
		return out;
	}
	public Node add(Node n)
	{
		if(n.coord.x == 0 && this.coord.x > 0)
			return getNode(0,coord.y).add(n);
		else if (n.coord.y > this.coord.y && this.down != null)
			return this.down.add(n);
		else if (n.coord.x > this.coord.x && this.right != null)
			return this.right.add(n);
		else if (n.coord.y > this.coord.y)
		{
			down = n;
			n.up = this;
			if(getNode(n.coord.x+1,n.coord.y)!=null)
			{
				getNode(n.coord.x+1,n.coord.y).left = n;
				n.right= getNode(n.coord.x+1,n.coord.y);
			}
			return n;
		}
		else if(n.coord.x > this.coord.x)
		{
			right = n;
			n.left=this;
			if(getNode(n.coord.x,n.coord.y-1)!=null)
			{
				getNode(n.coord.x,n.coord.y-1).down = n;
				n.up = getNode(n.coord.x,n.coord.y-1);
			}
			return n;
		}
		return null;
	}
	
	public Node getNode(Coord c)
	{
		return getNode(c.x, c.y);
	}
	public Node getNode(int x,int y)
	{
		if(this.coord.x == x && this.coord.y == y)
			return this;
		else
		{
			Node current = this;
			do
			{
				if(x < current.coord.x)
					current = current.left;
				else if(y < current.coord.y)
					current = current.up;
				else if(x > current.coord.x)
					current = current.right;
				else if(y > current.coord.y)
					current  = current.down;
			}while(current !=null && (!(current.coord.x == x && current.coord.y == y)));
			return current;
		}
	}
	public Node bottomLeft()
	{
		if(down != null)
			return down.bottomLeft();
		else
			return this;
	}
	public void connectTeleporters(Array<Node> teles)
	{
		connectTeleporters(new Array<>(),teles);
	}
	public void connectTeleporters(Array<Node> found,Array<Node> teles)
	{
		Node current=this;
		for(int i=0;i<getWidth();i++)
		{
			for(int j=0;j<getDepth();j++)
			{
				current = current.getNode(i,j);
				if(current.teleNum > 0)
				{
					Node a = null;
					for(Node n : found)
					{
						if(n.teleNum == current.teleNum)
						{
							a = n;
							break;
						}
					}
					if(a!=null)
					{
						connect(a, current);
						teles.add(a);
						teles.add(current);
					}
					else
						found.add(current);
				}
			}
		}
		
	}
	public static void connect(Node a,Node b)
	{
		a.tele = b;
		b.tele = a;
	}
	@Override
	public String toString()
	{
		return "Weight: "+weight+" "+coord.toString();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		try
		{
			Node n = (Node) obj;
			if(n.coord.equals(coord))
				return true;
		}
		catch(ClassCastException e)
		{
			return false;
		}
		return false;
	}
}
