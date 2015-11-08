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
package org.spongepowered.api.world.biome;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.util.weighted.SeededValue;
import org.spongepowered.api.util.weighted.SeededVariableAmount;
import org.spongepowered.api.util.weighted.VariableAmount;

/**
 * Represents a layer of BlockStates specific to a biome which may be placed in
 * during generation.
 */
public class GroundCoverLayer {

    private SeededValue<BlockState, Double> block;
    private SeededVariableAmount<Double> depth;

    /**
     * Creates a new {@link GroundCoverLayer}.
     * 
     * @param block The block state to place down for the layer
     * @param depth The depth of the layer
     */
    public GroundCoverLayer(BlockState block, SeededVariableAmount<Double> depth) {
        this(SeededValue.constant(block), depth);
    }

    public GroundCoverLayer(SeededValue<BlockState, Double> block, SeededVariableAmount<Double> depth) {
        this.block = checkNotNull(block, "block");
        this.depth = checkNotNull(depth, "depth");
    }

    /**
     * Gets the {@link BlockState} for this layer.
     * 
     * @return The block state
     */
    public SeededValue<BlockState, Double> getBlockState() {
        return this.block;
    }

    /**
     * Sets the {@link BlockState} for this layer.
     * 
     * @param block The block state
     */
    public void setBlockState(SeededValue<BlockState, Double> block) {
        this.block = checkNotNull(block, "block");
    }

    public void setBlockState(BlockState block) {
        this.block = SeededValue.constant(checkNotNull(block, "block"));
    }

    /**
     * Gets a representation of the depth of this layer. The variable amount
     * will be seeded with the stone noise at generation time.
     * 
     * @return The depth
     */
    public SeededVariableAmount<Double> getDepth() {
        return this.depth;
    }

    /**
     * Sets the {@link VariableAmount} representing the depth of this layer. The
     * variable amount will be seeded with the stone noise at generation time.
     * 
     * @param depth The new variable amount
     */
    public void setDepth(SeededVariableAmount<Double> depth) {
        this.depth = checkNotNull(depth, "depth");
    }

    public void setDepth(VariableAmount depth) {
        this.depth = SeededVariableAmount.wrapped(checkNotNull(depth, "depth"));
    }

    /**
     * Sets the depth of this layer.
     * 
     * @param depth The new depth
     */
    public void setDepth(double depth) {
        this.depth = SeededVariableAmount.fixed(depth);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("block", this.block)
                .add("depth", this.depth)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GroundCoverLayer)) {
            return false;
        }
        GroundCoverLayer object = (GroundCoverLayer) obj;
        if (!this.depth.equals(object.depth)) {
            return false;
        }
        if (!this.block.equals(object.block)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + this.block.hashCode();
        result = 37 * result + this.depth.hashCode();
        return result;
    }

}
