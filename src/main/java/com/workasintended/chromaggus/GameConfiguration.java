package com.workasintended.chromaggus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by mazimeng on 1/15/16.
 */
public abstract class GameConfiguration {
    private Skin skin;

    public abstract EventListener makeInputListener(WorldStage worldStage, Player player);
    public abstract Viewport makeWorldViewport();
    public abstract Viewport makeGuiViewport();

    public Skin makeSkin() {
        if(skin !=null) return skin;

        skin = Service.assetManager().get("uiskin.json");
        return skin;

//        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
//        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
//        skin = new Skin();
//
//        // Generate a 1x1 white texture and store it in the skin named "white".
//        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
//        pixmap.setColor(Color.WHITE);
//        pixmap.fill();
//        skin.add("white", new Texture(pixmap));
//
//        // Store the default libgdx font under the name "default".
//        skin.add("default", new BitmapFont());
//
//        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
//        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
//        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
//        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
//        textButtonStyle.font = skin.getFont("default");
//        skin.add("default", textButtonStyle);
//
//        Label.LabelStyle labelStyle = new Label.LabelStyle();
//        labelStyle.font = skin.getFont("default");
//        skin.add("default", labelStyle);
//
//        com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle listStyle = new com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle();
//        listStyle.background = skin.newDrawable("white", Color.DARK_GRAY);
//        listStyle.font = skin.getFont("default");
//        listStyle.fontColorSelected = Color.GREEN;
//        listStyle.fontColorUnselected = Color.GRAY;
//
//        return skin;
    }

    protected OrthographicCamera makeCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        OrthographicCamera cam = new OrthographicCamera(w, h);
        cam.update();
        return cam;
    }
}
