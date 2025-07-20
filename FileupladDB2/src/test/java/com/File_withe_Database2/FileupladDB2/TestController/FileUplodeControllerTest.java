package com.File_withe_Database2.FileupladDB2.TestController;


//import com.File_withe_Database2.FileupladDB2.Controller.FileUploadController;
//import com.File_withe_Database2.FileupladDB2.Service.ImageService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//
////import static org.mockito.ArgumentMatchers.any;
////import static org.mockito.Mockito.doThrow;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.awaitility.Awaitility.given;
import static  org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
////import static org.springframework.test.web.servlet.result.MockMvcRequestBuilders.status;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import com.File_withe_Database2.FileupladDB2.Controller.FileUploadController;
import com.File_withe_Database2.FileupladDB2.Model.Uploade_File;
import com.File_withe_Database2.FileupladDB2.Service.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileUploadController.class)
public class FileUplodeControllerTest {

    @Autowired
    private MockMvc mockMvc;  // retrn the fack http request wite out starting application

    @MockBean
    private ImageService imageService;


    @Test
    public  void TestshowForm() throws Exception {
                mockMvc.perform(get("/upload"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("upload-form"));
    }





        @Test
        void uploadSuccess() throws Exception {                                                             //content
            var file = new MockMultipartFile("file", "a.jpg", "image/jpeg", "123".getBytes());

            mockMvc.perform(multipart("/upload")
                            .file(file)) // one fake file to uplode the server
                    .andExpect(content().string("sucess"));
        }

        @Test
        void uploadFail() throws Exception {
            var file = new MockMultipartFile("file", "a.jpg", "image/jpeg", "123".getBytes());
            doThrow(new RuntimeException()).when(imageService).saveImage(any());
            mockMvc.perform(multipart("/upload").file(file))
                    .andExpect(content().string("error"));
            }

            @Test
            void view_allRecord_SholudReturnHtmlviewWitheModel() throws Exception {

                 List<Uploade_File> mockData=List.of(new Uploade_File());

                 when(imageService.getAllRecords()).thenReturn(mockData);

                 mockMvc.perform(get("/All_record"))

                         .andExpect(status().isOk())

                         .andExpect(view().name("viewAllRecord"))

                         .andExpect(model().attributeExists("images"));

            }
            @Test
            void  testviewImge_SholudReturnimgViewAndModel() throws Exception {

                   Long filedId=1L;
                   Uploade_File file=new Uploade_File();
                   file.setId(filedId);
                   file.setFile_name("test.jpg");

                   when(imageService.getFileById(filedId)).thenReturn(file);

                   mockMvc.perform(get("/images/view/" + filedId))
                           .andExpect(status().isOk())
                           .andExpect(view().name("view_image"))
                           .andExpect(model().attributeExists("image"));

                   verify(imageService).getFileById(filedId);


            }

            @Test
            void testdeleteimage() throws Exception {
                Long filedId=1L;
                mockMvc.perform(get("/images/delete/" + filedId))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/All_record"));

                verify(imageService).deleteImageById(filedId);


            }

            @Test
            void  showupdateForm_success() throws  Exception
            {

                Uploade_File mockImge=new Uploade_File();

                mockImge.setId(1L);

                mockImge.setFile_name("test.jpg");

                given(imageService.getFileById(1L)).willReturn(mockImge);

                mockMvc.perform(get("/images/update/1"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("update"))
                        .andExpect(model().attributeExists("image"));

            }

            @Test
            void  showupdateForm_redirectNotFound() throws  Exception
            {

                    given(imageService.getFileById(1L)).willThrow(new RuntimeException("File not found"));

                    mockMvc.perform(get("/images/update/1"))

                            .andExpect(status().is3xxRedirection())

                            .andExpect(redirectedUrl("/All_record"));


            }









}
