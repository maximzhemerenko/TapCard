package com.github.devnied.sample.emvnfccard.activity;

import com.github.devnied.sample.emvnfccard.fragment.IRefreshable;
import io.github.tapcard.emvnfccard.model.EmvCard;

public interface IContentActivity {

	/**
	 * Method used to get log from root activity
	 * 
	 * @return String buffer
	 */
	StringBuffer getLog();

	/**
	 * Method used to get read card data
	 * 
	 * @return
	 */
	EmvCard getCard();

	/**
	 * Method to set refreshabe content
	 * 
	 * @param pRefreshable
	 */
	void setRefreshableContent(IRefreshable pRefreshable);

}
