package io.github.tapcard.sample.android.simple;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.TextView;
import io.github.tapcard.android.NFCCardReader;
import io.github.tapcard.emvnfccard.model.EmvCard;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
  private NFCCardReader nfcCardReader;
  private TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    nfcCardReader = new NFCCardReader(this);
    textView = (TextView) findViewById(R.id.text);
  }

  @Override protected void onNewIntent(Intent intent) {
    if (nfcCardReader.isSuitableIntent(intent)) {
      textView.setText(R.string.reading);
      readCardDataAsync(intent);
    }
  }

  @Override protected void onResume() {
    nfcCardReader.enableDispatch();
    super.onResume();
  }

  @Override protected void onPause() {
    nfcCardReader.disableDispatch();
    super.onPause();
  }

  private void showCardInfo(EmvCard card) {
    String text;

    if (card != null) {
      text = TextUtils.join("\n", new Object[] {
          CardUtils.formatCardNumber(card.getCardNumber(), card.getType()),
          DateFormat.format("M/y", card.getExpireDate()),
          "---",
          card.toString().replace(", ", ",\n")
      });
    } else {
      text = getString(R.string.error);
    }
    textView.setText(text);
  }

  private void readCardDataAsync(Intent intent) {
    new AsyncTask<Intent, Object, EmvCard>() {

      @Override protected EmvCard doInBackground(Intent... intents) {
        try {
          return nfcCardReader.readCardBlocking(intents[0]);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (NFCCardReader.WrongIntentException e) {
          e.printStackTrace();
        } catch (NFCCardReader.WrongTagTech e) {
          e.printStackTrace();
        }
        return null;
      }

      @Override protected void onPostExecute(EmvCard emvCard) {
        showCardInfo(emvCard);
      }
    }.execute(intent);
  }
}
