package a.getter_setter;

/**
 * Created by TUSHAR on 11-04-18.
 */

public class DeveloperDetails {
    String name,title;
    int image;
    public void setName(String name)
    {
        this.name = name;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setImage(int image)
    {
        this.image = image;
    }
    public String getName()
    {
        return name;
    }
    public String getTitle()
    {
        return title;
    }
    public int getImage()
    {
        return image;
    }
}
