package io.github.tapcard.emvnfccard.provider;

import org.apache.commons.lang3.StringUtils;
import org.fest.assertions.Assertions;

import io.github.tapcard.emvnfccard.parser.IProvider;

import io.github.tapcard.emvnfccard.utils.BytesUtils;

public class ProviderSelectPaymentEnvTest implements IProvider {

	/**
	 * Expected data
	 */
	private String expectedData = StringUtils.EMPTY;

	/**
	 * Returned data
	 */
	private String returnedData = StringUtils.EMPTY;

	@Override
	public byte[] transceive(final byte[] pCommand) {
		Assertions.assertThat(BytesUtils.bytesToStringNoSpace(pCommand)).isEqualTo(expectedData);
		return returnedData != null ? BytesUtils.fromString(returnedData) : null;
	}

	/**
	 * Setter for the field expectedData
	 * 
	 * @param expectedData
	 *            the expectedData to set
	 */
	public void setExpectedData(final String expectedData) {
		this.expectedData = expectedData;
	}

	/**
	 * Setter for the field returnedData
	 * 
	 * @param returnedData
	 *            the returnedData to set
	 */
	public void setReturnedData(final String returnedData) {
		this.returnedData = returnedData;
	}

}
