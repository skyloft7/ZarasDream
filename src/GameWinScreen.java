import rebel.graphics.Color;
import rebel.graphics.FontRes;
import rebel.graphics.Renderer2D;
import rebel.graphics.Window;

public class GameWinScreen implements Screen {

    public FontRes fontRes;
    public String gameOverText = "You won! Thanks for playing!\nMade using the Rebel game engine\nhttps://github.com/Northern-Lights-Games/Rebel";

    public GameWinScreen(){
        fontRes = new FontRes("Arial", FontRes.BOLD, 25, true);
    }

    @Override
    public void update(Window window, Renderer2D renderer2D, float time) {
        renderer2D.drawFilledRect(0, 0, renderer2D.getWidth(), renderer2D.getHeight(), Color.BLACK);

        float w = fontRes.getWidthOf(gameOverText);
        float h = fontRes.getHeightOf(gameOverText);

        float textX = renderer2D.getWidth() / 2f - w / 2;
        float textY = renderer2D.getHeight() / 2f - h / 2;

        renderer2D.drawText(textX, textY, gameOverText, Color.WHITE, fontRes);

    }

}
