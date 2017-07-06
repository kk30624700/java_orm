package m.cnblogs.com.orm.conn;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import m.cnblogs.com.orm.anno.ID;
import m.cnblogs.com.orm.anno.Property;
import m.cnblogs.com.orm.anno.Table;

public class Session
{
    
    public Session()
    {
        System.out.println("SESSION INIT");
    }
    
    public <T> void save(T obj) throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException
    {
        Class<? extends Object> cls = obj.getClass();
        
        StringBuffer sql = new StringBuffer();
        
        sql.append("INSERT INTO ");
        
        Table tbl = (Table) cls.getAnnotation(Table.class);
        
        if (null != tbl)
        {
            sql.append(tbl.name());
        }
        else
        {
            sql.append(cls.getName().substring(cls.getName().lastIndexOf(".") + 1));
        }
        
        sql.append("( ");
        
        Field[] fields = cls.getDeclaredFields();
        StringBuffer valueSql = new StringBuffer();
        
        for (Field field : fields)
        {
            
            Property proper = field.getAnnotation(Property.class);
            
            // 获得字段第一个字母大写
            String methodFirstLetter = field.getName().substring(0, 1).toUpperCase();
            // 转换成字段的get方法
            String getterName = "get" + methodFirstLetter + field.getName().substring(1);
            
            Method getMethod = cls.getMethod(getterName, new Class[]
            {});
            // 这个对象字段get方法的值
            Object value = getMethod.invoke(obj, new Object[]
            {});
            
            if (field.getType().getName().equals(java.lang.String.class.getName()))
            {
                valueSql.append("'" + value + "'").append(",");
            }
            else
            {
                valueSql.append(value).append(",");
            }
            
            if (null != proper)
            {
                sql.append(proper.name()).append(",");
            }
            else
            {
                sql.append(field.getName()).append(",");
            }
        }
        
        valueSql.deleteCharAt(valueSql.length() - 1);
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" ) VALUES( ");
        sql.append(valueSql);
        sql.append(" )");
        
        System.out.println(sql.toString());
    }
    
    public <T> void query(T obj) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        Class<? extends Object> cls = obj.getClass();
        
        StringBuffer sql = new StringBuffer();
          
        sql.append("SELETE FROM ");  
          
        Table tbl = (Table)cls.getAnnotation(Table.class);
        
        if (null != tbl) {  
            sql.append(tbl.name());  
        } else {  
            sql.append(cls.getName().substring(cls.getName().lastIndexOf(".")+1));  
        }  
          
        Field[] fields = cls.getDeclaredFields();  
        
        for (Field field : fields) {
            
            if (null != field.getAnnotation(ID.class))
            {
                //获得字段第一个字母大写   
                String methodFirstLetter = field.getName().substring(0,1).toUpperCase();   
                //转换成字段的get方法  
                String getterName = "get" + methodFirstLetter + field.getName().substring(1);    
                      
                Method getMethod = cls.getMethod(getterName, new Class[] {});  
                //这个对象字段get方法的值   
                Object value = getMethod.invoke(obj, new Object[] {});
                sql.append(" WHERE ").append(field.getName()).append("=");
                if (field.getType().getName().equals(java.lang.String.class.getName())) {  
                    sql.append("'" + value + "'");  
                } else {  
                    sql.append(value);  
                } 
            }
            
   
        }  
        
        System.out.println(sql.toString());
        
    }
    
    public <T> void update(T obj) throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException
    {
        Class<? extends Object> cls = obj.getClass();
        
        StringBuffer sql = new StringBuffer();
        StringBuffer condition = new StringBuffer();
        
        sql.append("UPDATE ");
        
        Table tbl = (Table) cls.getAnnotation(Table.class);
        
        if (null != tbl)
        {
            sql.append(tbl.name());
        }
        else
        {
            sql.append(cls.getName().substring(cls.getName().lastIndexOf(".") + 1));
        }
        
        sql.append(" SET ");
        
        Field[] fields = cls.getDeclaredFields();
        
        for (Field field : fields)
        {
            
            Property proper = field.getAnnotation(Property.class);
            
            // 获得字段第一个字母大写
            String methodFirstLetter = field.getName().substring(0, 1).toUpperCase();
            // 转换成字段的get方法
            String getterName = "get" + methodFirstLetter + field.getName().substring(1);
            
            Method getMethod = cls.getMethod(getterName, new Class[]
            {});
            // 这个对象字段get方法的值
            Object value = getMethod.invoke(obj, new Object[]
            {});
            
            if (null != field.getAnnotation(ID.class))
            {
                condition.append(" WHERE ").append(field.getName()).append("=");
                if (field.getType().getName().equals(java.lang.String.class.getName()))
                {
                    condition.append("'" + value + "'");
                }
                else
                {
                    condition.append(value);
                }
            }
            else
            {
                String tmp = "";
                
                if (field.getType().getName().equals(java.lang.String.class.getName()))
                {
                    tmp = "'" + value + "'";
                }
                else
                {
                    tmp = value + "";
                }
                
                if (null != proper)
                {
                    sql.append(proper.name()).append("=").append(tmp).append(",");
                }
                else
                {
                    sql.append(field.getName()).append("=").append(tmp).append(",");
                }
            }
            
        }
        
        sql.deleteCharAt(sql.length() - 1);
        
        sql.append(condition);
        
        System.out.println(sql.toString());
    }
    
    public <T> void delete(T obj) throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException
    {
        Class<? extends Object> cls = obj.getClass();
        
        StringBuffer sql = new StringBuffer();
        
        sql.append("DELETE FROM ");
        
        Table tbl = (Table) cls.getAnnotation(Table.class);
        
        if (null != tbl)
        {
            sql.append(tbl.name());
        }
        else
        {
            sql.append(cls.getName().substring(cls.getName().lastIndexOf(".") + 1));
        }
        
        Field[] fields = cls.getDeclaredFields();
        
        for (Field field : fields)
        {
            
            if (null != field.getAnnotation(ID.class))
            {
                // 获得字段第一个字母大写
                String methodFirstLetter = field.getName().substring(0, 1).toUpperCase();
                // 转换成字段的get方法
                String getterName = "get" + methodFirstLetter + field.getName().substring(1);
                
                Method getMethod = cls.getMethod(getterName, new Class[]
                {});
                // 这个对象字段get方法的值
                Object value = getMethod.invoke(obj, new Object[]
                {});
                sql.append(" WHERE ").append(field.getName()).append("=");
                if (field.getType().getName().equals(java.lang.String.class.getName()))
                {
                    sql.append("'" + value + "'");
                }
                else
                {
                    sql.append(value);
                }
            }
            
        }
        
        System.out.println(sql.toString());
    }
    
    public void close()
    {
        System.out.println("SESSION CLOSE");
    }
}
