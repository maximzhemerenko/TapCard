package io.github.tapcard.emvnfccard.provider;

import io.github.tapcard.emvnfccard.exception.CommunicationException;
import io.github.tapcard.emvnfccard.parser.IProvider;

public class ExceptionProviderTest implements IProvider {
	@Override
	public byte[] transceive(final byte[] pCommand) throws CommunicationException {

		throw new CommunicationException();
	}

}
