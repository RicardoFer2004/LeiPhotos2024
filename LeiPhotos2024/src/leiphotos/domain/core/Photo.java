package leiphotos.domain.core;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;
import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.RegExpMatchable;
import leiphotos.utils.Utils;
/**
 * This class represents a Photo 
 * from a jpeg file
 */
public class Photo implements IPhoto,RegExpMatchable{
    private File file;
    private String title;
    private PhotoMetadata metaData;
    private LocalDateTime dateOfAdd;
    private boolean isFavourite;
    private long size;
    /**
     * Creates a Photo object with the given
     * title,date when it was added to the app,metadata
     * and file.
     * The state of any and all received objects is not changed
     * @param title The title of the photo
     * @param dateAdded The date when the photo was added to the app
     * @param metaData The photo's metadata
     * @param file The photo's file
     * @requires {@code dateAdded != null && metaData != null && file!= null && file.exists()}.
     * @ensures {@code title != null && size >= 0 && !isFavourite}.If title is null,the photo's title will be ""
     */
    public Photo(String title, LocalDateTime dateAdded, PhotoMetadata metaData, File file){
        this.file = file;
        this.title = title == null ? "" : title;
        this.metaData = metaData;
        this.dateOfAdd = dateAdded;
        this.size = file.length();
    }
    /**
     * Creates a photo object where all atributes have their default value.
     * This constructor should only be used for testing purposes.
     * The use of this constructor will breach some of this class'
     * methods contracts and thus, should only be used in a controlled setting.
     */
    protected Photo(){
    }
    /**
     * Creates a photo object where all atributes except file have their default value.
     * This constructor should only be used for testing purposes.
     * The use of this constructor will breach some of this class'
     * methods contracts and thus, should only be used in a controlled setting.
     * @param file the photo's file
     */
    protected Photo(File file){
        this.file = file;
    }
    @Override
    public String title() {
        return this.title;
    }

    @Override
    public LocalDateTime capturedDate() {
        return this.metaData.dateOfCapture();
    }

    @Override
    public LocalDateTime addedDate() {
        return this.dateOfAdd;
    }

    @Override
    public boolean isFavourite() {
        return this.isFavourite;
    }

    @Override
    public void toggleFavourite() {
        this.isFavourite = !isFavourite;
    }

    @Override
    public Optional<? extends GPSCoordinates> getPlace() {
        Optional<GPSCoordinates> optional = Optional.empty();
        if(metaData.location() != null){
            optional = Optional.of(metaData.location());
        }

        return optional;
    }

    @Override
    public long size() {
        return this.size;
    }

    @Override
    public File file() {
        return this.file;
    }

    @Override
    public boolean matches(String regexp) {
        return title.matches(regexp) || dateOfAdd.toString().matches(regexp) 
               || metaData.matches(regexp) || file.getName().matches(regexp);
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("File:" + file.getPath());
        sb.append(System.lineSeparator());
        sb.append("Title:" + title + " Added:" + Utils.formatString(dateOfAdd.toString(),"....-..-.. ..:..") + 
                  " Size:" + Utils.separateNumber(',',size));
        sb.append(System.lineSeparator());
        sb.append(metaData.toString());

        return sb.toString();
    }
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof IPhoto)){
            return false;
        }
        IPhoto otherPhoto = (IPhoto) obj;
        return this.hashCode() == otherPhoto.hashCode();
    }
    @Override
    public int hashCode(){
        return file.getAbsolutePath().hashCode();
    }
    
}
