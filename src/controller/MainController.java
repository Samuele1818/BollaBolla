package controller;

import model.Model;
import model.utils.FileManager;
import view.View;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MainController {
    private static MainController instance = null;

    private ProfileController profileController;
    private GameController gameController;
    private ResultController resultController;
    private PauseController pauseController;
    private LeaderboardController leaderboardController;
    private MenuController controllerListener;
    private View view;
    private Model model;

    /**
     * MainController constructor
     * Initialize every controller, view and model
     */
    private MainController() {
        model = Model.getInstance();
        view = View.getInstance();
        profileController = ProfileController.getInstance();
        controllerListener = MenuController.getInstance();
        gameController = GameController.getInstance();
        resultController = ResultController.getInstance();
        leaderboardController = LeaderboardController.getInstance();
        pauseController = PauseController.getInstance();
    }

    /**
     * Get MainController instance
     *
     * @return MainController instance
     */
    public static MainController getInstance() {
        if (instance == null) instance = new MainController();
        return instance;
    }

    /**
     * Register observers and init every controller
     * Add cursor listener (custom cursor)
     */
    public void init() {
        // Register observers
        view.init(model.getGlobalTheme());

        view.getProfilePanel().init(model.getPlayer());

        view.getMapPanel().init(model.getLevel());

        view.getLeaderboardPanel().init(model.getLeaderboard());

        // Add observer to GameRecordsPanel
        view.getProfilePanel().getGameRecordsPanel().init(model.getPlayer().getRegister());

        // Init controllers
        controllerListener.init();
        profileController.init();
        gameController.init();
        resultController.init();
        leaderboardController.init();
        pauseController.init();

        // Add custom cursor
        cursorListener();
    }

    /**
     * Get View instance
     *
     * @return View instance
     */
    public View getView() {
        return view;
    }

    /**
     * Get Model instance
     *
     * @return Model instance
     */
    public Model getModel() {
        return model;
    }

    /**
     * Replace cursor with an animated bubble that decrease and increase in sizes
     */
    private void cursorListener() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image[] cursorFrames = new Image[7];

        cursorFrames[0] = toolkit.getImage(FileManager.getResource("static_image", "bubble", "bubble3.png"));
        cursorFrames[1] = toolkit.getImage(FileManager.getResource("static_image", "bubble", "bubble4.png"));
        cursorFrames[2] = toolkit.getImage(FileManager.getResource("static_image", "bubble", "bubble5.png"));
        cursorFrames[3] = toolkit.getImage(FileManager.getResource("static_image", "bubble", "bubble6.png"));
        cursorFrames[4] = toolkit.getImage(FileManager.getResource("static_image", "bubble", "bubble5.png"));
        cursorFrames[5] = toolkit.getImage(FileManager.getResource("static_image", "bubble", "bubble4.png"));
        cursorFrames[6] = toolkit.getImage(FileManager.getResource("static_image", "bubble", "bubble3.png"));

        Point point = new Point(9, 9);

        MainController.getInstance().getView().addMouseMotionListener(new MouseMotionAdapter() {
            int frameIndex = 0;

            @Override
            public void mouseMoved(MouseEvent e) {
                Cursor customCursor = toolkit.createCustomCursor(cursorFrames[frameIndex], point, "Animated Cursor");
                MainController.getInstance().getView().setCursor(customCursor);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                frameIndex = (frameIndex + 1) % cursorFrames.length;
            }
        });
    }

}
