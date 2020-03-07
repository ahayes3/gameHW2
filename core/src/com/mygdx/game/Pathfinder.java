package com.mygdx.game;

import java.util.HashMap;
import com.badlogic.gdx.utils.Array;
import java.util.PriorityQueue;

public class Pathfinder
{
	private Grid grid;
	private boolean dijkstra;
	
	public Pathfinder(Grid g)
	{
		grid =g;
	}
	public Pathfinder(Grid g,boolean dijkstra)
	{
		grid = g;
		this.dijkstra =dijkstra;
	}
	public int heuristic(Node a,Node b,Node tele1,Node tele2)
	{
		return heuristic(a.getCoord(),b.getCoord(),tele1.getCoord(),tele2.getCoord());
	}
	public int heuristic(Coord a,Coord b,Coord t1,Coord t2)
	{
		if(dijkstra)
			return 0;
		else
		{
			return Math.min(Math.min(Math.abs(a.x-b.x)+Math.abs(a.y-b.y),Math.abs(a.x-t1.x)+Math.abs(a.y-t1.y)),Math.abs(t2.x-b.x)+Math.abs(t2.y-b.y));
			//return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
		}
	}
	public int heuristic(Node a,Node b)
	{
		return heuristic(a.getCoord(),b.getCoord());
	}
	public int heuristic(Coord a,Coord b)
	{
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}
	public int manhattanDistance(Node a,Node b)
	{
		return manhattanDistance(a.getCoord(),b.getCoord());
	}
	public int manhattanDistance(Coord a,Coord b)
	{
		return Math.abs(a.x - b.x) + Math.abs(a.y-b.y);
	}
	public Array<Coord> find(Node start,Node goal)
	{
		return find(start,goal,new HashMap<>(),new HashMap<>());
	}
	public Array<Coord> find(Node start, Node goal, HashMap<Node,Node> cameFrom, HashMap<Node,Integer> costSoFar)
	{
		boolean hasTeles = !grid.getTeleporters().isEmpty();
		Node startTele = null;
		Node endTele = null;
		if(hasTeles)
		{
			
			int cls = -1;
			//TODO find path to nearest teleporter to end and to current position and see if it is worth it at all
			int closest = -1;
			for (Node n : grid.getTeleporters())
			{
				int cost = manhattanDistance(start, n);
				int cost2 = manhattanDistance(n, goal);
				if (startTele == null || cost < cls)
				{
					startTele = n;
					cls = cost;
				}
				if (endTele == null || cost2 < closest)
				{
					endTele = n;
					closest = cost2;
				}
			}
		}
		
		
		PriorityQueue<NodeWrapper> frontier = new PriorityQueue<>();
		frontier.add(new NodeWrapper(start,0));
		cameFrom.put(start,start);
		costSoFar.put(start,0);
		
		while(!frontier.isEmpty())
		{
			Node current = frontier.poll().get();
			if(current.equals(goal))
				break;
			for(Node next: current.getNeighbors())
			{
				Integer newCost = costSoFar.get(current) + grid.getNode(next.getCoord()).getWeight();
				if(!costSoFar.containsKey(next) || newCost < costSoFar.get(next))
				{
					if(!costSoFar.containsKey(next))
						costSoFar.put(next,newCost);
					else
						costSoFar.replace(next,newCost);
					if(hasTeles)
					{
						int closest;
						if(startTele !=null)
							closest = manhattanDistance(next,startTele);
						else
							closest  = -1;
						for (Node n : grid.getTeleporters())
						{
							int cost = manhattanDistance(next, n);
							if (startTele == null || cost <closest)
							{
								startTele = n;
								closest = cost;
							}
						}
					}
					
					Integer priority;
					if(hasTeles)
						priority = newCost + heuristic(next,goal,startTele,endTele);
					else
						priority = newCost + heuristic(next,goal);
					frontier.add(new NodeWrapper(next,priority));
					if(cameFrom.containsKey(next))
						cameFrom.replace(next,current);
					else
						cameFrom.put(next,current);
				}
			}
		}
		Array<Node> reversed = new Array<>();
		for(Node n = goal;!n.equals(start);n = cameFrom.get(n))
		{
			reversed.add(n);
		}
		Array<Coord> out = new Array<>();
		reversed.forEach(p -> out.insert(0,p.getCoord()));
		return out;
	}
	public int findCost(Node start,Node goal)
	{
		HashMap<Node,Node> cameFrom = new HashMap<>();
		HashMap<Node,Integer> costSoFar = new HashMap<>();
		PriorityQueue<NodeWrapper> frontier = new PriorityQueue<>();
		frontier.add(new NodeWrapper(start,0));
		cameFrom.put(start,start);
		costSoFar.put(start,0);
		
		while(!frontier.isEmpty())
		{
			Node current = frontier.poll().get();
			if(current.equals(goal))
				break;
			for(Node next: current.getNeighbors())
			{
				Integer newCost = costSoFar.get(current) + grid.getNode(next.getCoord()).getWeight();
				if(!costSoFar.containsKey(next) || newCost < costSoFar.get(next))
				{
					if(!costSoFar.containsKey(next))
						costSoFar.put(next,newCost);
					else
						costSoFar.replace(next,newCost);
					Integer priority = newCost + heuristic(next,goal);
					frontier.add(new NodeWrapper(next,priority));
					if(cameFrom.containsKey(next))
						cameFrom.replace(next,current);
					else
						cameFrom.put(next,current);
				}
			}
		}
		return costSoFar.get(goal);
		
	}
}