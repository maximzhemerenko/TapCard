package io.github.tapcard.emvnfccard.utils;

import org.fest.assertions.Assertions;
import org.junit.Test;

import io.github.tapcard.emvnfccard.enums.CommandEnum;

import io.github.tapcard.emvnfccard.utils.BytesUtils;

public class CommandApduTest {

	@Test
	public void testToByte() {
		Assertions.assertThat(BytesUtils.bytesToString(new CommandApdu(CommandEnum.GPO, 0x01, 0x02, 0x01).toBytes())).isEqualTo(
				"80 A8 01 02 01");
		Assertions.assertThat(BytesUtils.bytesToString(new CommandApdu(CommandEnum.GPO, new byte[] {}, 0x01).toBytes()))
				.isEqualTo("80 A8 00 00 01");
		Assertions.assertThat(BytesUtils.bytesToString(new CommandApdu(CommandEnum.GPO, null, 0x01).toBytes())).isEqualTo(
				"80 A8 00 00 01");
		Assertions.assertThat(BytesUtils.bytesToString(new CommandApdu(CommandEnum.GPO, 0x01, 0x02).toBytes())).isEqualTo(
				"80 A8 01 02");
	}

}
