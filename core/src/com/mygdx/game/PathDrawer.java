package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PathDrawer
{
	private float thickness;
	private Array<Vector2> points;
	public PathDrawer(float thickness)
	{
		this.thickness=thickness;
		points = new Array<>(1);
	}
	public void setStart(Vector2 point)
	{
		points.set(0,point);
	}
	public void addPoint(Vector2 point)
	{
		points.add(point);
	}
	public void addAll(Array<Vector2> array)
	{
		points.addAll(array);
	}
	public void draw(ShapeRenderer sr)
	{
		sr.begin(ShapeRenderer.ShapeType.Filled);
		for(int i=0;i<points.size-2;i++)
		{
			sr.rectLine(points.get(i).x,points.get(i).y,points.get(i+1).x,points.get(i+1).y,thickness);
		}
		sr.end();
	}
}
