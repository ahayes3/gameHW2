package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
	private int weight; // -1 is impassable
	private Texture text;
	public static final Texture texture = new Texture(Gdx.files.internal("tile.png"));
	private static final int width = 20;
	private boolean selected;
	public Tile(int x,int y)
	{
		Random r = new Random();
		teleport = Optional.empty();
		weight = r.nextInt(9)+1;
		rectangle = new Rectangle(x*width,y*width,width,width);
		if(!teleport.isPresent())
			text = new Texture(Gdx.files.internal(weight+".png"));
		else if(weight == -1)
			text = new Texture(Gdx.files.internal("f.png"));
		else
			text = new Texture(Gdx.files.internal(weight+"t.png"));
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
	public void select()
	{
		selected = !selected;
	}
	public void draw(SpriteBatch batch)
	{
		batch.begin();
		batch.draw(texture,rectangle.x,rectangle.y,rectangle.width,rectangle.height);
		batch.draw(text,rectangle.x,rectangle.y,rectangle.width,rectangle.height);
		batch.end();
	}
}
