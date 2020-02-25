package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Optional;
import java.util.Random;

public class Tile
{
	private Rectangle rectangle;
	private Optional<Tile> teleport;
	private int weight;
	private static final int width = 10;
	public Tile(int x,int y)
	{
		Random r = new Random();
		teleport = Optional.empty();
		weight = r.nextInt(10);
		rectangle = new Rectangle(x*width,y*width,width,width);
	}
	public Tile(int w,Tile t,int x,int y)
	{
		weight = w;
		teleport = Optional.of(t);
		rectangle = new Rectangle(x,y,width,width);
	}
	public Vector2 getCenter()
	{
		return new Vector2(rectangle.x+5,rectangle.y+5);
	}
	public int getWeight()
	{
		return weight;
	}
	public void draw(ShapeRenderer sr)
	{
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(Color.LIGHT_GRAY);
		sr.rect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
		sr.end();
	}
}
