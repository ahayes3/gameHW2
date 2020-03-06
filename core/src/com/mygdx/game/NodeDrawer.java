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
	private static final BitmapFont font = new BitmapFont(true);
	private static final Texture texture = new Texture(Gdx.files.internal("tile.png"));
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
	public void draw(Grid grid)
	{
		grid.forEach(this::draw);
	}
	public void draw(Node n)
	{
		batch.begin();
		if(n.isSelected())
			batch.setColor(Color.BLUE.r,Color.BLUE.g,Color.BLUE.b,.5f);
		else
			batch.setColor(1,1,1,1);
		batch.draw(texture,n.getCoord().x*side,n.getCoord().y*side,side,side);
		if(n.getTele()!=null)
			font.draw(batch,"t"+n.getTeleNum(),n.getCoord().x+(side/2),n.getCoord().y+(side/2),side - 6, Align.center,false);
		else
			font.draw(batch,""+n.getWeight(),n.getCoord().x+(side/2),n.getCoord().y+(side/2),side - 6, Align.center,false);
		batch.end();
	}
}
