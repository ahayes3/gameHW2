package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class MyCameraController extends CameraInputController
{
	public MyCameraController(OrthographicCamera cam)
	{
		super(cam);
	}
	@Override
	public boolean scrolled(int amount)
	{
		((OrthographicCamera)camera).zoom += (amount* Gdx.graphics.getDeltaTime());
		return true;
	}
}
