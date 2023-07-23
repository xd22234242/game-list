package com.digu.fivechesses.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.digu.fivechesses.entity.User;

//继承JpaRepository来完成对数据库的操作
public interface UserRepository extends JpaRepository<User, Long>{
    // @Param 代替参数占位符，  hql或者sql里就用  :firstname替换   方法里的参数顺序可以打乱
    @Query(value="select count(*) from User where username=?1", nativeQuery=true)
    int existUserName(String userName);

    @Query(value="select * from User where username=?1", nativeQuery=true)
    User getUserByUserName(String userName);

    //如果是更新或者删除操作，方法上面要加@Modifying      默认开启的事务只是可读的，更新操作加入@Modifying 就会关闭可读
    @Modifying
    @Query(value="update User set password=?2 where username=?1", nativeQuery=true)
    void updatePasswordByUsername(String username, String newPassword);
}
