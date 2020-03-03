package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
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

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1920,1080);
		inputProcessor = new KeyProcessor(camera);
		Gdx.input.setInputProcessor(inputProcessor);
		path = new PathDrawer(3);
		teleporterNum=0;

		int xTiles = 50;
		int yTiles = 50;
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
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(Gdx.input.isTouched())
		{
			Vector3 unprojected = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
			int x = (int) (unprojected.x/20);
			int y = (int) (unprojected.y/20);

			//TODO change tile to selected

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
