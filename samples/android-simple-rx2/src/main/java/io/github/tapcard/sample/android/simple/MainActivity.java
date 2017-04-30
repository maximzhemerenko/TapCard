package io.github.tapcard.sample.android.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.TextView;

import io.github.tapcard.android.NFCCardReader;
import io.github.tapcard.emvnfccard.model.EmvCard;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class MainActivity extends AppCompatActivity {
    private NFCCardReader nfcCardReader;
    private TextView textView;
    private Disposable cardReadDisposable = Disposables.empty();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);

        nfcCardReader = new NFCCardReader(this);
    }

    @Override
    protected void onResume() {
        nfcCardReader.enableDispatch();
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (nfcCardReader.isSuitableIntent(intent)) {
            textView.setText(R.string.reading);

            cardReadDisposable = nfcCardReader
                    .readCardRx2(intent)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::showCardInfo,
                            throwable -> showError());
        }
    }

    @Override
    protected void onPause() {
        cardReadDisposable.dispose();
        nfcCardReader.disableDispatch();
        super.onPause();
    }

    private void showCardInfo(EmvCard card) {
        String text = TextUtils.join("\n", new Object[]{
                CardUtils.formatCardNumber(card.getCardNumber(), card.getType()),
                DateFormat.format("M/y", card.getExpireDate()),
                "---",
                "Bank info (probably): ",
                card.getAtrDescription(),
                "---",
                card.toString().replace(", ", ",\n")
        });
        textView.setText(text);
    }

    private void showError() {
        textView.setText(getString(R.string.error));
    }

}
