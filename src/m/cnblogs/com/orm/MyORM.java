package m.cnblogs.com.orm;

import m.cnblogs.com.orm.conn.Factory;
import m.cnblogs.com.orm.conn.Session;

public final class MyORM
{
    private static Factory sin = null;
    
    private static class SessionFactory implements Factory
    {
        private SessionFactory(String path) throws ClassNotFoundException
        {
            System.out.println("use the file path provided to init factory");
        }
        
        public Session openConnection()
        {
            return new Session();
        }

    }
    
    public static Factory getSessionFactory(String path) throws ClassNotFoundException
    {
        if (null == sin)
        {
            sin = new SessionFactory(path);
        }
        return sin;
    }
}


