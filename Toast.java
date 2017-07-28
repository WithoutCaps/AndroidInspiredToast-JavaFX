
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;


public class Toast {
    private static final Duration DURATION = Duration.millis(2000);
    private static Tooltip mToast;
    private static Label mLabel;
    private static PauseTransition mTimer;
    private static Stage mStage;

    public static Tooltip JToast(String content) {
        mToast = new Tooltip();

        mLabel = new Label(content);
        mLabel.setStyle("-fx-text-fill:#FFF ; -fx-font: 17px Tahoma;");

        HBox rootLayout = new HBox();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setSpacing(10);
        rootLayout.getChildren().addAll(mLabel);

        mToast.setGraphic(new Group(rootLayout));
        mToast.show(mStage, 0, 0);
        mToast.centerOnScreen();

        mTimer = hoverSupport(mToast, rootLayout);
        return mToast;
    }

    private static PauseTransition hoverSupport(Window window, Region hoverRegion) {
        SimpleBooleanProperty HoveProperty = new SimpleBooleanProperty(false);
        hoverRegion.setOnMouseEntered(v -> HoveProperty.set(true));
        hoverRegion.setOnMouseExited(v -> HoveProperty.set(false));

        PauseTransition hoverTimer = new PauseTransition(DURATION);
        hoverTimer.setOnFinished((e) -> {
            if (HoveProperty.get())
                mTimer.play();
            else
                window.hide();
        });
        hoverTimer.play();
        HoveProperty.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> hoverTimer.playFromStart());
        return hoverTimer;
    }

    public static void makeText(Stage stage, String txt) {
        mStage = stage;
        if (mToast != null) {
            mLabel.setText(txt);

            if (mToast.isShowing()) {
                mTimer.playFromStart();
            } else {
                mToast.show(mStage);
                mTimer.play();
            }
        } else
            JToast(txt);
    }
}