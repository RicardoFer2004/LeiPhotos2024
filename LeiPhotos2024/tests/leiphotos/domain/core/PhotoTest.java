package leiphotos.domain.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PhotoTest {

	@ParameterizedTest(name = "CreatePhotoWithGPS = {0}")
	@CsvSource({
		"false",
		"true"
	})
	void testCreatePhotoWithOrWithoutGPS(boolean withGPS) {
		GPSLocation gps = null;
		if(withGPS){
			gps = new GPSLocation(0, 0);
		}
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		File expectedFile = new File("test.jpg");
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		PhotoMetadata metadata = new PhotoMetadata(expectedCapturedDate,gps);
		
		Photo photo = new Photo(expectedTitle, expectedAddedDate, metadata, expectedFile);
		assertEquals(photo.capturedDate(), expectedCapturedDate);
		assertEquals(expectedAddedDate, photo.addedDate());
		assertEquals(photo.title(),expectedTitle);
		assertEquals(photo.file(),expectedFile);
		assertFalse(photo.getPlace() == null);
		assertEquals(photo.getPlace().isPresent(),gps != null);
		assertEquals(photo.isFavourite(),false);
		assertEquals(photo.size(),expectedFile.length());
	}
	@Test
	void testToggleFavourite() {
		Photo photo = new Photo();
		photo.toggleFavourite();
		assertTrue(photo.isFavourite());
	}

	@Test
	void testSize() { //requires the use of a mock file class
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("test.jpg",expectedSize);
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();

		Photo photo = new Photo(expectedTitle, expectedAddedDate, null, expectedFile);
		assertEquals(photo.size(), expectedSize);
	}

	@Test
	void testNoMatches() {
		String regexp = "Exp.*";
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("test.jpg",expectedSize);
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		PhotoMetadata data = new PhotoMetadata(expectedAddedDate, null);
		Photo photo = new Photo(expectedTitle, expectedAddedDate, data, expectedFile);
		assertFalse(photo.matches(regexp));
	}


	@Test
	void testMatchesTitle() {
		String regexp = "Test.*";
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("test.jpg",expectedSize);
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		PhotoMetadata data = new PhotoMetadata(expectedAddedDate, null);
		Photo photo = new Photo(expectedTitle, expectedAddedDate, data, expectedFile);
		assertTrue(photo.matches(regexp));
	}


	@Test
	void testMatchesFile() {
		String regexp = "test.*";
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("test.jpg",expectedSize);
		String expectedTitle = "Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		PhotoMetadata data = new PhotoMetadata(expectedAddedDate, null);

		Photo photo = new Photo(expectedTitle, expectedAddedDate, data, expectedFile);
		assertTrue(photo.matches(regexp));
	}
	@Test
	public void testMatchesCamera(){
		String regexp = "cam.*";
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("test.jpg",expectedSize);
		String expectedTitle = "Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		PhotoMetadata data = new PhotoMetadata(expectedAddedDate, "camera",null, null);

		Photo photo = new Photo(expectedTitle, expectedAddedDate, data, expectedFile);
		assertTrue(photo.matches(regexp));
	}
	@Test
	public void testMatchesManufacturer(){
		String regexp = "manuf.*";
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("test.jpg",expectedSize);
		String expectedTitle = "Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		PhotoMetadata data = new PhotoMetadata(expectedAddedDate, null,"manufacturer", null);

		Photo photo = new Photo(expectedTitle, expectedAddedDate, data, expectedFile);
		assertTrue(photo.matches(regexp));
	}
	@Test
	public void testMatchesLocation(){
		String regexp = "New l.*";
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("test.jpg",expectedSize);
		String expectedTitle = "Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		GPSLocation location = new GPSLocation(expectedSize, expectedSize,"New location");
		PhotoMetadata data = new PhotoMetadata(expectedAddedDate, null,null, location);

		Photo photo = new Photo(expectedTitle, expectedAddedDate, data, expectedFile);
		assertTrue(photo.matches(regexp));
	}
	@Test
	public void testMatchesAddedDate(){
		String regexp = "2024-03.*";
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("test.jpg",expectedSize);
		String expectedTitle = "Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.of(2024, 3, 23, 3, 0, 0);
		LocalDateTime capturedDate = LocalDateTime.of(0, 1, 1, 0, 0, 0);
		PhotoMetadata data = new PhotoMetadata(capturedDate, null,null, null);

		Photo photo = new Photo(expectedTitle, expectedAddedDate, data, expectedFile);
		assertTrue(photo.matches(regexp));
	}
	@Test
	public void testMatchesCaptureDate(){
		String regexp = "2024-03.*";
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("test.jpg",expectedSize);
		String expectedTitle = "Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.of(0, 1, 1, 0, 0, 0);
		LocalDateTime capturedDate = LocalDateTime.of(2024, 3, 23, 3, 0, 0);
		PhotoMetadata data = new PhotoMetadata(capturedDate, null,null, null);

		Photo photo = new Photo(expectedTitle, expectedAddedDate, data, expectedFile);
		assertTrue(photo.matches(regexp));
	}
	
	@Test
	void testEquals() {
		File file1 = new File("test1.jpg");
		File file2 = new File("test2.jpg");
		File file3 = new File("test1.jpg");

		Photo photo1 = new Photo(file1);
		Photo photo2 = new Photo(file2);
		Photo photo3 = new Photo(file3);

		assertEquals(photo1,photo3);
		assertFalse(photo1.equals(photo2));
		assertFalse(photo2.equals(photo3));
	}

	@Test
	void testHashCode() {
		File file1 = new File("test1.jpg");
		File file2 = new File("test1.jpg");

		Photo photo1 = new Photo(file1);
		Photo photo2 = new Photo(file2);
		assertEquals(photo1.hashCode(),photo2.hashCode());
	}

	//COMPLETE ME

}