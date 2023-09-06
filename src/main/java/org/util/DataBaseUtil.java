package org.util;

import org.apache.ibatis.session.SqlSession;
import org.entity.Course;
import org.entity.SC;
import org.mapper.Mapper;

import java.util.List;

/*
 *@author lyz
 *@version 2.0
 *@time 2023.3.5
 *@commit 对底层操作的一个封装，可以通过不同底层操作实现一个目标功能
 */
public class DataBaseUtil {
    private static SqlSession session;

    static {
        session = MyBatisUtil.getSession(true);
    }

    public static int CheckUser(String account, String pwd) {
        Mapper mapper = session.getMapper(Mapper.class);
        String cs = mapper.CheckStudent(account, pwd);
        String ct = mapper.CheckTeacher(account, pwd);
        if (account.equals(cs)) return 1;
        else if (account.equals(ct)) return 2;
        else return 0;
    }

    public static List<Course> SelectAllCourse() {
        Mapper mapper = session.getMapper(Mapper.class);
        return mapper.SelectAllCourse();
    }

    public static List<SC> SelectAllSCBySno(String Sno) {
        Mapper mapper = session.getMapper(Mapper.class);
        return mapper.SelectAllSCBySno(Sno);
    }

    public static int AddSC(String Cno, String Sno) {
        Mapper mapper = session.getMapper(Mapper.class);
        String Tno = mapper.SelectTnoFromCourse(Cno);
        return mapper.AddSC(Cno, Sno, Tno);
    }

    public static int DeleteSC(String Cno, String Sno) {
        Mapper mapper = session.getMapper(Mapper.class);
        return mapper.DeleteSC(Cno, Sno);
    }

    public static List<Course> SelectCourseByTno(String Tno) {
        Mapper mapper = session.getMapper(Mapper.class);
        return mapper.SelectCourseByTno(Tno);
    }

    public static int DeleteCourse(String Cno, String Tno) {
        Mapper mapper = session.getMapper(Mapper.class);
        return mapper.DeleteCourse(Cno, Tno);
    }

    public static int AddCourse(String Cno, String Cname, String Ccredit, String Cnum, String Tno) {
        Mapper mapper = session.getMapper(Mapper.class);
        return mapper.AddCourse(Cno, Cname, Ccredit, Cnum, Tno);
    }

    public static List<SC> SelectStudentAndGrade(String Cno, String Tno) {
        Mapper mapper = session.getMapper(Mapper.class);
        return mapper.SelectStudentAndGrade(Cno, Tno);
    }

    public static int UpdateGrade(String Grade, String Sno, String Cno, String Tno) {
        Mapper mapper = session.getMapper(Mapper.class);
        return mapper.UpdateGrade(Grade, Sno, Cno, Tno);
    }
}
