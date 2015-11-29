package com.database;


import android.os.AsyncTask;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;

public class Layers {
    private TextView parsedHtmlNode;



    class UPCDatabase extends AsyncTask<Void, Void, Void> {



        private Document htmlDocument;
        private String htmlPageUrl = "http://www.upcdatabase.com/item/";
        private String productName;
        public String barcode = "012000014338";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String charset = "UTF-8";
                String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
                String url;
                //System.out.println(google + URLEncoder.encode(searchString, charset));
                //  System.setProperty("http.proxyHost", "10.1.20.18");
                // System.setProperty("http.proxyPort", "9090");
                Document doc = Jsoup.connect(htmlPageUrl + URLEncoder.encode(barcode, charset)).userAgent(userAgent).get();
                Element searchResultCenterContent = doc.select(".data").select("tr").get(2).select("td").get(2);
                System.out.println(searchResultCenterContent.text());
                productName = searchResultCenterContent.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            parsedHtmlNode.setText(productName);
        }
    }

    private class koiOrWebsite extends AsyncTask<Void, Void, Void> {
        private Document htmlDocument;
        private String htmlPageUrl = "http://inducesmile.com/";

        private String htmlContentInStringFormat;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                htmlDocument = Jsoup.connect(htmlPageUrl).get();
                htmlContentInStringFormat = htmlDocument.title();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            parsedHtmlNode.setText(htmlContentInStringFormat);
        }
    }


}
