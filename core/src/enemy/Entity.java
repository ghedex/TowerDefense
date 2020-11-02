package enemy;

public class Entity {

    public final String entityFile;
    public int damage = 0;

    public Entity(String entityFile, int damage){
        this.entityFile = entityFile;
        this.damage = damage;
    }

}
