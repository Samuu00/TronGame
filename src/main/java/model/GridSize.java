package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("grid_size")
public class GridSize {
    @Param(0) private int width;
    @Param(1) private int height;

    public GridSize() {}
    public GridSize(int width, int height) { this.width = width; this.height = height; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
}