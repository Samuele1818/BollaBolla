package controller;

import model.Model;
import model.files.FileManager;
import view.View;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MainController {
    private static MainController instance = null;

    private final ProfileController profileController;
    private MenuController controllerListener;
    private GameController gameController;
    private ResultController resultController;

    private LeaderboardController leaderboardController;

    private View view;
    private Model model;

    private MainController() {


        model = Model.getInstance();

        view = View.getInstance();

        profileController = ProfileController.getInstance();

        controllerListener = MenuController.getInstance();

        gameController = GameController.getInstance();

        resultController = ResultController.getInstance();

        leaderboardController = LeaderboardController.getInstance();
    }

    public static MainController getInstance() {
        if (instance == null) instance = new MainController();
        return instance;
    }

    public void init() {
        view.init(model.getGlobalTheme(), model.getPlayer());
        view.getMapPanel().init(model.getLevel());
        view.getLeaderboardPanel().init(model.getLeaderboard());

        controllerListener.init();
        profileController.init();
        gameController.init();
        resultController.init();
        leaderboardController.init();

        view.getMapPanel().addKeyListener(gameController);
        cursorListener();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public MenuController getControllerListener() {
        return controllerListener;
    }

    public void setControllerListener(MenuController controllerListener) {
        this.controllerListener = controllerListener;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private void cursorListener() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image[] cursorFrames = new Image[7];

        cursorFrames[0] = toolkit.getImage(FileManager.getResource("static_image", "bolla", "Bolla3.png"));
        cursorFrames[1] = toolkit.getImage(FileManager.getResource("static_image", "bolla", "Bolla4.png"));
        cursorFrames[2] = toolkit.getImage(FileManager.getResource("static_image", "bolla", "Bolla5.png"));
        cursorFrames[3] = toolkit.getImage(FileManager.getResource("static_image", "bolla", "Bolla6.png"));
        cursorFrames[4] = toolkit.getImage(FileManager.getResource("static_image", "bolla", "Bolla5.png"));
        cursorFrames[5] = toolkit.getImage(FileManager.getResource("static_image", "bolla", "Bolla4.png"));
        cursorFrames[6] = toolkit.getImage(FileManager.getResource("static_image", "bolla", "Bolla3.png"));

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
