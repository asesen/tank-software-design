package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class LevelGraphics implements Disposable {

    private final TiledMap tiledMap;
    private final MapRenderer mapRenderer;
    private final TileMovement tileMovement;

    public LevelGraphics(String mapPath, Batch batch) {
        this.tiledMap = new TmxMapLoader().load(mapPath);
        this.mapRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public TileMovement getTileMovement() {
        return tileMovement;
    }

    public void render() {
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
    }
}
