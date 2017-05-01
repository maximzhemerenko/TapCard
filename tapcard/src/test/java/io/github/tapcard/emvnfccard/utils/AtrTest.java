package io.github.tapcard.emvnfccard.utils;

import org.fest.assertions.Assertions;
import org.junit.Test;

import java.util.Arrays;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class AtrTest {

	@Test
	public void testDescriptionFromATR() {

		Assertions.assertThat(AtrUtils.getDescription("3B 02 14 50")).isEqualTo(Arrays.asList("Schlumberger Multiflex 3k"));
		Assertions.assertThat(AtrUtils.getDescription(null)).isEqualTo(null);
		Assertions
				.assertThat(AtrUtils.getDescription("3B 07 64 11.........."))
				.isEqualTo(
						Arrays.asList("HID Corporate 1000 Format",
								"http://www.hidglobal.com/sites/hidglobal.com/files/resource_files/omnikey_contactless_developer_guide.pdf"));
		Assertions
				.assertThat(AtrUtils.getDescription("3F FD 11 25 02 50 00 03 33 B0 15 69 FF 4A 50 F0 80 03 4B 4C 03"))
				.isEqualTo(Arrays.asList("Kabel Deutschland G02  (Pay TV)"));
	}

	@Test
	public void testDescriptionFromATS() {

		Assertions.assertThat(AtrUtils.getDescriptionFromAts("20 63 CB A3 A0")).isEqualTo(
				Arrays.asList("VISA card from Banque Populaire"));
		Assertions.assertThat(AtrUtils.getDescription(null)).isEqualTo(null);
	}
}
