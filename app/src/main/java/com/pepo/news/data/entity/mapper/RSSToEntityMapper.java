package com.pepo.news.data.entity.mapper;

import android.util.Xml;

import com.pepo.news.data.entity.NewsFeedEntity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * it extract the data from xml files received from server
 */
public class RSSToEntityMapper {


    @Inject
    public RSSToEntityMapper() {

    }

    // We don't use namespaces
    private final String ns = null;

    public List<NewsFeedEntity> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            inputStream.close();
        }
    }

    private List<NewsFeedEntity> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "rss");
        String title = null;
        String link = null;
        String imageLink = null;
        List<NewsFeedEntity> items = new ArrayList<NewsFeedEntity>();
        int itemID = 0;
        while (parser.next() != XmlPullParser.END_DOCUMENT && items.size() < 16) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("link")) {
                link = readLink(parser);
            } else if (name.equals("description")) {
                imageLink = readDescription(parser);
            }
            if (title != null && link != null && imageLink != null) {
                NewsFeedEntity item = new NewsFeedEntity(itemID, title, link, imageLink, false);
                items.add(item);
                title = null;
                link = null;
                imageLink = null;
                itemID++;
            }
        }
        return items;
    }

    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        String[] strings = description.split("'");
        if (strings.length == 0 || strings.length == 1) {
            return null;
        } else {
            return strings[1];
        }

    }

    // For the tags title and link, extract their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


}
