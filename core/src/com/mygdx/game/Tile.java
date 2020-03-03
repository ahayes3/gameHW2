package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.Random;

public class Tile
{
	private Rectangle rectangle;
	private static BitmapFont font = new BitmapFont();
	private Optional<Tile> teleport;
	private int weight; // -1 is impassable
//	private Texture text;
	private String text;
	public static final Texture texture = new Texture(Gdx.files.internal("tile.png"));
	public static final int width = 50;
	private int teleportNum;
	private boolean selected;
	
	public Tile(int x, int y, String text) throws InvalidParameterException
	{
		rectangle = new Rectangle(y * width, x * -width, width, width);
		if (text.charAt(0) >= 49 && text.charAt(0) <= 57)
		{
			weight = new Integer(text);
			teleport = Optional.empty();
		}
		else if (text.charAt(0) == 116)
		{
			weight = 0;
			teleportNum = new Integer(text.substring(1));
		}
		else if (text.charAt(0) == 102)
		{
			weight = -1;
			teleport = Optional.empty();
		}
		else
			throw new InvalidParameterException();
		this.text = text;
	}
	
//	public Tile(int x, int y)
//	{
//		Random r = new Random();
//		teleport = Optional.empty();
//		weight = r.nextInt(9) + 1;
//		rectangle = new Rectangle(x * width, y * width, width, width);
//		if (!teleport.isPresent())
//			text = new Texture(Gdx.files.internal(weight + ".png"));
//		else if (weight == -1)
//			text = new Texture(Gdx.files.internal("f.png"));
//		else
//			text = new Texture(Gdx.files.internal(weight + "t.png"));
//	}
	
//	public Tile(int w, Tile t, int x, int y)
//	{
//		weight = w;
//		teleport = Optional.of(t);
//		rectangle = new Rectangle(x, y, width, width);
//	}
	
	public Vector2 getCenter()
	{
		return new Vector2(rectangle.x + 5, rectangle.y + 5);
	}
	
	public int getWeight()
	{
		return weight;
	}
	
	public void select()
	{
		selected = true;
	}
	public void deselect(){selected = false;}
	public boolean isSelected(){return selected;}
	
	public void draw(SpriteBatch batch)
	{
		batch.begin();
		batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		font.draw(batch,text,rectangle.x+3,rectangle.y + rectangle.height/2 + font.getXHeight()/2,width - 6, Align.center,true);
		//batch.draw(text, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		batch.end();
	}
}
