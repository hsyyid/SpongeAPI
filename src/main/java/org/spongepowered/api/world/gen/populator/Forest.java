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
package org.spongepowered.api.world.gen.populator;

import java.util.Collection;

import org.spongepowered.api.util.VariableAmount;
import org.spongepowered.api.world.gen.Populator;
import org.spongepowered.api.world.gen.type.BiomeTreeType;

import java.util.Optional;

/**
 * A populator which will place several trees into a chunk in order to create a
 * forest.
 */
public interface Forest extends Populator {

    /**
     * Gets the number of trees to attempt to spawn per chunk, must be greater
     * than zero.
     *
     * @return The number to spawn
     */
    VariableAmount getTreesPerChunk();

    /**
     * Sets the number of trees to attempt to spawn per chunk, must be greater
     * than zero.
     *
     * @param count The new amount to spawn
     */
    void setTreesPerChunk(VariableAmount count);

    /**
     * Sets the number of trees to attempt to spawn per chunk, must be greater
     * than zero.
     * 
     * @param count The new amount to spawn
     */
    default void setTreesPerChunk(int count) {
        setTreesPerChunk(VariableAmount.fixed(count));
    }

    /**
     * Gets the a mutable weighted collection of {@link BiomeTreeType}s to
     * spawn.
     * 
     * @return The type to spawn
     */
    WeightedCollection<WeightedBiomeTreeType> getTypes();

    /**
     * A builder for constructing {@link Forest} populators.
     */
    interface Builder {

        /**
         * Sets the number of trees to attempt to spawn per chunk, must be
         * greater than zero.
         *
         * @param count The new amount to spawn
         * @return This builder, for chaining
         */
        Builder perChunk(VariableAmount count);

        /**
         * Sets the number of trees to attempt to spawn per chunk, must be
         * greater than zero.
         * 
         * @param count The new amount to spawn
         * @return This builder, for chaining
         */
        default Builder perChunk(int count) {
            return perChunk(VariableAmount.fixed(count));
        }

        /**
         * Sets the {@link BiomeTreeType}s to spawn.
         * 
         * @param types The new types to spawn
         * @return This builder, for chaining
         */
        Builder types(WeightedBiomeTreeType... types);

        /**
         * Sets the {@link BiomeTreeType}s to spawn.
         * 
         * @param types The new types to spawn
         * @return This builder, for chaining
         */
        Builder types(Collection<WeightedBiomeTreeType> types);

        /**
         * Sets the {@link BiomeTreeType} to the list of weighted types.
         * 
         * @param type The new type to add
         * @param weight The weight of the type
         * @param large If the large variant should be used
         * @return This builder, for chaining
         */
        Builder type(BiomeTreeType type, int weight, boolean large);

        /**
         * Sets the {@link WeightedBiomeTreeType} to the list of weighted types.
         * 
         * @param type The new type to add
         * @return This builder, for chaining
         */
        Builder type(WeightedBiomeTreeType type);

        /**
         * Resets this builder to the default values.
         *
         * @return This builder, for chaining
         */
        Builder reset();

        /**
         * Builds a new instance of a {@link Forest} populator with the settings
         * set within the builder.
         *
         * @return A new instance of the populator
         * @throws IllegalStateException If there are any settings left unset
         *         which do not have default values
         */
        Forest build() throws IllegalStateException;

    }

}
