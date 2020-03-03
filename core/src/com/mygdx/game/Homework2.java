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
import java.util.Scanner;

/*
NOTES
TRY A* but with multiple outputs where 1 is direct to the goal and the others are routed to each teleport unless it is already longer to the teleport than goal
 */
public class Homework2 extends ApplicationAdapter
{
	OrthographicCamera camera;
	KeyProcessor inputProcessor;
	PathDrawer path;
	Array<Array<Tile>> tiles;
	SpriteBatch batch;
	int teleporterNum;
	Tile start,end;
	
	@Override
	public void create()
	{
		start =null;
		end = null;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1920,1080);
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
		camera.translate(0,-(camera.viewportHeight-Tile.width));
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(Gdx.input.isTouched())
		{
			Vector3 unprojected = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
			int x = (int) (unprojected.x/Tile.width);
			int y = (int) (-unprojected.y/Tile.width);
			
			if(tiles.size<y && tiles.get(y).size < x)
			{
				Tile t = tiles.get(y).get(x);
				if(t.isSelected())
				{
					t.deselect();
					if(start == t)
						start = null;
					if(end == t)
						end =null;
				}
				else if(start == null)
					{start = t;t.select();}
				else if(end == null)
					{end = t;t.select();}
			}
		}
		
		//TODO path find
		if(start!= null && end !=null)
		{
		
		}

		batch.setProjectionMatrix(camera.combined);
		
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
