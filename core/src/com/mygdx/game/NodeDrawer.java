package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

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
	public void drawGrid(Grid grid, Array<Coord> path)
	{
		grid.forEach(p -> drawNode(p,path));
	}
	public void drawNode(Node n,Array<Coord> path)
	{
		float x = n.getCoord().x*side;
		float y = n.getCoord().y*side;
		
		batch.begin();
		if(n.isSelected())
			batch.setColor(Color.BLUE.r,Color.BLUE.g,Color.BLUE.b,1);
		else if(path.contains(n.getCoord(),false))
			batch.setColor(Color.PINK.r,Color.PINK.g,Color.PINK.b,1);
		else
			batch.setColor(1,1,1,1);
		batch.draw(texture,x,y,side,side);
		if(n.getTele()!=null)
			font.draw(batch,"t"+n.getTeleNum(),x+3,y+(side/2),side - 6, Align.center,false);
		else
			font.draw(batch,String.valueOf(n.getWeight()),x+3,y+(side/2)-font.getXHeight()/2,side - 6, Align.center,false);
		batch.end();
	}
}