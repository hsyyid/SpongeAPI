/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.util;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.Validate;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.data.type.DyeColor;

import java.util.Objects;

public final class Color implements DataSerializable {

    private static final int MASK = 0xff;

    public static final Color BLACK = ofHex(0x000000);

    public static final Color GRAY = ofHex(0x808080);

    public static final Color WHITE = ofHex(0xFFFFFF);

    public static final Color BLUE = ofHex(0x0000FF);

    public static final Color GREEN = ofHex(0x008000);

    public static final Color LIME = ofHex(0x00FF00);

    public static final Color RED = ofHex(0xFF0000);

    public static final Color YELLOW = ofHex(0xFFFF00);

    public static final Color MAGENTA = ofHex(0xFF00FF);

    public static final Color PURPLE = ofHex(0xAA00FF);

    public static final Color DARK_CYAN = ofHex(0x008B8B);

    public static final Color DARK_GREEN = ofHex(0x006400);

    public static final Color DARK_MAGENTA = ofHex(0x8B008B);

    public static final Color NAVY = ofHex(0x000080);

    public static final Color UNKNOWN = ofHex(0xFF00AA);

    public static Color ofHex(int hex) {
        return ofRGB(hex >> 16 & MASK, hex >> 8 & MASK, hex & MASK);
    }

    public static Color ofRGB(int red, int green, int blue) {
        return new Color(red, green, blue);
    }

    public static Color ofJavaColor(java.awt.Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Color mixDyeColors(DyeColor... colors) {
        Validate.noNullElements(colors, "No nulls allowed!");
        final Color[] actualColors = new Color[colors.length];
        for (int i = 0; i < colors.length; i++) {
            actualColors[i] = colors[i].getColor();
        }

        return mixColors(actualColors);
    }

    public static Color mixColors(Color... colors) {
        Validate.noNullElements(colors, "No null colors allowed!");
        checkArgument(colors.length > 0, "Cannot have an empty color array!");
        if (colors.length == 1) {
            return colors[0];
        } else {
            int red = colors[0].getRed();
            int green = colors[0].getGreen();
            int blue = colors[0].getBlue();
            for (int i = 1; i < colors.length; i++) {
                red += colors[i].getRed();
                green += colors[i].getGreen();
                blue += colors[i].getBlue();
            }
            double averageRed = red / colors.length;
            double averageGreen = green / colors.length;
            double averageBlue = blue / colors.length;
            return ofRGB((int) Math.round(averageRed), (int) Math.round(averageGreen), (int) Math.round(averageBlue));
        }
    }

    private final byte red;
    private final byte green;
    private final byte blue;

    private Color(int red, int green, int blue) {
        this.red = (byte) (red & MASK);
        this.green = (byte) (green & MASK);
        this.blue = (byte) (blue & MASK);
    }

    public int getRed() {
        return MASK & this.red;
    }

    public Color withRed(int red) {
        return ofRGB(red, getGreen(), getBlue());
    }

    public int getGreen() {
        return MASK & this.green;
    }

    public Color withGreen(int green) {
        return ofRGB(getRed(), green, getBlue());
    }

    public int getBlue() {
        return MASK & this.blue;
    }

    public Color withBlue(int blue) {
        return ofRGB(getRed(), getGreen(), blue);
    }

    public java.awt.Color asJavaColor() {
        return new java.awt.Color(getRed(), getGreen(), getBlue());
    }

    public Color mixWithColors(Color... colors) {
        Color[] newColorArray = new Color[colors.length + 1];
        newColorArray[0] = this;
        System.arraycopy(colors, 0, newColorArray, 1, colors.length);
        return mixColors(newColorArray);
    }

    public Color mixWithDyes(DyeColor... dyeColors) {
        Color[] newColorArray = new Color[dyeColors.length + 1];
        newColorArray[0] = this;
        for (int i = 0; i < dyeColors.length; i++) {
            newColorArray[i + 1] = dyeColors[i].getColor();
        }
        return mixColors(newColorArray);
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
            .set(Queries.COLOR_RED, this.getRed())
            .set(Queries.COLOR_GREEN, this.getGreen())
            .set(Queries.COLOR_BLUE, this.getBlue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Color.class, this.red, this.green, this.blue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Color other = (Color) obj;
        return Objects.equals(this.red, other.red)
               && Objects.equals(this.green, other.green)
               && Objects.equals(this.blue, other.blue);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
            .add("red", this.red)
            .add("green", this.green)
            .add("blue", this.blue)
            .toString();
    }
}
