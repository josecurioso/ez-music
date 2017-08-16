package logic;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;


public class ID3Tools {
	
	public static void copyTags(String from, String to, Logger logger) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException{

		MP3File a = (MP3File) AudioFileIO.read(new File(from));
		MP3File b = (MP3File) AudioFileIO.read(new File(to));
		Tag tagA = a.getTag();
		Tag tagB = b.getTag();
		
		
		
		logger.log("debug", "info", "####################################################################\n"
		+ "Artist = " + tagA.getFirst(FieldKey.ARTIST) + "\n"
		+ "Title = " + tagA.getFirst(FieldKey.TITLE) + "\n"
		+ "Album = " + tagA.getFirst(FieldKey.ALBUM) + "\n"
		+ "Year = " + tagA.getFirst(FieldKey.YEAR) + "\n"
		+ "Track = " + tagA.getFirst(FieldKey.TRACK) + "\n"
		+ "Genre = " + tagA.getFirst(FieldKey.GENRE) + "\n"
		+ "Album artist = " + tagA.getFirst(FieldKey.ALBUM_ARTIST) + "\n"
		+ "Comment = " + tagA.getFirst(FieldKey.COMMENT) + "\n"
		+ "####################################################################\n");
		
		
		
		tagB.setField(FieldKey.ARTIST, tagA.getFirst(FieldKey.ARTIST));
		tagB.setField(FieldKey.TITLE, tagA.getFirst(FieldKey.TITLE));
		tagB.setField(FieldKey.ALBUM, tagA.getFirst(FieldKey.ALBUM));
		tagB.setField(FieldKey.YEAR, tagA.getFirst(FieldKey.YEAR));
		tagB.setField(FieldKey.TRACK, tagA.getFirst(FieldKey.TRACK));
		tagB.setField(FieldKey.GENRE, tagA.getFirst(FieldKey.GENRE));
		tagB.setField(FieldKey.ALBUM_ARTIST, tagA.getFirst(FieldKey.ALBUM_ARTIST));
		tagB.setField(FieldKey.COMMENT, tagA.getFirst(FieldKey.COMMENT));
		
		tagB.setField(tagA.getFirstArtwork());
		
		a.commit();
		b.commit();
	}

}
