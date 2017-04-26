package io.github.tapcard.android;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import io.github.tapcard.emvnfccard.log.Logger;
import io.github.tapcard.emvnfccard.log.LoggerFactory;
import io.github.tapcard.emvnfccard.model.EmvCard;
import io.github.tapcard.emvnfccard.parser.EmvParser;
import java.io.IOException;

public class NFCCardReader {
  private NFCUtils nfcUtils;
  private AndroidNfcProvider provider;
  private Logger logger;

  public NFCCardReader(Activity activity) {
    nfcUtils = new NFCUtils(activity);
    provider = new AndroidNfcProvider();
    logger = LoggerFactory.getLogger(NFCCardReader.class);
  }

  /**
   * Begin waiting for bank card been tapped to NFC module of phone
   */
  public void enableDispatch() {
    nfcUtils.enableDispatch();
  }

  /**
   * Stop waiting for bank card
   */
  public void disableDispatch() {
    nfcUtils.disableDispatch();
  }

  /**
   * Checks that intent is suitable for NFC card reading
   *
   * @param intent intent for check
   * @return true - intent is good. false - this is not the intent you're looking for
   */
  public boolean isSuitableIntent(Intent intent) {
    final Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    if (tag == null) {
      logger.debug("No TAG in intent");
      return false;
    }

    IsoDep tagComm = IsoDep.get(tag);
    if (tagComm == null) {
      logger.debug("IsoDep was not enumerated in getTechList()");
      return false;
    }

    return true;
  }

  /**
   * Read card data from given intent.
   * <p>Note that this method is blocking. You should not use it as is.
   * Instead - wrap it into some async framework: RxJava, AsyncTask, etc</p>
   *
   * <p>Intent by itself does not contain all data. It contain metadata of NFC card.
   * For reading data library will open NFC connection and read byte data from card</p>
   *
   * <p>You should check that this intent contain right data with {@link #isSuitableIntent(Intent)}
   * before calling this method</p>
   *
   * @param intent intent with initial card information.
   * @return Ready for use card data
   * @throws IOException may be thrown during NFC data transfer
   * @throws WrongIntentException thrown if intent does not contain {@link NfcAdapter#EXTRA_TAG}
   * @throws WrongTagTech thrown when this NFC tech is not supported:
   * not enumerated in {@link Tag#getTechList}.
   */
  public EmvCard readCardBlocking(Intent intent)
      throws IOException, WrongIntentException, WrongTagTech {
    final Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    if (tag == null) {
      throw new WrongIntentException("No TAG in intent");
    }

    IsoDep tagComm = IsoDep.get(tag);
    if (tagComm == null) {
      throw new WrongTagTech();
    }

    try {
      tagComm.connect();
      provider.setmTagCom(tagComm);

      EmvParser parser = new EmvParser(provider, true);
      return parser.readEmvCard();
    } finally {
      tagComm.close();
    }
  }

  public static class WrongIntentException extends Exception {
    WrongIntentException(String detailMessage) {
      super(detailMessage);
    }
  }

  public static class WrongTagTech extends Exception {
    WrongTagTech() {
      super("IsoDep was not enumerated in getTechList()");
    }
  }
}
