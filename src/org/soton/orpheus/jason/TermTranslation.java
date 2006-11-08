package org.soton.orpheus.jason;

import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import se.sics.tac.aw.TACAgent;

public class TermTranslation {
	public static final String TYPE_MUSEUM = "type_museum";

	public static final String TYPE_AMUSEMENT = "type_amusement";

	public static final String TYPE_ALLIGATOR_WRESTLING = "type_alligator_wrestling";

	public static final String TYPE_CHEAP_HOTEL = "type_cheap_hotel";

	public static final String TYPE_GOOD_HOTEL = "type_good_hotel";

	public static final String TYPE_OUTFLIGHT = "type_outflight";

	public static final String TYPE_INFLIGHT = "type_inflight";

	public static final String CAT_ENTERTAINMENT = "cat_entertainment";

	public static final String CAT_HOTEL = "cat_hotel";

	public static final String CAT_FLIGHT = "cat_flight";

	private static TermTranslation termTranslationSingleton = null;

	public static TermTranslation getTermTranslation() {
		if (termTranslationSingleton == null) {
			termTranslationSingleton = new TermTranslation();
		}

		return termTranslationSingleton;
	}

	private TermTranslation() {

	}

	/**
	 * Converts a representation of an auction's category into a number
	 * meaningful to the TAC Server.
	 * 
	 * @param category
	 *            A Term representing the category
	 * @return A nummber
	 */
	public int getCategory(Term categoryTerm) {
		String category = ((StringTerm) categoryTerm).getString();
		if (category.equals(TermTranslation.CAT_HOTEL)) {
			return TACAgent.CAT_HOTEL;
		} else if (category.equals(TermTranslation.CAT_FLIGHT)) {
			return TACAgent.CAT_FLIGHT;
		} else if (category.equals(TermTranslation.CAT_ENTERTAINMENT)) {
			return TACAgent.CAT_ENTERTAINMENT;
		}

		return -1;
	}

	/**
	 * Convert a representation of an auction's type into a number meaningful to
	 * the TAC Server
	 * 
	 * @param typeTerm
	 *            A Term representing the type
	 * @return
	 */
	public int getType(Term typeTerm) {
		String type = ((StringTerm) typeTerm).getString();
		if (type.equals(TermTranslation.TYPE_INFLIGHT)) {
			return TACAgent.TYPE_INFLIGHT;
		} else if (type.equals(TermTranslation.TYPE_OUTFLIGHT)) {
			return TACAgent.TYPE_OUTFLIGHT;
		} else if (type.equals(TermTranslation.TYPE_GOOD_HOTEL)) {
			return TACAgent.TYPE_GOOD_HOTEL;
		} else if (type.equals(TermTranslation.TYPE_CHEAP_HOTEL)) {
			return TACAgent.TYPE_CHEAP_HOTEL;
		} else if (type.equals(TermTranslation.TYPE_ALLIGATOR_WRESTLING)) {
			return TACAgent.TYPE_ALLIGATOR_WRESTLING;
		} else if (type.equals(TermTranslation.TYPE_AMUSEMENT)) {
			return TACAgent.TYPE_AMUSEMENT;
		} else if (type.equals(TermTranslation.TYPE_MUSEUM)) {
			return TACAgent.TYPE_MUSEUM;
		}
		return -1;
	}

	/**
	 * Converts a representation of an auction category into a string.
	 * 
	 * @param category
	 *            The category to translate.
	 * @return A string representation of the auction category.
	 */
	public String getCategory(int category) {
		switch (category) {
		case TACAgent.CAT_ENTERTAINMENT:
			return TermTranslation.CAT_ENTERTAINMENT;
		case TACAgent.CAT_FLIGHT:
			return TermTranslation.CAT_FLIGHT;
		case TACAgent.CAT_HOTEL:
			return TermTranslation.CAT_HOTEL;
		}
		return null;
	}

	/**
	 * Converts a representation of a flight type into a string.
	 * 
	 * @param category
	 *            The type to translate.
	 * @return A string representation of the flight type.
	 */
	public String getFlightType(int type) {
		switch (type) {
		case TACAgent.TYPE_INFLIGHT:
			return TermTranslation.TYPE_INFLIGHT;
		case TACAgent.TYPE_OUTFLIGHT:
			return TermTranslation.TYPE_OUTFLIGHT;
		}
		return null;
	}

	/**
	 * Converts a representation of a hotel type into a string.
	 * 
	 * @param category
	 *            The type to translate.
	 * @return A string representation of the hotel type.
	 */
	public String getHotelType(int type) {
		switch (type) {
		case TACAgent.TYPE_GOOD_HOTEL:
			return TermTranslation.TYPE_GOOD_HOTEL;
		case TACAgent.TYPE_CHEAP_HOTEL:
			return TermTranslation.TYPE_CHEAP_HOTEL;
		}

		return null;
	}

	/**
	 * Converts a representation of an entertainment type into a string.
	 * 
	 * @param category
	 *            The type to translate.
	 * @return A string representation of the entertainment type.
	 */
	public String getEntertainmentType(int type) {
		switch (type) {
		case TACAgent.TYPE_ALLIGATOR_WRESTLING:
			return TermTranslation.TYPE_ALLIGATOR_WRESTLING;
		case TACAgent.TYPE_AMUSEMENT:
			return TermTranslation.TYPE_AMUSEMENT;
		case TACAgent.TYPE_MUSEUM:
			return TermTranslation.TYPE_MUSEUM;
		}

		return null;
	}
}
