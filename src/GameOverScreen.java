import org.joml.Vector2f;
import rebel.Math;
import rebel.Time;
import rebel.graphics.*;

import java.util.List;

public class GameOverScreen implements Screen {

    public FontRes fontRes;
    public String gameOverText = "Game over! :(";
    private boolean showRestartText = false;
    private float opacity = 1;
    private List<Line> lines;

    private float animWidth = 1;

    public GameOverScreen(){
        fontRes = new FontRes("Arial", FontRes.BOLD, 25, true);
        CubicBezierCurve cubicBezierCurve = new CubicBezierCurve(
                new Vector2f(0, 350),
                new Vector2f(200, 150),
                new Vector2f(500 + 350, 650),
                new Vector2f(500 + 650, 350)
        );
        lines = cubicBezierCurve.calculate(20);
    }

    @Override
    public void update(Window window, Renderer2D renderer2D, float time) {
        renderer2D.drawFilledRect(0, 0, renderer2D.getWidth(), renderer2D.getHeight(), Color.BLACK);

        for(Line line : lines){
            renderer2D.drawLine(line.start.x, line.start.y, line.end.x, line.end.y, Color.WHITE, 3, true);
        }

        animWidth = Math.clamp(animWidth - 0.3f * Time.deltaTime, 0, 1);
        renderer2D.drawFilledRect(0, 0, renderer2D.getWidth() * animWidth, renderer2D.getHeight(), Color.BLACK);





        Color textColor = new Color(1f, 1f, 1f, opacity);

        float w = fontRes.getWidthOf(gameOverText);
        float h = fontRes.getHeightOf(gameOverText);

        float textX = renderer2D.getWidth() / 2f - w / 2;
        float textY = renderer2D.getHeight() / 2f - h / 2;

        renderer2D.drawText(textX, textY, gameOverText, textColor, fontRes);

        if(!showRestartText)
            opacity = Math.clamp(opacity - 0.65f * Time.deltaTime, 0, 1);

        if(opacity == 0){
            gameOverText = "Press space to restart";
            opacity = 1f;
            showRestartText = true;
        }




    }

}
