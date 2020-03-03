package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class KeyProcessor implements InputProcessor
{
	public boolean aPressed,sPressed,wPressed,dPressed;
	private OrthographicCamera camera;
	public KeyProcessor(OrthographicCamera camera)
	{
		this.camera = camera;
	}
	public void moveCamera()
	{
		if(aPressed)
		{
			camera.translate(-500* Gdx.graphics.getDeltaTime(),0);
		}
		if(sPressed)
		{
			camera.translate(0,-500* Gdx.graphics.getDeltaTime());
		}
		if(dPressed)
		{
			camera.translate(500* Gdx.graphics.getDeltaTime(),0);
		}
		if(wPressed)
		{
			camera.translate(0,500* Gdx.graphics.getDeltaTime());
		}
	}
	@Override
	public boolean keyDown(int keycode)
	{
		if(keycode == Input.Keys.A)
			aPressed = true;
		if(keycode == Input.Keys.S)
			sPressed = true;
		if(keycode == Input.Keys.D)
			dPressed = true;
		if(keycode == Input.Keys.W)
			wPressed = true;
		
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode)
	{
		if(keycode == Input.Keys.A)
			aPressed = false;
		if(keycode == Input.Keys.S)
			sPressed = false;
		if(keycode == Input.Keys.D)
			dPressed = false;
		if(keycode == Input.Keys.W)
			wPressed = false;
		
		return false;
	}
	
	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}
	
	@Override
	public boolean scrolled(int amount)
	{
		camera.zoom += amount*2*Gdx.graphics.getDeltaTime();
		if(camera.zoom <0.05)
			camera.zoom = 0.05f;
		return false;
	}
}
