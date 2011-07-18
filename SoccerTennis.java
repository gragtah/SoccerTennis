//package com.bearisdriving.st;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SoccerTennis extends BasicGame {
	private Image stage;
	private Player p1;
	public static final int gravity = 1;
	private static final int WIN_WIDTH = 1200;
	private static final int WIN_HEIGHT = 600;

	private void pollInput(GameContainer gc) {
		Input input = gc.getInput();

		if ( input.isKeyDown( Input.KEY_Q ) ) {
			gc.exit();
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		stage = new Image("stage0.png").getScaledCopy( winWidth, winHeight );
		p1 = new Player();

		gc.setVSync( true ); //setTargetFrameRate( 60 );
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		pollInput( gc );

		p1.pollInput( gc );
		p1.update( delta );
	}

	public SoccerTennis() {
		super("Untitled ST");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		stage.draw( 0, 0 );
		p1.draw();
	}

	public static void main (String[] argv) throws SlickException {
		AppGameContainer app = new AppGameContainer( new SoccerTennis() );
		 
		app.setDisplayMode( WIN_WIDTH, WIN_HEIGHT, false );
		app.start();
	}
}
