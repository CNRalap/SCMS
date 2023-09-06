package org.mapper;

import org.apache.ibatis.annotations.*;
import org.entity.Course;
import org.entity.SC;

import java.util.List;

/*
 *@author lyz
 *@version 2.0
 *@time 2023.3.5
 *@commit 用于mybatis的匹配
 */
public interface Mapper {
    @Select("select Sno from student where Sno=#{Sno} and Spwd=#{Spwd}")
    String CheckStudent(@Param("Sno") String Sno, @Param("Spwd") String Spwd);

    @Select("select Tno from teacher where Tno=#{Tno} and Tpwd=#{Tpwd}")
    String CheckTeacher(@Param("Tno") String Tno, @Param("Tpwd") String Tpwd);

    @Select("select * from course ")
    List<Course> SelectAllCourse();

    @Select("select * from sc where Sno=#{Sno}")
    List<SC> SelectAllSCBySno(String Sno);

    @Select("select Tno from course where Cno=#{Cno}")
    String SelectTnoFromCourse(String Cno);

    @Insert("insert into sc(Cno,Sno,Tno) values (#{Cno},#{Sno},#{Tno})")
    int AddSC(@Param("Cno") String Cno, @Param("Sno") String Sno, @Param("Tno") String Tno);

    @Delete("delete from sc where Cno=#{Cno} and Sno=#{Sno}")
    int DeleteSC(@Param("Cno") String Cno, @Param("Sno") String Sno);

    @Select("select * from course where Tno=#{Tno}")
    List<Course> SelectCourseByTno(String Tno);

    @Delete("delete from course where Cno=#{Cno} and Tno=#{Tno}")
    int DeleteCourse(@Param("Cno") String Cno, @Param("Tno") String Tno);

    @Insert("insert into course values (#{Cno},#{Cname},#{Ccredit},#{Cnum},#{Tno})")
    int AddCourse(@Param("Cno") String Cno, @Param("Cname") String Cname, @Param("Ccredit") String Ccredit, @Param("Cnum") String Cnum, @Param("Tno") String Tno);

    @Select("select * from sc where Cno=#{Cno} and Tno=#{Tno}")
    List<SC> SelectStudentAndGrade(@Param("Cno") String Cno, @Param("Tno") String Tno);

    @Update("update sc set Grade=#{Grade} where Sno=#{Sno} and Cno=#{Cno} and Tno=#{Tno}")
    int UpdateGrade(@Param("Grade")String Grade,@Param("Sno")String Sno,@Param("Cno")String Cno,@Param("Tno")String Tno);
}
