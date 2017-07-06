package m.cnblogs.com.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.junit.Test;

import m.cnblogs.com.orm.MyORM;
import m.cnblogs.com.orm.conn.Session;

public class OrmTest
{
    
    @Test
    public void test() throws ClassNotFoundException, SQLException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        Session ss = MyORM.getSessionFactory("db.properties").openConnection();
        
        ss.save(new Student(1001, "大山", 27));
        ss.update(new Student(1001, "大山", 27));
        ss.delete(new Student(1001, "大山", 27));
        ss.query(new Student(1001, "大山", 27));
        
        ss.close();
    }
    
}

