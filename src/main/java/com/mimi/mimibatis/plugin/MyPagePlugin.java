package com.mimi.mimibatis.plugin;


import com.mimi.mimibatis.util.PageUtil;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;


//args : 你需要mybatis传入什么参数给你 type :你需要拦截的对象  method=要拦截的方法
@Intercepts(@Signature(type = StatementHandler.class,method ="prepare",args = {Connection.class,Integer.class}))
public class MyPagePlugin implements Interceptor {

    String databaseType = "";

    String pageSqlId = "";

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getPageSqlId() {
        return pageSqlId;
    }

    public void setPageSqlId(String pageSqlId) {
        this.pageSqlId = pageSqlId;
    }

    //我们自己拦截器里面的逻辑
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

//        statementHandler.getDelegate().getmappedStatement().getId();
//        Field delegate = StatementHandler.class.getDeclaredField("delegate");
//        delegate.setAccessible(true);
//        Object o = delegate.get(statementHandler);
//        o.getClass().getDeclaredField("mappedStatement").

        MetaObject metaObject = MetaObject.forObject(
                statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,new DefaultReflectorFactory());
        String sqlId = (String)metaObject.getValue("delegate.mappedStatement.id");
        //判断一下是否是分页
//        <!--第一步 执行一条couunt语句-->
        //1.1拿到连接
        //1.2 预编译SQL语句 拿到绑定的sql语句
        //1.3 执行 count语句   怎么返回你执行count的结果

//    <!--第二部 重写sql  select * from luban_product  limit start,limit -->
        //2.1 ？ 怎么知道 start 和limit
        //2.2拼接start 和limit
        //2.3 替换原来绑定sql
        //拿到原来应该执行的sql
        if (sqlId.matches(pageSqlId)){
            ParameterHandler parameterHandler = statementHandler.getParameterHandler();
            //原来应该执行的sql
            String sql = statementHandler.getBoundSql().getSql();
            //sql= select * from  product    select count(0) from (select * from  product) as a
            //select * from luban_product where name = #{name}
            //执行一条count语句
            //拿到数据库连接对象
            Connection connection = (Connection) invocation.getArgs()[0];
            String countSql = "select count(0) from ("+sql+") a";
            System.out.println(countSql);
            //渲染参数
            PreparedStatement preparedStatement = connection.prepareStatement(countSql);
            //条件交给mybatis
            parameterHandler.setParameters(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            int count =0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
            //获得你传进来的参数对象
            Map<String, Object> parameterObject = (Map<String, Object>) parameterHandler.getParameterObject();
            //limit  page
            PageUtil pageUtil = (PageUtil) parameterObject.get("page");
            //limit 1 ,10  十条数据   总共可能有100   count 要的是 后面的100
            pageUtil.setCount(count);

            //拼接分页语句(limit) 并且修改mysql本该执行的语句
            String pageSql = getPageSql(sql, pageUtil);
            metaObject.setValue("delegate.boundSql.sql",pageSql);
            System.out.println(pageSql);
        }
        //推进拦截器调用链
        return invocation.proceed();
    }

    public String getPageSql(String sql,PageUtil pageUtil){
        if(databaseType.equals("mysql")){
            return sql+" limit "+pageUtil.getStart()+","+pageUtil.getLimit();
        }else if(databaseType.equals("oracle")){
            //拼接oracle的分语句
        }

        return sql+" limit "+pageUtil.getStart()+","+pageUtil.getLimit();
    }


    //需要你返回一个动态代理后的对象  target :StatementHandler
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    //会传入配置文件内容 用户可根据配置文件自定义
    @Override
    public void setProperties(Properties properties) {

    }
}
