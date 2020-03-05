package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Pathfinder
{
	private Array<Array<Tile>> tiles;
	public Pathfinder(Array<Array<Tile>> t)
	{
		tiles =t;
	}
	public static Integer heuristic(Coord a, Coord b)
	{
		return Math.abs(a.x - b.x) +Math.abs(a.y - b.y);
	}
	public Pair<Array<Coord>,Integer> find(Coord start,Coord end)
	{
		return find(start,end,new HashMap<Coord,Coord>(),new HashMap<Coord,Integer>());
	}
	private Pair<Array<Coord>,Integer> find(Coord start, Coord goal, Map<Coord,Coord> cameFrom,Map<Coord,Integer> costSoFar)
	{
		PriorityQueue<CoordWrapper> frontier = new PriorityQueue<>();
		frontier.add(new CoordWrapper(start,0));
		
		cameFrom.put(start,start);
		costSoFar.put(start,0);
		
		while(!frontier.isEmpty())
		{
			Coord current = frontier.poll().coordinates;
			if(current.equals(goal))
				break;
			
			for(Coord next : getNeighbors(current))
			{
				Integer newCost = costSoFar.get(current) + tiles.get(next.x).get(next.y).getWeight();
				if(!costSoFar.containsKey(next) || newCost < costSoFar.get(next))
				{
					if(!costSoFar.containsKey(next))
						costSoFar.put(next,newCost);
					else
						costSoFar.replace(next,newCost);
					Integer priority = newCost + heuristic(next,goal);
					frontier.add(new CoordWrapper(next,priority));
					if(cameFrom.containsKey(next))
						cameFrom.replace(next,current);
					else
						cameFrom.put(next,current);
				}
			}
		}
		Array<Coord> out =new Array<>();
		for(Coord c = goal;c!=start;c = cameFrom.get(c))
		{
			out.add(c);
		}
		out.reverse();
		return new Pair<>(out,costSoFar.get(goal));
	}
	public Array<Coord> getNeighbors(Coord coord)
	{
		Array<Coord> neighbors = new Array<>();
		if(coord.x > 0)
		{
			neighbors.add(new Coord(coord.x-1,coord.y));
		}
		if(coord.x < tiles.size -1)
		{
			neighbors.add(new Coord(coord.x+1,coord.y));
		}
		if(coord.y < tiles.get(coord.x).size -1)
			neighbors.add(new Coord(coord.x,coord.y+1));
		if(coord.y > 0)
			neighbors.add(new Coord(coord.x,coord.y-1));
		return neighbors;
	}
}
