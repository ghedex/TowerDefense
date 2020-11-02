package enemy;

public class Entity {

    public final String entityFile;
    public int damage = 0;
    public int speed;

    public Entity(String entityFile, int damage, int speed){
        this.entityFile = entityFile;
        this.damage = damage;
        this.speed = speed;
    }

}
