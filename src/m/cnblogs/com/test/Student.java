package m.cnblogs.com.test;

import m.cnblogs.com.orm.anno.ID;
import m.cnblogs.com.orm.anno.Property;
import m.cnblogs.com.orm.anno.Table;

@Table(name = "STUDENT")
public class Student
{
    @ID
    @Property(name = "ID", type = "int")
    private int id;
    @Property(name = "NAME", type = "varchar")
    private String name;
    @Property(name = "AGE", type = "int")
    private int age;
    
    public Student(int id, String name, int age)
    {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
    }
    public Integer getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int getAge()
    {
        return age;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
}
