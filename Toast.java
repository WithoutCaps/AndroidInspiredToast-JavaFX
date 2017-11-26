package sample;

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
    private static Tooltip toast;
    private static Label label;
    private static PauseTransition timer;
    private static Stage stage;

    private static void JToast(String content) {
        toast = new Tooltip();

        label = new Label(content);
        label.setStyle("-fx-text-fill:#FFF ; -fx-font: 17px Tahoma;");

        HBox rootLayout = new HBox();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setSpacing(10);
        rootLayout.getChildren().addAll(label);

        toast.setGraphic(new Group(rootLayout));
        toast.show(stage, 0, 0);
        toast.centerOnScreen();

        timer = hoverSupport(toast, rootLayout);
    }

    private static PauseTransition hoverSupport(Window window, Region hoverRegion) {
        SimpleBooleanProperty HoveProperty = new SimpleBooleanProperty(false);
        hoverRegion.setOnMouseEntered(v -> HoveProperty.set(true));
        hoverRegion.setOnMouseExited(v -> HoveProperty.set(false));

        PauseTransition hoverTimer = new PauseTransition(DURATION);
        hoverTimer.setOnFinished((e) -> {
            if (HoveProperty.get())
                timer.play();
            else
                window.hide();
        });
        hoverTimer.play();
        HoveProperty.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> hoverTimer.playFromStart());
        return hoverTimer;
    }

    public static void makeText(Stage stage, String txt) {
        Toast.stage = stage;
        if (toast != null) {
            label.setText(txt);

            if (toast.isShowing()) {
                timer.playFromStart();
            } else {
                toast.show(Toast.stage);
                timer.play();
            }
        } else
            JToast(txt);
    }
}
