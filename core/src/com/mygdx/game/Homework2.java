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
import com.badlogic.gdx.math.Path;
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
	Grid grid;
	SpriteBatch batch;
	Node start,end;
	Pathfinder pathfinder;
	Array<Node> path = new Array<>();
	NodeDrawer drawer;
	
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
		
		Scanner scanner = null;
		
		scanner = new Scanner(Gdx.files.internal("input.txt").reader());
		
		Array<String> lines = new Array<>();
		while(scanner.hasNext())
		{
			lines.add(scanner.nextLine());
		}
		Node next = null;
		for(int i=0;i<lines.size;i++)
		{
			System.out.println("LINE: "+i);
			String[] strs = lines.get(i).split(" ");
			for(int j=0;j<strs.length;j++)
			{
				if(next !=null)
					next = next.add(new Node(strs[j],new Coord(j,i)));
				else
					next = grid.add(new Node(strs[j],new Coord(j,i)));
			}
		}
		grid.connectTeleporters();
		drawer = new NodeDrawer(50,batch);

		pathfinder = new Pathfinder(grid);
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isKeyJustPressed(Input.Keys.X))
		{
			grid.forEach(Node::deselect);
			start = null;
			end = null;
			path = null;
		}
		
		if(Gdx.input.justTouched())
		{
			Vector3 unprojected = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
			int x = (int) (unprojected.y/drawer.side());
			int y = (int) (unprojected.x/drawer.side());
			
			System.out.println("X: "+x+" Y: "+y);
			Node touched = grid.getNode(x,y);
			if(touched != null)
			{
				//TODO select node
			}
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
