import rebel.graphics.Renderer2D;
import rebel.graphics.Window;

public interface Screen {
    void update(Window window, Renderer2D renderer2D, float time);
}
