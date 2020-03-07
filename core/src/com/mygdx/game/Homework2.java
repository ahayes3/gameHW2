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
	Array<Coord> path = new Array<>();
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
		grid = new Grid();
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
				if(touched.isSelected())
				{
					touched.deselect();
					if(touched == end)
						end = null;
					else if(touched == start)
						start = null;
				}
				else if(start == null)
				{
					touched.select();
					start = touched;
				}
				else if(end == null)
				{
					touched.select();
					end = touched;
				}
			}
		}
		
		if(start!= null && end !=null)
		{
			path = pathfinder.find(start,end);
			//TODO make it work with teleporters
			
			
//			for(Coord c:flipped)
//			{
//				path.add(new Coord(c.y,c.x));
//			}
			
			System.out.println(path);
			start.deselect();
			end.deselect();
			start = null;
			end = null;
		}

		batch.setProjectionMatrix(camera.combined);
		
		if(path !=null)
		{
			//TODO DRAW PATH
		}
		drawer.drawGrid(grid);
		inputProcessor.moveCamera();
		camera.update();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		NodeDrawer.texture.dispose();
		NodeDrawer.font.dispose();
	}
}
