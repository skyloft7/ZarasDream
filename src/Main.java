import rebel.Input;
import rebel.Time;
import rebel.graphics.Renderer2D;
import rebel.graphics.Window;

public class Main {

    public enum GameState {
        INTRO,
        PLAYING,
        GAME_OVER,
        GAME_WIN
    }

    public static void main(String[] args) {
        Window window = new Window(1000, 600, "Zara's Dream");
        Renderer2D renderer2D = new Renderer2D(1000, 600);




        GameState state = GameState.INTRO;
        float elapsedTime = 0;

        Screen screen = new Intro();

        while(!window.shouldClose()){
            renderer2D.clear(1f, 1f, 1f, 1f);
            elapsedTime += Time.deltaTime * 1000;

            screen.update(window, renderer2D, elapsedTime);

            if(state == GameState.INTRO) {
                if(elapsedTime >= 4000 || window.isKeyPressed(Input.REBEL_KEY_SPACE)) {
                    screen = new GamePlayScreen(renderer2D);
                    state = GameState.PLAYING;
                }
            }
            if(state == GameState.PLAYING){
                if(GameManager.getRemainingChances() <= 0){
                    screen = new GameOverScreen();
                    state = GameState.GAME_OVER;
                    GameManager.tileKillCount = 0;
                    GameManager.fails = 0;
                }
                else if(GameManager.tileKillCount == GameManager.tileNums){
                    screen = new GameWinScreen();
                    state = GameState.GAME_WIN;
                    GameManager.tileKillCount = 0;
                    GameManager.fails = 0;
                }

            }
            if(state == GameState.GAME_OVER){
                if(window.isKeyPressed(Input.REBEL_KEY_SPACE)){
                    screen = new GamePlayScreen(renderer2D);
                    state = GameState.PLAYING;
                }
            }
            if(state == GameState.GAME_WIN){
                if(window.isKeyPressed(Input.REBEL_KEY_SPACE)){
                    screen = new GamePlayScreen(renderer2D);
                    state = GameState.PLAYING;
                }
            }

            renderer2D.render();
            window.update();
        }


        window.close();


    }
}