package io.github.artynova.mediaworks.client.macula;

import io.github.artynova.mediaworks.macula.Visage;
import io.github.artynova.mediaworks.macula.VisageType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;

import java.util.Map;

public class VisageRendererLoader implements SynchronousResourceReloader {
    private static final VisageRendererLoader INSTANCE = new VisageRendererLoader();
    private static Map<VisageType<?>, VisageRenderer<?>> visageRenderers;

    private VisageRendererLoader() {
    }

    @Override
    public void reload(ResourceManager manager) {
        visageRenderers = VisageRenderers.loadVisageRendererMap();
    }

    public static VisageRendererLoader getInstance() {
        return INSTANCE;
    }

    public static <T extends Visage> VisageRenderer<T> getRenderer(T visage) {
        return (VisageRenderer<T>) visageRenderers.get(visage.getType());
    }
}