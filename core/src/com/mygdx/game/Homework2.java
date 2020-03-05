package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

/*
NOTES
TRY A* but with multiple outputs where 1 is direct to the goal and the others are routed to each teleport unless it is already longer to the teleport than goal
 */
/*
Instructions:
wasd to pan camera
scroll to zoom
click start and end tiles to calculate path
x to reset
 */
public class Homework2 extends ApplicationAdapter
{
	OrthographicCamera camera;
	KeyProcessor inputProcessor;
	PathDrawer path;
	Array<Array<Tile>> tiles;
	Pathfinder pathFinder;
	SpriteBatch batch;
	int teleporterNum;
	Coord start,end;
	
	@Override
	public void create()
	{
		start =null;
		end = null;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(true,1920,1080);
		inputProcessor = new KeyProcessor(camera);
		Gdx.input.setInputProcessor(inputProcessor);
		path = new PathDrawer(3);
		teleporterNum=0;
		Scanner s = null;
		
		System.out.println(Gdx.files.internal("input.txt").exists());
		s = new Scanner(Gdx.files.internal("input.txt").reader());
		
		Array<String> lines = new Array<>();
		while(s.hasNext())
		{
			lines.add(s.nextLine());
		}
		int strAmt =0;
		for(int i=0;i<lines.get(0).length();i++)
		{
			if(lines.get(0).charAt(i) == ' ')
				strAmt++;
		}
		Array<Array<String>> inputs = new Array<>();
		for(int i=0;i<lines.size;i++)
		{
			inputs.add(new Array<String>(lines.get(i).split(" ")));
		}
		
		int xTiles = inputs.size;
		int yTiles = inputs.get(0).size;
		tiles = new Array<>(xTiles);
		for(int i=0;i<xTiles;i++)
		{
			tiles.add(new Array<Tile>());
			for(int j=0;j<yTiles;j++)
			{
				tiles.get(i).add(new Tile(i,j,inputs.get(i).get(j)));
			}
		}
		pathFinder = new Pathfinder(tiles);
		
		Array<Tile> teleports = new Array<>();
		for(Array<Tile> a:tiles)
		{
			for(Tile t:a)
			{
				if(t.getTeleportNum() != -1)
				{
					boolean found = false;
					for(Iterator<Tile> iter =teleports.iterator();iter.hasNext();)
					{
						Tile i = iter.next();
						if(i.getTeleportNum() == t.getTeleportNum())
						{
							i.connect(t);
							t.connect(i);
							iter.remove();
							found = true;
						}
					}
					if(!found)
						teleports.add(t);
				}
			}
		}
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isKeyJustPressed(Input.Keys.X))
		{
			for(Array<Tile> a:tiles)
			{
				for(Tile t:a)
				{
					t.deselect();
				}
			}
			start = null;
			end = null;
			path = null;
		}
		
		if(Gdx.input.justTouched())
		{
			Vector3 unprojected = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
			int x = (int) (unprojected.y/Tile.width);
			int y = (int) (unprojected.x/Tile.width);
			
			System.out.println("X: "+x+" Y: "+y);
			
			if(tiles.size>x && tiles.get(x).size > y)
			{
				Tile t = tiles.get(x).get(y);
				System.out.println("Weight: "+t.getWeight());
				if(t.isSelected())
				{
					t.deselect();
					if(start != null && start.equals(t.getCoords()))
						start = null;
					if(end != null && end.equals(t.getCoords()))
						end =null;
				}
				else if(start == null)
					{
						for(int i = 0;i<tiles.size;i++)
						{
							if(tiles.get(i).indexOf(t,true)!=-1)
								start = new Coord(i,tiles.get(i).indexOf(t,true));
						}
						t.select();
					}
				else if(end == null)
					{
						for(int i = 0;i<tiles.size;i++)
						{
							if(tiles.get(i).indexOf(t,true)!=-1)
								end = new Coord(i,tiles.get(i).indexOf(t,true));
						}
						t.select();
					}
			}
		}
		
		if(start!= null && end !=null)
		{
			Pair<Array<Coord>,Integer> out = pathFinder.find(start,end);
			//TODO make it work with teleporters
			
			
//			for(Coord c:flipped)
//			{
//				path.add(new Coord(c.y,c.x));
//			}
			
			System.out.println(path);
			tiles.get(start.x).get(start.y).deselect();
			tiles.get(end.x).get(end.y).deselect();
			start = null;
			end = null;
		}

		batch.setProjectionMatrix(camera.combined);
		
		if(path !=null)
		{
			//TODO DRAW PATH
		}
		for(Array<Tile> a:tiles)
		{
			for(Tile t:a)
			{
				t.draw(batch);
			}
		}
		inputProcessor.moveCamera();
		camera.update();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		Tile.texture.dispose();
	}
}
