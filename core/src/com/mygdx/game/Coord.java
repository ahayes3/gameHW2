package com.mygdx.game;

public class Coord
{
	public int x,y;
	public Coord(int x,int y)
	{
		this.x = x;
		this.y= y;
	}
	
	@Override
	public boolean equals(Object o)
	{
		Coord c;
		try
		{
			c = (Coord) o;
		}
		catch(ClassCastException e)
		{
			return false;
		}
	
		if(x == c.x && y == c.y)
			return true;
		return false;
	}
	
	@Override
	public String toString()
	{
		return "("+x+","+y+")";
	}
	
	@Override
	public int hashCode()
	{
		int hash =23;
		hash = hash* 31 + x;
		hash = hash * 31 +y;
		return hash;
	}
}
