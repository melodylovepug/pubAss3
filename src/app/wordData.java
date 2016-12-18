package app;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class wordData {
    @Parent
    Key<ancestor> ancestorName;
    @Id
    @Index
    public String word;
    @Index
    public int count;
    public int sources;



    public wordData(){
    }

    public wordData(String name, String word, int count, int sources) {
        this();
        if( name != null ) {
            ancestorName = Key.create(ancestor.class, name);  // Creating the Ancestor key
        } else {
            ancestorName = Key.create(ancestor.class, "default");
        }
        this.word = word;
        this.count = count;
        this.sources = sources;

    }

}
