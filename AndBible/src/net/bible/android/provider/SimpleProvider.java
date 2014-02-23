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
import org.jdom2.Content;
import org.jdom2.Document;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;
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
		
		SwordDocumentFacade swordDocumentFacade = SwordDocumentFacade.getInstance();
		
		List<Book> bibles = swordDocumentFacade.getBibles();
		
		SwordContentFacade swordContentFacade = SwordContentFacade.getInstance();
	
		//swordContentFacade.getPlainText(book, key, maxKeyCount)

		String[] row = new String[] { "passage" };
        MatrixCursor matrixCursor = new MatrixCursor(row);
        row = new String[] { "This is from and-bible" };
        matrixCursor.addRow(row);
		return matrixCursor;
	}

    private MatrixCursor getPassageCursor(String passage) {
        String[] row = new String[] { "passage" };
        MatrixCursor matrixCursor = new MatrixCursor(row);
        passage = passage.replace(" ", ""); // strip spaces
        passage = passage.toLowerCase();
        String assetPath = "passage/" + passage + ".html";
        InputStream in;
        BufferedReader reader;
        String line;
        String html = "";
        Log.d("ASSET", assetPath);
        try {
            in = getContext().getAssets().open(assetPath);
            reader = new BufferedReader(new InputStreamReader(in));
            line = reader.readLine();
            while (line != null) {
                html = html + line;
                line = reader.readLine();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        row = new String[] { html };
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
