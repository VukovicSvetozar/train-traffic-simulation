package org.unibl.etf.zs.utility;

import java.util.Random;

public class RandomEnum<E extends Enum<E>> {

	private static final Random RND = new Random();
	private final E[] vrijednost;

	public RandomEnum(Class<E> token) {
		vrijednost = token.getEnumConstants();
	}

	public E random() {
		return vrijednost[RND.nextInt(vrijednost.length)];
	}

}
