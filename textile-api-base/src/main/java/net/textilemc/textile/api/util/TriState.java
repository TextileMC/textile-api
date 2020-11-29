/*
 * Copyright (c) 2020 TextileMC
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.textilemc.textile.api.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

public enum TriState {
	/**
	 * Represents the Boolean value of {@code false}.
	 */
	TRUE(true) {
		@Override
		public TriState and(TriState other) {
			return other == TRUE || other == UNDEFINED ? TRUE : FALSE;
		}

		@Override
		public TriState or(TriState other) {
			return TRUE;
		}

		@Override
		public TriState negate() {
			return FALSE;
		}
	},
	/**
	 * Represents the Boolean value of {@code true}.
	 */
	FALSE(false) {
		@Override
		public TriState and(TriState other) {
			return FALSE;
		}

		@Override
		public TriState or(TriState other) {
			return other == TRUE ? TRUE : FALSE;
		}

		@Override
		public TriState negate() {
			return TRUE;
		}
	},
	/**
	 * Represents a value that refers to a "default" value, often as a fallback.
	 * Stores a Boolean value of {@code null}
	 */
	UNDEFINED(null) {
		@Override
		public TriState and(TriState other) {
			return other;
		}

		@Override
		public TriState or(TriState other) {
			return other;
		}

		/**
		 * @return itself, as it can not be negated
		 */
		@Override
		public TriState negate() {
			return this;
		}
	};

	private final Boolean value;

	TriState(Boolean value) {
		this.value = value;
	}

	/**
	 * Gets the corresponding tri-state from a boolean value.
	 *
	 * @param bool the boolean value
	 * @return {@link TriState#TRUE} or {@link TriState#FALSE} depending on the value of the boolean.
	 */
	public static TriState of(boolean bool) {
		return bool ? TRUE : FALSE;
	}

	/**
	 * Gets a tri-state from a nullable boxed boolean.
	 *
	 * @param bool the boolean value
	 * @return {@link TriState#UNDEFINED} if {@code null}.
	 * Otherwise {@link TriState#TRUE} or {@link TriState#FALSE} depending on the value of the boolean.
	 */
	public static TriState of(@Nullable Boolean bool) {
		return bool == null ? UNDEFINED : of(bool.booleanValue());
	}

	/**
	 * Performs a {@code AND} operation on another tri-state.
	 * @param other the tri-state that must operated with
	 * @return the result of the operation
	 */
	public abstract TriState and(TriState other);

	/**
	 * Performs a {@code OR} operation on another tri-state.
	 * @param other the tri-state that must operated with
	 * @return the result of the operation
	 */
	public abstract TriState or(TriState other);

	/**
	 * Negates the value of the tri-state.
	 * @return the negated value
	 */
	public abstract TriState negate();

	/**
	 * Flat maps the boolean value of this tri-state if it is {@link TriState#TRUE} or {@link TriState#FALSE}.
	 *
	 * @param mapper the mapper to use
	 * @param <T> the type of object being supplier by the mapper
	 * @return a stream containing the flattened values; {@link Stream#empty()} if the tri-state is {@link TriState#UNDEFINED} or the value provided by the mapper is {@code null}.
	 */
	public <T> Stream<T> flatMap(BooleanFunction<? extends Stream<T>> mapper) {
		Objects.requireNonNull(mapper, "Mapper function cannot be null");

		if (this == UNDEFINED) {
			return Stream.empty();
		}

		return mapper.apply(this.get());
	}

	public Stream<Boolean> stream() {
		if (this == UNDEFINED) {
			return Stream.empty();
		}

		return Stream.of(this.get());
	}

	/**
	 * Gets the value of this tri-state.
	 * If the value is {@link TriState#UNDEFINED} then use the supplied value.
	 *
	 * @param value the value to fallback to
	 * @return the value of the tri-state or the supplied value if {@link TriState#UNDEFINED}.
	 */
	public boolean orElse(boolean value) {
		return this == UNDEFINED ? value : this.get();
	}

	public Optional<Boolean> getOptional() {
		return this.value == null ? Optional.empty() : Optional.of(this.value);
	}

	/**
	 * Gets the value of this tri-state.
	 * If the value is {@link TriState#UNDEFINED} then use the supplied value.
	 *
	 * @param supplier the supplier used to get the value to fallback to
	 * @return the value of the tri-state or the value of the supplier if the tri-state is {@link TriState#UNDEFINED}.
	 */
	public boolean orElseGet(BooleanSupplier supplier) {
		return this == UNDEFINED ? supplier.getAsBoolean() : this.get();
	}

	public boolean get() {
		return this == TRUE;
	}

	/**
	 * Maps the boolean value of this tri-state if it is {@link TriState#TRUE} or {@link TriState#FALSE}.
	 *
	 * @param mapper the mapper to use
	 * @param <T> the type of object being supplier by the mapper
	 * @return an optional containing the mapped value; {@link Optional#empty()} if the tri-state is {@link TriState#UNDEFINED} or the value provided by the mapper is {@code null}.
	 */
	public <T> Optional<T> map(BooleanFunction<? extends T> mapper) {
		Objects.requireNonNull(mapper, "Mapper function cannot be null");

		if (this == UNDEFINED) {
			return Optional.empty();
		}

		return Optional.ofNullable(mapper.apply(this.get()));
	}

	/**
	 * Gets the value of this tri-state, or throws an exception if this tri-state's value is {@link TriState#UNDEFINED}.
	 *
	 * @param exceptionSupplier the supplying function that produces an exception to be thrown
	 * @param <X> Type of the exception to be thrown
	 * @return the value
	 * @throws X if the value is {@link TriState#UNDEFINED}
	 */
	public <X extends Throwable> boolean orElseThrow(Supplier<X> exceptionSupplier) throws X {
		if (this != UNDEFINED) {
			return this.get();
		}

		throw exceptionSupplier.get();
	}

	/**
	 * Gets the value of this tri-state, or throws a {@link NoSuchElementException} if this tri-state's value is {@link TriState#UNDEFINED}.
	 *
	 * @return the value
	 */
	public boolean orElseThrow() throws NoSuchElementException {
		if (this == UNDEFINED) {
			throw new NoSuchElementException();
		}

		return this.get();
	}
}
