package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.Field;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameRenderer implements Disposable {

    private static final String LEVEL_MAP = "level.tmx";
    private static final String TANK_TEXTURE = "images/tank_blue.png";
    private static final String TREE_TEXTURE = "images/greenTree.png";

    private final Batch batch;
    private final TiledMap tiledMap;
    private final MapRenderer mapRenderer;
    private final TileMovement tileMovement;
    private final TankGraphics tankGraphics;
    private final List<TreeGraphics> treeGraphics = new ArrayList<TreeGraphics>();

    public GameRenderer(Tank tank, Field field) {
        this.batch = new SpriteBatch();
        this.tiledMap = new TmxMapLoader().load(LEVEL_MAP);
        this.mapRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        this.tankGraphics = new TankGraphics(tank, TANK_TEXTURE);
        for (Tree tree : field.getTrees()) {
            treeGraphics.add(new TreeGraphics(tree, TREE_TEXTURE, groundLayer));
        }
    }

    public void updateTankPosition() {
        tankGraphics.update(tileMovement);
    }

    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        mapRenderer.render();
        batch.begin();
        tankGraphics.draw(batch);
        for (TreeGraphics graphics : treeGraphics) {
            graphics.draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        tankGraphics.dispose();
        for (TreeGraphics graphics : treeGraphics) {
            graphics.dispose();
        }
        tiledMap.dispose();
        batch.dispose();
    }
}
