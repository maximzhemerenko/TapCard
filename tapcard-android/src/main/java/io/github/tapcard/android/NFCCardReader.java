package io.github.tapcard.android;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import io.github.tapcard.emvnfccard.model.EmvCard;
import io.github.tapcard.emvnfccard.parser.EmvParser;
import java.io.IOException;

public class NFCCardReader {
  private NFCUtils nfcUtils;
  private AndroidNfcProvider provider;

  public NFCCardReader(Activity activity) {
    nfcUtils = new NFCUtils(activity);
    provider = new AndroidNfcProvider();
  }

  public void onResume() {
    nfcUtils.enableDispatch();
  }

  public void onPause() {
    nfcUtils.disableDispatch();
  }

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
