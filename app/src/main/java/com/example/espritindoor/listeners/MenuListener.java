package com.example.espritindoor.listeners;

/**
 * Listener interface to catch data context fetching events
 *
 * @author Martin Hansen
 */
public interface MenuListener {
	/**
	 * Listener method to catch search events from the menu.
	 */
	void onMenuSearch(String searchString, boolean finalSearch);

	/**
	 * Listener method to catch search events from the menu.
	 */
	void onMenuSelect(Object selectedObject, int objViewType);
	/**
	 * If called, the callee want to show a location on the map
	 */

	/**
	 * If called, the callee want to show a route to a location on the map
	 */
	/**
	 * If called, the callee want to change venue
	 */
	void onMenuVenueSelect(String venueId);
}
