# MiniMybatis
自己搭建Mybatis
##配置文件
```xml
<configuration>
        <environment id="development">
                <property name="driver" value="xxxx"/>
                <property name="url" value="xxxx"/>
                <property name="username" value="xxxx"/>
                <property name="password" value="xxxx"/>
        </environment>
    <mappers>
        <mapper resource="Mybatis2/mapping/AddressMapper.xml"/>
        <mapper class="Mybatis2/mapping/StudentMapper.xml"/>
    </mappers>
</configuration>
```

## 查找操作

### 1.定义接口
```java
public interface IStudent {
    @MySelect("Select * from student")
    public List<Student> selectAll();
}
```

### 2.读取配置文件初始化SqlSession
```java
//读取配置文件获取MySqlSessionFactory
MySqlSessionFactory build = new MySqlSessionFactoryBuilder().build(new File("./src/main/resource/mybatis.xml"));
//获得MySqlSession
MySqlSession mySqlSession = build.openSession(true);
//获得mapper的实例
IStudent mapper = mySqlSession.getMapper(IStudent.class);
//进行操作
List<Student> students = mapper.selectAll();
System.out.println(students);
```
