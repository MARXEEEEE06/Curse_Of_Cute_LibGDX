package com.javal.cute;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Forest {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public Forest(){
        //TmxMapLoader loader = new TmxMapLoader();
        map = new TmxMapLoader().load("maps/forest.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 5f);
    }

    public void render(OrthographicCamera camera){
        renderer.setView(camera);
        renderer.render();
    }

    public void dispose(){
        map.dispose();
        renderer.dispose();
    };
}
