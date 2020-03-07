package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class NodeDrawer
{
	private float side;
	public static final BitmapFont font = new BitmapFont(true);
	public static final Texture texture = new Texture(Gdx.files.internal("tile.png"));
	public SpriteBatch batch;
	public NodeDrawer(float side,SpriteBatch batch)
	{
		this.side =side;
		this.batch = batch;
	}
	public float side()
	{
		return side;
	}
	public void drawGrid(Grid grid)
	{
		grid.forEach(p -> drawNode(p));
	}
	public void drawNode(Node n)
	{
		float x = n.getCoord().x*side;
		float y = n.getCoord().y*side;
		
		batch.begin();
		if(n.isSelected())
			batch.setColor(Color.BLUE.r,Color.BLUE.g,Color.BLUE.b,.5f);
		else
			batch.setColor(1,1,1,1);
		batch.draw(texture,x,y,side,side);
		if(n.getTele()!=null)
			font.draw(batch,"t"+n.getTeleNum(),x+3,y+(side/2),side - 6, Align.center,false);
		else
			font.draw(batch,String.valueOf(n.getWeight()),x+3,y+(side/2),side - 6, Align.center,false);
		batch.end();
	}
}
