package com.example.hp.gap.APIinterface;


import com.example.hp.gap.APIresponse.ChangeClassCode_Response;
import com.example.hp.gap.APIresponse.ChangePasswordResponse;
import com.example.hp.gap.APIresponse.ClassStudentListResponse;
import com.example.hp.gap.APIresponse.ClassTeacherListResponse;
import com.example.hp.gap.APIresponse.CreateClassResponse;
import com.example.hp.gap.APIresponse.DeleteClassResponse;
import com.example.hp.gap.APIresponse.FileListAllListResponse;
import com.example.hp.gap.APIresponse.ForgotPasswordResponse;
import com.example.hp.gap.APIresponse.JoinClassResponse;
import com.example.hp.gap.APIresponse.LoginResponse;
import com.example.hp.gap.APIresponse.MessageFileListResponse;
import com.example.hp.gap.APIresponse.MessageSendedResponse;
import com.example.hp.gap.APIresponse.RegistrationUserResponse;
import com.example.hp.gap.APIresponse.ServerImageResponse;
import com.example.hp.gap.APIresponse.SetToDoListResponse;
import com.example.hp.gap.APIresponse.StudentEnrollClass;
import com.example.hp.gap.APIresponse.StudentListResponse;
import com.example.hp.gap.APIresponse.TeacherListResponse;
import com.example.hp.gap.APIresponse.TeacherMessageList;
import com.example.hp.gap.APIresponse.ToDoListListResponse;
import com.example.hp.gap.adapterFiles.StudentMessageReceiveResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface APIinterface {
    @FormUrlEncoded
    @POST("loginControllerJson.php")
    Call<LoginResponse> LOGIN_RESPONSE_CALL(
            @Field("getLogin") String getLogin,
            @Field("u_email") String email,
            @Field("u_password") String password);


   /* @FormUrlEncoded
    @POST("loginControllerJsonArray.php")
    Call<LoginListResponse> GET_LOGIN_RESPONSE_CALL(
            @Field("getLogin") String getLogin);*/

    @FormUrlEncoded
    @POST("userControllerJson.php")
    Call<RegistrationUserResponse> SET_USER_RESPONSE_CALL(
            @Field("setUser") String setUser,
            @Field("u_name") String uname,
            @Field("u_email") String uemail,
            @Field("u_password") String upassword);


    @FormUrlEncoded
    @POST("classroomControllerJson.php")
    Call<TeacherListResponse> GET_CLASSROOM_RESPONSE_CALL(
            @Field("getTeacherClass") String getTeacherClass,
            @Field("u_id") int userId);


    @FormUrlEncoded
    @POST("joinclassroomControllerJson.php")
    Call<StudentListResponse> GET_JOINCLASSROOM_RESPONSE_CALL(
            @Field("getStudentClass") String getStudentClass,
            @Field("u_id") int userId);


    @FormUrlEncoded
    @POST("joinclassroomControllerJson.php")
    Call<JoinClassResponse> SET_JOINCLASSROOM_RESPONSE_CALL(
            @Field("joinClass") String joinClass,
            @Field("c_code") String c_code,
            @Field("u_id") int userId);


    @FormUrlEncoded
    @POST("classroomControllerJson.php")
    Call<CreateClassResponse> SET_CLASSROOM_RESPONSE_CALL(
            @Field("createClass") String createClass,
            @Field("u_id") int userId,
            @Field("c_name") String c_name,
            @Field("c_subject") String c_subject);

    @FormUrlEncoded
    @POST("joinclassroomControllerJson.php")
    Call<StudentEnrollClass> ENROLL_JOINCLASSROOM_RESPONSE_CALL(
            @Field("deleteJoinClass") String deleteJoinClass,
            @Field("u_id") int userId,
            @Field("c_id") int c_id);


    @FormUrlEncoded
    @POST("classroomControllerJson.php")
    Call<DeleteClassResponse> DELETE_CLASSROOM_RESPONSE_CALL(
            @Field("deleteClass") String deleteClass,
            @Field("c_id") int c_id);

    @FormUrlEncoded
    @POST("message_masterControllerJson.php")
    Call<TeacherMessageList> GET_MESSAGE_LIST_RESPONSE_CALL(
            @Field("getMessageList") String getMessageList,
            @Field("c_id") int c_id,
            @Field("u_id") int u_id);

    @Multipart
    @POST("imageupload.php")
    Call<ServerImageResponse> uploadFileImage(@Part MultipartBody.Part file1);

    @Multipart
    @POST("pdfupload.php")
    Call<ServerImageResponse> uploadFilePdf(@Part MultipartBody.Part file3);

    @Multipart
    @POST("profilepic_imageupload.php")
    Call<ServerImageResponse> uploadProfileFileImage(@Part MultipartBody.Part file1, @Part("u_id") RequestBody id);

    @Multipart
    @POST("videoupload.php")
    Call<ServerImageResponse> uploadFileVideo(@Part MultipartBody.Part file2);

    @FormUrlEncoded
    @POST("fileControllerJson.php")
    Call<MessageFileListResponse> MESSAGE_FILE_LIST_RESPONSE_CALL(
            @Field("getFile") String getFile,
            @Field("message_master_id") int message_master_id);


    @FormUrlEncoded
    @POST("joinclassroomControllerJson.php")
    Call<ClassStudentListResponse> GET_STUDENT_LIST_RESPONSE_CALL(
            @Field("getClassStudent") String getClassStudent,
            @Field("c_id") int c_id);


    @FormUrlEncoded
    @POST("classroomControllerJson.php")
    Call<ClassTeacherListResponse> GET_TEACHER_LIST_RESPONSE_CALL(
            @Field("getClassTeacher") String getClassTeacher,
            @Field("c_id") int c_id);


    @FormUrlEncoded
    @POST("sendMail.php")
    Call<ForgotPasswordResponse> GET_FORGOT_PASSWORD_RESPONSE_CALL(
            @Field("setForgotPasssword") String setForgotPasssword,
            @Field("u_email") String u_email);


    @FormUrlEncoded
    @POST("loginControllerJson.php")
    Call<ChangePasswordResponse> GET_CHANGE_PASSWORD_RESPONSE_CALL(
            @Field("setChangePassword") String setChangePassword,
            @Field("u_id") int u_id,
            @Field("u_password") String u_password);


    @FormUrlEncoded
    @POST("message_masterControllerJson.php")
    Call<MessageSendedResponse> SET_MESSAGE_RESPONSE_CALL(
            @Field("setMessageList") String setMessageList,
            @Field("message_title") String message_title,
            @Field("c_id") int c_id,
            @Field("u_id") int u_id);


    @FormUrlEncoded
    @POST("message_receiver_masterController.php")
    Call<StudentMessageReceiveResponse> SET_STUDENT_RECEIVER_RESPONSE_CALL(
            @Field("setMessageReceiver") String setMessageReceiver,
            @Field("string") String string);

    @FormUrlEncoded
    @POST("linkUpload.php")
    Call<ServerImageResponse> SET_LINK_RESPONSE_CALL(
            @Field("setLink") String setLink,
            @Field("link") String link);


    @FormUrlEncoded
    @POST("fileControllerJson.php")
    Call<FileListAllListResponse> GET_FILE_LIST_ALL_RESPONSE_CALL(
            @Field("getFileList") String getFileList,
            @Field("c_id") int c_id,
            @Field("u_id") int u_id);


    @FormUrlEncoded
    @POST("notificationControllerJson.php")
    Call<ChangePasswordResponse> SET_FCM(
            @Field("setfcm") String setfcm,
            @Field("fcm_token") String fcm_token,
            @Field("u_id") int u_id);


    @FormUrlEncoded
    @POST("classroomControllerJson.php")
    Call<ChangeClassCode_Response> SET_CLASSCODE_RESPONSE_CALL(
            @Field("setChangeCode") String setChangeCode,
            @Field("c_id") int c_id);



    @FormUrlEncoded
    @POST("todo_listControllerJson.php")
    Call<SetToDoListResponse> SET_LIST(
            @Field("settodo_list") String settodo_list,
            @Field("u_id") int u_id,
            @Field("list_name") String list_name);


    @FormUrlEncoded
    @POST("todo_listControllerJson.php")
    Call<ToDoListListResponse> GET_LIST(
            @Field("gettodo_list") String gettodo_list,
            @Field("u_id") int u_id);



    @FormUrlEncoded
    @POST("todo_listControllerJson.php")
    Call<DeleteClassResponse> DELETE_LIST(
            @Field("deleteItem") String deleteItem,
            @Field("todo_list_id") int todo_list_id);



    @FormUrlEncoded
    @POST("userControllerJson.php")
    Call<ChangePasswordResponse> UPDATE_PROFILE(
            @Field("setChangeProfile") String setChangeProfile,
            @Field("u_name") String u_name,
            @Field("u_email") String u_email,
            @Field("u_id") int u_id);



}
