package clases;

import java.awt.image.BufferedImage;

public class CharacterAnimationDetails {
    private String name;
    private BufferedImage image;
    private int frameCount, delay, currentFrame, posX, posY;

    public CharacterAnimationDetails(String name, BufferedImage image, int frameCount, int delay, int posX, int posY) {
        this.name = name;
        this.image = image;
        this.frameCount = frameCount;
        this.delay = delay;
        this.posX = posX;
        this.posY = posY;
        this.currentFrame = 0;
    }
    public BufferedImage updateFrame() {
        this.currentFrame = (this.currentFrame + 1) % this.frameCount;
        return this.image.getSubimage(currentFrame*posX, 0, posX, posY);
    }
}
