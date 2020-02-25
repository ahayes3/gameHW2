package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Homework2 extends ApplicationAdapter
{
	OrthographicCamera camera;
	CameraInputController cameraController;
	ShapeRenderer sr;
	PathDrawer path;
	Array<Array<Tile>> tiles;
	int teleporterNum;

	@Override
	public void create()
	{
		//batch = new SpriteBatch();
		sr = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(true,1920,1080);
		cameraController = new MyCameraController(camera);
		Gdx.input.setInputProcessor(cameraController);
		//Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
		//Gdx.graphics.setFullscreenMode(displayMode);
		path = new PathDrawer(3);
		teleporterNum=0;

		int xTiles = 10;
		int yTiles = 10;
		tiles = new Array<>(xTiles);
		for(int i=0;i<xTiles;i++)
		{
			tiles.add(new Array<Tile>());
			for(int j=0;j<yTiles;j++)
			{
				tiles.get(i).add(new Tile(i,j));
			}
		}

	}

	@Override
	public void render()
	{


		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		sr.setProjectionMatrix(camera.projection);
		for(Array<Tile> a:tiles)
		{
			for(Tile t:a)
			{
				t.draw(sr);
			}
		}
	}

	@Override
	public void dispose()
	{
		sr.dispose();
	}
}
