MiniMybatis
=========

。。。。基于XML的还未完成，目前只有基于注解的版本。。。。。。等待填坑
自己搭建Mybatis


使用方式
=========
配置文件
--------
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
1.sqlSession的初始化
--------
```java
//读取配置文件获取MySqlSessionFactory
MySqlSessionFactory build = new MySqlSessionFactoryBuilder().build(new File("./src/main/resource/mybatis.xml"));
//获得MySqlSession
MySqlSession mySqlSession = build.openSession(true);
```

2.定义bean
--------
```java
public class Student implements Serializable {
    private Integer id;
    private Integer classid;
    private String name;
    private Integer age;
    private String sex;
    private Integer addressid;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }
    public Integer getClassid() {
        return classid;
    }
    public void setClassid(Integer classid) {
        this.classid = classid;
    }
    public Integer getAddressid() {
        return addressid;
    }
    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", classid=" + classid +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", addressid=" + addressid +
                '}';
    }
}
```


3.定义接口
--------
```java
public interface IStudent {
    @MySelect("Select * from student")
    public List<Student> selectAll();
    @MySelect("Select * from student where id = #{id}")
    public Student select(Integer id);
    @MyInsert("insert into student values(#{id},#{classid},#{name},#{age},#{sex},#{addressid})")
    public boolean Insert(Integer id,Integer classid,String name,Integer age,String sex,Integer addressid);
    @MyDelete("Delete from student where id = #{id}")
    public boolean Delete(Integer id);
    @MyUpdate("UPDATE student set name=#{name} WHERE id=#{id}")
    public boolean Update(String name,Integer id);
}
```
4.通过SqlSession获取Map实例进行相应操作
--------
```java
//获得mapper的实例
IStudent mapper = mySqlSession.getMapper(IStudent.class);
//进行操作
List<Student> students = mapper.selectAll();
System.out.println(students);
```

