package ua.ck.tars.parsehtml.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ua.ck.tars.parsehtml.R;
import ua.ck.tars.parsehtml.model.Word;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int sWordsFile = R.raw.words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUi();
        writeJsonToFile(listToJson(parseHtml(readRawFile(sWordsFile))));
    }

    private void setUi() {
        // Layout
        setContentView(R.layout.activity_main);
    }

    private String readRawFile(int rawFile) {
        String buffer;
        StringBuilder stringBuilder = new StringBuilder();

        // Create stream
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(getResources().openRawResource(rawFile)));

        // Read with stream
        try {
            while ((buffer = bufferedReader.readLine()) != null) {
                stringBuilder.append(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return stringBuilder.toString();
    }

    private ArrayList<Word> parseHtml(String html) {

        ArrayList<Word> wordList = new ArrayList<>();

        Document document = Jsoup.parse(html);

        // Selecting in text all <td>...</td> elements
        Elements elements = document.select("td");

        if (elements != null) {
            // Clear wordList
            if (wordList.size() > 0) {
                wordList.clear();
            }

            // Inflate wordList
            for (int i = 0; i < elements.size(); i++) {
                wordList.add(new Word(
                        Integer.valueOf(elements.get(i).text()),
                        elements.get(i + 1).text(),
                        elements.get(i + 2).text()));
                i = i + 2;
            }
        }

        return wordList;
    }

    private String listToJson(ArrayList<Word> wordsList) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordsList);
    }

    private boolean writeJsonToFile(String json) {
        try {
            // Save json to file on internal storage
            FileOutputStream fileOutputStream = openFileOutput("words.json", Context.MODE_PRIVATE);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}