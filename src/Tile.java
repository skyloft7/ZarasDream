import rebel.graphics.Rect2D;
import rebel.graphics.Renderer2D;
import rebel.graphics.Texture;

import java.util.ArrayList;

public class Tile {
    public Rect2D rect2D;
    public Texture texture;
    public Tile(Rect2D rect2D, Texture texture){
        this.rect2D = rect2D;
        this.texture = texture;
    }
    public static ArrayList<Tile> createTiles(int n, Renderer2D renderer2D, Texture logo){
        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Rect2D rect2D = new Rect2D((float) (
                    renderer2D.getWidth() * Math.random()),
                    (float) ((renderer2D.getHeight() / 2) * Math.random()),
                    70,
                    70
            );

            tiles.add(new Tile(rect2D, logo));
        }

        return tiles;
    }
}
