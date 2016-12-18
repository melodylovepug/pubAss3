package app;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ancestor {
    @Id
    public String name;
}
