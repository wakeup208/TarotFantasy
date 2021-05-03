package com.wakeup.tarot.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.wakeup.tarot.data.MapData;
import com.wakeup.tarot.quiz.QuizContract.QuestionTable;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AwesomeQuiz.db";
    public static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " +
                QuizContract.CategoriesTable.TABLE_NAME + " ( " +
                QuizContract.CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.CategoriesTable.COLUMN_NAME + " TEXT " + ")";

        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_URI + " INTEGER, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER, " +
                //QuestionTable.COLUMN_DIFFICULTY + " TEXT," +
                QuestionTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                QuizContract.CategoriesTable.TABLE_NAME + "(" + QuizContract.CategoriesTable._ID + ")" + "ON DELETE CASCADE" + ")";

        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillCategoriesTable();
        //fillQuestionTable();
        fillQuestionTarotTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Major Arcana");
        insertCategory(c1);

        Category c2 = new Category("Suit of Wands");
        insertCategory(c2);

        Category c3 = new Category("Suit of Cups");
        insertCategory(c3);

        Category c4 = new Category("Suit of Swords");
        insertCategory(c4);

        Category c5 = new Category("Suit of Pentacles");
        insertCategory(c5);
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(QuizContract.CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionTarotTable() {
        Question q1 = new Question("Ý nghĩa của lá bài The Fool ?",
                "Hành Động, Nhận Thức Có Ý Thức, Tập Trung, Quyền Lực",
                "Sức Mạnh, Nhẫn Nại, Lòng Trắc Ẩn, Kiểm Soát Mềm Mỏng",
                "Nuôi Nấng, Sung Túc, Trù Phú, Tri Giác, Thiên Nhiên",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                4, MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q1);

        Question q2 = new Question("Ý nghĩa của lá bài The Magician ?",
                "Nội Quan (*tự xem xét nội tâm), Tìm Kiếm, Dẫn Dắt, Đơn Độc",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                "Hành Động, Nhận Thức Có Ý Thức, Tập Trung, Quyền Lực",
                "Sự Hợp Nhất, Sự Hoàn Thành Trọn Vẹn, Sự Kết Nối, Sự Thỏa Mãn",
                3,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q2);

        Question q3 = new Question("Ý nghĩa của lá bài The High Priestess ?",
                "Thụ Động, Vô Thức, Tiềm Năng, Bí Ẩn",
                "Làm Cha, Cấu Trúc, Nắm Quyền, Luật Lệ",
                "Chừng Mực, Cân Bằng, Sức Khỏe, Kết Hợp",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                1,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q3);

        Question q4 = new Question("Ý nghĩa của lá bài The Empress ?",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                "Nuôi Nấng, Sung Túc, Trù Phú, Tri Giác, Thiên Nhiên",
                "Thay Đổi Đột Ngột, Giải Thoát, Sa Sút, Mặc Khải",
                "Công Lý, Trách Nhiệm, Quyết Định, Nguyên Nhân – Kết Quả",
                2,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q4);

        Question q5 = new Question("Ý nghĩa của lá bài The Emperor ?",
                "Hy Vọng, Cảm Hứng, Hào Phóng, Yên Bình",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Làm Cha, Cấu Trúc, Nắm Quyền, Luật Lệ",
                4,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q5);

        Question q6 = new Question("Ý nghĩa của lá bài The Hierophant ?",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Nội Quan (*tự xem xét nội tâm), Tìm Kiếm, Dẫn Dắt, Đơn Độc",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                1,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q6);

        Question q7 = new Question("Ý nghĩa của lá bài The Lovers ?",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                "Mối Quan Hệ, Bản Năng Giới Tính, Niềm Tin Cá Nhân, Giá Trị",
                "Hy Vọng, Cảm Hứng, Hào Phóng, Yên Bình",
                "Công Lý, Trách Nhiệm, Quyết Định, Nguyên Nhân – Kết Quả",
                2,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q7);

        Question q8 = new Question("Ý nghĩa của lá bài The Chariot ?",
                "Chiến Thắng, Ước Muốn, Tự Khẳng Định, Khó Kiểm Soát",
                "Kết Thúc, Thay Đổi, Loại Bỏ, Tác Động Tuyệt Đối",
                "Cảnh Nô Lệ, Chủ Nghĩa Vật Chất, Sự Ngu Dốt, Sự Tuyệt Vọng",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                1,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q8);

        Question q9= new Question("Ý nghĩa của lá bài Strength ?",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                "Sức Mạnh, Nhẫn Nại, Lòng Trắc Ẩn, Kiểm Soát Mềm Mỏng",
                "Thụ Động, Vô Thức, Tiềm Năng, Bí Ẩn",
                3,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q9);

        Question q10= new Question("Ý nghĩa của lá bài The Hermit ?",
                "Chiến Thắng, Ước Muốn, Tự Khẳng Định, Khó Kiểm Soát",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                "Thay Đổi Đột Ngột, Giải Thoát, Sa Sút, Mặc Khải",
                "Nội Quan (*tự xem xét nội tâm), Tìm Kiếm, Dẫn Dắt, Đơn Độc",
                4,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q10);

        Question q11= new Question("Ý nghĩa của lá bài Wheel of Fortune ?",
                "Mối Quan Hệ, Bản Năng Giới Tính, Niềm Tin Cá Nhân, Giá Trị",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                "Hành Động, Nhận Thức Có Ý Thức, Tập Trung, Quyền Lực",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                2,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q11);

        Question q12= new Question("Ý nghĩa của lá bài JUSTICE ?",
                "Công Lý, Trách Nhiệm, Quyết Định, Nguyên Nhân – Kết Quả",
                "Nỗi Sợ Hãi, Ảo Ảnh, Sự Tưởng Tượng, Sự Hoang Mang",
                "Sự Khai Sáng, Sự Vĩ Đại, Sức Sống, Sự Tự Tin",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                1,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q12);

        Question q13= new Question("Ý nghĩa của lá bài The Hanged Man ?",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Thay Đổi Đột Ngột, Giải Thoát, Sa Sút, Mặc Khải",
                "Nuôi Nấng, Sung Túc, Trù Phú, Tri Giác, Thiên Nhiên",
                1,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q13);

        Question q14= new Question("Ý nghĩa của lá bài Death ?",
                "Sự Khai Sáng, Sự Vĩ Đại, Sức Sống, Sự Tự Tin",
                "Kết Thúc, Thay Đổi, Loại Bỏ, Tác Động Tuyệt Đối",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                "Nội Quan (*tự xem xét nội tâm), Tìm Kiếm, Dẫn Dắt, Đơn Độc",
                2,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q14);

        Question q15= new Question("Ý nghĩa của lá bài Temperance ?",
                "Chừng Mực, Cân Bằng, Sức Khỏe, Kết Hợp",
                "Nỗi Sợ Hãi, Ảo Ảnh, Sự Tưởng Tượng, Sự Hoang Mang",
                "Sự Khai Sáng, Sự Vĩ Đại, Sức Sống, Sự Tự Tin",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                1,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q15);

        Question q16= new Question("Ý nghĩa của lá bài The Devil ?",
                "Cảnh Nô Lệ, Chủ Nghĩa Vật Chất, Sự Ngu Dốt, Sự Tuyệt Vọng",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                "Hành Động, Nhận Thức Có Ý Thức, Tập Trung, Quyền Lực",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                1,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q16);

        Question q17= new Question("Ý nghĩa của lá bài The Tower ?",
                "Hy Vọng, Cảm Hứng, Hào Phóng, Yên Bình",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Thay Đổi Đột Ngột, Giải Thoát, Sa Sút, Mặc Khải",
                4,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q17);

        Question q18= new Question("Ý nghĩa của lá bài The Star ?",
                "Sự Hợp Nhất, Sự Hoàn Thành Trọn Vẹn, Sự Kết Nối, Sự Thỏa Mãn",
                "Nuôi Nấng, Sung Túc, Trù Phú, Tri Giác, Thiên Nhiên",
                "Hy Vọng, Cảm Hứng, Hào Phóng, Yên Bình",
                "Thụ Động, Vô Thức, Tiềm Năng, Bí Ẩn",
                3,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q18);

        Question q19= new Question("Ý nghĩa của lá bài The Moon ?",
                "Nỗi Sợ Hãi, Ảo Ảnh, Sự Tưởng Tượng, Sự Hoang Mang",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                "Hy Vọng, Cảm Hứng, Hào Phóng, Yên Bình",
                "Làm Cha, Cấu Trúc, Nắm Quyền, Luật Lệ",
                1,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q19);

        Question q20= new Question("Ý nghĩa của lá bài The Sun ?",
                "Nội Quan (*tự xem xét nội tâm), Tìm Kiếm, Dẫn Dắt, Đơn Độc",
                "Mối Quan Hệ, Bản Năng Giới Tính, Niềm Tin Cá Nhân, Giá Trị",
                "Sự Khai Sáng, Sự Vĩ Đại, Sức Sống, Sự Tự Tin",
                "Sức Mạnh, Nhẫn Nại, Lòng Trắc Ẩn, Kiểm Soát Mềm Mỏng",
                3,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q20);

        Question q21= new Question("Ý nghĩa của lá bài Judgement ?",
                "Kết Thúc, Thay Đổi, Loại Bỏ, Tác Động Tuyệt Đối",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                "Sức Mạnh, Nhẫn Nại, Lòng Trắc Ẩn, Kiểm Soát Mềm Mỏng",
                3,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q21);

        Question q22= new Question("Ý nghĩa của lá bài The World ?",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                "Sức Mạnh, Nhẫn Nại, Lòng Trắc Ẩn, Kiểm Soát Mềm Mỏng",
                "Sự Hợp Nhất, Sự Hoàn Thành Trọn Vẹn, Sự Kết Nối, Sự Thỏa Mãn",
                4,MapData.arrCardImage_R_Id[0], Category.MajorArcana);
        insertQuestion(q22);

        //End MajorArcana
        Question q23= new Question("Ý nghĩa của lá bài Ace of Cups ?",
                "Sự kết nối, Đình chiến, Sự hấp dẫn",
                "Dễ xúc động, Trực giác, Thân mật, Yêu thương",
                "Niềm vui, An bình, Gia đình",
                "Sức mạnh cảm xúc, Trực giác, Thân mật, Tình yêu",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q23);

        Question q24= new Question("Ý nghĩa của lá bài 2 of Cups ?",
                "Sự kết nối, Đình chiến, Sự hấp dẫn",
                "Lãng mạn – dễ xúc động, Giàu tưởng tượng – không thực tế, Nhạy cảm – thất thường, Tinh tế – quá tế nhị, Nội tâm – nhút nhát",
                "Hoàn thành ước nguyện, Hài lòng, Thỏa mãn thể chất",
                "Chỉ biết mỗi bản thân, Sự hờ hững, Tiến vào nội tâm",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q24);

        Question q25= new Question("Ý nghĩa của lá bài 3 of Cups ?",
                "Sức mạnh cảm xúc, Trực giác, Thân mật, Tình yêu",
                "Tâm trạng phấn khởi, Tình bạn, Cộng đồng",
                "Ý nghĩa sâu thẳm – chân lý, Tiến tới, Mệt mỏi",
                "Dễ xúc động, Trực giác, Thân mật, Yêu thương",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q25);

        Question q26= new Question("Ý nghĩa của lá bài 4 of Cups ?",
                "Niềm vui, An bình, Gia đình",
                "Chỉ biết mỗi bản thân, Sự hờ hững, Tiến vào nội tâm",
                "Sức mạnh cảm xúc, Trực giác, Thân mật, Tình yêu",
                "Lãng mạn – dễ xúc động, Giàu tưởng tượng – không thực tế, Nhạy cảm – thất thường, Tinh tế – quá tế nhị, Nội tâm – nhút nhát",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q26);

        Question q27= new Question("Ý nghĩa của lá bài 5 of Cups ?",
                "Thiện chí, Ngây thơ/khờ dại, Thời thơ ấu",
                "Mất mát, Tang thương, Hối tiếc",
                "Niềm vui, An bình, Gia đình",
                "Khôn ngoan, Trầm tĩnh, Tài ngoại giao, Chăm sóc, Khoan dung",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q27);

        Question q28= new Question("Ý nghĩa của lá bài 6 of Cups ?",
                "Khôn ngoan, Trầm tĩnh, Tài ngoại giao, Chăm sóc, Khoan dung",
                "Mất mát, Tang thương, Hối tiếc",
                "Thiện chí, Ngây thơ/khờ dại, Thời thơ ấu",
                "Dễ xúc động, Trực giác, Thân mật, Yêu thương",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q28);

        Question q29= new Question("Ý nghĩa của lá bài 7 of Cups ?",
                "Ý nghĩa sâu thẳm – chân lý, Tiến tới, Mệt mỏi",
                "Sức mạnh cảm xúc, Trực giác, Thân mật, Tình yêu",
                "Ước vọng, Những lựa chọn, Sự tiêu pha",
                "Chỉ biết mỗi bản thân, Sự hờ hững, Tiến vào nội tâm",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q29);

        Question q30= new Question("Ý nghĩa của lá bài 8 of Cups ?",
                "Mất mát, Tang thương, Hối tiếc",
                "Tâm trạng phấn khởi, Tình bạn, Cộng đồng",
                "Ý nghĩa sâu thẳm – chân lý, Tiến tới, Mệt mỏi",
                "Dễ xúc động, Trực giác, Thân mật, Yêu thương",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q30);

        Question q31= new Question("Ý nghĩa của lá bài 9 of Cups ?",
                "Thiện chí, Ngây thơ/khờ dại, Thời thơ ấu",
                "Mất mát, Tang thương, Hối tiếc",
                "Niềm vui, An bình, Gia đình",
                "Hoàn thành ước nguyện, Hài lòng, Thỏa mãn thể chất",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q31);

        Question q32= new Question("Ý nghĩa của lá bài 10 of Cups ?",
                "Sức mạnh cảm xúc, Trực giác, Thân mật, Tình yêu",
                "Ước vọng, Những lựa chọn, Sự tiêu pha",
                "Chỉ biết mỗi bản thân, Sự hờ hững, Tiến vào nội tâm",
                "Niềm vui, An bình, Gia đình",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q32);

        Question q33= new Question("Ý nghĩa của lá bài King of Cups ?",
                "Lãng mạn – dễ xúc động, Giàu tưởng tượng – không thực tế, Nhạy cảm – thất thường, Tinh tế – quá tế nhị, Nội tâm – nhút nhát",
                "Khôn ngoan, Trầm tĩnh, Tài ngoại giao, Chăm sóc, Khoan dung",
                "Ý nghĩa sâu thẳm – chân lý, Tiến tới, Mệt mỏi",
                "Dễ xúc động, Trực giác, Thân mật, Yêu thương",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q33);

        Question q34= new Question("Ý nghĩa của lá bài Queen of Cups ?",
                "Thương yêu, Nhân hậu, Trực giác, Tâm lý, Tâm linh",
                "Ước vọng, Những lựa chọn, Sự tiêu pha",
                "Chỉ biết mỗi bản thân, Sự hờ hững, Tiến vào nội tâm",
                "Niềm vui, An bình, Gia đình",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q34);

        Question q35= new Question("Ý nghĩa của lá bài Knight of Cups ?",
                "Lãng mạn – dễ xúc động, Giàu tưởng tượng – không thực tế, Nhạy cảm – thất thường, Tinh tế – quá tế nhị, Nội tâm – nhút nhát",
                "Thiện chí, Ngây thơ/khờ dại, Thời thơ ấu",
                "Mất mát, Tang thương, Hối tiếc",
                "Niềm vui, An bình, Gia đình",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q35);

        Question q36= new Question("Ý nghĩa của lá bài Page of Cups ?",
                "Dễ xúc động, Trực giác, Thân mật, Yêu thương",
                "Ước vọng, Những lựa chọn, Sự tiêu pha",
                "Ý nghĩa sâu thẳm – chân lý, Tiến tới, Mệt mỏi",
                "Chỉ biết mỗi bản thân, Sự hờ hững, Tiến vào nội tâm",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfCups);
        insertQuestion(q36);
        //End Cups
        Question q37= new Question("Ý nghĩa của lá bài Ace of Pentacles ?",
                "Cân bằng, Linh hoạt, Vui vẻ",
                "Làm việc nhóm, Lập kế hoạch, Thành tựu/xuất sắc",
                "Có sự ảnh hưởng, Thực tế, Thịnh vượng, Tin tưởng/đáng tin cậy",
                "Sức mạnh vật chất, Sự thịnh vượng, Tính khả thi, Niềm tin tưởng",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q37);

        Question q38= new Question("Ý nghĩa của lá bài 2 of Pentacles ?",
                "Cân bằng, Linh hoạt, Vui vẻ",
                "Sự đánh giá, Phần thưởng, Thay đổi định hướng",
                "Sung túc, Bền lâu, Tập quán/quy tắc",
                "Mạnh dạn, Lão luyện, Đáng tin cậy, Sự ủng hộ/hỗ trợ, Vững chắc",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q38);

        Question q39= new Question("Ý nghĩa của lá bài 3 of Pentacles ?",
                "Sức mạnh cảm xúc, Trực giác, Thân mật, Tình yêu",
                "Làm việc nhóm, Lập kế hoạch, Thành tựu/xuất sắc",
                "Siêng năng, Hiểu biết, Tỉ mỉ",
                "Dễ xúc động, Trực giác, Thân mật, Yêu thương",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q39);

        Question q40= new Question("Ý nghĩa của lá bài 4 of Pentacles ?",
                "Niềm vui, An bình, Gia đình",
                "Chiếm hữu, Kiểm soát, Sự thay đổi bị chặn đứng",
                "Sức mạnh cảm xúc, Trực giác, Thân mật, Tình yêu",
                "Sự đánh giá, Phần thưởng, Thay đổi định hướng",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q40);

        Question q41= new Question("Ý nghĩa của lá bài 5 of Pentacles ?",
                "Làm việc nhóm, Lập kế hoạch, Thành tựu/xuất sắc",
                "Thời điểm khó khăn, Sức khỏe kém, Bị từ chối",
                "Có sự ảnh hưởng, Thực tế, Thịnh vượng, Tin tưởng/đáng tin cậy",
                "Kiên định – Lì lợm, Thận trọng – Không mạo hiểm, Kỹ lưỡng – Ám ảnh, Thực tế – Bi quan, Cần cù – Chăm chỉ",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q41);

        Question q42= new Question("Ý nghĩa của lá bài 6 of Pentacles ?",
                "Nuôi dưỡng, Nhân cách cao cả, Tính hợp lý, Tháo vát, Đáng tin cậy",
                "Thời điểm khó khăn, Sức khỏe kém, Bị từ chối",
                "Nguồn lực, Kiến thức, Quyền năng",
                "Kỷ luật, Tự lực, Tinh tế",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q42);

        Question q43= new Question("Ý nghĩa của lá bài 7 of Pentacles ?",
                "Nguồn lực, Kiến thức, Quyền năng",
                "Kỷ luật, Tự lực, Tinh tế",
                "Sự đánh giá, Phần thưởng, Thay đổi định hướng",
                "Kiên định – Lì lợm, Thận trọng – Không mạo hiểm, Kỹ lưỡng – Ám ảnh, Thực tế – Bi quan, Cần cù – Chăm chỉ",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q43);

        Question q44= new Question("Ý nghĩa của lá bài 8 of Pentacles ?",
                "Nuôi dưỡng, Nhân cách cao cả, Tính hợp lý, Tháo vát, Đáng tin cậy",
                "Thời điểm khó khăn, Sức khỏe kém, Bị từ chối",
                "Siêng năng, Hiểu biết, Tỉ mỉ",
                "Kỷ luật, Tự lực, Tinh tế",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q44);

        Question q45= new Question("Ý nghĩa của lá bài 9 of Pentacles ?",
                "Thiện chí, Ngây thơ/khờ dại, Thời thơ ấu",
                "Mất mát, Tang thương, Hối tiếc",
                "Niềm vui, An bình, Gia đình",
                "Kỷ luật, Tự lực, Tinh tế",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q45);

        Question q46= new Question("Ý nghĩa của lá bài 10 of Pentacles ?",
                "Sức mạnh cảm xúc, Trực giác, Thân mật, Tình yêu",
                "Ước vọng, Những lựa chọn, Sự tiêu pha",
                "Chỉ biết mỗi bản thân, Sự hờ hững, Tiến vào nội tâm",
                "Sung túc, Bền lâu, Tập quán/quy tắc",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q46);

        Question q47= new Question("Ý nghĩa của lá bài King of Pentacles ?",
                "Kỷ luật, Tự lực, Tinh tế",
                "Mạnh dạn, Lão luyện, Đáng tin cậy, Sự ủng hộ/hỗ trợ, Vững chắc",
                "Chỉ biết mỗi bản thân, Sự hờ hững, Tiến vào nội tâm",
                "Sức mạnh cảm xúc, Trực giác, Thân mật, Tình yêu",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q47);

        Question q48= new Question("Ý nghĩa của lá bài Queen of Pentacles ?",
                "Nuôi dưỡng, Nhân cách cao cả, Tính hợp lý, Tháo vát, Đáng tin cậy",
                "Ước vọng, Những lựa chọn, Sự tiêu pha",
                "Chỉ biết mỗi bản thân, Sự hờ hững, Tiến vào nội tâm",
                "Niềm vui, An bình, Gia đình",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q48);

        Question q49= new Question("Ý nghĩa của lá bài Knight of Pentacles ?",
                "Kiên định – Lì lợm, Thận trọng – Không mạo hiểm, Kỹ lưỡng – Ám ảnh, Thực tế – Bi quan, Cần cù – Chăm chỉ",
                "Thiện chí, Ngây thơ/khờ dại, Thời thơ ấu",
                "Mất mát, Tang thương, Hối tiếc",
                "Niềm vui, An bình, Gia đình",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q49);

        Question q50= new Question("Ý nghĩa của lá bài Page of Pentacles ?",
                "Có sự ảnh hưởng, Thực tế, Thịnh vượng, Tin tưởng/đáng tin cậy",
                "Ước vọng, Những lựa chọn, Sự tiêu pha",
                "Ý nghĩa sâu thẳm – chân lý, Tiến tới, Mệt mỏi",
                "Chỉ biết mỗi bản thân, Sự hờ hững, Tiến vào nội tâm",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfPentacles);
        insertQuestion(q50);
        //End Pentacles

        Question q51= new Question("Ý nghĩa của lá bài Ace of Swords ?",
                "Sử dụng lý trí, Thật thà, Công bằng, Kiên cường",
                "Hạn chế, Rối loạn, Mất quyền lực",
                "Tự lợi, Bất hoà, Mất danh dự một cách công khai",
                "Sức mạnh trí óc, Sự thật, Công lý, Kiên cường",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q51);

        Question q52= new Question("Ý nghĩa của lá bài 2 of Swords ?",
                "Ngăn chặn cảm xúc, Sự tránh xa, Sự bế tắc",
                " Chạy trốn, Thích phong cách đơn độc, Nỗi hổ thẹn giấu kín",
                "Thông minh, Biết phân tích, Ăn nói lưu loát, Công bằng, Đạo đức",
                "Thẳng thắn – lỗ mãng, Có thẩm quyền – hống hách, Sắc bén – cắt giảm, Am hiểu – ngoan cố, Lý trí – nhẫn tâm",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q52);

        Question q53= new Question("Ý nghĩa của lá bài 3 of Swords ?",
                "Chạm xuống đáy, Tâm lý bị hại, Chịu khổ nhục",
                "Đau khổ, Cô đơn, Phản bội",
                "Siêng năng, Hiểu biết, Tỉ mỉ",
                "Dễ xúc động, Trực giác, Thân mật, Yêu thương",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q53);

        Question q54= new Question("Ý nghĩa của lá bài 4 of Swords ?",
                "Chạy trốn, Thích phong cách đơn độc, Nỗi hổ thẹn giấu kín",
                " Nghỉ ngơi, Suy tính, Chuẩn bị trong lặng lẽ",
                "Thẳng thắn – lỗ mãng, Có thẩm quyền – hống hách, Sắc bén – cắt giảm, Am hiểu – ngoan cố, Lý trí – nhẫn tâm",
                "Sử dụng lý trí, Thật thà, Công bằng, Kiên cường",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q54);

        Question q55= new Question("Ý nghĩa của lá bài 5 of Swords ?",
                "Sức mạnh trí óc, Sự thật, Công lý, Kiên cường",
                "Tự lợi, Bất hoà, Mất danh dự một cách công khai",
                "Ngăn chặn cảm xúc, Sự tránh xa, Sự bế tắc",
                "Buồn tẻ, Phục hồi, Di chuyển",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q55);

        Question q56= new Question("Ý nghĩa của lá bài 6 of Swords ?",
                "Nuôi dưỡng, Nhân cách cao cả, Tính hợp lý, Tháo vát, Đáng tin cậy",
                "Thời điểm khó khăn, Sức khỏe kém, Bị từ chối",
                "Buồn tẻ, Phục hồi, Di chuyển",
                "Kỷ luật, Tự lực, Tinh tế",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q56);

        Question q57= new Question("Ý nghĩa của lá bài 7 of Swords ?",
                "Chân thật, Sắc sảo, Thẳng thắn, Vui tính, Từng trải",
                "Đau khổ, Cô đơn, Phản bội",
                "Chạy trốn, Thích phong cách đơn độc, Nỗi hổ thẹn giấu kín",
                "Thẳng thắn – lỗ mãng, Có thẩm quyền – hống hách, Sắc bén – cắt giảm, Am hiểu – ngoan cố, Lý trí – nhẫn tâm",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q57);

        Question q58= new Question("Ý nghĩa của lá bài 8 of Swords ?",
                "Chạy trốn, Thích phong cách đơn độc, Nỗi hổ thẹn giấu kín",
                "Thẳng thắn – lỗ mãng, Có thẩm quyền – hống hách, Sắc bén – cắt giảm, Am hiểu – ngoan cố, Lý trí – nhẫn tâm",
                "Hạn chế, Rối loạn, Mất quyền lực",
                "Chạm xuống đáy, Tâm lý bị hại, Chịu khổ nhục",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q58);

        Question q59= new Question("Ý nghĩa của lá bài 9 of Swords ?",
                "Nuôi dưỡng, Nhân cách cao cả, Tính hợp lý, Tháo vát, Đáng tin cậy",
                "Thời điểm khó khăn, Sức khỏe kém, Bị từ chối",
                "Buồn tẻ, Phục hồi, Di chuyển",
                "Lo lắng, Cảm giác tội lỗi, Nỗi thống khổ",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q59);

        Question q60= new Question("Ý nghĩa của lá bài 10 of Swords ?",
                "Sử dụng lý trí, Thật thà, Công bằng, Kiên cường",
                "Chân thật, Sắc sảo, Thẳng thắn, Vui tính, Từng trải",
                "Thông minh, Biết phân tích, Ăn nói lưu loát, Công bằng, Đạo đức",
                "Chạm xuống đáy, Tâm lý bị hại, Chịu khổ nhục",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q60);

        Question q61= new Question("Ý nghĩa của lá bài King of Swords ?",
                "Sức mạnh trí óc, Sự thật, Công lý, Kiên cường",
                "Thông minh, Biết phân tích, Ăn nói lưu loát, Công bằng, Đạo đức",
                "Ngăn chặn cảm xúc, Sự tránh xa, Sự bế tắc",
                "Đau khổ, Cô đơn, Phản bội",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q61);

        Question q62= new Question("Ý nghĩa của lá bài Queen of Swords ?",
                "Chân thật, Sắc sảo, Thẳng thắn, Vui tính, Từng trải",
                "Thời điểm khó khăn, Sức khỏe kém, Bị từ chối",
                "Buồn tẻ, Phục hồi, Di chuyển",
                "Lo lắng, Cảm giác tội lỗi, Nỗi thống khổ",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q62);

        Question q63= new Question("Ý nghĩa của lá bài Knight of Swords ?",
                "Thẳng thắn – lỗ mãng, Có thẩm quyền – hống hách, Sắc bén – cắt giảm, Am hiểu – ngoan cố, Lý trí – nhẫn tâm",
                "Thiện chí, Ngây thơ/khờ dại, Thời thơ ấu",
                "Mất mát, Tang thương, Hối tiếc",
                "Niềm vui, An bình, Gia đình",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q63);

        Question q64= new Question("Ý nghĩa của lá bài Page of Swords ?",
                "Sử dụng lý trí, Thật thà, Công bằng, Kiên cường",
                "Hạn chế, Rối loạn, Mất quyền lực",
                "Buồn tẻ, Phục hồi, Di chuyển",
                "Đau khổ, Cô đơn, Phản bội",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfSwords);
        insertQuestion(q64);

        //End Swords
        Question q65= new Question("Ý nghĩa của lá bài Ace of WANDS ?",
                "Quyến rũ – Khô khan Tự tin – Tự mãn Dũng cảm – LIều lĩnh Mạo hiểm – Lo lắng Nồng nhiệt – Nóng nảy",
                "Phòng vệ Kiên nhẫn Chịu đựng",
                "Ăn mừng Tự do Niềm phấn khích",
                "Sáng tạo, Nhiệt tình, Tự tin, Can đảm",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q65);

        Question q66= new Question("Ý nghĩa của lá bài 2 of WANDS ?",
                "Sức mạnh bản thân, Lòng can đảm, Tính độc đáo/nguyên bản",
                "Hành động nhanh Kết thúc / dứt điểm Tin tức",
                "Ăn mừng Tự do Niềm phấn khích",
                "Quá sức Gánh nặng Cam chịu",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q66);

        Question q67= new Question("Ý nghĩa của lá bài 3 of WANDS ?",
                "Ca khúc khải hoàn Tôn vinh Niềm kiêu hãnh",
                "Khám phá Thấy trước Tài lãnh đạo",
                "Năng nổ Chống cự Chắc chắn",
                "Bất đồng Đấu tranh Phiền nhiễu",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q67);

        Question q68= new Question("Ý nghĩa của lá bài 4 of WANDS ?",
                "Bất đồng Đấu tranh Phiền nhiễu",
                "Ăn mừng Tự do Niềm phấn khích",
                "Sáng tạo Truyền cảm hứng Mạnh mẽ Có sức lôi cuốn Táo bạo",
                "Hấp dẫn Toàn tâm Nhiệt huyết Vui vẻ Tự tin",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q68);

        Question q69= new Question("Ý nghĩa của lá bài 5 of WANDS ?",
                "Quá sức Gánh nặng Cam chịu",
                "Bất đồng Đấu tranh Phiền nhiễu",
                "Sức mạnh bản thân, Lòng can đảm, Tính độc đáo/nguyên bản",
                "Khám phá Thấy trước Tài lãnh đạo",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q69);

        Question q70= new Question("Ý nghĩa của lá bài 6 of WANDS ?",
                "Quá sức Gánh nặng Cam chịu",
                "Phòng vệ Kiên nhẫn Chịu đựng",
                "Ca khúc khải hoàn Tôn vinh Niềm kiêu hãnh",
                "Thẳng thắn – lỗ mãng, Có thẩm quyền – hống hách, Sắc bén – cắt giảm, Am hiểu – ngoan cố, Lý trí – nhẫn tâm\"",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q70);

        Question q71= new Question("Ý nghĩa của lá bài 7 of WANDS ?",
                "Hành động nhanh Kết thúc / dứt điểm Tin tức",
                "Sáng tạo, Nhiệt tình, Tự tin, Can đảm",
                "Năng nổ Chống cự Chắc chắn",
                "Bất đồng Đấu tranh Phiền nhiễu",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q71);

        Question q72= new Question("Ý nghĩa của lá bài 8 of WANDS ?",
                "Ca khúc khải hoàn Tôn vinh Niềm kiêu hãnh",
                "Thẳng thắn – lỗ mãng, Có thẩm quyền – hống hách, Sắc bén – cắt giảm, Am hiểu – ngoan cố, Lý trí – nhẫn tâm",
                "Hành động nhanh Kết thúc / dứt điểm Tin tức",
                "Năng nổ Chống cự Chắc chắn",
                3,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q72);

        Question q73= new Question("Ý nghĩa của lá bài 9 of WANDS ?",
                "Hành động nhanh Kết thúc / dứt điểm Tin tức",
                "Ăn mừng Tự do Niềm phấn khích",
                "Quá sức Gánh nặng Cam chịu",
                "Phòng vệ Kiên nhẫn Chịu đựng",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q73);

        Question q74= new Question("Ý nghĩa của lá bài 10 of WANDS ?",
                "Bất đồng Đấu tranh Phiền nhiễu",
                "Hành động nhanh Kết thúc / dứt điểm Tin tức",
                "Ăn mừng Tự do Niềm phấn khích",
                "Quá sức Gánh nặng Cam chịu",
                4,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q74);

        Question q75= new Question("Ý nghĩa của lá bài King of WANDS ?",
                "Quá sức Gánh nặng Cam chịu",
                "Sáng tạo Truyền cảm hứng Mạnh mẽ Có sức lôi cuốn Táo bạo",
                "Phòng vệ Kiên nhẫn Chịu đựng",
                "Ăn mừng Tự do Niềm phấn khích",
                2,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q75);

        Question q76= new Question("Ý nghĩa của lá bài Queen of WANDS ?",
                "Hấp dẫn Toàn tâm Nhiệt huyết Vui vẻ Tự tin",
                "Sức mạnh bản thân, Lòng can đảm, Tính độc đáo/nguyên bản",
                "Năng nổ Chống cự Chắc chắn",
                "Quá sức Gánh nặng Cam chịu",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q76);

        Question q77= new Question("Ý nghĩa của lá bài Knight of WANDS ?",
                "Quyến rũ – Khô khan Tự tin – Tự mãn Dũng cảm – LIều lĩnh Mạo hiểm – Lo lắng Nồng nhiệt – Nóng nảy",
                "Phòng vệ Kiên nhẫn Chịu đựng",
                "Hành động nhanh Kết thúc / dứt điểm Tin tức",
                "Ca khúc khải hoàn Tôn vinh Niềm kiêu hãnh",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q77);

        Question q78= new Question("Ý nghĩa của lá bài Page of WANDS ?",
                "Sáng tạo Nhiệt huyết Tự tin Can đảm",
                "Quá sức Gánh nặng Cam chịu",
                "Sức mạnh bản thân, Lòng can đảm, Tính độc đáo/nguyên bản",
                "Khám phá Thấy trước Tài lãnh đạo",
                1,MapData.arrCardImage_R_Id[0], Category.SuitOfWands);
        insertQuestion(q78);
        // End Wands
    }


    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionTable.COLUMN_URI, question.getUri());
        cv.put(QuestionTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        //cv.put(QuestionTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuizContract.CategoriesTable.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(QuizContract.CategoriesTable._ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(QuizContract.CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestion() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndex(QuestionTable._ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION4)));

                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                //question.setDifficulty(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String selection = QuestionTable.COLUMN_CATEGORY_ID + "= ? " ;
                //+ " AND " + QuestionTable.COLUMN_DIFFICULTY + " = ?";

        String[] selectionArgs = new String[] {String.valueOf(categoryID), difficulty};

        Cursor cursor = db.query(QuestionTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,null,null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndex(QuestionTable._ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                //question.setDifficulty(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String selection = QuestionTable.COLUMN_CATEGORY_ID + "= ? " ;
        //+ " AND " + QuestionTable.COLUMN_DIFFICULTY + " = ?";
        String rawquery = "SELECT * FROM " +QuestionTable.TABLE_NAME +
                " WHERE "
                + QuestionTable.COLUMN_CATEGORY_ID + "= ? ";

        String[] selectionArgs = new String[] {String.valueOf(categoryID)};
        Cursor cursor = db.query(QuestionTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,null,null);

        //Cursor cursor = db.rawQuery(rawquery,selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndex(QuestionTable._ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                question.setUri(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_URI)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                question.setCategoryID(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }

}