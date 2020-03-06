package com.mygdx.game;

public class NodeWrapper implements Comparable<NodeWrapper>
{
	public Node node;
	public Integer priority;
	public NodeWrapper(Node n, Integer priority)
	{
		node=n;
		this.priority = priority;
	}
	//	public NodeWrapper(Coord coords, Integer priority)
//	{
//		node = n;
//		this.priority = priority;
//	}
	public Node get()
	{
		return node;
	}
	@Override
	public int compareTo(NodeWrapper n)
	{
		return Integer.compare(priority,n.priority);
		//return Double.compare(Pathfinder.heuristic(location.coordinates, goal), Pathfinder.heuristic(this.coordinates, goal));
	}
	@Override
	public String toString()
	{
		return node.toString();
	}
}
