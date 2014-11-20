package com.andoowhy.flipmatch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FlipMatch extends Game
{
	//Screen Size
	public final int screenWidth = 480;
	public final int screenHeight = 800;

	//Fonts
	public BitmapFont fontReg32;
	public BitmapFont fontBlk100;

	//Textures
	public Texture unflippedFace;
	public Texture flippedFace;

	//Screens
	public StartScreen startScreen;
	public ControlsScreen controlsScreen;
	public ControlsScreen2 controlsScreen2;

	public SpriteBatch batch;
	
	@Override
	public void create()
	{
		//Fonts
		fontReg32 = generateFont( "Brandon_reg.otf", 32 );
		fontReg32.setColor( 0.2f, 0.2f, 0.2f, 1.0f );

		fontBlk100 = generateFont( "Brandon_blk.otf", 100 );
		fontBlk100.setColor( 0.2f, 0.2f, 0.2f, 1.0f );

		//Init Screens
		startScreen = new StartScreen( this );
		controlsScreen = new ControlsScreen( this );
		controlsScreen2 = new ControlsScreen2( this );

		//Init Textures
		unflippedFace = new Texture( Gdx.files.internal("Unflipped.png") );
		flippedFace = new Texture( Gdx.files.internal("flipped.png") );

		//Batch
		batch = new SpriteBatch();

		this.setScreen( startScreen );


	}

	private BitmapFont generateFont( String assetName, int fontSize )
	{
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal( assetName ));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = fontSize;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}

	public void drawFontFromCenterRelative( SpriteBatch batch
		,BitmapFont font
		,String str
		,float w
		,float h )
	{
		BitmapFont.TextBounds bounds = font.getBounds( str );
		float x = this.screenWidth * w - bounds.width / 2;
		float y = this.screenHeight * h + bounds.height / 2;
		font.draw( batch, str, x, y );
	}

	public void drawMultiLineFontFromCenterRelative( SpriteBatch batch
			,BitmapFont font
			,String str
			,float w
			,float h )
	{
		BitmapFont.TextBounds bounds = font.getBounds( str );
		float x = this.screenWidth * w - bounds.width / 2;
		float y = this.screenHeight * h + bounds.height / 2;
		font.drawMultiLine( batch, str, x, y );
	}

	public void drawMultiLineFontFromCenterRelative( SpriteBatch batch
			,BitmapFont font
			,String str
			,float w
			,float h
			,BitmapFont.HAlignment alignment)
	{
		BitmapFont.TextBounds bounds = font.getBounds( str );
		float x = this.screenWidth * w - bounds.width / 2;
		float y = this.screenHeight * h + bounds.height / 2;
		font.drawMultiLine( batch, str, x, y, bounds.width, alignment );
	}

	@Override
	public void render()
	{
		super.render(); //important!
	}


	@Override
	public void dispose()
	{
		batch.dispose();
		fontBlk100.dispose();
		fontReg32.dispose();
	}


}
