package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.sun.org.apache.xpath.internal.operations.Or;

public class MyCameraController extends CameraInputController
{
	
	public MyCameraController(OrthographicCamera cam)
	{
		super(cam);
	}
	@Override
	public boolean scrolled(int amount)
	{
		OrthographicCamera c = (OrthographicCamera) camera;
		
		return true;
	}
}
