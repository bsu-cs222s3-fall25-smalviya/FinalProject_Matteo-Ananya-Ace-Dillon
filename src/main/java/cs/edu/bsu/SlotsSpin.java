package cs.edu.bsu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SlotsSpin {
    private final ImageView[] slots;
    private final List<Image> allImages;
    private final Random random = new Random();
    private Timeline timeline;

    public SlotsSpin(Map<String, Image> imageCache, ImageView... slots) {
        this.slots = slots;
        this.allImages = new ArrayList<>(imageCache.values());
    }

    public void spin(Duration spinDuration, Runnable onFinished) {
        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline();

        double stepMs = 100;
        int steps = (int) (spinDuration.toMillis() / stepMs);

        for (int i = 0; i <= steps; i++) {
            Duration t = Duration.millis(i * stepMs);

            timeline.getKeyFrames().add(new KeyFrame(t, e -> {
                for (ImageView slot : slots) {
                    slot.setImage(getRandomSymbol());
                }
            }));
        }

        timeline.setOnFinished(e -> {
            if (onFinished != null) {
                onFinished.run();
            }
        });

        timeline.playFromStart();
    }

    private Image getRandomSymbol() {
        return allImages.get(random.nextInt(allImages.size()));
    }
}