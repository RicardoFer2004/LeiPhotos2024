package leiphotos.domain.core;

public class ExistingMockFile extends MockFile{
    private final boolean exists;
    public ExistingMockFile(String pathName,int size,boolean exists){
        super(pathName,size);
        this.exists = exists;
    }
    @Override
    public boolean exists(){
        return exists;
    }
}
