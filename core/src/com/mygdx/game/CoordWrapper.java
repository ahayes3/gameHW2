package com.mygdx.game;

public class CoordWrapper implements Comparable<CoordWrapper>
{
	public Coord coordinates;
	public Integer priority;
	public CoordWrapper(int x, int y, Integer priority)
	{
		coordinates=new Coord(x,y);
		this.priority = priority;
	}
	public CoordWrapper(Coord coords, Integer priority)
	{
		coordinates = coords;
		this.priority = priority;
	}
	@Override
	public int compareTo(CoordWrapper location)
	{
		return Integer.compare(priority,location.priority);
		//return Double.compare(Pathfinder.heuristic(location.coordinates, goal), Pathfinder.heuristic(this.coordinates, goal));
	}
	@Override
	public String toString()
	{
		return coordinates.toString();
	}
}
