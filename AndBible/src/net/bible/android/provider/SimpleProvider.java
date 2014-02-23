package net.bible.android.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.crosswire.common.activate.Lock;
import org.crosswire.common.util.Language;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookCategory;
import org.crosswire.jsword.book.BookDriver;
import org.crosswire.jsword.book.BookException;
import org.crosswire.jsword.book.BookMetaData;
import org.crosswire.jsword.book.FeatureType;
import org.crosswire.jsword.index.IndexStatus;
import org.crosswire.jsword.index.IndexStatusListener;
import org.crosswire.jsword.index.search.SearchRequest;
import org.crosswire.jsword.passage.Key;
import org.crosswire.jsword.passage.NoSuchKeyException;
import org.crosswire.jsword.passage.Verse;
import org.crosswire.jsword.versification.BibleBook;
import org.jdom2.Content;
import org.jdom2.Document;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;
import net.bible.service.common.ParseException;
import net.bible.service.sword.SwordContentFacade;
import net.bible.service.sword.SwordDocumentFacade;

public class SimpleProvider extends ContentProvider  {

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
        return "vnd.android.cursor.dir/vnd.net.bible.android.provider.simple";
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		List<String> segments = uri.getPathSegments();
		SwordDocumentFacade swordDocumentFacade = SwordDocumentFacade.getInstance();
		List<Book> bibles = swordDocumentFacade.getBibles();
		SwordContentFacade swordContentFacade = SwordContentFacade.getInstance();
		String plainText="";
		try {
			Book bible = bibles.get(0);
			Key key = bible.getKey(segments.get(1));
			plainText = swordContentFacade.readHtmlText(bible, key);
		} catch (NoSuchKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] row = new String[] { "passage" };
        MatrixCursor matrixCursor = new MatrixCursor(row);
        row = new String[] { plainText };
        matrixCursor.addRow(row);
		return matrixCursor;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
