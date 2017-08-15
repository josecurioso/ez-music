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

	public static void main(String[] args) throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
	    
		String from = "D:/Escritorio/Musica 36/ACDC - Highway to Hell [A].mp3";
		String to = "D:/Escritorio/Musica 36/ACDC - Highway to Hell [B].mp3";
		
		copyTags(from, to);
	}
	
	public static void copyTags(String from, String to) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException{

		MP3File a = (MP3File) AudioFileIO.read(new File(from));
		MP3File b = (MP3File) AudioFileIO.read(new File(to));
		Tag tagA = a.getTag();
		Tag tagB = b.getTag();
		
		System.out.println(tagA.getFirst(FieldKey.ARTIST));
		tagB.setField(FieldKey.ARTIST, tagA.getFirst(FieldKey.ARTIST));
		System.out.println(tagA.getFirst(FieldKey.TITLE));
		tagB.setField(FieldKey.TITLE, tagA.getFirst(FieldKey.TITLE));
		System.out.println(tagA.getFirst(FieldKey.ALBUM));
		tagB.setField(FieldKey.ALBUM, tagA.getFirst(FieldKey.ALBUM));
		System.out.println(tagA.getFirst(FieldKey.YEAR));
		tagB.setField(FieldKey.YEAR, tagA.getFirst(FieldKey.YEAR));
		System.out.println(tagA.getFirst(FieldKey.TRACK));
		tagB.setField(FieldKey.TRACK, tagA.getFirst(FieldKey.TRACK));
		System.out.println(tagA.getFirst(FieldKey.GENRE));
		tagB.setField(FieldKey.GENRE, tagA.getFirst(FieldKey.GENRE));
		System.out.println(tagA.getFirst(FieldKey.ALBUM_ARTIST));
		tagB.setField(FieldKey.ALBUM_ARTIST, tagA.getFirst(FieldKey.ALBUM_ARTIST));
		System.out.println(tagA.getFirst(FieldKey.COMMENT));
		tagB.setField(FieldKey.COMMENT, tagA.getFirst(FieldKey.COMMENT));
		
		tagB.setField(tagA.getFirstArtwork());
		
		a.commit();
		b.commit();
	}

}
