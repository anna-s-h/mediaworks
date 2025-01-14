package io.github.artynova.mediaworks.logic.macula;

import io.github.artynova.mediaworks.api.logic.macula.Visage;
import net.minecraft.util.math.Vec3i;

/**
 * Wrapper around a {@link Visage} implementation, containing data that is used for display of any visage.
 * Because of the nature of visage deserialization, this object does not contain specific class information about the visage.
 */
public class VisageEntry {
    public static final long FADE_TICKS = 20;
    private final Visage visage;
    private final Vec3i origin;
    private final long startTime;
    private final long endTime;

    public VisageEntry(Visage visage, Vec3i origin, long startTime, long endTime) {
        if (startTime > endTime)
            throw new IllegalArgumentException("Malformed visage lifetime, with start at " + startTime + " and end at " + endTime);
        if (endTime < 0 && endTime != -1)
            throw new IllegalArgumentException("End time of a VisageEntry must be a natural number, or -1 if the entry is not fleeting");
        this.visage = visage;
        this.origin = origin;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Vec3i getOrigin() {
        return origin;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean doFadeout() {
        return endTime - startTime > FADE_TICKS;
    }

    /**
     * @param currentTime Current world age.
     * @return true if the entry is fleeting and the world age is greater than the entry timeout point.
     */
    public boolean hasTimedOut(long currentTime) {
        return endTime > -1 && endTime <= currentTime;
    }

    public Visage getVisage() {
        return visage;
    }
}
