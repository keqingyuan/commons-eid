/*
 * Commons eID Project.
 * Copyright (C) 2008-2012 FedICT.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version
 * 3.0 as published by the Free Software Foundation.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, see 
 * http://www.gnu.org/licenses/.
 */

package be.fedict.commons.eid.examples.events;

import java.math.BigInteger;
import java.util.Set;
import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import be.fedict.commons.eid.client.CardAndTerminalManager;
import be.fedict.commons.eid.client.event.CardEventsListener;

public class CardEventsExamples {

	/*
	 * get information about Cards being inserted and removed, while doing
	 * something else:
	 */
	public CardEventsExamples cardterminals_basic_asynchronous()
			throws InterruptedException {
		// -------------------------------------------------------------------------------------------------------
		// instantiate a CardAndTerminalManager with default settings (no
		// logging, default timeout)
		// -------------------------------------------------------------------------------------------------------
		CardAndTerminalManager cardAndTerminalManager = new CardAndTerminalManager();

		// -------------------------------------------------------------------------------------------------------
		// register a CardEventsListener
		// -------------------------------------------------------------------------------------------------------
		cardAndTerminalManager.addCardListener(new CardEventsListener() {
			@Override
			public void cardInserted(CardTerminal cardTerminal, Card card) {
				if (card != null)
					System.err.println("Card ["
							+ String.format("%x", new BigInteger(1, card
									.getATR().getBytes()))
							+ "] Inserted Into Terminal ["
							+ cardTerminal.getName() + "]");
				else
					System.err.println("Card present but failed to connect()");
			}

			@Override
			public void cardRemoved(CardTerminal cardTerminal) {
				System.err.println("Card Removed From ["
						+ cardTerminal.getName() + "]");
			}
		});

		// -------------------------------------------------------------------------------------------------------
		// start the CardAndTerminalManager instance running as a daemon thread.
		// -------------------------------------------------------------------------------------------------------
		cardAndTerminalManager.start();

		// -------------------------------------------------------------------------------------------------------
		// the main thread goes off and does other things (for this example,
		// just loop and sleep)
		// -------------------------------------------------------------------------------------------------------
		System.err.println("Now.. insert and remove some cards");
		for (;;)
			Thread.sleep(2000);
	}

	// -------------------------------------------------------------------------------------------------------

	public static void main(String[] args) throws InterruptedException {
		CardEventsExamples examples = new CardEventsExamples();
		examples.cardterminals_basic_asynchronous();
	}
}
