## TapCard ![Build Status](https://travis-ci.org/TapCard/TapCard.png)
Replace card number input with NFC tap!

### Description
A Java library to let your app users enter an NFC or EMV card info in a flash – just by holding it to the back of their phone. Don’t lose clients because they hate to manually enter all the card details – this process on a phone is awkward.<br/>

Demo app available on the Google Play:

<a href="http://bit.ly/2uxKxPD"><img height="60px" alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/apps/en-play-badge.png" /></a>

[![Demo app screen](https://raw.githubusercontent.com/devnied/EMV-NFC-Paycard-Enrollment/master/images/demo.gif)](https://raw.githubusercontent.com/devnied/EMV-NFC-Paycard-Enrollment/master/images/demo.gif)

### Getting started

Create the reader
```java
nfcCardReader = new NFCCardReader(activity);
```

When you will be ready - wait for NFC card.
```java
nfcCardReader.enableDispatch();
```

After this, app will activate NFC and start waiting for card.
Once user is tapped phone, activity will got intent.

Check that it is right intent and pass it to library.
Library processing is blocking so wrap it in async call. For example `AsyncTask`:
```java
    @Override
    protected void onNewIntent(Intent intent) {
        if (nfcCardReader.isSuitableIntent(intent)) {
            readCardDataAsync(intent);
        }
    }

    private void readCardDataAsync(Intent intent) {
        new AsyncTask<Intent, Object, EmvCard>() {

            @Override
            protected EmvCard doInBackground(Intent... intents) {
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

            @Override
            protected void onPostExecute(EmvCard emvCard) {
                showCardInfo(emvCard);
            }
        }.execute(intent);
    }

```


Don't forget to stop waiting if Activity is paused or card reading is not needed more.
```java
nfcCardReader.disableDispatch();
```

Done! You got a card object, that contains all read data (Aid, card number, expiration date, card type, transactions history)


## Download
Library is not published yet. But you can use it from JitPack!

Step 1. Add the JitPack repository to your build file
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```


Step 2. Add the dependency
```groovy
	dependencies {
		compile 'com.github.TapCard.TapCard:android:master-SNAPSHOT'
	}
```
or for specific commit
```groovy
	dependencies {
		compile 'com.github.TapCard.TapCard:android:6673d687ba'
	}
```

### About fork
This is a fork of [EMV-NFC-Paycard-Enrollment](https://github.com/devnied/EMV-NFC-Paycard-Enrollment) by [Millau Julien](http://twitter.com/devnied).

Fork is aimed at better Android compatibility and simpler API.

Original library is heavy using Apache libraries.
So for smaller method count used methods are included inside library sources and Apache library was detached from dependencies.
Also log4j was replaced with simple logging, that can be easily configured via `LoggerFactory.setLogWriter(LogWriter)`

## Build
**To build the project launch:**
```bash
./gradlew build
```

## Bugs

Please report bugs and feature requests to the GitHub issue tracker.<br/>
Forks and Pull Requests are also welcome.


## Copyright and license

Copyright 2014 Millau Julien.

Copyright 2017 CkEsc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this work except in compliance with the License.
You may obtain a copy of the License in the LICENSE file, or at:

  [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

