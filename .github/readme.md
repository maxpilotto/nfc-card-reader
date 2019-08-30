# Nfc Card Reader
Wrapper for pro100svitlo's nfc card reading library 

# Getting started
In your project's `build.gradle`
```gradle
repositories {
	maven { url "https://jitpack.io" }
}
```

In your modules's `build.gradle`
```gradle 
dependencies {
    implementation 'com.github.maxpilotto:nfc-card-reader:2.1'
}
```

# Usage
The easiest way to use it is to extend the NfcReaderActivity in the activity that will handle the scanned card
```java
public class MainActivity extends NfcReaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public void onReaderError(NfcCardReader.Error error) {
        // Handle the error
    }

    @Override public void onReadComplete(EmvCard card) {
        // Handle the card

        card.getCardNumber();
        card.getExpireDate();
    }
}

```

The other way requires you to create a NfcCardReader object and override onPause,onResume and onNewIntent
``` java
public abstract class NfcReaderActivity extends AppCompatActivity {
    private NfcCardReader reader;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate the NfcCardReader object, pass it the activity and the listener
        reader = new NfcCardReader(this, new NfcCardListener() {
            @Override public void onReaderError(NfcCardReader.Error error) {
                // Handle the error
            }

            @Override public void onReadComplete(EmvCard card) {
                // Handle the card
            }
        });

        // Parse the intent, this resolves the background reading issues
        reader.parse(getIntent());
    }

    @Override protected void onResume() {
        super.onResume();

        reader.enableDispatch();
    }

    @Override protected void onPause() {
        super.onPause();

        reader.disableDispatch();
    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        reader.parse(intent);
    }
}
```

Remember to add the NFC permission
```xml
<uses-permission android:name="android.permission.NFC" />
```

And add this intent filter to the activity you want to launch when a card is discovered, this is needed if you want your app to scan cards even when it's closed.  
If you don't want the activity to automatically open do not add the filter
```xml
<intent-filter>
    <action android:name="android.nfc.action.TECH_DISCOVERED" />
    <category android:name="android.intent.category.DEFAULT" />
</intent-filter>

<meta-data
    android:name="android.nfc.action.TECH_DISCOVERED"
    android:resource="@xml/nfc_tech" />
```
# Check out the [Demo](https://github.com/maxpilotto/nfc-card-reader/releases)
